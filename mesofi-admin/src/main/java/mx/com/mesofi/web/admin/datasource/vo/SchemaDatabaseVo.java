package mx.com.mesofi.web.admin.datasource.vo;

import mx.com.mesofi.framework.util.EntityVo;

public class SchemaDatabaseVo extends EntityVo {
    private String schemaDatabase;
    private boolean defaultSchemaDb;
    private boolean database;

    /**
     * @return the schemaDatabase
     */
    public String getSchemaDatabase() {
        return schemaDatabase;
    }

    /**
     * @param schemaDatabase
     *            the schemaDatabase to set
     */
    public void setSchemaDatabase(String schemaDatabase) {
        this.schemaDatabase = schemaDatabase;
    }

    /**
     * @return the defaultSchemaDb
     */
    public boolean isDefaultSchemaDb() {
        return defaultSchemaDb;
    }

    /**
     * @param defaultSchemaDb
     *            the defaultSchemaDb to set
     */
    public void setDefaultSchemaDb(boolean defaultSchemaDb) {
        this.defaultSchemaDb = defaultSchemaDb;
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

}
