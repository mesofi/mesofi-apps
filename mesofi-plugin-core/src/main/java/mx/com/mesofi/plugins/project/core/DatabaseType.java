/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core;

/**
 * This class contains several constants related to the database type.
 * 
 * @author Armando Rivas
 * @since 11 Feb 2014
 */
public enum DatabaseType {
    /**
     * Oracle.
     */
    ORACLE("org.hibernate.dialect.OracleDialect", "oracle.jdbc.driver.OracleDriver", "com.oracle", "ojdbc14", 0.1),
    /**
     * MySql.
     */
    MY_SQL("org.hibernate.dialect.MySQLDialect", "com.mysql.jdbc.Driver", "mysql", "mysql-connector-java", "5.1.6"),
    /**
     * Db2.
     */
    DB2("org.hibernate.dialect.DB2Dialect", ""),
    /**
     * HSQLDB.
     */
    HSQLDB("org.hibernate.dialect.HSQLDialect", "org.hsqldb.jdbcDriver"),
    /**
     * SQL server.
     */
    SQL_SERVER("org.hibernate.dialect.SQLServerDialect", ""),
    /**
     * Postgresql
     */
    POSTGRESQL("org.hibernate.dialect.PostgreSQLDialect", "org.postgresql.Driver"),
    /**
     * Derby.
     */
    DERBY("", "org.apache.derby.jdbc.ClientDriver");
    /**
     * Stores the identifier for the SQL dialect.
     */
    private String sqlDialect;
    /**
     * Stores the identifier for the driver class name.
     */
    private String driverClassName;
    /**
     * The groupId.
     */
    private String groupId;
    /**
     * The artifact Id.
     */
    private String artifactId;
    /**
     * The version.
     */
    private String version;

    /**
     * Sets the identifier for the driverClassName.
     * 
     * @param sqlDialect
     *            Constant related to the SQL Dialect.
     * @param driverClassName
     *            Constant related to the driverClassName.
     * @see #DatabaseType(String, String, String, double)
     * @see #DatabaseType(String, String, String)
     */
    private DatabaseType(final String sqlDialect, final String driverClassName) {
        this.sqlDialect = sqlDialect;
        this.driverClassName = driverClassName;
    }

    /**
     * Sets the identifier for the driverClassName among other properties
     * related to the database.
     * 
     * @param sqlDialect
     *            Constant related to the sqlDialect.
     * @param driverClassName
     *            Constant related to the driverClassName.
     * @param groupId
     *            The groupId.
     * @param artifactId
     *            The artifactId.
     * @param version
     *            The versionId.
     * @see #DatabaseType(String, String, String, double)
     * @see #DatabaseType(String)
     */
    private DatabaseType(final String sqlDialect, final String driverClassName, String groupId, String artifactId,
            String version) {
        this(sqlDialect, driverClassName);
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    /**
     * Sets the identifier for the driverClassName among other properties
     * related to the database. Using this version, is possible to pass a number
     * for the artifact version.
     * 
     * @param sqlDialect
     *            Constant related to the sqlDialect.
     * @param driverClassName
     *            Constant related to the driverClassName.
     * @param groupId
     *            The groupId.
     * @param artifactId
     *            The artifactId.
     * @param version
     *            The versionId.
     * @see #DatabaseType(String, String, String, String)
     * @see #DatabaseType(String)
     */
    private DatabaseType(final String sqlDialect, final String driverClassName, String groupId, String artifactId,
            double version) {
        this(sqlDialect, driverClassName, groupId, artifactId, String.valueOf(version));
    }

    /**
     * Get the constant for the driverClassName.
     * 
     * @return Identifier for the driverClassName.
     */
    public String getDriverClassName() {
        return driverClassName;
    }

    /**
     * @return the sqlDialect
     */
    public String getSqlDialect() {
        return sqlDialect;
    }

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @return the artifactId
     */
    public String getArtifactId() {
        return artifactId;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Gets the database name for a given identifier.
     * 
     * @param databaseType
     *            The database type.
     * @return The formal name.
     */
    public static String getFormalName(int databaseType) {
        switch (databaseType) {
        case 1:
            return "Oracle";
        case 2:
            return "MySql";
        case 3:
            return "Db2";
        case 4:
            return "Hsql";
        case 5:
            return "Sql Server";
        case 6:
            return "Postgresql";
        case 7:
            return "derby";
        }
        throw new IllegalStateException("There is no database formal name for this identifier [" + databaseType
                + "] given");
    }

    /**
     * Gets the database type given the identifier.
     * 
     * @param databaseType
     *            Identifier for the database.
     * @return The database type.
     */
    public static DatabaseType valueOf(int databaseType) {
        switch (databaseType) {
        case 1:
            return ORACLE;
        case 2:
            return MY_SQL;
        case 3:
            return DB2;
        case 4:
            return HSQLDB;
        case 5:
            return SQL_SERVER;
        case 6:
            return POSTGRESQL;
        case 7:
            return DERBY;
        }
        throw new IllegalStateException("There is no database type for this identifier [" + databaseType + "] given");
    }

    public int getId(String enumString) {
        int id = 0;
        if (DatabaseType.ORACLE.toString().equals(enumString)) {
            id = 1;
        } else if (DatabaseType.MY_SQL.toString().equals(enumString)) {
            id = 2;
        } else if (DatabaseType.DB2.toString().equals(enumString)) {
            id = 3;
        } else if (DatabaseType.HSQLDB.toString().equals(enumString)) {
            id = 4;
        } else if (DatabaseType.SQL_SERVER.toString().equals(enumString)) {
            id = 5;
        } else if (DatabaseType.POSTGRESQL.toString().equals(enumString)) {
            id = 6;
        } else if (DatabaseType.DERBY.toString().equals(enumString)) {
            id = 7;
        } else
            throw new IllegalStateException("There is no database type for this string identifier [" + enumString
                    + "] given");
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.valueOf(this.name());
    }

}
