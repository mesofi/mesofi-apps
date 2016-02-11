/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.framework.error;

/**
 * This exception is thrown when an error occurs in the framework.
 * 
 * @author Armando Rivas
 * @since Nov 10 2013
 */
public class FrameworkException extends RuntimeException {

    /**
     * serial version.
     */
    private static final long serialVersionUID = 7199943459786636705L;

    /**
     * Default message.
     */
    private static final String DEFAULT_MSG = "An unexpected error has ocurred inside Mesofi framework: ";

    /**
     * Contains the error trace of the exception.
     */
    private String detailError;

    /**
     * Creates a new exception for handling the process.
     * 
     * @param msg
     *            Error message.
     */
    public FrameworkException(String msg) {
        super(DEFAULT_MSG + msg);
    }

    /**
     * Creates a new exception for handling the process.
     * 
     * @param msg
     *            Error message.
     * @param detail
     *            Detail of the exception.
     */
    public FrameworkException(String msg, String detail) {
        super(DEFAULT_MSG + msg);
        this.detailError = detail;
    }

    /**
     * @return the detailError
     */
    public String getDetailError() {
        return detailError;
    }

}
