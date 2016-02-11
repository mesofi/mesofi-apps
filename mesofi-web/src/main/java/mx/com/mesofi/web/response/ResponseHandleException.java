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
 * Handles the errors for the response.
 * 
 * @author Armando Rivas
 * @since Oct 10 2013
 */
public class ResponseHandleException extends RuntimeException {
    /**
     * serial version.
     */
    private static final long serialVersionUID = -4114220317795266607L;

    /**
     * Default message.
     */
    private static final String DEFAULT_MSG = "An error has ocurred in the response due to: ";

    /**
     * Creates a new exception for handling the responses.
     * 
     * @param msg
     *            Error message.
     */
    public ResponseHandleException(String msg) {
        super(DEFAULT_MSG + msg);
    }

    /**
     * Creates a new exception for handling the responses.
     * 
     * @param exception
     *            Error message.
     */
    public ResponseHandleException(Exception exception) {
        super(DEFAULT_MSG + exception);
    }
}
