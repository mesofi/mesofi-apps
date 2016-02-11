/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.response;

/**
 * This StreamResponse handles the response in stream for the client. Use this
 * type when you want to download certain existing file located in the server.
 * 
 * @author Armando Rivas.
 * @since Mar 03 2014
 */
public class FullWebResponse {
    /**
     * The content of the response.
     */
    private String content;
    /**
     * Reference to the type of response handled by the framework.
     */
    private WebResponse webResponse;

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the webResponse
     */
    public WebResponse getWebResponse() {
        return webResponse;
    }

    /**
     * @param webResponse
     *            the webResponse to set
     */
    public void setWebResponse(WebResponse webResponse) {
        this.webResponse = webResponse;
    }

}
