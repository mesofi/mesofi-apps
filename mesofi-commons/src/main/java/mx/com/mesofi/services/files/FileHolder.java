/*
 * COPYRIGHT. Mesofi 2009. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.services.files;

import static mx.com.mesofi.services.util.SimpleValidator.isNull;

import java.io.File;

/**
 * Clase que contiene una referencia hacia archivos dentro del sistema de
 * archivos.
 * 
 * @author mesofi
 * 
 */
public abstract class FileHolder {
    /**
     * Devuelve una referencia hacia un archivo valido existente. Esta
     * referencia a file, siempre va a ser una refrencia no nula.
     * 
     * @return Referencia valida hacia un archivo valido existente.
     */
    public abstract File getFileHolder();

    /**
     * Valida que la referencia a file exista y que no sea un directorio pero si
     * un archivo de cualquier tipo.
     * 
     * @param file
     *            Referencia a un archivo.
     */
    void validateFile(final File file) {
        isNull(file, "Debe proporcionar una referencia no nula hacia File");
        if (file.isDirectory()) {
            // forzosamente debe ser un archivo, no directorio.
            throw new IllegalArgumentException(
                    "Debe especificar una referencia hacia un archivo valido");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        File file = getFileHolder();
        return (file == null) ? "" : file.getPath();
    }
}
