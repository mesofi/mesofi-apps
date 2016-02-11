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

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contiene metodos utilitarios para validar ciertos tipos de datos.
 * 
 * @author mesofi
 */
public final class SimpleValidator {

    /**
     * Constructor privado para evitar crear instancias de esta clase.
     */
    private SimpleValidator() {
    }

    /**
     * Valida que una cadena tenga longitud, si no la tiene lanza una excepcion
     * en tiempo de ejecucion. Se dice que tiene longitud incluso cuando la
     * cadena tiene espacios en blanco.
     * 
     * @param cadena
     *            Cadena de caracteres a probar.
     * @see #isEmpty(String)
     */
    public static void hasLength(final String cadena) {
        hasLength(cadena, "La cadena especificada esta vacia");
    }

    /**
     * Prueba que la cadena especificada tiene una longitud valida no vacia. Se
     * dice que tiene longitud incluso cuando la cadena tiene espacios en
     * blanco.
     * 
     * @param cadena
     *            Cadena de caracteres a probar.
     * @param descripcion
     *            Descripcion del mensaje en caso de que la cadena de caracteres
     *            no tenga longitud.
     * @see #isEmpty(String, String)
     */
    public static void hasLength(final String cadena, final String descripcion) {
        if (cadena == null || cadena.length() == 0) {
            throw new IllegalArgumentException(inicializarCadena(descripcion));
        }
    }

    /**
     * Prueba que una coleccion esta vacia o no. Si esta vacia lanza una
     * excepcion con un mensaje por defecto.
     * 
     * @param collection
     *            Coleccion a probar que esta vacia.
     */
    public static void isEmpty(final Collection<?> collection) {
        isEmpty(collection, "La coleccion especificada esta vacia");
    }

    /**
     * Prueba que una coleccion esta vacia o no. Si esta vacia lanza una
     * excepcion con un mensaje especificado.
     * 
     * @param collection
     *            Coleccion a probar que esta vacia.
     * @param descripcion
     *            Descrpcion mostrada en caso de que la coleccion esta vacia.
     */
    public static void isEmpty(final Collection<?> collection, final String descripcion) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(inicializarCadena(descripcion));
        }
    }

    /**
     * Prueba que la coleccion pasada como argumento tiene elementos validos. Un
     * elemento valido es aquel que no tiene elementos null. Si tal coleccion
     * tiene elementos validos, lanza una excepcion con un mensaje especificado.
     * 
     * @param collection
     *            Coleccion valida no nula.
     */
    public static void hasElementsNotNull(final Collection<?> collection) {
        hasElementsNotNull(collection, "La coleccion a probar no debe estar vacia o null");
    }

    /**
     * Prueba que la coleccion pasada como argumento tiene elementos validos. Un
     * elemento valido es aquel que no tiene elementos null. Si tal coleccion
     * tiene elementos validos, lanza una excepcion con un mensaje especificado.
     * 
     * @param collection
     *            Coleccion valida no nula.
     * @param descripcion
     *            Descrpcion mostrada en caso de que la coleccion tiene
     *            elementos nulos.
     */
    public static void hasElementsNotNull(final Collection<?> collection, final String descripcion) {
        isEmpty(collection, descripcion);
        for (Object o : collection) {
            if (o == null) {
                throw new IllegalArgumentException(inicializarCadena(descripcion));
            }
        }
    }

    /**
     * Prueba que un objeto sea nulo o no, si es nulo, se lanza una excepcion
     * con un mensaje definido.
     * 
     * @param object
     *            Objeto a probar.
     * @param descripcion
     *            Descripcion que sera mostrada cuando se lanza la excepcion.
     */
    public static void isNull(final Object object, final String descripcion) {
        if (object == null) {
            throw new IllegalArgumentException(inicializarCadena(descripcion));
        }
    }

    /**
     * Prueba que una clase sea nula o no, si es nula, se lanza una excepcion
     * con un mensaje definido.
     * 
     * @param <T>
     *            Algun tipo.
     * @param clazz
     *            Clase a probar.
     * @param descripcion
     *            Descripcion que sera mostrada cuando se lanza la excepcion.
     */
    public static <T> void isNull(final Class<T> clazz, final String descripcion) {
        if (clazz == null) {
            throw new IllegalArgumentException(inicializarCadena(descripcion));
        }
    }

    /**
     * Prueba que una clase es nula o no, si es nulo lanza una excepcion con un
     * mensaje especifico.
     * 
     * @param <T>
     *            Algun tipo.
     * @param clazz
     *            Clase a probar.
     */
    public static <T> void isNull(final Class<T> clazz) {
        isNull(clazz, "La clase especificada no debe ser null");
    }

    /**
     * Prueba que un objeto es nulo o no, si es nulo lanza una excepcion con un
     * mensaje especifico.
     * 
     * @param object
     *            Objeto a probar.
     */
    public static void isNull(final Object object) {
        isNull(object, "El objeto especificado no debe ser null");
    }

    /**
     * Prueba que la condicion dada es valida o no, si no es valida lanza una
     * excepcion en tiempo de ejecucion.
     * 
     * @param valido
     *            Indica si la condicion es valida o no.
     */
    public static void isValid(final boolean valido) {
        isValid(valido, "");
    }

    /**
     * Prueba que la condicion dada es valida o no, si no es valida lanza una
     * excepcion en tiempo de ejecucion.
     * 
     * @param valido
     *            Indica si la condicion es valida o no.
     * @param descripcion
     *            Descripcion del error dado cuando este se presenta.
     */
    public static void isValid(final boolean valido, final String descripcion) {
        if (!valido) {
            throw new IllegalArgumentException(inicializarCadena(descripcion));
        }
    }

    /**
     * Metodo que prueba que una cadena este o no vacia. Se dice que una cadena
     * esta vacia cuando tiene un valor de null o no tiene caracteres, es vacia
     * tambien cuando existe una cadena con espacios en blaco, en este caso, se
     * considera una cadena vacia.
     * 
     * @param string
     *            Cadena que sera probada, en caso de estar vacia, se lanza una
     *            excepcion.
     * @see #isEmpty(String, String)
     * @see #hasLength(String)
     */
    public static void isEmpty(final String string) {
        isEmpty(string, "La cadena especificada esta vacia");
    }

    /**
     * Metodo que prueba que una cadena este o no vacia. Se dice que una cadena
     * esta vacia cuando tiene un valor de null o no tiene caracteres, es vacia
     * tambien cuando existe una cadena con espacios en blaco, en este caso, se
     * considera una cadena vacia. En caso de que la cadena este vacia, se lanza
     * una excepcion con un mensaje definido.
     * 
     * @param string
     *            Cadena que sera probada, en caso de estar vacia, se lanza una
     *            excepcion.
     * @param description
     *            Descripcion del mensaje de error.
     * @see #isEmpty(String)
     * @see #hasLength(String, String)
     */
    public static void isEmpty(final String string, final String description) {
        if (string == null || string.trim().length() == 0) {
            throw new IllegalArgumentException(inicializarCadena(description));
        }
    }

    /**
     * Validates the input string, if the String is null or has an empty
     * content, then returns true, otherwise false.
     * 
     * @param string
     *            String to validate.
     * @return true, if the content is null or empty, otherwise false.
     */
    public static boolean isEmptyString(final String string) {
        return string == null || string.trim().length() == 0;
    }

    /**
     * Test whether the array passed as argument has at least one element in it.
     * If this array of objects has one element or more and is not null, this
     * method returns true, otherwise false.
     * 
     * @param arr
     *            Array of objects.
     * @return true if this array is empty, otherwise false.
     */
    public static boolean isEmpty(Object... arr) {
        return !(arr != null && arr.length > 0);
    }

    /**
     * Inicializa una cadena a una cadena vacia si esta es nula, en otro caso
     * devuelve la misma cadena.
     * 
     * @param cadena
     *            Cadena que puede ser nula.
     * @return Una cadena vacia si es nula o la misma cadena pasada como
     *         argumento.
     */
    private static String inicializarCadena(final String cadena) {
        String nuevaCadena = null;
        if (cadena == null) {
            nuevaCadena = "";
        } else {
            nuevaCadena = cadena;
        }
        return nuevaCadena;
    }

    /**
     * Valida el formato de un correo electronico.
     * 
     * @param correo
     *            Cadena que contiene el correo electronico.
     * @return true, el formato es correcto o false si no lo es.
     */
    public static boolean hasEmailRightFormat(final String correo) {
        boolean rightFormat = false;
        String pattern = "[a-z]([a-z])*[@]([a-z])+[.][a-z]([.][a-z])*";
        if (correo != null && correo.trim().length() > 0) {
            if (!Character.isDigit(correo.charAt(0))) {
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(correo);
                if (m.find()) {
                    rightFormat = true;
                }
            }
        }
        return rightFormat;
    }

}
