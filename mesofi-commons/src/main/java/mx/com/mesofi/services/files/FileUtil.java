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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

import org.apache.log4j.Logger;

/**
 * Utileria que maneja el acceso a archivos.
 * 
 * @author mesofi
 * 
 */
public final class FileUtil {
    /**
     * log4j.
     */
    private static Logger logger = Logger.getLogger(FileUtil.class);

    /**
     * Constructor privado.
     */
    private FileUtil() {
        // evita que se creen instancias de esta clase directamente.
    }

    /**
     * Cierra el outputStream de un archivo y en caso de lanzar una exception,
     * esta se silencia.
     * 
     * @param outputStream
     *            OutputStream hacia algun archivo.
     * @see #closeNotQuietly(OutputStream)
     */
    public static void close(final OutputStream outputStream) {
        try {
            FileUtil.closeNotQuietly(outputStream);
        } catch (IOException exception) {
            // silencia la excepcion.
            logger.warn(exception.getMessage());
        }
    }

    /**
     * Cierra el inputStream de un archivo y en caso de lanzar una exception,
     * esta se silencia.
     * 
     * @param inputStream
     *            InputStream hacia algun archivo.
     * @see #closeNotQuietly(InputStream)
     */
    public static void close(final InputStream inputStream) {
        try {
            FileUtil.closeNotQuietly(inputStream);
        } catch (IOException exception) {
            // silencia la excepcion.
            logger.warn(exception.getMessage());
        }

    }

    /**
     * Cierra el flujo al acceso a archivos y en caso de lanzar una exception,
     * esta se lanza para atraparla en otro proceso.
     * 
     * @param outputStream
     *            OutputStream asociado a la apertura de algun archivo.
     * @throws IOException
     *             En caso de generarse una excepcion.
     * @see #close(OutputStream)
     */
    public static void closeNotQuietly(final OutputStream outputStream)
            throws IOException {
        if (outputStream != null) {
            outputStream.close();
        }
    }

    /**
     * Cierra el flujo al acceso a archivos y en caso de lanzar una exception,
     * esta se lanza para atraparla en otro proceso.
     * 
     * @param inputStream
     *            InputStream asociado a la apertura de algun archivo.
     * @throws IOException
     *             En caso de generarse una excepcion.
     * @see #close(InputStream)
     */
    public static void closeNotQuietly(final InputStream inputStream)
            throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
    }

    /**
     * Cierra el flujo al acceso a archivos y en caso de lanzar una exception,
     * esta se lanza para atraparla en otro proceso.
     * 
     * @param reader
     *            Referencia a Reader.
     * @throws IOException
     *             En caso de enviar una excepcion.
     * @see #close(Reader)
     */
    public static void closeNotQuietly(final Reader reader) throws IOException {
        if (reader != null) {
            reader.close();
        }
    }

    /**
     * Cierra el reader de un archivo y en caso de lanzar una exception, esta se
     * silencia.
     * 
     * @param reader
     *            Reader.
     * @see #closeNotQuietly(Reader)
     */
    public static void close(final Reader reader) {
        try {
            FileUtil.closeNotQuietly(reader);
        } catch (IOException exception) {
            // silencia la excepcion.
            logger.warn(exception.getMessage());
        }
    }

}