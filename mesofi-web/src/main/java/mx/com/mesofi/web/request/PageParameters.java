/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.request;

import static mx.com.mesofi.services.util.SimpleValidator.isNull;

import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Handles the parameters coming from the request so that can be easier to
 * handle.
 * 
 * @author Armando Rivas.
 * @since Oct 11 2013
 */
public class PageParameters implements Parameters {

    /**
     * log4j.
     */
    private static final Logger log = Logger.getLogger(PageParameters.class);
    /**
     * Contains all the parameters.
     */
    private Map<String, String[]> parameterMap;

    /**
     * Creates an object that contains the parameters to be used.
     * 
     * @param parameterMap
     *            Map of parameters.
     */
    public PageParameters(Map<String, String[]> parameterMap) {
        this.parameterMap = parameterMap;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getValue(String parameterName, Class<T> requiredType) {
        T t = null;
        String value = getValue(parameterName);
        if (value != null) {
            isNull(requiredType, "The required type must be specified to get the value from the request");
            try {
                if (requiredType.isAssignableFrom(Integer.class)) {
                    t = (T) new Integer(value);
                } else if (requiredType.isAssignableFrom(Long.class)) {
                    t = (T) new Long(value);
                } else if (requiredType.isAssignableFrom(Short.class)) {
                    t = (T) new Short(value);
                } else if (requiredType.isAssignableFrom(Byte.class)) {
                    t = (T) new Byte(value);
                } else if (requiredType.isAssignableFrom(Float.class)) {
                    t = (T) new Float(value);
                } else if (requiredType.isAssignableFrom(Double.class)) {
                    t = (T) new Double(value);
                } else if (requiredType.isAssignableFrom(Boolean.class)) {
                    t = (T) Boolean.valueOf(value.equals("on") ? "true" : value);
                } else if (requiredType.isAssignableFrom(String.class)) {
                    t = (T) value;
                } else {
                    t = (T) value;
                    if (log.isDebugEnabled()) {
                        log.warn("This type [" + requiredType + "] is not recognized as a valid type, " + String.class
                                + " is taken as a default value");
                    }
                }
            } catch (NumberFormatException ex) {
                // shows a friendly message to the user in detail.
                throw new IllegalStateException("The parameter named [" + parameterName + "] with value \"" + value
                        + "\" could not be converted to this type: " + requiredType);
            }
        }
        return t;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue(String parameterName) {
        return parameterMap.get(parameterName) == null ? null : parameterMap.get(parameterName)[0];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getValues(String parameterName) {
        return parameterMap.get(parameterName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getNames() {
        String[] parameterNames = new String[parameterMap.keySet().size()];
        int i = 0;
        for (String key : parameterMap.keySet()) {
            parameterNames[i] = key;
            i++;
        }
        return parameterNames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, String[]> getAll() {
        return parameterMap;
    }

    /**
     * Gets a representation of the parameters.
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        boolean lastCommaOuter = false;
        for (String key : parameterMap.keySet()) {
            sb.append(key);
            sb.append("=");
            sb.append("[");
            boolean lastComma = false;
            for (String s : parameterMap.get(key)) {
                sb.append(s);
                sb.append(",");
                lastComma = true;
            }
            if (lastComma) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("],");
            lastCommaOuter = true;
        }
        if (lastCommaOuter) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

}
