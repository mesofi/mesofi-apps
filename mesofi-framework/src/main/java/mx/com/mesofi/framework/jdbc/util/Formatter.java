/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.framework.jdbc.util;

/**
 * This interface defines the method(s) to format some string, the typical
 * implementations of this interface is when formating SQL statements.
 * 
 * @author Armando Rivas
 * @since 24 Sep 2013
 * @see BasicFormatterImpl
 */
public interface Formatter {

    /**
     * Format an specified string and returns the same string with an specific
     * format.
     * 
     * @param source
     *            Source.
     * @return Result of the string formatted.
     */
    String format(String source);
}
