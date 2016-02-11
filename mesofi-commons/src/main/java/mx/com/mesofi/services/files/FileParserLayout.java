/*
 * COPYRIGHT. Mesofi 2010. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.services.files;

import static mx.com.mesofi.services.files.FileUtil.close;
import static mx.com.mesofi.services.util.SimpleCommonActions.isAnnotated;
import static mx.com.mesofi.services.util.SimpleValidator.isNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import mx.com.mesofi.services.files.annotation.Layout;
import mx.com.mesofi.services.files.annotation.LayoutPosition;
import mx.com.mesofi.services.files.config.FileLayoutConfig;
import mx.com.mesofi.services.files.exception.FileAccessException;
import mx.com.mesofi.services.files.exception.FileReaderParserException;
import mx.com.mesofi.services.files.exception.FileWriterParserException;

import org.apache.log4j.Logger;

/**
 * Clase que procesa los diferentes parseos para leer o escribir layouts basados
 * en la configuracion.
 * 
 * @author mesofi
 * 
 */
public class FileParserLayout {
    /**
     * log4j.
     */
    private Logger logger = Logger.getLogger(FileParserLayout.class);
    /**
     * Referencia al objeto para lectura y escritura de archivos.
     */
    private FileProcessor fileProcessor = new FileProcessor();
    /**
     * Configuracion del layout.
     */
    private FileLayoutConfig config;

    /**
     * Crea un objeto para parsear los layouts correspondientes.
     * 
     * @param config
     *            Configuracion del layout.
     */
    public FileParserLayout(final FileLayoutConfig config) {
        isNull(config, "Debe especificar una referencia valida "
                + "para la configuracion del layout");
        this.config = config;
    }

    /**
     * Lee el archivo layout para ser procesado en una lista.
     * 
     * @param <T>
     *            Tipo de dato.
     * @param clazz
     *            Clase asociada al layout.
     * @param fileHolder
     *            Referencia al fileholder en donde se encuentra el layout.
     * @return Lista de registros.
     */
    public <T> List<T> read(final Class<T> clazz, final FileHolder fileHolder) {
        isNull(clazz, "Debe especificar una clase en la cual se "
                + "puedan almacenar los datos recibidos por el layout");
        isNull(fileHolder, "Debe especificar una referencia "
                + "hacia donde leer el archivo de origen");

        List<T> list = null;
        if (isAnnotated(clazz.getAnnotations(), Layout.class)) {
            // existe la anotacion, entonces puede iniciar el parseo
            list = processReaderLayout(clazz, fileHolder.getFileHolder());
        } else {
            throw new FileReaderParserException(
                    "La clase especificada debe estar anotada por: "
                            + Layout.class.getName()
                            + ", esto con la finalidad de parsear como un objeto de layout.");
        }
        return list;
    }

    /**
     * Procesa el layout que sera leido.
     * 
     * @param <T>
     *            Tipo de dato.
     * @param clazz
     *            Clase que esta asociada al layout que sera leido.
     * @param file
     *            Archivo en el cual apunta la direccion del layout.
     * @return Lista de registros leidos a partir del layout.
     */
    private <T> List<T> processReaderLayout(final Class<T> clazz,
            final File file) {
        List<T> list = new ArrayList<T>();
        BufferedReader br = null;
        try {
            br = fileProcessor.readPlainContentByLine(file);
            String strLine = null;
            T t = null;
            // lee el archivo linea por linea
            while ((strLine = br.readLine()) != null) {
                t = fromLineToObject(clazz, strLine);
                list.add(t);
            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            throw new FileReaderParserException(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new FileReaderParserException(e.getMessage());
        } finally {
            close(br);
        }
        return list;
    }

    /**
     * Metodo que regresa un objeto que es compatible con alguna linea parseada
     * del layout.
     * 
     * @param <T>
     *            Tipo de dato.
     * @param clazz
     *            Clase que contiene los campos que seran asignados.
     * @param lineRead
     *            Linea que fue leida del layout.
     * @return Objeto que es simetrica a la linea leida por el layout.
     */
    private <T> T fromLineToObject(final Class<T> clazz, final String lineRead) {
        T t = null;
        String[] token = null;
        try {
            if (config.getSeparator() == null) {
                // no tiene un caracter de separador, entonces forzosamente
                // existe un conjunto de caracteres por posiciones.
                token = splitStringByPositions(config.getPositions(), lineRead);
                isNull(token, "No se pudo dividir la cadena: \"" + lineRead
                        + "\" con los siguientes delimitadores: \""
                        + config.getPositions() + "\"");

            } else {
                // existe un caracter de separador.
                token = lineRead.split(config.getSeparator());
                isNull(token, "No se pudo dividir la cadena: \"" + lineRead
                        + "\" con el delimitador dado por: \""
                        + config.getSeparator() + "\"");
            }
            t = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            Annotation ann = null;
            String valueToAssign = null;
            Object objectValue = null;
            int position = -1;

            // itera los campos que estan contenidos en la clase.
            for (Field field : fields) {
                ann = field.getAnnotation(LayoutPosition.class);
                if (ann instanceof LayoutPosition) {
                    LayoutPosition annotation = (LayoutPosition) ann;
                    position = annotation.position();
                    // valida la posicion.
                    if (position < 1 || position > token.length) {
                        throw new FileReaderParserException(
                                "La posicion del layout en la clase \""
                                        + clazz.getName()
                                        + "\" debe ser mayor o igual a 1 y menor o igual a "
                                        + token.length);
                    }
                    valueToAssign = token[annotation.position() - 1];
                    // pone accesible a los campos aunque sean privados.
                    field.setAccessible(true);
                    objectValue = ensureCompatibility(field.getType(),
                            valueToAssign);
                    field.set(t, objectValue); // asigna el valor
                }
            }
        } catch (Throwable exception) {
            throw new FileReaderParserException(exception.getMessage());
        }
        return t;
    }

    /**
     * Metodo que obtiene un arreglo de caracteres que fueron separados por
     * posiciones.
     * 
     * @param positions
     *            Arreglo que contiene las posiciones en las que se tienen que
     *            dividir cada linea.
     * @param lineRead
     *            Linea leida.
     * @return Arreglo de cadenas.
     */
    private String[] splitStringByPositions(final int[] positions,
            final String lineRead) {
        String[] strings = new String[positions.length];
        int pos = 0;
        int i = 0;
        int totales = 0;

        for (int j = 0; j < positions.length; j++) {
            totales += positions[j];
        }

        if (totales > lineRead.length()) {
            throw new FileReaderParserException(
                    "La longitud de la linea a parsear no puede ser "
                            + "menor a las posiciones para dividir");
        }

        for (int position : positions) {
            // cuando es cero, se lo salta (los omite)
            if (position > 0) {
                strings[i] = lineRead.substring(pos, pos + position);
                pos += position;
                i++;
            }
        }
        return strings;
    }

    /**
     * Metodo que asigna los diferentes tipos de datos hacia las propiedades de
     * los beans que seran parseados.
     * 
     * @param type
     *            Tipo de dato del campo al cual sera asignado.
     * @param valueToAssign
     *            Valor que sera asignado a las diferentes propiedades.
     * @return Objeto que sera asiggnado.
     */
    private Object ensureCompatibility(final Class<?> type,
            final String valueToAssign) {
        Object object = null;
        String javaType = type.getName();

        try {
            if (javaType.equals(String.class.getName())) {
                // intenta convertir el valor en string.
                object = valueToAssign;
            }
            if (javaType.equals(Boolean.TYPE.getName())
                    || javaType.equals(Boolean.class.getName())) {
                // intenta convertir el valor en boolean.
                object = Boolean.parseBoolean(valueToAssign);
            }
            if (javaType.equals(Byte.TYPE.getName())
                    || javaType.equals(Byte.class.getName())) {
                // intenta convertir el valor en byte.
                object = Byte.parseByte(valueToAssign);
            }
            if (javaType.equals(Short.TYPE.getName())
                    || javaType.equals(Short.class.getName())) {
                // intenta convertir el valor en Short.
                object = Short.parseShort(valueToAssign);
            }
            if (javaType.equals(Integer.TYPE.getName())
                    || javaType.equals(Integer.class.getName())) {
                // intenta convertir el valor en entero.
                object = Integer.parseInt(valueToAssign);
            }
            if (javaType.equals(Long.TYPE.getName())
                    || javaType.equals(Long.class.getName())) {
                // intenta convertir el valor en Long.
                object = Long.parseLong(valueToAssign);
            }
            if (javaType.equals(Float.TYPE.getName())
                    || javaType.equals(Float.class.getName())) {
                // intenta convertir el valor en Float.
                object = Float.parseFloat(valueToAssign);
            }
            if (javaType.equals(Double.TYPE.getName())
                    || javaType.equals(Double.class.getName())) {
                // intenta convertir el valor en Double.
                object = Double.parseDouble(valueToAssign);
            }
            if (javaType.equals(Date.class.getName())) {
                // intenta convertir a una fecha
                config.getDateFormat().setLenient(false);
                if (valueToAssign == null) {
                    object = null;
                } else {
                    object = config.getDateFormat().parse(valueToAssign);
                }
            }
        } catch (ParseException exception) {
            throw new FileReaderParserException(
                    "No se puede transformar la fecha: \""
                            + valueToAssign
                            + "\" debido a un formato incorrecto, verifique por favor");
        } catch (Exception exception) {
            throw new FileReaderParserException(
                    "No se puede asignar el valor: \"" + valueToAssign
                            + "\" a una variable de tipo: " + javaType);
        }
        return object;
    }

    /**
     * Metodo que escribe en una archivo el contenido del layout proveniente de
     * una lista de elementos.
     * 
     * @param <T>
     *            Tipo de dato.
     * @param t
     *            Lista que contiene elementos de los cuales seran escritos.
     * @param fileHolder
     *            Referencia al archivo en donde se escribira.
     */
    public <T> void write(final List<T> t, final FileHolder fileHolder) {
        isNull(fileHolder, "Debe especificar una referencia valida "
                + "para almacenar el archivo de salida");

        isNull(t, "Debe especificar una lista de elementos valida "
                + "para almacenar en el archivo de salida");
        String lineToWrite = "";
        int pos = 0;
        try {
            for (T element : t) {
                lineToWrite = fromObjectToLine(element.getClass(), element);
                fileProcessor.writePlainContent(fileHolder, lineToWrite, true);
                if (pos != t.size() - 1) {
                    fileProcessor.writePlainContent(fileHolder, config
                            .getBreakLine(), true);
                }
                pos++;
            }
        } catch (FileAccessException exception) {
            throw new FileWriterParserException(exception.getMessage());
        }
    }

    /**
     * Metodo que convierte el valor de las propiedasdes de un objeto a una
     * linea o una cadena que puede ser manipulada despues.
     * 
     * @param <T>
     *            Tipo de dato.
     * @param clazz
     *            Clase que contiene a los miembros.
     * @param nodeList
     *            Objeto que contiene a los mimebros.
     * @return Linea con los valores procesados.
     */
    private <T> String fromObjectToLine(final Class<?> clazz, final T nodeList) {
        String lineToWrite = "";
        try {
            if (config.getSeparator() == null) {
                // no tiene un caracter de separador, entonces forzosamente
                // existe un conjunto de caracteres por posiciones.
                lineToWrite = processWriterByPositions(clazz, nodeList);
            } else {
                // existe un caracter de separador.
                lineToWrite = processWriterBySeparators(clazz, nodeList);
            }
        } catch (Throwable exception) {
            throw new FileWriterParserException(exception.getMessage());
        }
        return lineToWrite;
    }

    /**
     * Procesa una linea por separadores la regresa.
     * 
     * @param <T>
     *            Tipo de Dato.
     * @param clazz
     *            Clase del objeto del cual se van a extraer los datos.
     * @param nodeList
     *            Objeto que contiene los miembros del cual se van a extraer los
     *            datos.
     * @return Linea procesada.
     * @throws Exception
     *             En caso de que ocurra una excepcion.
     */
    private <T> String processWriterBySeparators(final Class<?> clazz,
            final T nodeList) throws Exception {
        Object valueToAssign = null;
        StringBuffer sb = new StringBuffer();
        Map<Integer, String> map = new TreeMap<Integer, String>();

        Field[] fields = clazz.getDeclaredFields();
        Annotation ann = null;
        int position = -1;
        // itera los campos que estan contenidos en la clase.
        for (Field field : fields) {
            ann = field.getAnnotation(LayoutPosition.class);
            if (ann instanceof LayoutPosition) {
                LayoutPosition annotation = (LayoutPosition) ann;
                position = annotation.position();
                // valida la posicion.
                if (position < 1) {
                    throw new FileWriterParserException(
                            "La posicion del layout en la clase \""
                                    + clazz.getName()
                                    + "\" debe ser mayor o igual a 1");
                }
                field.setAccessible(true);
                valueToAssign = field.get(nodeList);
                if (valueToAssign == null) {
                    valueToAssign = "";
                }
                if (valueToAssign.getClass().isAssignableFrom(Date.class)) {
                    // puede que la fecha este formateada.
                    valueToAssign = config.getDateFormat()
                            .format(valueToAssign);
                }
                map.put(position, valueToAssign.toString()
                        + config.getSeparator());

            }
        }
        if (!map.isEmpty()) {
            for (Integer integer : map.keySet()) {
                sb.append(map.get(integer));
            }
            sb.replace(sb.toString().lastIndexOf(config.getSeparator()), sb
                    .length(), "");
        }
        return sb.toString();
    }

    /**
     * Procesa una linea por posiciones y la regresa.
     * 
     * @param <T>
     *            Tipo de Dato.
     * @param clazz
     *            Clase del objeto del cual se van a extraer los datos.
     * @param nodeList
     *            Objeto que contiene los miembros del cual se van a extraer los
     *            datos.
     * @return Linea procesada.
     * @throws Exception
     *             En caso de que ocurra una excepcion.
     */
    private <T> String processWriterByPositions(final Class<?> clazz,
            final T nodeList) throws Exception {
        int[] token = null;
        StringBuffer sb = new StringBuffer();
        Object valueToAssign = null;
        token = adjustTokenPositions(config.getPositions());
        int i = 0;
        Field[] fields = clazz.getDeclaredFields();

        Annotation ann = null;
        int position = -1;
        // itera los campos que estan contenidos en la clase.
        for (Field field : fields) {
            ann = field.getAnnotation(LayoutPosition.class);
            if (ann instanceof LayoutPosition) {
                LayoutPosition annotation = (LayoutPosition) ann;
                position = annotation.position();
                // valida la posicion.
                if (position < 1 || position > token.length) {
                    throw new FileWriterParserException(
                            "La posicion del layout en la clase \""
                                    + clazz.getName()
                                    + "\" debe ser mayor o igual a 1 y menor o igual a "
                                    + token.length);
                }
                field.setAccessible(true);
                valueToAssign = field.get(nodeList);
                if (valueToAssign == null) {
                    valueToAssign = "";
                }
                if (valueToAssign.getClass().isAssignableFrom(Date.class)) {
                    // puede que la fecha este formateada.
                    valueToAssign = config.getDateFormat()
                            .format(valueToAssign);
                }
                // no tiene un caracter de separador, entonces
                // forzosamente
                // existe un conjunto de caracteres por posiciones.
                if (valueToAssign.toString().length() > token[i]) {
                    valueToAssign = valueToAssign.toString().substring(0,
                            token[i]);
                }
                sb.append(valueToAssign.toString().substring(0, token[i]));
                i++;
            }
        }
        return sb.toString();
    }

    /**
     * Metodo que procesa un arreglo de enteros, y si algun elemento es igual a
     * cero, entonces lo omite en el arreglo final. Si el arreglo pasado como
     * argumento tiene elementos mayores a cero, entonces no sufre ninguna
     * modificacion.
     * 
     * @param positions
     *            Arreglo de posisiciones a ser procesado.
     * @return Arreglo con posiciones mayores a cero.
     */
    private int[] adjustTokenPositions(int[] positions) {
        List<Integer> list = new ArrayList<Integer>();
        int[] array = null;
        for (int i : positions) {
            if (i > 0) {
                list.add(i);
            }
        }
        if (list.size() == 0) {
            array = positions;
        } else {
            array = new int[list.size()];
            for (int i = 0; i < array.length; i++) {
                array[i] = list.get(i);
            }
        }
        return array;
    }

}
