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

import org.apache.log4j.Logger;

/**
 * This special class allow the invoker to return any format supported by the
 * Mesofi framework. To decide which in witch format return the response, the
 * special header in the request must exist ("accept") and according to that
 * value is how the response is sending back.
 * 
 * @author Armando Rivas.
 * @since Oct 11 2013
 */
public class HeaderResponse extends AbstractResponse implements WebResponse {
    /**
     * log4j.
     */
    private static Logger log = Logger.getLogger(HeaderResponse.class);

    /**
     * Response to be returned.
     */
    private String response;

    /**
     * Creates this object given a valid object.
     * 
     * @param object
     *            Object to be converted.
     */
    public HeaderResponse(Object object) {
        setObjectToConvert(object, HeaderResponse.class);
    }

    /**
     * Process the correct response given a header value.
     * 
     * @param accept
     *            Header value in the request.
     * @return A web response.
     * @throws ConversionFormatException
     *             In case the conversion cannot be achieved.
     */
    public WebResponse processBasedOnHeader(String accept) throws ConversionFormatException {
        WebResponse webResponse = null;
        switch (accept) {
        case MediaType.APPLICATION_JSON:
            webResponse = new JsonResponse(getObjectToConvert());
            logResponse(MediaType.APPLICATION_JSON);
            break;
        case MediaType.APPLICATION_XML:
            webResponse = new XmlResponse(getObjectToConvert());
            logResponse(MediaType.APPLICATION_XML);
            break;
        case MediaType.TEXT_HTML:
            webResponse = new HttpResponse(getObjectToConvert());
            logResponse(MediaType.TEXT_HTML);
            break;
        case MediaType.TEXT_PLAIN:
            webResponse = new PlainResponse(getObjectToConvert());
            logResponse(MediaType.TEXT_PLAIN);
            break;
        case MediaType.APPLICATION_OCTET_STREAM:
            webResponse = new StreamResponse(getObjectToConvert());
            logResponse(MediaType.APPLICATION_OCTET_STREAM);
            break;
        default:
            log.warn("Could not find a matching type for handling the header value: [" + accept + "]");
            log.warn("Returning the default content [" + MediaType.TEXT_PLAIN + "]");
            webResponse = new PlainResponse(getObjectToConvert());
        }
        response = webResponse.getResponse();
        return webResponse;
    }

    /**
     * Log the automatic conversion.
     * 
     * @param value
     *            Value logged.
     */
    private void logResponse(String value) {
        if (log.isDebugEnabled()) {
            log.warn("Automatic conversion response, assigning to: [" + value + "]");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResponse() throws ConversionFormatException {
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MediaType getMediaType() {
        throw new UnsupportedOperationException("This response type cannot handle an specific Media type");
    }
}
