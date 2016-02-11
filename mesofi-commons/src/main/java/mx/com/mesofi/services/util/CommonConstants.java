/*
 * COPYRIGHT. Mesofi 2010. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.services.util;

/**
 * Contiene constantes que pueden ser usadas por cualquier servicio.
 * 
 * @author mesofi.
 */
public enum CommonConstants {
    /**
     * Patron correspondiente a la fecha: yyyy-MM-dd
     */
    PATTERN_YYYYMMDD("yyyy-MM-dd"),
    /**
     * Patron correspondiente a la fecha: MM/dd/yyyy
     */
    PATTERN_MMDDYYYY("MM/dd/yyyy"),
    /**
     * Patron correspondiente a la fecha: dd/MM/yyyy
     */
    PATTERN_DDMMYYYY("dd/MM/yyyy"),
    /**
     * Espacio en blanco.
     */
    WHITE_SPACE(" "),
    /**
     * Special white space.
     */
    SPECIAL_WHITE_SPACE(" \n\r\f\t"),
    /**
     * Caracter desconocido representado por ??.
     */
    UNKNOWN_CHAR("??"),
    /**
     * Caracter slash /.
     */
    SLASH_CHAR("/"),
    /**
     * Caracter porcentaje %.
     */
    PERCENT_CHAR("%"),
    /**
     * Caracter back slash \.
     */
    BACKSLASH_CHAR("\""),
    /**
     * Caracter separador |
     */
    PIPE_CHAR("|"),
    /**
     * Caracter coma.
     */
    COMMA(","),
    /**
     * Caracter de tabulador \t.
     */
    TAB("\t"),
    /**
     * Caracter de salto de linea para el sistema (Windows o Unix) \n.
     */
    BREAK_LINE_STANDARD("\n"),
    /**
     * Caracter de salto de linea forzoso para Windows \r\n.
     */
    BREAK_LINE_WINDOWS("\r\n"),
    /**
     * Caracter de guion bajo.
     */
    UNDER_SCORE("_"),
    /**
     * Caracter de guion medio.
     */
    DASH("-");

    /**
     * Almacena la constante a usar.
     */
    private final String constant;

    /**
     * Constructor privado que tiene como parametro a la constante.
     * 
     * @param constant
     *            Valor de la constante.
     */
    private CommonConstants(final String constant) {
        SimpleValidator.isNull(constant, "Especifique una constante valida");
        this.constant = constant;
    }

    /**
     * Obtiene la representacion de la contante en forma de cadena para su
     * manipulacion.
     * 
     * @return the constant
     */
    public String getConstant() {
        return constant;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getConstant();
    }

}
