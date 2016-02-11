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

import java.util.Map;

/**
 * Defines certain methods to handle the incoming parameters from the client.
 * 
 * @author Armando Rivas.
 * @since Oct 11 2013
 */
public interface Parameters {
    /**
     * Given a parameter name, gets its value converted in certain type.
     * 
     * @param parameterName
     *            Some parameter name.
     * @param requiredType
     *            Required type.
     * @see #getValue(String)
     * @return Value of such parameter.
     */
    <T> T getValue(String parameterName, Class<T> requiredType);

    /**
     * Given a parameter name, gets its value or null if the parameter name does
     * not exist.
     * 
     * @param parameterName
     *            Some parameter name.
     * @return Value of such parameter.
     */
    String getValue(String parameterName);

    /**
     * Given a parameter name, gets all the values that belong to such parameter
     * name, can be zero or more.
     * 
     * @param parameterName
     *            Some parameter name.
     * @return Array of values of such parameter.
     */
    String[] getValues(String parameterName);

    /**
     * Gets all the parameter names.
     * 
     * @return Array containing all the parameter names.
     */
    String[] getNames();

    /**
     * Get all the parameters and values.
     * 
     * @return Map that contains all the parameters and values.
     */
    Map<String, String[]> getAll();
}
