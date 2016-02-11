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
 * particular code, this one includes the relation between them.
 * 
 * @author Armando Rivas
 * @since Dec 25 2014.
 */
public class BuilderCodeTypeVo extends EntityVo {
    /**
     * Code identifier.
     */
    private int idCode;
    /**
     * Type identifier.
     */
    private int idType;

    /**
     * @return the idCode
     */
    public int getIdCode() {
        return idCode;
    }

    /**
     * @param idCode
     *            the idCode to set
     */
    public void setIdCode(int idCode) {
        this.idCode = idCode;
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BuilderCodeTypeVo [getId()=" + getId() + ", idCode=" + idCode + ", idType=" + idType + "]";
    }

}
