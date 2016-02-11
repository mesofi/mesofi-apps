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

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Creates a custom response in order to save the response content.
 * 
 * @author Armando Rivas
 * @since 12.11.2013
 */
public class LoggerServletResponse extends HttpServletResponseWrapper {
    /**
     * Custom printwriter.
     */
    private LoggerPrintWriter printWriter;

    /**
     * Creates a custom response given the standard HttpServletResponse.
     * 
     * @param response
     *            The HttpServletResponse.
     */
    public LoggerServletResponse(HttpServletResponse response) {
        super(response);
    }

    /**
     * Creates a custom writer and the instance is saved.
     */
    @Override
    public LoggerPrintWriter getWriter() throws IOException {
        LoggerPrintWriter printWriter = new LoggerPrintWriter(super.getWriter());
        this.printWriter = printWriter;
        return printWriter;
    }

    /**
     * Returns the content of the response.
     * 
     * @return Response content.
     */
    public String getContent() {
        String response = null;
        if (printWriter != null) {
            response = printWriter.getCopy().toString().trim();
        } else {
            // the PrintWriter was null, return an empty response.
            response = "";
        }
        return response;
    }
}
