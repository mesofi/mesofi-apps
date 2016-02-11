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

import mx.com.mesofi.framework.util.LabelEntityVo;
import mx.com.mesofi.plugins.project.core.DatabaseType;

/**
 * Vo for storing database a few details in the connection.
 * 
 * @author Armando Rivas.
 * @since April 19 2015
 */
public class DataSourceSimpleVo extends LabelEntityVo {
    /**
     * The database type.
     */
    private DatabaseType databaseType;
    /**
     * The database formal name.
     */
    private String databaseFormalName;

    /**
     * @return the databaseFormalName
     */
    public String getDatabaseFormalName() {
        return databaseFormalName;
    }

    /**
     * @param databaseFormalName
     *            the databaseFormalName to set
     */
    public void setDatabaseFormalName(String databaseFormalName) {
        this.databaseFormalName = databaseFormalName;
    }

    /**
     * @return the databaseType
     */
    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    /**
     * @param databaseType
     *            the databaseType to set
     */
    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DataSourceSimpleVo [databaseType=" + databaseType + ", databaseFormalName=" + databaseFormalName + "]";
    }

}
