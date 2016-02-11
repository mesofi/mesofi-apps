/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.response.types;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mx.com.mesofi.web.response.AbstractResponse;
import mx.com.mesofi.web.response.ConversionFormatException;
import mx.com.mesofi.web.response.StreamWebResponse;
import mx.com.mesofi.web.util.MediaType;

/**
 * This StreamResponse handles the response in stream for the client. Use this
 * type when you want to download certain existing file located in the server.
 * 
 * @author Armando Rivas.
 * @since Mar 04 2014
 */
public class StreamResponse extends AbstractResponse implements StreamWebResponse {
    /**
     * Buffer size used by the stream.
     */
    private int bufferSize = 1024;
    /**
     * This is the file name used to download the file.
     */
    private String fileName = "some-file-name";
    /**
     * The length of the file.
     */
    private long fileSize;

    /**
     * Creates an object for streaming, this object must be either
     * {@link InputStream} or {@link File}, if this object is different from the
     * specified, then throws an exception.
     * 
     * @param object
     *            Object to be streamed.
     */
    public StreamResponse(Object object) {
        InputStream is = validateInputStream(object);
        setFileName(object);
        setObjectToConvert(is, InputStream.class);
    }

    /**
     * Creates an object for streaming, this object must be either
     * {@link InputStream} or {@link File}, if this object is different from the
     * specified, then throws an exception. Using this constructor, we can
     * specify the buffer size.
     * 
     * @param object
     *            Object to be streamed.
     * @param bufferSize
     *            The buffer size.
     */
    public StreamResponse(Object object, int bufferSize) {
        InputStream is = validateInputStream(object);
        setFileName(object);
        setObjectToConvert(is, InputStream.class);
        this.bufferSize = bufferSize;
    }

    /**
     * Creates an object for streaming, this object must be either
     * {@link InputStream} or {@link File}, if this object is different from the
     * specified, then throws an exception. Using this constructor, we can
     * specify the final name of the streamed file.
     * 
     * @param object
     *            Object to be streamed.
     * @param finalFileName
     *            The final file name.
     */
    public StreamResponse(Object object, String finalFileName) {
        InputStream is = validateInputStream(object);
        setFileName(finalFileName);
        setObjectToConvert(is, InputStream.class);
    }

    /**
     * Creates an object for streaming, this object must be either
     * {@link InputStream} or {@link File}, if this object is different from the
     * specified, then throws an exception. Using this constructor, we can
     * specify the buffer size and the file name.
     * 
     * @param object
     *            Object to be streamed.
     * @param bufferSize
     *            The buffer size.
     * @param finalFileName
     *            The final file name.
     */
    public StreamResponse(Object object, int bufferSize, String finalFileName) {
        InputStream is = validateInputStream(object);
        setFileName(finalFileName);
        setObjectToConvert(is, InputStream.class);
        this.bufferSize = bufferSize;
    }

    /**
     * This method should not be used for this particular class.
     */
    @Override
    public String getResponse() throws ConversionFormatException {
        throw new UnsupportedOperationException(
                "This response cannot be handled by using a common response, call this method instead: "
                        + "public void getResponse(OutputStream outputStream) throws IOException");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getResponse(OutputStream outputStream) throws IOException {
        // processes the actual file.
        InputStream inputStream = (InputStream) getObjectToConvert();
        byte[] buffer = new byte[bufferSize];
        int bytesRead = 0;
        try {
            do {
                bytesRead = inputStream.read(buffer, 0, buffer.length);
                outputStream.write(buffer, 0, bytesRead);
            } while (bytesRead == buffer.length);
            outputStream.flush();
        } catch (IOException e) {
            throw e;
        } finally {
            inputStream.close();
            outputStream.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MediaType getMediaType() {
        return MediaType.APPLICATION_OCTET_STREAM_TYPE;
    }

    /**
     * Validate the type of the stream given an object.
     * 
     * @param object
     *            The object to be tested.
     * @return The actual input stream.
     */
    private InputStream validateInputStream(Object object) {
        InputStream is = null;
        if (!(object instanceof InputStream || object instanceof File)) {
            throw new IllegalStateException("The stream response should be either a class compatible with "
                    + InputStream.class + " or " + File.class);
        }
        if (object instanceof File) {
            try {
                File file = (File) object;
                this.fileSize = file.length();
                is = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw new IllegalStateException("Cannot create an stream due to: " + e.getMessage());
            }
        } else {
            is = (InputStream) object;
        }
        return is;
    }

    /**
     * Assign the file name to the file to be streamed.
     * 
     * @param object
     *            The object to be converted. This object must be either
     *            {@link File} or {@link String}
     */
    private void setFileName(Object object) {
        if (object != null && object instanceof File) {
            fileName = ((File) object).getName();
        } else if (object != null && object instanceof String) {
            fileName = (String) object;
        }
    }

    /**
     * @return the bufferSize
     */
    public int getBufferSize() {
        return bufferSize;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return the fileSize
     */
    public long getFileSize() {
        return fileSize;
    }

}
