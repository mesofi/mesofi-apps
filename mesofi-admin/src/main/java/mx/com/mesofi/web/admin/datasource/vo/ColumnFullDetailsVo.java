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

import java.util.ArrayList;
import java.util.List;

/**
 * Details about the columns in remote database.
 * 
 * @author Armando Rivas.
 * @since Feb 28 2014
 */
public class ColumnFullDetailsVo {
    /**
     * This flag states if config has been saved or not.
     */
    private boolean configSaved;
    /**
     * Table name.
     */
    private String tableName;
    /**
     * Connection identifier.
     */
    private Integer connId;
    /**
     * database or schema name.
     */
    private String databaseSchemaName;
    /**
     * This connection is database or schema?
     */
    private boolean database;
    /**
     * Total columns.
     */
    private int totalColumns;
    /**
     * Total of primary keys.
     */
    private int totalPrimaryKeys;
    /**
     * Metadata in columns.
     */
    private List<ColumnDetailsVo> metadata = new ArrayList<ColumnDetailsVo>();

    /**
     * @return the configSaved
     */
    public boolean isConfigSaved() {
        return configSaved;
    }

    /**
     * @param configSaved
     *            the configSaved to set
     */
    public void setConfigSaved(boolean configSaved) {
        this.configSaved = configSaved;
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
     * @return the databaseSchemaName
     */
    public String getDatabaseSchemaName() {
        return databaseSchemaName;
    }

    /**
     * @param databaseSchemaName
     *            the databaseSchemaName to set
     */
    public void setDatabaseSchemaName(String databaseSchemaName) {
        this.databaseSchemaName = databaseSchemaName;
    }

    /**
     * @return the database
     */
    public boolean isDatabase() {
        return database;
    }

    /**
     * @param database
     *            the database to set
     */
    public void setDatabase(boolean database) {
        this.database = database;
    }

    /**
     * @return the totalColumns
     */
    public int getTotalColumns() {
        return totalColumns;
    }

    /**
     * @param totalColumns
     *            the totalColumns to set
     */
    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    /**
     * @return the totalPrimaryKeys
     */
    public int getTotalPrimaryKeys() {
        return totalPrimaryKeys;
    }

    /**
     * @param totalPrimaryKeys
     *            the totalPrimaryKeys to set
     */
    public void setTotalPrimaryKeys(int totalPrimaryKeys) {
        this.totalPrimaryKeys = totalPrimaryKeys;
    }

    /**
     * @return the metadata
     */
    public List<ColumnDetailsVo> getMetadata() {
        return metadata;
    }

    /**
     * @param metadata
     *            the metadata to set
     */
    public void setMetadata(List<ColumnDetailsVo> metadata) {
        this.metadata = metadata;
    }
}
