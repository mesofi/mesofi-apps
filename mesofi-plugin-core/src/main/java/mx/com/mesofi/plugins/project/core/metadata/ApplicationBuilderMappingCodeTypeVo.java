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

import java.util.HashMap;
import java.util.Map;

import mx.com.mesofi.framework.util.EntityVo;

/**
 * This class contains information regarding mapping types and code in order to
 * create the custom code.
 * 
 * @author Armando Rivas
 * @since Dec 02 2014.
 */
public class ApplicationBuilderMappingCodeTypeVo extends EntityVo {
    private String pointCutName;
    private String code;
    private int idType;
    private String javaType;
    private Map<Integer, String> types;

    /**
     * Initialized the types.
     */
    public ApplicationBuilderMappingCodeTypeVo() {
        types = new HashMap<Integer, String>();
    }

    /**
     * @return the pointCutName
     */
    public String getPointCutName() {
        return pointCutName;
    }

    /**
     * @param pointCutName
     *            the pointCutName to set
     */
    public void setPointCutName(String pointCutName) {
        this.pointCutName = pointCutName;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the idType
     */
    public int getIdType() {
        return idType;
    }

    /**
     * @param idType
     *            the idType to set
     */
    public void setIdType(int idType) {
        this.idType = idType;
    }

    /**
     * @return the javaType
     */
    public String getJavaType() {
        return javaType;
    }

    /**
     * @param javaType
     *            the javaType to set
     */
    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    /**
     * @return the types
     */
    public Map<Integer, String> getTypes() {
        return types;
    }

    /**
     * @param types
     *            the types to set
     */
    public void setTypes(Map<Integer, String> types) {
        this.types = types;
    }

    public void put(int idType, String javaType) {
        types.put(idType, javaType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = (int) (prime * result + getId());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ApplicationBuilderMappingCodeTypeVo other = (ApplicationBuilderMappingCodeTypeVo) obj;
        if (getId() != other.getId())
            return false;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return pointCutName + ", types=" + types;
    }

}
