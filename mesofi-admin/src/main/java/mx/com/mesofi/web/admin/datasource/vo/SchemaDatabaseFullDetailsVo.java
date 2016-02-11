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

import java.util.ArrayList;
import java.util.List;

/**
 * Details about the schema or database in remote database.
 * 
 * @author Armando Rivas.
 * @since Feb 27 2014
 */
public class SchemaDatabaseFullDetailsVo {
    private String schemaOrDatabase;
    private int totalTables;
    private int totalViews;
    private int totalSystemTables;
    private int totalGlobalTemporary;
    private int totalLocalTemporary;
    private int totalAlias;
    private int totalSynonym;
    private List<SchemaDatabaseDetailsVo> metadata = new ArrayList<SchemaDatabaseDetailsVo>();

    /**
     * @return the schemaOrDatabase
     */
    public String getSchemaOrDatabase() {
        return schemaOrDatabase;
    }

    /**
     * @param schemaOrDatabase
     *            the schemaOrDatabase to set
     */
    public void setSchemaOrDatabase(String schemaOrDatabase) {
        this.schemaOrDatabase = schemaOrDatabase;
    }

    /**
     * @return the totalTables
     */
    public int getTotalTables() {
        return totalTables;
    }

    /**
     * @param totalTables
     *            the totalTables to set
     */
    public void setTotalTables(int totalTables) {
        this.totalTables = totalTables;
    }

    /**
     * @return the totalViews
     */
    public int getTotalViews() {
        return totalViews;
    }

    /**
     * @param totalViews
     *            the totalViews to set
     */
    public void setTotalViews(int totalViews) {
        this.totalViews = totalViews;
    }

    /**
     * @return the totalSystemTables
     */
    public int getTotalSystemTables() {
        return totalSystemTables;
    }

    /**
     * @param totalSystemTables
     *            the totalSystemTables to set
     */
    public void setTotalSystemTables(int totalSystemTables) {
        this.totalSystemTables = totalSystemTables;
    }

    /**
     * @return the totalGlobalTemporary
     */
    public int getTotalGlobalTemporary() {
        return totalGlobalTemporary;
    }

    /**
     * @param totalGlobalTemporary
     *            the totalGlobalTemporary to set
     */
    public void setTotalGlobalTemporary(int totalGlobalTemporary) {
        this.totalGlobalTemporary = totalGlobalTemporary;
    }

    /**
     * @return the totalLocalTemporary
     */
    public int getTotalLocalTemporary() {
        return totalLocalTemporary;
    }

    /**
     * @param totalLocalTemporary
     *            the totalLocalTemporary to set
     */
    public void setTotalLocalTemporary(int totalLocalTemporary) {
        this.totalLocalTemporary = totalLocalTemporary;
    }

    /**
     * @return the totalAlias
     */
    public int getTotalAlias() {
        return totalAlias;
    }

    /**
     * @param totalAlias
     *            the totalAlias to set
     */
    public void setTotalAlias(int totalAlias) {
        this.totalAlias = totalAlias;
    }

    /**
     * @return the totalSynonym
     */
    public int getTotalSynonym() {
        return totalSynonym;
    }

    /**
     * @param totalSynonym
     *            the totalSynonym to set
     */
    public void setTotalSynonym(int totalSynonym) {
        this.totalSynonym = totalSynonym;
    }

    /**
     * @return the metadata
     */
    public List<SchemaDatabaseDetailsVo> getMetadata() {
        return metadata;
    }

    /**
     * @param metadata
     *            the metadata to set
     */
    public void setMetadata(List<SchemaDatabaseDetailsVo> metadata) {
        this.metadata = metadata;
    }

}
