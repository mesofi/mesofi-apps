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

/**
 * Details about the schema or database in remote database.
 * 
 * @author Armando Rivas.
 * @since Feb 27 2014
 */
public class SchemaDatabaseDetailsVo {
    private String cat;
    private String schem;
    private String name;
    private String type;
    private String remarks;

    /**
     * @return the cat
     */
    public String getCat() {
        return cat;
    }

    /**
     * @param cat
     *            the cat to set
     */
    public void setCat(String cat) {
        this.cat = cat;
    }

    /**
     * @return the schem
     */
    public String getSchem() {
        return schem;
    }

    /**
     * @param schem
     *            the schem to set
     */
    public void setSchem(String schem) {
        this.schem = schem;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks
     *            the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
