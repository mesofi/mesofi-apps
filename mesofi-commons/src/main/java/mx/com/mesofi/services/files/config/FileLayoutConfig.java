/*
 * COPYRIGHT. Mesofi 2010. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.services.files.config;

import static mx.com.mesofi.services.util.SimpleValidator.hasLength;
import static mx.com.mesofi.services.util.SimpleValidator.isNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import mx.com.mesofi.services.util.CommonConstants;

/**
 * Clase que maneja la configuracion del parseo de layouts separador por algun
 * delimitante.
 * 
 * @author mesofi.
 * 
 */
public class FileLayoutConfig {
    /**
     * Posiciones variables que funcionan como separadores en el layout.
     */
    private int[] positions;
    /**
     * Separador de para el layout.
     */
    private String separator;
    /**
     * Almacena los encabezados del archivo.
     */
    private String[] headers;
    /**
     * Indica si se usa o no un salto de linea definido por el sistema.
     */
    private boolean systemBreakLine = true;
    /**
     * Almacena el caracter de salto de linea.
     */
    private String breakLine = CommonConstants.BREAK_LINE_STANDARD.toString();
    /**
     * Referencia a la jsutificacion de las columnas, por defecto toma a la
     * izquierda.
     */
    private JustificationColumnsLayout columnsLayout = JustificationColumnsLayout.LEFT;
    /**
     * DateFormat para el uso de fechas en el layout.
     */
    private DateFormat dateFormat = new SimpleDateFormat(
            CommonConstants.PATTERN_DDMMYYYY.toString());

    /**
     * Construye un objeto con un separador por defecto de tabulador.
     */
    public FileLayoutConfig() {
        this(CommonConstants.TAB);
    }

    /**
     * Construye un objeto con un separador incluido en las constantes definidas
     * por defecto.
     * 
     * @param separatorConstant
     *            Constante definidas.
     */
    public FileLayoutConfig(final CommonConstants separatorConstant) {
        isNull(separatorConstant, "Debe especificar una referencia valida "
                + "para el conjunto de caracteres "
                + "que funciona como separador");
        validateAndAssignSeparator(separatorConstant.getConstant());
    }

    /**
     * Construye un objeto con un separador personalizado, este separador puede
     * ser cualquier caracter a excepcion de un valor null.
     * 
     * @param customSeparator
     *            Separador valido.
     */
    public FileLayoutConfig(final String customSeparator) {
        isNull(customSeparator, "Debe especificar un conjunto de caracteres "
                + "para separacion de datos");
        validateAndAssignSeparator(customSeparator);
    }

    /**
     * Construye un objeto con un conjunto de separadores variables, estas
     * posiciones deben ser mayor o igual a 1 en cuanto su longitud. En caso de
     * que alguna posicion sea cero, entonces estas se omiten por lo que no
     * seran tomadas en cuenta.
     * 
     * @param positions
     *            Posiciones variables.
     */
    public FileLayoutConfig(final int... positions) {
        isNull(positions, "Debe especificar un conjunto de pocisiones validas.");
        this.positions = positions;
    }

    /**
     * Define si tiene o no encabezados en el layout.
     * 
     * @param headerFileLayout
     *            Encabezados en el layout.
     */
    public void setHeaders(final String... headerFileLayout) {
        this.headers = headerFileLayout;
    }

    /**
     * Define si se utiliza un salto de linea definido por el sistema u otro
     * forzando a que sea el de Windows. Por defecto se utiliza el del sistema.
     * 
     * @param systemBreakLine
     *            the systemBreakLine to set
     */
    public void setSystemBreakLine(boolean systemBreakLine) {
        this.systemBreakLine = systemBreakLine;
    }

    /**
     * Define una alineacion en las columnas del layout. Si el valor del
     * argumento es null entonces se lanza una excepcion. En caso de no
     * definirse una alineacion entonces se toma como por defecto la de la
     * izquierda.
     * 
     * @param columnsLayout
     *            Referencia a la alineacion del layout.
     */
    public void setJustifyColumns(final JustificationColumnsLayout columnsLayout) {
        isNull(columnsLayout, "Debe especificar una alineacion "
                + "en las columnas del layout");
        this.columnsLayout = columnsLayout;
    }

    /**
     * Define el formato de fecha para este layout. Por defecto es dd/mm/yyyy.
     * 
     * @param dateFormat
     *            Formato de fecha para este layout.
     */
    public void setDateFormat(final DateFormat dateFormat) {
        isNull(dateFormat, "Debe especificar un formato para fecha valido");
        this.dateFormat = dateFormat;
    }

    /**
     * Devuelve el formato de fecha para este layout. Si no se especifica, por
     * defecto toma el dd/mm/yyyy.
     * 
     * @return the dateFormat Formato de fecha para este layout.
     */
    public DateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     * Obtiene un separador valido para el layout.
     * 
     * @return the separator
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * Si existen, obtiene las cabeceras del layout.
     * 
     * @return the headers Cabeceras o null si no existen validas.
     */
    public String[] getHeaders() {
        return headers;
    }

    /**
     * Indica si se utiliza un salto de linea definido por el sistema o uno por
     * windows. Por defecto, se utiliza la del sistema.
     * 
     * @return the systemBreakLine true, es un salto de linea del sistema.
     *         False, no lo es.
     */
    public boolean isSystemBreakLine() {
        return systemBreakLine;
    }

    /**
     * Obtiene el salto de linea, si no se especifico alguno otro, toma el del
     * sistema.
     * 
     * @return the breakLine Salto de linea.
     */
    public String getBreakLine() {
        return breakLine;
    }

    /**
     * Obtiene la justificacion de las columnas. Por defecto se toma a la
     * izquierda.
     * 
     * @return the columnsLayout Las columnas, las cuales pueden ser, izquierda,
     *         centrado o derecha.
     */
    public JustificationColumnsLayout getColumnsLayout() {
        return columnsLayout;
    }

    /**
     * Obtiene las posiciones que sirven como separadores.
     * 
     * @return the positions Posiciones, estas posiciones nunca deben de ser
     *         null.
     */
    public int[] getPositions() {
        return positions;
    }

    /**
     * Metodo que valida que el separador del layout sea valido, de ser una
     * expresion valida, entonces se asigna para alamacenarlo.
     * 
     * @param separatorString
     *            Separador del layout.
     */
    private void validateAndAssignSeparator(final String separatorString) {
        hasLength(separatorString);
        this.separator = separatorString;
    }

    /**
     * Define las posiciones en las que el layout puede estar jsutificado.
     * 
     * @author mesofi
     * 
     */
    public enum JustificationColumnsLayout {
        CENTER, LEFT, RIGHT;
    }

}
