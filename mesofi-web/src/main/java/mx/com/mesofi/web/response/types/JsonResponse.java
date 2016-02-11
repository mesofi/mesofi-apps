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

import mx.com.mesofi.web.response.AbstractResponse;
import mx.com.mesofi.web.response.ConversionFormatException;
import mx.com.mesofi.web.response.WebResponse;
import mx.com.mesofi.web.util.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Class to handle the JSON responses. This class contains the original JSON
 * response and add some specific format for Mesofi Framework. A typical format
 * for JSON contains the following format:
 * 
 * <pre>
 * {
 * "success": true,
 * "data": {
 *   "firstName": "John",
 *   "lastName": "Smith",
 *   "gender": "man",
 *   "age": 32,
 *   "address": {
 *     "streetAddress": "21 2nd Street",
 *     "city": "New York",
 *     "state": "NY",
 *     "postalCode": "10021"
 *   },
 *   "phoneNumbers": [
 *     {
 *       "type": "home",
 *       "number": "212 555-1234"
 *     },
 *     {
 *       "type": "fax",
 *       "number": "646 555-4567"
 *     }
 *   ]
 *  }
 * }
 * </pre>
 * 
 * As explain above, the extra information added is:<br/>
 * 
 * <pre>
 * {
 * "success": true,
 * "data": {  }
 * }
 * If you wish to return only the JSON response without the custom information use the following class: 
 * {@link mx.com.mesofi.web.response.types.JsonPlainResponse}
 * 
 * @author Armando Rivas.
 * @since Oct 11 2013
 */
public class JsonResponse extends AbstractResponse implements WebResponse {

    /**
     * Indicates if the response was success or not.
     */
    private boolean success = true;

    /**
     * Creates this object given a valid object.
     * 
     * @param object
     *            Object to be converted.
     */
    public JsonResponse(Object object) {
        setObjectToConvert(object, JsonResponse.class);
    }

    /**
     * Creates this object given a valid object and a status for given object.
     * 
     * @param object
     *            Object to be converted.
     * @param success
     *            Status of the response.
     */
    public JsonResponse(Object object, boolean success) {
        this(object);
        this.success = success;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResponse() throws ConversionFormatException {
        ObjectMapper mapper = new ObjectMapper();
        String response = null;
        try {
            response = mapper.writeValueAsString(getObjectToConvert());
            if (success) {
                return prepareOutput(createFinalResponse(response, "data"));
            } else {
                return prepareOutput(createFinalResponse(response, "message"));
            }
        } catch (JsonGenerationException e) {
            throw new ConversionFormatException(e);
        } catch (JsonMappingException e) {
            throw new ConversionFormatException(e);
        } catch (IOException e) {
            throw new ConversionFormatException(e);
        }
    }

    /**
     * Returns a JSON string based on the object given in the constructor.
     * 
     * @return Object's JSON.
     * @throws ConversionFormatException
     *             In case the transformation could not be made.
     */
    public String getJson() throws ConversionFormatException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(getObjectToConvert());
        } catch (IOException e) {
            throw new ConversionFormatException(e);
        }
    }

    /**
     * Creates the final response for the end user.
     * 
     * @param response
     *            Response coming from the controller layer.
     * @param tag
     *            Type of element in JSON.
     * @return The final response.
     */
    private String createFinalResponse(String response, String tag) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"success\":");
        sb.append(success);
        sb.append(",\"" + tag + "\":");
        sb.append(response);
        sb.append("}");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MediaType getMediaType() {
        return MediaType.APPLICATION_JSON_TYPE;
    }
}
