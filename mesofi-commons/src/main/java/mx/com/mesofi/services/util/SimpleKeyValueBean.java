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
 * Clase que tiene una estructura como el estandard de JavaBeans, este JavaBean
 * contiene solamente dos propiedades, la llave y el valor. Puede ser utilizado
 * por clases que solamente hagan uso similar al comportamiento de un mapa.
 * 
 * @author mesofi
 */
public class SimpleKeyValueBean {

    /**
     * Almacena la clave.
     */
    private String keyLabel;
    /**
     * Almacena el valor.
     */
    private String valueLabel;

    /**
     * Construye un objeto de este tipo sin argumentos.
     */
    public SimpleKeyValueBean() {
        // constructor sin argumentos.
    }

    /**
     * Construye un objeto de este tipo pasandole como argumentos la clave y el
     * valor.
     * 
     * @param keyLabel
     *            Clave.
     * @param valueLabel
     *            Valor.
     */
    public SimpleKeyValueBean(final String keyLabel, final String valueLabel) {
        this.keyLabel = keyLabel;
        this.valueLabel = valueLabel;
    }

    /**
     * Obtiene el valor de la llave.
     * 
     * @return Valor de la llave.
     */
    public String getKeyLabel() {
        return keyLabel;
    }

    /**
     * Asigna el valor de la llave.
     * 
     * @param keyLabel
     *            Valor de la llave.
     */
    public void setKeyLabel(String keyLabel) {
        this.keyLabel = keyLabel;
    }

    /**
     * Devuelve el valor en el valor de bean.
     * 
     * @return Valor del bean.
     */
    public String getValueLabel() {
        return valueLabel;
    }

    /**
     * Asigna el valor en el valor del bean.
     * 
     * @param valueLabel
     *            Valor del bean.
     */
    public void setValueLabel(String valueLabel) {
        this.valueLabel = valueLabel;
    }

    @Override
    public String toString() {
        return "[" + this.keyLabel + "=" + this.valueLabel + "]";
    }
}
