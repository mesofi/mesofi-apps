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
 * This exception is thrown when an error occurs during the process of the
 * several layers.
 * 
 * @author Armando Rivas
 * @since Nov 09 2013
 */
public class ProcessRulesException extends RuntimeException {

    /**
     * serial version.
     */
    private static final long serialVersionUID = 7199943459786636705L;

    /**
     * Default message.
     */
    private static final String DEFAULT_MSG = "An error has ocurred due to: ";

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
    public ProcessRulesException(String msg) {
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
    public ProcessRulesException(String msg, String detail) {
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
