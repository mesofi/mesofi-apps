/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core.web;

/**
 * This class represents a context param in the web.xml.
 * 
 * @author Armando Rivas
 * @since Mar 12 2014.
 */
public class WebContextParam {
    /**
     * Param name.
     */
    private String paramName;
    /**
     * Param value.
     */
    private String paramValue;
    /**
     * Includes the comments related to the listener.
     */
    private String comments;

    /**
     * Creates a web context using the paramName and the paramValue.
     * 
     * @param paramName
     *            The parameter name.
     * @param paramValue
     *            The parameter value.
     */
    public WebContextParam(String paramName, String paramValue) {
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    /**
     * @return the paramName
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * @param paramName
     *            the paramName to set
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * @return the paramValue
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * @param paramValue
     *            the paramValue to set
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments
     *            the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

}
