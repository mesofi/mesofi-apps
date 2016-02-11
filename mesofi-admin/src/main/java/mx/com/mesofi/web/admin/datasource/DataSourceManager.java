/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.datasource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

/**
 * Singleton to store all the existing dataSources for the application.
 * 
 * @author Armando Rivas
 * @since 13 Feb 2014.
 * 
 */
public class DataSourceManager {
    /**
     * Single instance.
     */
    private static DataSourceManager dataSourceManager = new DataSourceManager();
    /**
     * Contains all dataSources.
     */
    private Map<Integer, DataSource> dataSources = new HashMap<Integer, DataSource>();

    /**
     * Private constructor to avoid creation of instances directly.
     */
    private DataSourceManager() {

    }

    /**
     * Gets the current instance.
     * 
     * @return Current instance.
     */
    public static DataSourceManager getInstance() {
        return dataSourceManager;
    }

    /**
     * Add a new dataSource into the pool.
     * 
     * @param dataSourceId
     *            Identifier for the DataSource.
     * @param dataSource
     *            DataSource associated.
     */
    public void addDataSource(Integer dataSourceId, DataSource dataSource) {
        dataSources.put(dataSourceId, dataSource);
    }

    /**
     * Gets an existing dataSource, if this DataSource is not found, then throws
     * an error.
     * 
     * @param dataSourceId
     *            Identifier for the dataSource.
     * @return Current DataBase.
     */
    public DataSource getDataSource(Integer dataSourceId) {
        DataSource ds = dataSources.get(dataSourceId);
        if (ds == null) {
            throw new IllegalStateException("Could not found any datasource given this identifier: " + dataSourceId);
        }
        return ds;
    }

    /**
     * Test if a certain dataSource exists or not.
     * 
     * @param dataSourceId
     *            Identifier for the dataSource.
     * @return true, exists otherwise, false.
     */
    public boolean existDataSource(Integer dataSourceId) {
        return dataSources.containsKey(dataSourceId);
    }
}
