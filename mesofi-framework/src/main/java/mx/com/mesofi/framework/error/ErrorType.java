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
 * Contains the types of errors.
 * 
 * @author Armando Rivas.
 * @since Nov 09 2013
 */
public enum ErrorType {

    /**
     * Framework error.
     */
    FRAMEWORK,

    /**
     * Fatal error.
     */
    FATAL,

    /**
     * Default error.
     */
    DEFAULT,

    /**
     * Business error.
     */
    BUSINESS
}
