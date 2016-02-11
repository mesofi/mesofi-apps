/*
 * COPYRIGHT. Mesofi 2009. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.services.dao.exception;

/**
 * Exception lanzada por el hecho de tener acceso a la base de datos.
 * 
 * @author mesofi
 */
public class DataAccessException extends Exception {

    /**
     * Serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construye un objeto de este tipo.
     * 
     * @param exception
     *            Exception delegada.
     */
    public DataAccessException(final Exception exception) {
        super(exception);
    }

    /**
     * Construye un objeto de este tipo, asignandole un mensaje.
     * 
     * @param msg
     *            Mensaje o descripcion de la exception.
     */
    public DataAccessException(String msg) {
        super(msg);
    }
}
