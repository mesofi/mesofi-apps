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
import java.io.OutputStream;

/**
 * Interface that gets the response for the requests. This interface is an
 * specialization of the existing one. Added new features to handle streams.
 * 
 * @author Armando Rivas
 * @since Mar 03 2014
 */
public interface StreamWebResponse extends WebResponse {
    /**
     * Gets the response using a stream.
     * 
     * @param outputStream
     *            The OutputStream.
     * @throws IOException
     *             If there is an error during the process.
     */
    void getResponse(OutputStream outputStream) throws IOException;
}
