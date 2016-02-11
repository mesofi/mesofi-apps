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

import java.util.List;
import java.util.Map;

import mx.com.mesofi.framework.error.ErrorType;

/**
 * This class handles the error response for business rules.
 * 
 * @author Armando Rivas.
 * @since Feb 20 2014
 */
public class ErrorBusinessResponse {
    /**
     * Type of error.
     */
    private ErrorType errorType = ErrorType.BUSINESS;
    /**
     * List of errors.
     */
    private Map<String, List<String>> fields;
    /**
     * Contains the bussiness error in one single field.
     */
    private String field;

    /**
     * List of errors.
     * 
     * @param fields
     *            The fields.
     */
    public ErrorBusinessResponse(Map<String, List<String>> fields) {
        this.fields = fields;
    }
    
    /**
     * The validation error.
     * 
     * @param field
     *            The field.
     */
    public ErrorBusinessResponse(String field) {
        this.field = field;
    }

    /**
     * @return the errorType
     */
    public ErrorType getErrorType() {
        return errorType;
    }

    /**
     * @param errorType
     *            the errorType to set
     */
    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    /**
     * @return the fields
     */
    public Map<String, List<String>> getFields() {
        return fields;
    }

    /**
     * @param fields
     *            the fields to set
     */
    public void setFields(Map<String, List<String>> fields) {
        this.fields = fields;
    }

    /**
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * @param field
     *            the field to set
     */
    public void setField(String field) {
        this.field = field;
    }

}
