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

import static mx.com.mesofi.services.util.SimpleValidator.isEmpty;

import java.io.File;

/**
 * Procesa un archivo que se encuentra dentro del sistema de archivos del
 * sistema.
 * 
 * @author mesofi
 * 
 */
public class FileSystemFileHolder extends FileHolder {
    /**
     * Referencia a File.
     */
    private File file;

    /**
     * Construye un objeto para el almacenamiento y procesamiento de archivos
     * existentes en el sistema de archivos del sistema.
     * 
     * @param pathSystemFile
     *            Nombre del recurso en el sistema de archivos.
     */
    public FileSystemFileHolder(final String pathSystemFile) {
        isEmpty(pathSystemFile,
                "Debe proporcionar una ruta valida en el sistema de archivos");
        file = new File(pathSystemFile);
        validateFile(file);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File getFileHolder() {
        return file;
    }

}
