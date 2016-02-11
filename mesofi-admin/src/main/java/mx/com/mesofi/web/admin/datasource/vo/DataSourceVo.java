/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.datasource.vo;

/**
 * Vo for storing database details in the connection.
 * 
 * @author Armando Rivas.
 * @since Feb 12 2014
 */
public class DataSourceVo extends DataSourceSimpleVo {
    private boolean serviceNameInUse;
    private int databaseTypeId;
    private String dbConnName;
    private String databaseName;
    private String host;
    private String url;
    private int port;
    private String user;
    private String password;

    /**
     * @return the serviceNameInUse
     */
    public boolean isServiceNameInUse() {
        return serviceNameInUse;
    }

    /**
     * @param serviceNameInUse
     *            the serviceNameInUse to set
     */
    public void setServiceNameInUse(boolean serviceNameInUse) {
        this.serviceNameInUse = serviceNameInUse;
    }

    /**
     * @return the databaseTypeId
     */
    public int getDatabaseTypeId() {
        return databaseTypeId;
    }

    /**
     * @param databaseTypeId
     *            the databaseTypeId to set
     */
    public void setDatabaseTypeId(int databaseTypeId) {
        this.databaseTypeId = databaseTypeId;
    }

    /**
     * @return the dbConnName
     */
    public String getDbConnName() {
        return dbConnName;
    }

    /**
     * @param dbConnName
     *            the dbConnName to set
     */
    public void setDbConnName(String dbConnName) {
        this.dbConnName = dbConnName;
    }

    /**
     * @return the databaseName
     */
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * @param databaseName
     *            the databaseName to set
     */
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host
     *            the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port
     *            the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DataSourceVo [databaseType=" + getDatabaseType() + ", dbConnName=" + dbConnName + ", databaseName="
                + databaseName + ", host=" + host + ", url=" + url + ", port=" + port + ", user=" + user
                + ", password=" + password + "]";
    }

}
