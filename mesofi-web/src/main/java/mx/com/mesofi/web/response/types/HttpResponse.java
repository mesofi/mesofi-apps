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

import mx.com.mesofi.web.response.AbstractResponse;
import mx.com.mesofi.web.response.ConversionFormatException;
import mx.com.mesofi.web.response.WebResponse;
import mx.com.mesofi.web.util.MediaType;

/**
 * Class to handle the HTTP responses.
 * 
 * @author Armando Rivas.
 * @since Oct 11 2013
 */
public class HttpResponse extends AbstractResponse implements WebResponse {
    /**
     * Creates this object given a valid object.
     * 
     * @param object
     *            Object to be converted.
     */
    public HttpResponse(Object object) {
        setObjectToConvert(object, HttpResponse.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResponse() throws ConversionFormatException {
        return getObjectToConvert().toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MediaType getMediaType() {
        return MediaType.TEXT_HTML_TYPE;
    }
}
