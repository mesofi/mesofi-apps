/*
 * COPYRIGHT. Mesofi 2010. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.services.files;

import static mx.com.mesofi.services.files.FileUtil.close;
import static mx.com.mesofi.services.util.SimpleValidator.isNull;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import mx.com.mesofi.services.files.exception.FileAccessException;
import mx.com.mesofi.services.files.exception.FileAccessRuntimeException;
import mx.com.mesofi.services.util.CommonConstants;

import org.apache.log4j.Logger;

/**
 * Clase que procesa los archivos para escritura y lectura.
 * 
 * @author mesofi
 * 
 */
public class FileProcessor {
    /**
     * Log4j.
     */
    private Logger logger = Logger.getLogger(FileProcessor.class);

    /**
     * Escribe a un archivo plano el contenido que se desee. Se puede
     * especificar si el contenido a escribir se agrega al final o no.
     * 
     * @param fileHolder
     *            Referencia al FileHolder del archivo.
     * @param contenido
     *            Contenido a escribir.
     * @param append
     *            Indica si se agrega al final o no el contenido nuevo del
     *            archivo.
     * @throws FileAccessException
     *             En caso de que haya un error al escribir al archivo.
     */
    public void writePlainContent(final FileHolder fileHolder, final String contenido, final boolean append)
            throws FileAccessException {
        isNull(fileHolder, "Debe especificar una referencia en la cual " + "se escribira el archivo");
        isNull(contenido, "El contenido a escribir no debe de ser nulo");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(fileHolder.getFileHolder(), append);
            out.write(contenido.getBytes());
        } catch (Throwable e) {
            logger.error(e.getMessage());
            throw new FileAccessException(e.getMessage());
        } finally {
            close(out);
            out = null;
        }
    }

    /**
     * Escribe a un archivo plano el contenido que se desee. Por defecto el
     * contenido se sobreescribe, esto es, si se desea escribir sobre el mismo
     * archivo, entonces el nuevo contenido no se agrega al final.
     * 
     * @param fileHolder
     *            Referencia al FileHolder del archivo.
     * @param contenido
     *            Contenido a escribir.
     * @throws FileAccessException
     *             En caso de que haya un error al escribir al archivo.
     */
    public void writePlainContent(final FileHolder fileHolder, final String contenido) throws FileAccessException {
        writePlainContent(fileHolder, contenido, false);
    }

    /**
     * Lee un archivo con la particularidad de que este archivo a leer se lee
     * linea por linea con una referencia a BufferedReader.
     * 
     * @param file
     *            Archivo a leer.
     * @return BufferedReader
     * @throws FileNotFoundException
     *             En caso de no encontrar el archivo.
     */
    public BufferedReader readPlainContentByLine(final File file) throws FileNotFoundException {
        isNull(file, "Debe especificar una referencia a un archivo valido");
        if (!file.exists()) {
            throw new FileAccessRuntimeException("La referencia al archivo especificado no existe");
        }
        if (!file.canRead()) {
            throw new FileAccessRuntimeException("La referencia al archivo especificado no se puede leer");
        }
        if (!file.isFile()) {
            throw new FileAccessRuntimeException(
                    "La referencia al archivo no es valido, ya que no es un archivo valido que leer");
        }

        FileInputStream fstream = null;
        fstream = new FileInputStream(file);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        return br;
    }

    /**
     * Based on a location, reads the content of the file contained in the
     * classpath.
     * 
     * @param fileLocation
     *            File location on classpath.
     * @return The content of the file.
     */
    public String readPlainContentByLine(final String fileLocation) {
        isNull(fileLocation, "The fileLocation should not be empty");
        InputStream input = null;
        input = ClassPathFileHolder.getInputStreamFromResource(fileLocation);
        return readPlainContentByLine(input);
    }

    /**
     * Based on an existing stream, reads the content of the file contained in
     * the classpath.
     * 
     * @param input
     *            The input stream of the resource.
     * @return The content of the file.
     */
    public String readPlainContentByLine(final InputStream inputStream) {
        isNull(inputStream);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String strLine = null;
        try {
            while ((strLine = br.readLine()) != null) {
                sb.append(strLine);
                sb.append(CommonConstants.BREAK_LINE_STANDARD);
            }
        } catch (IOException e) {
            throw new FileAccessRuntimeException(e);
        }
        return sb.toString();
    }
}
