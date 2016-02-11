/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */

package mx.com.mesofi.framework.error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles the validation business exception.
 * 
 * @author Armando Rivas
 * @since Nov 09 2013
 */
public class ValidationBusinessException extends BusinessException {

    /**
     * serial version.
     */
    private static final long serialVersionUID = 7199943459786636705L;

    /**
     * Fields containing the field name and description of the error.
     */
    private Map<String, List<String>> fields;
    /**
     * Description of the error.
     */
    private String field;
    /**
     * Default error message.
     */
    private static final String DEFAULT_MSG = "Error in fields";
    /**
     * Default error message error.
     */
    private static final String DEFAULT_MSG_ERR = "There is no configuration for fields in validation";

    /**
     * Creates a new exception for handling the validation business.
     * 
     * @param msg
     *            Error message.
     */
    public ValidationBusinessException(String msg) {
        super(msg);
        this.field = msg;
    }

    /**
     * Creates a new exception for handling the validation business. This
     * particular constructor is meant to used to give any field a valid
     * description of the validation. This constructor also supports the used of
     * several descriptions for a particular field.
     * 
     * @param fields
     *            The field name and the list of descriptions for each field.
     *            Notice that the key for this Map should be the name of the
     *            field and the value in this Map should contain the description
     *            of the validation. This description can be one or more.
     */
    public ValidationBusinessException(Map<String, List<String>> fields) {
        super(fields == null ? DEFAULT_MSG_ERR : DEFAULT_MSG);
        this.fields = fields;
    }

    /**
     * Creates a new exception for handling the validation business. This
     * particular constructor is meant to used to give any field a valid
     * description of the validation. This constructor also supports the used of
     * several descriptions for a particular field.
     * 
     * @param fieldName
     *            Field name.
     * @param descriptions
     *            List of descriptions associated to this field.
     */
    public ValidationBusinessException(String fieldName, List<String> descriptions) {
        super(fieldName == null ? DEFAULT_MSG_ERR : DEFAULT_MSG);
        this.fields = new HashMap<String, List<String>>();
        this.fields.put(fieldName, descriptions);
    }

    /**
     * Creates a new exception for handling the validation business. This
     * particular constructor is meant to used to give any field a valid
     * description of the validation. This constructor also supports the used of
     * several descriptions for a particular field.
     * 
     * @param fieldName
     *            Field name.
     * @param description
     *            Description for this field.
     */
    public ValidationBusinessException(String fieldName, String description) {
        super(fieldName == null ? DEFAULT_MSG_ERR : DEFAULT_MSG);
        this.fields = new HashMap<String, List<String>>();
        List<String> desc = new ArrayList<String>();
        desc.add(description);
        this.fields.put(fieldName, desc);
    }

    /**
     * @return the fields
     */
    public Map<String, List<String>> getFields() {
        return fields;
    }

    /**
     * @return the field
     */
    public String getField() {
        return field;
    }

}
