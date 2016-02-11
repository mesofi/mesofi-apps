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
 * Excepcion lanzada cuando se produce un error al parsear un layout en una
 * lista de objetos.
 * 
 * @author mesofi
 * 
 */
public class FileReaderParserException extends FileParserException {
    /**
     * Serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construye un objeto de este tipo para lanzar una excepcion especificando
     * el mensaje de error.
     * 
     * @param msg
     *            Mensaje de error de esta excepcion.
     */
    public FileReaderParserException(final String msg) {
        super(msg);
    }
}
