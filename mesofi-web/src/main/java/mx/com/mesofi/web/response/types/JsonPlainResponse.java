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

import java.io.IOException;

import mx.com.mesofi.web.response.ConversionFormatException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Class to handle the JSON responses. This specific class returns any JSON
 * format.
 * 
 * @author Armando Rivas.
 * @since Feb 15 2014
 * @see mx.com.mesofi.web.response.types.JsonResponse
 */
public class JsonPlainResponse extends JsonResponse {
    /**
     * Creates a new object to return a JSON response.
     * 
     * @param object
     *            Object to be converted.
     */
    public JsonPlainResponse(Object object) {
        super(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResponse() throws ConversionFormatException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(getObjectToConvert());
        } catch (JsonGenerationException e) {
            throw new ConversionFormatException(e);
        } catch (JsonMappingException e) {
            throw new ConversionFormatException(e);
        } catch (IOException e) {
            throw new ConversionFormatException(e);
        }
    }
}
