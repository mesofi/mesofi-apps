/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.builder.vo;

import mx.com.mesofi.framework.util.EntityVo;

/**
 * Vo that contains properties for the configuration of application.
 * 
 * @author Armando Rivas
 * @since Mar 01 2014.
 */
public class BuilderAppVo extends EntityVo {
    private Integer connId;
    private Boolean database;
    private String databaseSchema;
    private String tableName;

    /**
     * @return the connId
     */
    public Integer getConnId() {
        return connId;
    }

    /**
     * @param connId
     *            the connId to set
     */
    public void setConnId(Integer connId) {
        this.connId = connId;
    }

    /**
     * @return the database
     */
    public Boolean getDatabase() {
        return database;
    }

    /**
     * @param database
     *            the database to set
     */
    public void setDatabase(Boolean database) {
        this.database = database;
    }

    /**
     * @return the databaseSchema
     */
    public String getDatabaseSchema() {
        return databaseSchema;
    }

    /**
     * @param databaseSchema
     *            the databaseSchema to set
     */
    public void setDatabaseSchema(String databaseSchema) {
        this.databaseSchema = databaseSchema;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName
     *            the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
