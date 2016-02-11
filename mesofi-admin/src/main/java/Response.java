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
 * Contains the logic to manage the request from clients.
 * 
 * @author Mesofi
 * @since Sun Nov 01 18:20:31 CST 2015
 */
public class Response {
    /**
     * Depending on the response, this code might return 0 (success), 500
     * (error) or 300 (fail)
     */
    private final int code;
    /**
     * Readable status of the response.
     */
    private final String status;

    /**
     * Creates a response using a code and a status.
     * 
     * @param code
     *            The code.
     * @param status
     *            The status.
     */
    public Response(int code, String status) {
        this.code = code;
        this.status = status;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Response [code=" + code + ", status=" + status + "]";
    }

}
