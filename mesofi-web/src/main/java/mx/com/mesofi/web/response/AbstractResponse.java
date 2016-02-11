/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.response;

/**
 * Contains the common things among the implementations for the responses.
 * 
 * @author Armando Rivas
 * @since Oct 11 2013
 */
public abstract class AbstractResponse {
    /**
     * The actual object to transform to a certain format.
     */
    private Object objectToConvert;

    /**
     * @return the objectToConvert
     */
    public Object getObjectToConvert() {
        return objectToConvert;
    }

    /**
     * @param objectToConvert
     *            the objectToConvert to set
     */
    public void setObjectToConvert(Object objectToConvert, Class<?> clazz) {
        if (objectToConvert == null) {
            throw new IllegalStateException("The object passed as argument in [" + clazz + "] object cannot be null");
        }
        this.objectToConvert = objectToConvert;
    }

    /**
     * Utility method to replace the string from StringBuilder.
     * 
     * @param sb
     *            the StringBuilder object.
     * @param toReplace
     *            the String that should be replaced.
     * @param replacement
     *            the String that has to be replaced by.
     * 
     */
    protected void prepareOutput(StringBuilder sb, String toReplace, String replacement) {
        int index = -1;
        while ((index = sb.lastIndexOf(toReplace)) != -1) {
            sb.replace(index, index + toReplace.length(), replacement);
        }
    }

    /**
     * Prepares the output.
     * 
     * @param output
     *            String to be transformed.
     * @return Output for HTML.
     */
    protected String prepareOutput(String output) {
        StringBuilder sb = new StringBuilder(output);
        prepareOutput(sb, "\\n", "<br/>");
        prepareOutput(sb, "\\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
        return sb.toString();
    }
}
