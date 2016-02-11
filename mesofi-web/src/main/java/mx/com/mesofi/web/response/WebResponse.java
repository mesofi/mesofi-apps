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

import mx.com.mesofi.web.util.MediaType;

/**
 * Interface that gets the response for the requests.
 * 
 * @author Armando Rivas
 * @since Oct 10 2013
 */
public interface WebResponse {
    /**
     * Gets the response for the client.
     * 
     * @return The response.
     * @throws ConversionFormatException
     *             In case the object cannot convert to certain format.
     */
    String getResponse() throws ConversionFormatException;

    /**
     * Gets the media type for this kind of response.
     * 
     * @return The media type according to the response type.
     */
    MediaType getMediaType();
}
