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

import java.util.List;

/**
 * Details about the columns in remote database.
 * 
 * @author Armando Rivas.
 * @since Mar 17 2014
 */
public class BuilderAppConfigVo {
    /**
     * The connection name.
     */
    private String connName;
    /**
     * The table name.
     */
    private String tableName;
    /**
     * The column name.
     */
    private String columnName;
    /**
     * The column type.
     */
    private String type;
    /**
     * Permitted values for this column.
     */
    private List<String> permittedValues;
    /**
     * The column size.
     */
    private int size;
    /**
     * The scale.
     */
    private Integer scale;
    /**
     * The nullable.
     */
    private boolean nullable;
    /**
     * States if this field is primary key or not.
     */
    private boolean primaryKey;
    /**
     * States if this field is auto incremented.
     */
    private boolean autoIncrement;
    /**
     * The identifier for the SQL type.
     */
    private int sqlType;
    /**
     * The java type that can be casted.
     */
    private String fieldJavaCastType;

    /**
     * @return the connName
     */
    public String getConnName() {
        return connName;
    }

    /**
     * @param connName
     *            the connName to set
     */
    public void setConnName(String connName) {
        this.connName = connName;
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
     * @return the columnName
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param columnName
     *            the columnName to set
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the permittedValues
     */
    public List<String> getPermittedValues() {
        return permittedValues;
    }

    /**
     * @param permittedValues
     *            the permittedValues to set
     */
    public void setPermittedValues(List<String> permittedValues) {
        this.permittedValues = permittedValues;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size
     *            the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the scale
     */
    public Integer getScale() {
        return scale;
    }

    /**
     * @param scale
     *            the scale to set
     */
    public void setScale(Integer scale) {
        this.scale = scale;
    }

    /**
     * @return the nullable
     */
    public boolean isNullable() {
        return nullable;
    }

    /**
     * @param nullable
     *            the nullable to set
     */
    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    /**
     * @return the primaryKey
     */
    public boolean isPrimaryKey() {
        return primaryKey;
    }

    /**
     * @param primaryKey
     *            the primaryKey to set
     */
    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * @return the autoIncrement
     */
    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    /**
     * @param autoIncrement
     *            the autoIncrement to set
     */
    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    /**
     * @return the sqlType
     */
    public int getSqlType() {
        return sqlType;
    }

    /**
     * @param sqlType
     *            the sqlType to set
     */
    public void setSqlType(int sqlType) {
        this.sqlType = sqlType;
    }

    /**
     * @return the fieldJavaCastType
     */
    public String getFieldJavaCastType() {
        return fieldJavaCastType;
    }

    /**
     * @param fieldJavaCastType
     *            the fieldJavaCastType to set
     */
    public void setFieldJavaCastType(String fieldJavaCastType) {
        this.fieldJavaCastType = fieldJavaCastType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BuilderAppConfigVo [tableName=" + tableName + "]";
    }

}
