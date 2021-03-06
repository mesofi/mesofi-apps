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
 * The success response.
 * 
 * @author Mesofi
 * @since Sun Nov 01 18:20:31 CST 2015
 */
public class SuccessResponse extends Response {
    /**
     * The actual data for the success response.
     */
    private Object data;

    /**
     * Creates a success response.
     */
    public SuccessResponse() {
        super(0, "success");
    }

    /**
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }

}
