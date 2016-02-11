/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.datasource.error;

/**
 * Handles the errors when connection to database.
 * 
 * @author Armando Rivas
 * @since Feb 12 2014
 */
public class RemoteConnectionException extends Exception {

    /**
     * serial version.
     */
    private static final long serialVersionUID = 7199943459786636705L;
    /**
     * Current exception.
     */
    private Exception currentException;

    /**
     * Creates a new exception for handling remote connection.
     * 
     * @param msg
     *            Error message.
     */
    public RemoteConnectionException(String msg) {
        super(msg);
    }

    public RemoteConnectionException(Exception e) {
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
