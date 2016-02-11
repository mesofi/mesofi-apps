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
 * Handles the errors for the response in conversion.
 * 
 * @author Armando Rivas
 * @since Oct 11 2013
 */
public class ConversionFormatException extends Exception {
    /**
     * serial version.
     */
    private static final long serialVersionUID = -4114220317795266607L;

    /**
     * Creates an exception using as a base another exception.
     * 
     * @param e
     *            Exception taken as a base.
     */
    public ConversionFormatException(Exception e) {
        super(e);
    }

    /**
     * Creates an exception using a predefined message.
     * 
     * @param msg
     *            Message to display.
     */
    public ConversionFormatException(String msg) {
        super(msg);
    }
}
