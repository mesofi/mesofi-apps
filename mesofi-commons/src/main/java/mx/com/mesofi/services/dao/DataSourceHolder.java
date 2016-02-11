/*
 * COPYRIGHT. Mesofi 2010. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.services.dao;

import javax.sql.DataSource;

/**
 * Clase que utiliza el patron singleton para mantener en memoria una instancia
 * o referencia a un datasource con la intencion de que esta referencia sea
 * llamada desde cualquier clase.
 * 
 * @author mesofi
 */
public class DataSourceHolder {

    /**
     * Instancia estatica que crea un objeto cuando la clase se carga.
     */
    private static DataSourceHolder dataSourceHolder = new DataSourceHolder();
    /**
     * Referencia al dataSource.
     */
    private DataSource dataSource;

    /**
     * Constructor privado.
     */
    private DataSourceHolder() {
    }

    /**
     * Obtiene una instancia de esta clase.
     * 
     * @return Instancia de esta clase.
     */
    public static DataSourceHolder getInstance() {
        return dataSourceHolder;
    }

    /**
     * Obtiene una referencia al DataSource.
     * 
     * @return Referencia al DataSource.
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * Define una referencia al DataSource.
     * 
     * @param dataSource
     *            Referencia al DataSource.
     */
    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
