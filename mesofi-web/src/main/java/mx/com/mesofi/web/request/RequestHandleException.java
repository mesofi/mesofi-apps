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

/**
 * Handles the errors for the incoming request.
 * 
 * @author Armando Rivas
 * @since Oct 08 2013
 */
public class RequestHandleException extends RuntimeException {
    /**
     * serial version.
     */
    private static final long serialVersionUID = -4114220317795266607L;

    /**
     * Default message.
     */
    private static final String DEFAULT_MSG = "An error has ocurred in the request due to: ";

    /**
     * Contains the error trace of the exception.
     */
    private String detailError;

    /**
     * Creates a new exception for handling the requests.
     * 
     * @param msg
     *            Error message.
     */
    public RequestHandleException(String msg) {
        super(DEFAULT_MSG + msg);
    }

    /**
     * Creates a new exception for handling the requests.
     * 
     * @param msg
     *            Description of the exception.
     * @param detail
     *            Detail of the exception.
     */
    public RequestHandleException(String msg, String detail) {
        super(DEFAULT_MSG + msg);
        this.detailError = detail;
    }

    /**
     * Creates a new exception for handling the requests.
     * 
     * @param exception
     *            Error message.
     */
    public RequestHandleException(Exception exception) {
        super(DEFAULT_MSG + exception);
    }

    /**
     * @return the detailError
     */
    public String getDetailError() {
        return detailError;
    }
}
