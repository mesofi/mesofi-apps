/*
 * COPYRIGHT. Mesofi 2009. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.services.util;

import static mx.com.mesofi.services.util.CommonConstants.DASH;
import static mx.com.mesofi.services.util.CommonConstants.UNDER_SCORE;
import static mx.com.mesofi.services.util.SimpleValidator.isEmpty;
import static mx.com.mesofi.services.util.SimpleValidator.isNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contiene metodos utilitarios para ser usados bajo culaquier entorno. Son
 * utilerias completamente genericas.
 * 
 * @author mesofi
 */
public final class SimpleCommonActions {

    /**
     * Prefijo para los metodos set.
     */
    public static final String PREFIX_SETTER = "set";
    /**
     * Prefijo para los metodos get.
     */
    public static final String PREFIX_GETTER = "get";
    /**
     * Prefijo para los metodos get.
     */
    public static final String PREFIX_IS = "is";
    /**
     * Separador para los nombre de etiquetas de xml.
     */
    public static final String XML_SEPARATOR = DASH.toString();

    /**
     * Constructor privado.
     */
    private SimpleCommonActions() {
    }

    /**
     * Obtiene un numero aleatorio que se encuentra desde un intervalo menor
     * hasta un numero maximo.
     * 
     * @param min
     *            Numero minimo en el que se puede encontrar un numero
     *            aleatorio.
     * @param max
     *            Numero maximo en el que se puede encontrar un numero
     *            aleatorio.
     * @return Numero aleatorio en un intervalo de min a max.
     */
    public static int randomNumber(final int min, final int max) {
        return min + (new Random()).nextInt(max - min);
    }

    /**
     * Obtiene un numero aleatorio que se encuentra desde 0 hasta un numero
     * concreto.
     * 
     * @param max
     *            Numero maximo en el que se puede encontrar un numero
     *            aleatorio.
     * @return Numero aleatorio en un intervalo de 0 a max.
     */
    public static int randomNumber(final int max) {
        return randomNumber(0, max);
    }

    /**
     * Obtiene el metodo setter de una javabean a partir de el nombre de una
     * propiedad contenida en la clase especificada en los parametros.
     * 
     * @param <T>
     *            Tipo de dato.
     * @param clazz
     *            Clase que contiene la propiedad y el metodo.
     * @param field
     *            Nombre valido para la propiedad.
     * @see #getMethodFromField(String, Class, String)
     * @return Metodo setter a partir del campo.
     */
    public static <T> Method getSetterMethodFromField(final Class<T> clazz, final String field) {
        return getMethodFromField(PREFIX_SETTER, clazz, field);
    }

    /**
     * Obtiene el metodo getter de una javabean a partir de el nombre de una
     * propiedad contenida en la clase especificada en los parametros.
     * 
     * @param <T>
     *            Tipo de dato.
     * @param clazz
     *            Clase que contiene la propiedad y el metodo.
     * @param field
     *            Nombre valido para la propiedad.
     * @see #getMethodFromField(String, Class, String)
     * @return Metodo getter a partir del campo.
     */
    public static <T> Method getGetterMethodFromField(final Class<T> clazz, final String field) {
        return getMethodFromField(PREFIX_GETTER, clazz, field);
    }

    /**
     * Obtiene el metodo setter de una javabean a partir de la referencia hacia
     * el nombre de una propiedad contenida en la clase especificada en los
     * parametros.
     * 
     * @param <T>
     *            Tipo de dato.
     * @param clazz
     *            Clase que contiene la propiedad y el metodo.
     * @param field
     *            Referencia valida para la propiedad.
     * @see #getMethodFromField(String, Class, Field)
     * @return Referencia al metodo setter o null si no existe.
     */
    public static <T> Method getSetterMethodFromField(final Class<T> clazz, final Field field) {
        return getMethodFromField(PREFIX_SETTER, clazz, field);
    }

    /**
     * Obtiene el metodo getter de una javabean a partir de la referencia hacia
     * el nombre de una propiedad contenida en la clase especificada en los
     * parametros.
     * 
     * @param <T>
     *            Tipo de dato.
     * @param clazz
     *            Clase que contiene la propiedad y el metodo.
     * @param field
     *            Referencia valida para la propiedad.
     * @see #getMethodFromField(String, Class, Field)
     * @return Referencia al metodo getter o null si no existe.
     */
    public static <T> Method getGetterMethodFromField(final Class<T> clazz, final Field field) {
        return getMethodFromField(PREFIX_GETTER, clazz, field);
    }

    /**
     * Obtiene el metodo getter/setter o is de una javabean a partir de la
     * referencia hacia el nombre de una propiedad contenida en la clase
     * especificada en los parametros.
     * 
     * @param <T>
     *            Tipo de dato.
     * @param prefixMethod
     *            Prefijo del metodo, regularmente este valor puede ser "get",
     *            "set" o "is".
     * @param clazz
     *            Clase que contiene la propiedad y el metodo.
     * @param field
     *            Referencia valida para la propiedad.
     * @see #getMethodFromField(String, Class, String)
     * @return Referencia al metodo que se desea buscar o null si no existe.
     */
    public static <T> Method getMethodFromField(final String prefixMethod, final Class<T> clazz, final Field field) {
        isEmpty(prefixMethod, "Debe especificar un prefijo para el metodo get o set.");
        isNull(clazz, "La clase no debe estar null");
        isNull(field, "El campo no debe estar null");
        return getMethodFromField(prefixMethod, clazz, field.getName());
    }

    /**
     * Obtiene el metodo getter/setter o is de una javabean a partir de la
     * referencia hacia el nombre de una propiedad contenida en la clase
     * especificada en los parametros.
     * 
     * @param <T>
     *            Tipo de dato.
     * @param prefixMethod
     *            Prefijo del metodo, regularmente este valor puede ser "get",
     *            "set" o "is".
     * @param clazz
     *            Clase que contiene la propiedad y el metodo.
     * @param field
     *            Nombre o descripcion del campo.
     * @see #getMethodFromField(String, Class, Field)
     * @return Referencia al metodo que se desea buscar o null si no existe.
     */
    public static <T> Method getMethodFromField(final String prefixMethod, final Class<T> clazz, final String field) {
        Method finalMethod = null;
        isEmpty(prefixMethod, "Debe especificar un prefijo para el metodo get o set.");
        isNull(clazz, "La clase no debe estar null");
        isNull(field, "El campo no debe estar null");
        StringBuffer sb = null;
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().startsWith(prefixMethod)) {
                sb = new StringBuffer();
                sb.append(prefixMethod);
                sb.append(Character.toUpperCase(field.charAt(0)));
                sb.append(field.substring(1));
                if (sb.toString().equals(method.getName())) {
                    finalMethod = method;
                    break;
                }
            }
        }
        return finalMethod;
    }

    /**
     * Obtiene el campo que es correspondiente al estandar de los JavaBean, esto
     * es, a partir del nombre del metodo, obtiene la propiedad con la cual fue
     * creado dicho metodo. Forzosamente se deben de cumplir las reglas de
     * JavaBeans. Este metodo no es aplicable para miembros que son heredados,
     * solo aplica para metodos (y por lo tanto campos) que estan declarados
     * dentro de la misma clase.
     * 
     * @param <T>
     *            Tipo de Objeto.
     * @param clazz
     *            Clase que contiene tanto al metodo como a la propiedad.
     * @param method
     *            Metodo contenido dentro de la clase.
     * @return Campo compatible con el metodo get-set o null si no hay
     *         compatibilidad.
     */
    public static <T> Field getFieldFromMethod(final Class<T> clazz, final Method method) {
        Field field = null;
        if (method != null) {
            // prueba que el metodo este contenido dentro de la clase
            // correspondiente.
            boolean estaContenido = false;
            for (Method m : clazz.getMethods()) {
                if (m.equals(method)) {
                    estaContenido = true;
                    break;
                }
            }
            if (estaContenido) {
                String getterMethodPrefix = PREFIX_GETTER;
                String setterMethodPrefix = PREFIX_SETTER;
                String nameMethod = method.getName();

                // se asegura que tenga prefijos correctos de JavaBeans
                if (nameMethod.startsWith(getterMethodPrefix) || nameMethod.startsWith(setterMethodPrefix)) {

                    nameMethod = nameMethod.substring(3);
                    char ch = Character.toLowerCase(nameMethod.charAt(0));
                    String propertyName = ch + nameMethod.substring(1);

                    for (Field f : clazz.getDeclaredFields()) {
                        if (f.getName().equals(propertyName)) {
                            field = f;
                            break;
                        }
                    }
                }
            }
        }
        return field;
    }

    /**
     * Metodo que prueba si una clase esta anotada por cierto tipo de anotacion.
     * 
     * @param <T>
     *            Tipo de clase.
     * @param annotations
     *            Notaciones pertenencientes a ya sea metodos, campos o clases o
     *            cualquier elemento que pueda ser anotado.
     * @param clazz
     *            Clase de la anotacion por la que se esta buscando.
     * @return true, existe la anotacion, en otro caso no existe.
     */
    public static <T> boolean isAnnotated(final Annotation[] annotations, final Class<T> clazz) {
        boolean isAnnotated = false;
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().isAssignableFrom(clazz)) {
                isAnnotated = true;
                break;
            }
        }
        return isAnnotated;
    }

    /**
     * Metodo que obtiene una referencia a una clase que pertenece a un elemento
     * de cualquier lista.
     * 
     * @param type
     *            Tipo del elemento.
     * @return Clase del elemento de la lista.
     */
    public static Class<?> getClassFromListElement(final Type type) {
        Class<?> class1 = null;
        if (type instanceof ParameterizedType) {
            ParameterizedType typeParameterizedType = (ParameterizedType) type;
            Type[] typeArguments = typeParameterizedType.getActualTypeArguments();
            for (Type typeArgument : typeArguments) {
                class1 = (Class<?>) typeArgument;
                break;
            }
        }
        return class1;
    }

    /**
     * Metodo que transforma una cadena de caracteres a una representacion de la
     * misma pero en formato de estilo de nombre de JavaBean. Si el valor a
     * convertir es null, entonces no hace nada, simplemente regresa null. Por
     * ejemplo, una cadena denominada como "web-app", se convertiria como
     * "webApp" o "WebApp". El delimitador estaria dado por el caracter "-".
     * 
     * @param name
     *            Cadena que sera transformada en formato de JavaBean.
     * @param isFirstLetterUpper
     *            Indica si la primer letra de la cadena transformada es
     *            mayuscula o minuscula.
     * @see #fromJavaBeanToXmlStyle(String)
     * @return Cadena transformada en formato JavaBean.
     */
    public static String fromXmlToJavaBeanStyle(final String name, final boolean isFirstLetterUpper) {
        String beanName = null;
        if (name != null) {
            StringBuffer sb = new StringBuffer();
            StringTokenizer tokenizer = new StringTokenizer(name, XML_SEPARATOR);
            String token = null;
            boolean isFirstToken = true;
            char ch = ' ';
            if (tokenizer.countTokens() > 1) {
                while (tokenizer.hasMoreTokens()) {
                    token = tokenizer.nextToken();
                    if (isFirstToken) {
                        sb.append(token.toLowerCase());
                        isFirstToken = false;
                    } else if (token.length() >= 1) {
                        ch = Character.toUpperCase(token.charAt(0));
                        sb.append(ch);
                        sb.append(token.substring(1));
                    }
                }
            } else {
                sb.append(name);
            }
            if (sb.length() >= 1) {
                if (isFirstLetterUpper) {
                    ch = Character.toUpperCase(sb.charAt(0));
                    sb.replace(0, 1, ch + "");
                }
            }
            beanName = sb.toString();
        }
        return beanName;
    }

    /**
     * Metodo que transforma una cadena de caracteres a una representacion de la
     * misma pero en formato de estilo de xml. Si el valor a convertir es null,
     * entonces no hace nada, simplemente regresa null. Por ejemplo, una cadena
     * denominada como "webApp" o "WebApp", se convertiria como "web-app". El
     * delimitador estaria dado por el caracter "-".
     * 
     * @param name
     *            Cadena que sera transformada en formato de XML.
     * @see #fromXmlToJavaBeanStyle(String, boolean)
     * @return Cadena transformada en formato XML.
     */
    public static String fromJavaBeanToXmlStyle(final String name) {
        String xmlName = null;

        // separa la cadena
        if (name != null) {
            StringBuffer sb = new StringBuffer();
            sb.append(name);
            // averigua que exista al menos un caracter, si es asi lo hace
            // minuscula
            if (sb.length() >= 1 && Character.isUpperCase(sb.charAt(0))) {
                sb.replace(0, 1, Character.toLowerCase(sb.charAt(0)) + "");
            }
            findCamelCaseStyle(sb);
            xmlName = sb.toString();
        }
        return xmlName;
    }

    /**
     * Transforma un valor que esta en null a una cadena vacia.
     * 
     * @param value
     *            Valor a ser transformado en caso de que sea null.
     * @return Si el valor es null, entonces devuelve una cadena vacia, de lo
     *         contrario, devuelve la misma cadena.
     */
    public static String fromNullToCustomValue(final String value) {
        return fromNullToCustomValue(value, "");
    }

    /**
     * Transforma un valor en formato de cadena a un formato de numero entero.
     * Si este valor es null, entonces se asigna un valor por defecto.
     * 
     * @param value
     *            Valor que sera transformado.
     * @param defaultValue
     *            Numero por defecto a tomar en caso de que el valor sea null.
     * @return Valor trasnformado.
     */
    public static int fromNullToCustomValue(final String value, final int defaultValue) {
        int real = 0;
        if (value != null) {
            try {
                real = Integer.parseInt(value);
            } catch (NumberFormatException pa) {
                // silencia esta excepcion.
            }
        }
        return real;
    }

    /**
     * Transforma un valor en formato de cadena a un formato booleano. Si este
     * valor es null, entonces se asigna un valor por defecto.
     * 
     * @param value
     *            Valor que sera transformado.
     * @param defaultValue
     *            Boolean por defecto a tomar en caso de que el valor sea null.
     * @return Valor trasnformado.
     */
    public static boolean fromNullToCustomValue(final String value, final boolean defaultValue) {
        boolean real = false;
        if (value != null) {
            real = Boolean.parseBoolean(value);
        }
        return real;
    }

    /**
     * Transforma un valor en formato de cadena a un formato de numero con punto
     * decimal. Si este valor es null, entonces se asigna un valor por defecto.
     * 
     * @param value
     *            Valor que sera transformado.
     * @param defaultValue
     *            Numero por defecto a tomar en caso de que el valor sea null.
     * @return Valor trasnformado.
     */
    public static float fromNullToCustomValue(final String value, final float defaultValue) {
        float real = 0.0f;
        if (value != null) {
            try {
                real = Float.parseFloat(value);
            } catch (NumberFormatException pa) {
                // silencia esta excepcion.
            }
        }
        return real;
    }

    /**
     * Metodo que devuelve un valor personalizado en caso de que el valor pasado
     * sea null.
     * 
     * @param value
     *            Valor que sera probado.
     * @param defaultValue
     *            Valor por defecto que sera regresado en caso de el valor a
     *            probar sea null.
     * @return Si el valor es null, entonces devuelve un valor personalizado, de
     *         lo contrario, devuelve la misma cadena.
     */
    public static String fromNullToCustomValue(final String value, final String defaultValue) {
        return value == null ? defaultValue : value;
    }

    /**
     * En caso de que la fecha proporcionada sea null, entonces toma el valor de
     * la fecha que pasa por defecto, de otra forma, regresa la fecha original.
     * 
     * @param date
     *            Fecha a probar.
     * @param defaultDate
     *            Fecha por defecto.
     * @return Fecha valida por regresar.
     */
    public static Date fromNullToCustomValue(final Date date, final Date defaultDate) {
        return (date == null) ? defaultDate : date;
    }

    /**
     * Metodo que elimina los espacios existentes de la cadena especificada como
     * parametro. Si la cadena es null, entonces regresa el mismo valor. Si la
     * cadena no contiene espacios, entonces esta no se altera y regresa la
     * misma.
     * 
     * @param string
     *            Cadena a la cual se le tiene que provar.
     * @return Cadena sin espacios.
     */
    public static String removeSpaces(final String string) {
        String newString = null;
        if (string != null) {
            newString = string.replaceAll(CommonConstants.WHITE_SPACE.toString(), "");
        }
        return newString;
    }

    /**
     * Metodo que elimina y reemplaza los espacios existentes de la cadena
     * especificada como parametro. Si la cadena es null, entonces regresa el
     * mismo valor. Si la cadena no contiene espacios, entonces esta no se
     * altera y regresa la misma.
     * 
     * @param string
     *            Cadena a la cual se le tiene que provar.
     * @param replacement
     *            The replacement given.
     * @return Cadena sin espacios.
     */
    public static String removeAndReplaceSpaces(final String string, final String replacement) {
        String newString = null;
        if (string != null) {
            newString = string.replaceAll(CommonConstants.WHITE_SPACE.toString(), replacement);
        }
        return newString;
    }

    /**
     * Obtiene el nombre de la propiedad a partir del nombre de una metodo ya
     * sea getter o setter, esto tambien incluye los metodos "is". Si las
     * condiciones para el estilo camello no se cumple, entonces regresa la
     * misma cadena sin ninguna alteracion.
     * 
     * @param methodName
     *            Nombre del metodo getter, setter o is.
     */
    public static String getPropertyNameFromGetterSetterMethods(final String methodName) {
        isNull(methodName, "Debe especificar un nombre de metodo getter o setter no null");
        String propertyName = methodName;
        if (methodName.startsWith(PREFIX_GETTER) || methodName.startsWith(PREFIX_SETTER)) {
            propertyName = methodName.substring(PREFIX_GETTER.length());
        } else if (methodName.startsWith(PREFIX_IS)) {
            propertyName = methodName.substring(PREFIX_IS.length());
        }
        if (propertyName.length() >= 1 && Character.isUpperCase(propertyName.charAt(0))) {
            char ch = Character.toLowerCase(propertyName.charAt(0));
            propertyName = ch + propertyName.substring(1);
        } else {
            propertyName = methodName;
        }
        return propertyName;
    }

    /**
     * Metodo que indica si un nombre valido de clase esta compuesto o no. Se
     * dice que esta compuesto cuando es un tipo definido de clase por el
     * usuario. Cuando no esta compuesto es que es una clase representando a un
     * primitivo o su correspondiente en objeto. El objeto Date tambien es
     * considerado no compuesto.
     * 
     * @param className
     *            Nombre de la clase.
     * @return true, esta compuesto o false de otra forma.
     */
    public static boolean isSimpleClassName(final String className) {
        isNull(className, "Debe especificar un nombre de clase valido");
        boolean isClassNameSingleValue = false;

        if (className.equals(Date.class.getName())) {
            isClassNameSingleValue = true;
        } else if (className.equals(String.class.getName())) {
            isClassNameSingleValue = true;
        } else if (className.equals(Boolean.TYPE.getName()) || className.equals(Boolean.class.getName())) {
            isClassNameSingleValue = true;
        } else if (className.equals(Byte.TYPE.getName()) || className.equals(Byte.class.getName())) {
            isClassNameSingleValue = true;
        } else if (className.equals(Short.TYPE.getName()) || className.equals(Short.class.getName())) {
            isClassNameSingleValue = true;
        } else if (className.equals(Integer.TYPE.getName()) || className.equals(Integer.class.getName())) {
            isClassNameSingleValue = true;
        } else if (className.equals(Long.TYPE.getName()) || className.equals(Long.class.getName())) {
            isClassNameSingleValue = true;
        } else if (className.equals(Float.TYPE.getName()) || className.equals(Float.class.getName())) {
            isClassNameSingleValue = true;
        } else if (className.equals(Double.TYPE.getName()) || className.equals(Double.class.getName())) {
            isClassNameSingleValue = true;
        }
        return isClassNameSingleValue;
    }

    /**
     * Metodo que obtiene una reference del tipo Date que contiene el tiempo al
     * finalizar el dia de cierta fecha. Es decir, obtiene una fecha antes de
     * cumplir el siguiente dia.
     * 
     * @param startInitDate
     *            Fecha de inicio.
     * @return Fecha antes de pasar al siguiente dia, por ejemplo si
     *         startInitDate = 11/10/2009 entonces este metodo regresa
     *         11/10/2009 un milisegundo antes de pasar al siguiente dia. Si la
     *         fecha de inicio es null, entonces no sucede nada y regresa la
     *         fecha original.
     */
    public static Date getFullDatePerDay(final Date startInitDate) {
        Date finalDate = null;
        if (startInitDate != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(startInitDate);

            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(Calendar.YEAR, c.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, c.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
            calendar.add(Calendar.MILLISECOND, -1);
            finalDate = calendar.getTime();
        }
        return finalDate;
    }

    /**
     * Metodo que concatena al final otra cadena de caracteres y el resultado es
     * la cadena concatenada.
     * 
     * @param suffixString
     *            Cadena que sera agregada al final.
     * @param string
     *            Cadena original a la cual se le agregara la cadena
     *            concatenada.
     * @return Cadena concatenada.
     */
    public static String ensureLastStringWith(final String suffixString, final String string) {
        isNull(string, "Debe especificar una cadena valida para completarse");
        String finalString = null;
        if (string != null) {
            String tempString = string;
            tempString = tempString.trim();
            finalString = tempString;
            if (!tempString.endsWith(suffixString)) {
                finalString = tempString + fromNullToCustomValue(suffixString);
            }
        }
        return finalString;
    }

    /**
     * Metodo que devuelve una cadena de caracteres dada una lista de numeros
     * enteros.
     * 
     * @param list
     *            Lista de numeros enteros.
     * @param ch
     *            Caracter que sera el separador de la cadena final.
     * @return Cadena de caracteres separada por un separador pasado como
     *         parametro.
     */
    public static String getStringToken(List<Integer> list, char ch) {
        String finalString = "";
        if (list != null) {
            for (Integer i : list) {
                finalString += (i + "" + ch);
            }
            finalString = finalString.substring(0, finalString.length() - 1);
        }
        return finalString;
    }

    /**
     * Metodo que devuelve una cadena de caracteres dada una lista de numeros
     * enteros.
     * 
     * @param list
     *            Lista de enteros que sera procesada.
     * @return Cadena de caracteres separados por una coma.
     */
    public static String getStringToken(List<Integer> list) {
        return getStringToken(list, CommonConstants.COMMA.toString().charAt(0));
    }

    /**
     * This method converts any {@link String} in format <code>SOME_NAME</code>
     * into a new {@link String} in Java format for a method.
     * 
     * @param erFormat
     *            ER format.
     * @return The same string converted. For example, for the following
     *         {@link String} <code>SOME_SIMPLE_STRING</code> is converted into
     *         <code>someSimpleString</code>
     */
    public static String fromERFormatToMethodFormat(final String erFormat) {
        String classFormat = fromFormatToClassFormat(erFormat, UNDER_SCORE.toString(), false);
        return upperOrLowerFirstChar(classFormat, false);
    }

    /**
     * This method converts any {@link String} in format <code>someName</code>
     * into a new {@link String} in ER format.
     * 
     * @param methodFormat
     *            method format.
     * @return The same string converted. For example, for the following
     *         {@link String} <code>someSimpleString</code> is converted into
     *         <code>SOME_SIMPLE_STRING</code>
     */
    public static String fromMethodFormatToERFormat(final String methodFormat) {
        SimpleValidator.hasLength(methodFormat);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < methodFormat.length(); i++) {
            if (i == 0) {
                // first character
                if (Character.isUpperCase(methodFormat.charAt(i))) {
                    sb.append(methodFormat);
                    break;
                }
                sb.append(Character.toUpperCase(methodFormat.charAt(i)));
            } else {
                if (Character.isUpperCase(methodFormat.charAt(i))) {
                    sb.append(CommonConstants.UNDER_SCORE);
                    sb.append(methodFormat.charAt(i));
                } else {
                    sb.append(Character.toUpperCase(methodFormat.charAt(i)));
                }
            }
        }
        return sb.toString();
    }

    /**
     * Metodo que hace un reemplazo de la primer Mayuscula que encuentra por un
     * caracter denotado por "-". Por ejemplo, para la palabra "MiArchivo", el
     * resultado es "mi-archivo". O para la palabra "miVariableCompleja", el
     * resultado es "mi-variable-compleja".
     * 
     * @param sb
     *            StringBuffer a modificar.
     * @return StringBuffer modificado.
     */
    private static StringBuffer findCamelCaseStyle(final StringBuffer sb) {
        int posicion = -1;
        String replacement = null;
        String pattern = "([A-Z])+";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(sb);
        if (m.find()) {
            // encontro una coincidencia.
            posicion = m.start();
            replacement = XML_SEPARATOR + Character.toLowerCase(m.group().charAt(0));
            sb.replace(posicion, posicion + 1, replacement);
            findCamelCaseStyle(sb);
        }
        return sb;
    }

    /**
     * This method converts any {@link String} in format <code>SOME_NAME</code>
     * into a new {@link String} in Java format for a class.
     * 
     * @param erFormat
     *            ER format.
     * @return The same string converted. For example, for the following
     *         {@link String} <code>SOME_SIMPLE_STRING</code> is converted into
     *         <code>SomeSimpleString</code>
     */
    public static String fromERFormatToBeanFormat(final String erFormat) {
        return fromFormatToClassFormat(erFormat, UNDER_SCORE.toString(), false);
    }

    /**
     * This method converts any {@link String} in format <code>some-name</code>
     * into a new {@link String} in Java format for a class.
     * 
     * @param urlFormat
     *            ER format.
     * @return The same string converted. For example, for the following
     *         {@link String} <code>some-simple-string</code> is converted into
     *         <code>SomeSimpleString</code>
     */
    public static String fromUrlFormatToBeanFormat(final String urlFormat) {
        return fromFormatToClassFormat(urlFormat, DASH.toString(), true);
    }

    /**
     * This method converts any {@link String} in format <code>some-name</code>
     * into a new {@link String} in method format for a class.
     * 
     * @param urlFormat
     *            ER format.
     * @return The same string converted. For example, for the following
     *         {@link String} <code>some-simple-string</code> is converted into
     *         <code>someSimpleString</code>
     */
    public static String fromUrlFormatToMethodFormat(final String urlFormat) {
        String classFormat = fromFormatToClassFormat(urlFormat, DASH.toString(), true);
        return upperOrLowerFirstChar(classFormat, false);
    }

    /**
     * Gets a valid class name given an String expression.
     * 
     * @param possibleClassName
     *            Possible class name.
     * @param suffix
     *            The suffix expression to be used in the final class name. If
     *            this parameter is not provided, then it's ignored.
     * @return A valid class name using a possible suffix .
     */
    public static String getStandardClassName(String possibleClassName, String suffix) {
        isEmpty(possibleClassName, "Cannot get the class name from an invalid expression");
        StringBuilder sb = new StringBuilder();
        sb.append(possibleClassName.substring(0, 1).toUpperCase());
        sb.append(possibleClassName.substring(1));
        if (suffix != null && suffix.trim().length() > 0) {
            sb.append(suffix.substring(0, 1).toUpperCase());
            sb.append(suffix.substring(1));
        }
        return sb.toString();
    }

    /**
     * This method returns the fully qualified name of a certain class passed in
     * the arguments. If the className is Date, then it returns the
     * corresponding type for <code>java.util.Date</code>. When the className
     * contains ".", then this method assumes that the fully name is already
     * there, so there is no modification.
     * 
     * @param className
     *            The className.
     * @return The fully qualified name.
     */
    public static String getQualifiedClassName(final String className) {
        isEmpty(className, "Cannot get the qualified class name because class name is empty");
        String fullClassName = null;
        if (className.contains(".")) {
            fullClassName = className;
            return fullClassName;
        } else if (className.equals("Date")) {
            fullClassName = Date.class.getCanonicalName();
        } else {
            fullClassName = "java.lang." + className;
        }
        return fullClassName;
    }

    /**
     * Makes the first letter to upper case or lower case. Whenever exists a
     * letter in the first position, makes the transformation.
     * 
     * @param sb
     *            String builder.
     * @param isUpper
     *            In case transforms the data in upper case, otherwise in lower
     *            case.
     */
    private static String upperOrLowerFirstChar(String sb, boolean isUpper) {
        if (sb.trim().length() > 1) {
            if (isUpper) {
                return sb.substring(0, 1).toUpperCase() + sb.substring(1);
            } else {
                return sb.substring(0, 1).toLowerCase() + sb.substring(1);
            }
        }
        return sb;
    }

    /**
     * Performs the transformation from some string to another taking as a base
     * some delimiter.
     * 
     * @param someFormat
     *            Some String.
     * @param delimiter
     *            delimiter of the String.
     * @param keepOriginalFormat
     *            if this parameters is set to false, then the original string
     *            is converted to lower case.
     * @return Transformed string.
     */
    private static String fromFormatToClassFormat(String someFormat, String delimiter, boolean keepOriginalFormat) {
        StringBuilder sb = new StringBuilder();
        if (!keepOriginalFormat) {
            someFormat = someFormat.toLowerCase();
        }
        String[] parts = someFormat.split(delimiter);
        for (String part : parts) {
            sb.append(upperOrLowerFirstChar(part, true));
        }
        return sb.toString();
    }

    /**
     * Converts a plain string into a list of values.
     * 
     * @param plainValues
     *            The formatted string in the following format: ('a','b','c')
     * @return A list containing the elements in the arg. e.g. [a,b,c]
     * @see SimpleCommonActions#deserializeList(List)
     */
    public static List<String> serializeString(String plainValues) {
        List<String> values = null;
        if (plainValues != null) {
            String msg = "Invalid format for a given string: [" + plainValues + "], e.g. ('a','b')";
            SimpleValidator.isValid(!(plainValues.length() < 4), msg);

            // removes the two first and last characters
            plainValues = plainValues.substring(2);
            plainValues = plainValues.substring(0, plainValues.length() - 2);
            plainValues = plainValues.replaceAll("''", "'");

            String[] validValues = plainValues.split("','");
            if (validValues.length > 0) {
                values = Arrays.asList(validValues);
            }
        }
        return values;
    }

    /**
     * Converts an existing list of values in a plain string in the following
     * format. ('a','b','c')
     * 
     * @param items
     *            A simple list.
     * @return A string using the following format: ('a','b','c')
     * @see SimpleCommonActions#serializeString(String)
     */
    public static String deserializeList(List<String> items) {
        String plainValue = null;
        if (items != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (String s : items) {
                sb.append("'");
                sb.append(s);
                sb.append("'");
                sb.append(",");
            }
            if (sb.substring(sb.length() - 1).equals(",")) {
                sb.replace(sb.length() - 1, sb.length(), ")");
            } else {
                sb.append(")");
            }
            plainValue = sb.toString();
        }
        return plainValue;
    }
}
