/*
 * COPYRIGHT. Mesofi 2015. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

/**
 * This abstract class is meant to be used only for extension. Every class MUST
 * extend from this class in order simplify the use of the controller.
 *
 * @author Mesofi
 * @since Sun Nov 01 18:20:31 CST 2015
 */
public abstract class AbstractController {

    /**
     * log4j.
     */
    private static final Logger log = Logger.getLogger(AbstractController.class);

    /**
     * Autowires the message source to support the use of in18n.
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * Returns a valid response.
     * 
     * @param object
     *            The object to be returned.
     * @return Wrapping object.
     */
    protected Response json(Object object) {
        if (object instanceof Throwable) {
            ErrorResponse response = new ErrorResponse();
            Throwable e = (Throwable) object;

            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));

            response.setData(errors.toString());
            response.setMessage(e.getMessage());

            return response;
        } else {
            SuccessResponse response = new SuccessResponse();
            response.setData(object);
            return response;
        }
    }

    /**
     * Returns a valid response as validation.
     * 
     * @param object
     *            The object to be returned.
     * @param isValidation
     *            . This response is validation or not.
     * @return The actual validation response.
     */
    @SuppressWarnings("unchecked")
    protected Response json(Object object, boolean isValidation) {
        if (isValidation) {
            FailResponse failResponse = new FailResponse();
            if (object instanceof Map) {
                failResponse.setData((Map<String, String>) object);
                return failResponse;
            } else {
                return json(new Exception("Type not suitable for validations: " + object.getClass()
                        + " not allowed, expected type: " + Map.class));
            }
        } else {
            return json(object);
        }
    }

    /**
     * Gets or resolve the content of a messages given its identifier and a
     * locale.
     * 
     * @param key
     *            The message key.
     * @param locale
     *            The current locale.
     * @return The content of the message.
     */
    protected String getMessage(String key, Locale locale) {
        String[] os = null;
        return messageSource.getMessage(key, os, locale);
    }

    /**
     * Gets or resolve the content of a messages given its identifier.
     * 
     * @param key
     *            The message key.
     * @return The content of the message.
     */
    protected String getMessage(String key) {
        return getMessage(key, Locale.getDefault());
    }
}
