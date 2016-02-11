package mx.com.mesofi.web.admin.datasource.vo;

import mx.com.mesofi.framework.util.LabelEntityVo;

public class ColumnNameVo extends LabelEntityVo {
    /**
     * The primary Key.
     */
    private boolean primaryKey;
    /**
     * The foreign Key.
     */
    private boolean foreignKey;
    /**
     * Contains the configuration for references for a given table.
     */
    private String refTableName;
    /**
     * Contains the configuration for references for a given column.
     */
    private String refColumnName;

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
     * @return the foreignKey
     */
    public boolean isForeignKey() {
        return foreignKey;
    }

    /**
     * @param foreignKey
     *            the foreignKey to set
     */
    public void setForeignKey(boolean foreignKey) {
        this.foreignKey = foreignKey;
    }

    /**
     * @return the refTableName
     */
    public String getRefTableName() {
        return refTableName;
    }

    /**
     * @param refTableName
     *            the refTableName to set
     */
    public void setRefTableName(String refTableName) {
        this.refTableName = refTableName;
    }

    /**
     * @return the refColumnName
     */
    public String getRefColumnName() {
        return refColumnName;
    }

    /**
     * @param refColumnName
     *            the refColumnName to set
     */
    public void setRefColumnName(String refColumnName) {
        this.refColumnName = refColumnName;
    }

    /**
     * Sets the ref table and column name.
     * 
     * @param refTableName
     *            The ref table name.
     * @param refColumnName
     *            The ref column name.
     */
    public void setReferences(String refTableName, String refColumnName) {
        this.refTableName = refTableName;
        this.refColumnName = refColumnName;
    }
}
