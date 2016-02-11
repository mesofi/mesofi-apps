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
 * Handles the business exception.
 * 
 * @author Armando Rivas
 * @since Nov 09 2013
 */
public class BusinessException extends RuntimeException {

    /**
     * serial version.
     */
    private static final long serialVersionUID = 7199943459786636705L;

    /**
     * Creates a new exception for handling the business.
     * 
     * @param msg
     *            Error message.
     */
    public BusinessException(String msg) {
        super(msg);
    }

}
