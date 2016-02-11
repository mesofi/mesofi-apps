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

import java.io.PrintWriter;

/**
 * Creates a custom logger to manage the response.
 * 
 * @author Armando Rivas.
 * @since 12.11.2013
 */
public class LoggerPrintWriter extends PrintWriter {
    /**
     * Stores the content of the response.
     */
    private StringBuilder copy = new StringBuilder();

    /**
     * Creates a custom writer given a print writer.
     * 
     * @param writer
     *            PrintWriter.
     */
    public LoggerPrintWriter(PrintWriter writer) {
        super(writer);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(int c) {
        copy.append((char) c);
        super.write(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(char[] chars, int offset, int length) {
        copy.append(chars);
        super.write(chars, offset, length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(String string, int offset, int length) {
        copy.append(string);
        super.write(string, offset, length);
    }

    /**
     * @return the copy
     */
    public StringBuilder getCopy() {
        return copy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "LoggerPrintWriter [copy=" + copy + "]";
    }

}
