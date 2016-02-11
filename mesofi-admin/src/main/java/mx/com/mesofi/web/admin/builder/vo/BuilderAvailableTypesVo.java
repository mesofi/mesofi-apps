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
 * This class contains information regarding the available types for a
 * particular code.
 * 
 * @author Armando Rivas
 * @since Dec 25 2014.
 */
public class BuilderAvailableTypesVo extends EntityVo {
    /**
     * The id of the java type.
     */
    private int javaTypeId;
    /**
     * The java type name.
     */
    private String javaTypeName;
    /**
     * Id this type is used or not.
     */
    private boolean used;

    /**
     * Creates a new object.
     * 
     * @param id
     *            The id type.
     * @param javaTypeName
     *            Description of the type.
     * @param used
     *            Is this type used or not.
     */
    public BuilderAvailableTypesVo(long id, int javaTypeId, String javaTypeName, boolean used) {
        this.setId(id);
        this.setJavaTypeId(javaTypeId);
        this.setJavaTypeName(javaTypeName);
        this.setUsed(used);
    }

    /**
     * Empty constructor
     */
    public BuilderAvailableTypesVo() {
    }

    /**
     * @return the javaTypeId
     */
    public int getJavaTypeId() {
        return javaTypeId;
    }

    /**
     * @param javaTypeId
     *            the javaTypeId to set
     */
    public void setJavaTypeId(int javaTypeId) {
        this.javaTypeId = javaTypeId;
    }

    /**
     * @return the javaTypeName
     */
    public String getJavaTypeName() {
        return javaTypeName;
    }

    /**
     * @param javaTypeName
     *            the javaTypeName to set
     */
    public void setJavaTypeName(String javaTypeName) {
        this.javaTypeName = javaTypeName;
    }

    /**
     * @return the used
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * @param used
     *            the used to set
     */
    public void setUsed(boolean used) {
        this.used = used;
    }

}
