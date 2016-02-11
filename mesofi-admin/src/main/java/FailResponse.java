/*
 * COPYRIGHT. Mesofi 2015. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * There was a problem with the data submitted, or some pre-condition of the API
 * call wasn't satisfied.
 * 
 * @author Mesofi
 * @since Sun Nov 01 18:20:31 CST 2015
 */
public class FailResponse extends Response {
    /**
     * Contains the errors or validations.
     */
    private Map<String, String> data = new HashMap<String, String>();

    /**
     * Creates a fail response.
     */
    public FailResponse() {
        super(300, "fail");
    }

    /**
     * Add the validation.
     * 
     * @param fieldName
     *            Field name.
     * @param error
     *            The actual error description.
     */
    public void addValidation(String fieldName, String error) {
        data.put(fieldName, error);
    }

    /**
     * @return the data
     */
    public Map<String, String> getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(Map<String, String> data) {
        this.data = data;
    }

}
