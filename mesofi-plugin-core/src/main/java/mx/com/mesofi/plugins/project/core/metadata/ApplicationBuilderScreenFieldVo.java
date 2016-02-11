/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core.metadata;

import java.util.List;

/**
 * Contains all the attributes for creating a new field.
 * 
 * @author Armando Rivas
 * @since Mar 18 2014.
 */
public class ApplicationBuilderScreenFieldVo {
    /**
     * Field name.
     */
    private String fieldName;
    /**
     * Same as field name but as a label.
     */
    private String fieldLabel;
    /**
     * Same as field name but as a Java Type. (e.g. camel case)
     */
    private String fieldJavaType;
    /**
     * This is the corresponding database type.
     */
    private String fieldDataBaseType;
    /**
     * Contains the type to be cast into a java type.
     */
    private String fieldJavaCastType;
    /**
     * Type of this field.
     */
    private String fieldType;
    /**
     * Same as fieldType, but this field holds the fully qualified name of the
     * java type.
     */
    private String fieldFullyNameType;
    /**
     * Standard SQL type of this field.
     */
    private int fieldSqlType;
    /**
     * Permitted values.
     */
    private List<String> permittedValues;
    /**
     * The size.
     */
    private int size;
    /**
     * Number of fraction digits, default it to -1, this means, no applicable.
     */
    private int fractionDigits = -1;
    /**
     * States whether this field is optional or not.
     */
    private boolean optional;
    /**
     * States whether this field is the pk or not.
     */
    private boolean primaryKey;
    /**
     * States whether this is an auto incremented field or not.
     */
    private boolean autoIncrement;

    /**
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName
     *            the fieldName to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return the fieldLabel
     */
    public String getFieldLabel() {
        return fieldLabel;
    }

    /**
     * @param fieldLabel
     *            the fieldLabel to set
     */
    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    /**
     * @return the fieldJavaType
     */
    public String getFieldJavaType() {
        return fieldJavaType;
    }

    /**
     * @param fieldJavaType
     *            the fieldJavaType to set
     */
    public void setFieldJavaType(String fieldJavaType) {
        this.fieldJavaType = fieldJavaType;
    }

    /**
     * @return the fieldDataBaseType
     */
    public String getFieldDataBaseType() {
        return fieldDataBaseType;
    }

    /**
     * @param fieldDataBaseType
     *            the fieldDataBaseType to set
     */
    public void setFieldDataBaseType(String fieldDataBaseType) {
        this.fieldDataBaseType = fieldDataBaseType;
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

    /**
     * @return the fieldType
     */
    public String getFieldType() {
        return fieldType;
    }

    /**
     * @param fieldType
     *            the fieldType to set
     */
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * @return the fieldFullyNameType
     */
    public String getFieldFullyNameType() {
        return fieldFullyNameType;
    }

    /**
     * @param fieldFullyNameType
     *            the fieldFullyNameType to set
     */
    public void setFieldFullyNameType(String fieldFullyNameType) {
        this.fieldFullyNameType = fieldFullyNameType;
    }

    /**
     * @return the fieldSqlType
     */
    public int getFieldSqlType() {
        return fieldSqlType;
    }

    /**
     * @param fieldSqlType
     *            the fieldSqlType to set
     */
    public void setFieldSqlType(int fieldSqlType) {
        this.fieldSqlType = fieldSqlType;
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
     * @return the fractionDigits
     */
    public int getFractionDigits() {
        return fractionDigits;
    }

    /**
     * @param fractionDigits
     *            the fractionDigits to set
     */
    public void setFractionDigits(int fractionDigits) {
        this.fractionDigits = fractionDigits;
    }

    /**
     * @return the optional
     */
    public boolean isOptional() {
        return optional;
    }

    /**
     * @param optional
     *            the optional to set
     */
    public void setOptional(boolean optional) {
        this.optional = optional;
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "[" + primaryKey + "]" + fieldName;
    }

}
