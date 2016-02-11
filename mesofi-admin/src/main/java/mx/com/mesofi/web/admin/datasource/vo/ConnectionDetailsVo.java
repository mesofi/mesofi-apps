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

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Details about the connection in remote database.
 * 
 * @author Armando Rivas.
 * @since Feb 13 2014
 */
@XmlRootElement
public class ConnectionDetailsVo {
    private DataSourceVo dataSourceVo;
    private boolean success;
    private String errorDescription;
    private Map<String, Object> metadata;

    /**
     * @return the dataSourceVo
     */
    public DataSourceVo getDataSourceVo() {
        return dataSourceVo;
    }

    /**
     * @param dataSourceVo
     *            the dataSourceVo to set
     */
    public void setDataSourceVo(DataSourceVo dataSourceVo) {
        this.dataSourceVo = dataSourceVo;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success
     *            the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the errorDescription
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * @param errorDescription
     *            the errorDescription to set
     */
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    /**
     * @return the metadata
     */
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    /**
     * @param metadata
     *            the metadata to set
     */
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

}
