/*
 * COPYRIGHT. Mesofi 2010. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.services.files.exception;

/**
 * Excepcion definida para el tratamiento de errores en el servicio de parseo de
 * archivos para los layouts.
 * 
 * @author mesofi.
 */
public class FileParserException extends RuntimeException {

    /**
     * Serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Crea una exception para el parseo de archivos con un layout en
     * especifico.
     * 
     * @param msg
     *            Mensaje de la exception.
     */
    public FileParserException(final String msg) {
        super(msg);
    }
}
