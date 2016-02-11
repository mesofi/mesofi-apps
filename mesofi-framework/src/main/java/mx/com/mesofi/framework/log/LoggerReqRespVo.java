/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.framework.log;

import java.util.Date;
import java.util.Properties;

/**
 * Stores the properties for the request or the response.
 * 
 * @author Armando Rivas.
 * @since 12.05.2013
 */
public class LoggerReqRespVo {

    private Date transaction = new Date();
    private long requestId;
    private String httpMethod;
    private String httpRequest;
    private String httpContentType;
    private int httpContentLength;

    private String clientHost;
    private String clientAddr;
    private int clientPort;
    private String serverContentType;
    private int serverStatus;
    private Properties headers;
    private Properties requestBody;
    private String responseBody;

    /**
     * @return the transaction
     */
    public Date getTransaction() {
        return transaction;
    }

    /**
     * @param transaction
     *            the transaction to set
     */
    public void setTransaction(Date transaction) {
        this.transaction = transaction;
    }

    /**
     * @return the requestId
     */
    public long getRequestId() {
        return requestId;
    }

    /**
     * @param requestId
     *            the requestId to set
     */
    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    /**
     * @return the httpMethod
     */
    public String getHttpMethod() {
        return httpMethod;
    }

    /**
     * @param httpMethod
     *            the httpMethod to set
     */
    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    /**
     * @return the httpRequest
     */
    public String getHttpRequest() {
        return httpRequest;
    }

    /**
     * @param httpRequest
     *            the httpRequest to set
     */
    public void setHttpRequest(String httpRequest) {
        this.httpRequest = httpRequest;
    }

    /**
     * @return the httpContentType
     */
    public String getHttpContentType() {
        return httpContentType;
    }

    /**
     * @param httpContentType
     *            the httpContentType to set
     */
    public void setHttpContentType(String httpContentType) {
        this.httpContentType = httpContentType;
    }

    /**
     * @return the httpContentLength
     */
    public int getHttpContentLength() {
        return httpContentLength;
    }

    /**
     * @param httpContentLength
     *            the httpContentLength to set
     */
    public void setHttpContentLength(int httpContentLength) {
        this.httpContentLength = httpContentLength;
    }

    /**
     * @return the clientHost
     */
    public String getClientHost() {
        return clientHost;
    }

    /**
     * @param clientHost
     *            the clientHost to set
     */
    public void setClientHost(String clientHost) {
        this.clientHost = clientHost;
    }

    /**
     * @return the clientAddr
     */
    public String getClientAddr() {
        return clientAddr;
    }

    /**
     * @param clientAddr
     *            the clientAddr to set
     */
    public void setClientAddr(String clientAddr) {
        this.clientAddr = clientAddr;
    }

    /**
     * @return the clientPort
     */
    public int getClientPort() {
        return clientPort;
    }

    /**
     * @param clientPort
     *            the clientPort to set
     */
    public void setClientPort(int clientPort) {
        this.clientPort = clientPort;
    }

    /**
     * @return the serverContentType
     */
    public String getServerContentType() {
        return serverContentType;
    }

    /**
     * @param serverContentType
     *            the serverContentType to set
     */
    public void setServerContentType(String serverContentType) {
        this.serverContentType = serverContentType;
    }

    /**
     * @return the serverStatus
     */
    public int getServerStatus() {
        return serverStatus;
    }

    /**
     * @param serverStatus
     *            the serverStatus to set
     */
    public void setServerStatus(int serverStatus) {
        this.serverStatus = serverStatus;
    }

    /**
     * @return the headers
     */
    public Properties getHeaders() {
        return headers;
    }

    /**
     * @param headers
     *            the headers to set
     */
    public void setHeaders(Properties headers) {
        this.headers = headers;
    }

    /**
     * @return the requestBody
     */
    public Properties getRequestBody() {
        return requestBody;
    }

    /**
     * @param requestBody
     *            the requestBody to set
     */
    public void setRequestBody(Properties requestBody) {
        this.requestBody = requestBody;
    }

    /**
     * @return the responseBody
     */
    public String getResponseBody() {
        return responseBody;
    }

    /**
     * @param responseBody
     *            the responseBody to set
     */
    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

}
