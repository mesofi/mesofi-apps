/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.builder.error;

/**
 * Handles the errors during the creation of the project.
 * 
 * @author Armando Rivas
 * @since Mar 04 2014
 */
public class ApplicationBuilderException extends Exception {

    /**
     * serial version.
     */
    private static final long serialVersionUID = 7199943459786636705L;
    /**
     * Current exception.
     */
    private Exception currentException;

    /**
     * Creates a new exception for the creation of the project.
     * 
     * @param msg
     *            Error message.
     */
    public ApplicationBuilderException(String msg) {
        super(msg);
    }

    /**
     * Creates a new exception for the creation of the project.
     * 
     * @param e
     *            Some exception.
     */
    public ApplicationBuilderException(Exception e) {
        super(e.getMessage());
        currentException = e;
    }

    /**
     * @return the currentException
     */
    public Exception getCurrentException() {
        return currentException;
    }

}
