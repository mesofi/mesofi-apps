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

import mx.com.mesofi.framework.error.ErrorType;

/**
 * This class handles the error response and describe the type of error, a short
 * description and the detail for such exception.
 * 
 * @author Armando Rivas.
 * @since Nov 09 2013
 */
public class ErrorResponse {

    /**
     * Message that shown an unexpected error message.
     */
    public static final String FATAL_MSG = "Unknown cause";
    
    /**
     * Type of error.
     */
    private ErrorType errorType;
    
    /**
     * Description of the exception.
     */
    private String text;
    
    /**
     * Detail of the exception.
     */
    private String detail;

    /**
     * Creates a new object for errors.
     * 
     * @param errorType
     *            Error type.
     * @param text
     *            Description of the error.
     * @param detail
     *            Detail of the error.
     */
    public ErrorResponse(ErrorType errorType, String text, String detail) {
        this.errorType = errorType;
        this.text = text == null ? NullPointerException.class.toString() : text;
        this.detail = detail;
    }

    /**
     * @return the errorType
     */
    public ErrorType getErrorType() {
        return errorType;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }

}
