/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.framework.util;

/**
 * This enum class contains some constants to validate preferences parameters
 * from the application.
 * 
 * @author Armando Rivas
 * @since Oct 1 2013
 */
public enum ValidationType {
    
    /**
     * When some parameters have this type of error, the applications is unable
     * to start.
     */
    ERROR,
    
    /**
     * When some parameters have this type of error, only a warning message is
     * displayed.
     */
    WARNING
}
