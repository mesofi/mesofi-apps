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
import mx.com.mesofi.plugins.project.core.DatabaseType;

/**
 * This class contains information regarding mapping types among sql, java and
 * database specific types.
 * 
 * @author Armando Rivas
 * @since Nov 24 2014.
 */
public class BuilderMappingTypeVo extends EntityVo {
    /**
     * Database type.
     */
    private DatabaseType databaseType;
    /**
     * The specific database type.
     */
    private String database;
    /**
     * Identifier for the SQL type.
     */
    private int idSql;
    /**
     * The actual SQL type.
     */
    private String sql;
    /**
     * The java type.
     */
    private String java;
    /**
     * The effective type.
     */
    private boolean effectiveType;

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

    /**
     * @return the database
     */
    public String getDatabase() {
        return database;
    }

    /**
     * @param database
     *            the database to set
     */
    public void setDatabase(String database) {
        this.database = database;
    }

    /**
     * @return the idSql
     */
    public int getIdSql() {
        return idSql;
    }

    /**
     * @param idSql
     *            the idSql to set
     */
    public void setIdSql(int idSql) {
        this.idSql = idSql;
    }

    /**
     * @return the sql
     */
    public String getSql() {
        return sql;
    }

    /**
     * @param sql
     *            the sql to set
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * @return the java
     */
    public String getJava() {
        return java;
    }

    /**
     * @param java
     *            the java to set
     */
    public void setJava(String java) {
        this.java = java;
    }

    /**
     * @return the effectiveType
     */
    public boolean isEffectiveType() {
        return effectiveType;
    }

    /**
     * @param effectiveType
     *            the effectiveType to set
     */
    public void setEffectiveType(boolean effectiveType) {
        this.effectiveType = effectiveType;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "[" + databaseType + "]:" + database;
    }

}
