/*
 * COPYRIGHT. Mesofi 2015. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */

/**
 * An error occurred in processing the request, i.e. an exception was thrown
 * 
 * @author Mesofi
 * @since Sun Nov 01 18:20:31 CST 2015
 */
public class ErrorResponse extends Response {
    /**
     * Descritpion of the error.
     */
    private String message;
    /**
     * Trace of the error.
     */
    private String data;

    /**
     * Creates an error for the response.
     */
    public ErrorResponse() {
        super(500, "error");
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

}
