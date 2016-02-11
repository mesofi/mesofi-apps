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

import java.util.List;

/**
 * Details about the columns in remote database.
 * 
 * @author Armando Rivas.
 * @since Feb 28 2014
 */
public class ColumnDetailsVo extends ColumnNameVo {
    private long refTableId;
    private String typeName;
    private int typeId;
    private int columnType;
    private int size;
    private String scale;
    private Boolean nullable;
    private Boolean autoIncrement;
    private String remarks;
    private List<String> permittedValues;

    /**
     * @return the refTableId
     */
    public long getRefTableId() {
        return refTableId;
    }

    /**
     * @param refTableId
     *            the refTableId to set
     */
    public void setRefTableId(long refTableId) {
        this.refTableId = refTableId;
    }

    /**
     * @return the typeName
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * @param typeName
     *            the typeName to set
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * @return the typeId
     */
    public int getTypeId() {
        return typeId;
    }

    /**
     * @param typeId
     *            the typeId to set
     */
    public void setTypeId(int typeId) {
        this.typeId = typeId;
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
    public String getScale() {
        return scale;
    }

    /**
     * @param scale
     *            the scale to set
     */
    public void setScale(String scale) {
        this.scale = scale;
    }

    /**
     * @return the nullable
     */
    public Boolean getNullable() {
        return nullable;
    }

    /**
     * @param nullable
     *            the nullable to set
     */
    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    /**
     * @return the autoIncrement
     */
    public Boolean getAutoIncrement() {
        return autoIncrement;
    }

    /**
     * @param autoIncrement
     *            the autoIncrement to set
     */
    public void setAutoIncrement(Boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks
     *            the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
     * @return the columnType
     */
    public int getColumnType() {
        return columnType;
    }

    /**
     * @param columnType
     *            the columnType to set
     */
    public void setColumnType(int columnType) {
        this.columnType = columnType;
    }

}
