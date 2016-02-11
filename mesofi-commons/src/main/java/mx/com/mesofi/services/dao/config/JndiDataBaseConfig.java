/*
 * COPYRIGHT. Mesofi 2009. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.services.dao.config;

import static mx.com.mesofi.services.util.SimpleValidator.isNull;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import mx.com.mesofi.services.properties.PropertiesLoader;

/**
 * Clase que almacena y procesa los datos de la conexion a base de datos para
 * obtener su conexion. Tiene la modalidad de poder conectarse mediante el uso
 * del nombre de jdni especificado para la conexion.
 * 
 * @author mesofi
 * 
 */
public class JndiDataBaseConfig extends DataBaseConfig {
    /**
     * Nombre de la propiedad para el datasource.
     */
    private String datasourceName = "datasource.jndi";
    /**
     * Nombre de jndi.
     */
    private String jndiName;
    /**
     * DataSource.
     */
    private DataSource dataSource;

    /**
     * Construye un objeto para la conexion a la base de datos. Para esta
     * modalidad, es indispensable que se proporcione el nombre de jdni para
     * lograr la conexion de base de datos del proveedor.
     * 
     * @param jndiName
     *            Nombre valido de jndi.
     */
    public JndiDataBaseConfig(final String jndiName) {
        // prueba que sean valores no nulos.
        isNull(jndiName, "El nombre jdni no debe ser null");
        this.jndiName = jndiName;
    }

    /**
     * Construye un objeto para la conexion a la base de datos. Para esta
     * conexion, es necesario proporcionarse un archivo properties, en el cual
     * contiene los datos referentes a los datos del proveedor. Para obtener los
     * datos del proveedor, se deben de especificar en un archivo (dentro del
     * classpath) llamado "mx/com/mesofi/services/dao/jdbc.properties".
     */
    public JndiDataBaseConfig() {
        String jndiNameLocal = null;

        PropertiesLoader.getInstance().setResource(JDBC_FILE);
        existPropertiesNames(PropertiesLoader.getInstance().getPropertyNames(),
                datasourceName);
        jndiNameLocal = PropertiesLoader.getInstance().getProperty(
                datasourceName);

        isNull(jndiNameLocal, "El nombre jdni no debe ser null");
        this.jndiName = jndiNameLocal;
    }

    /**
     * Obtiene el dataSource asociado al contexto mediante el nombre jndi.
     */
    public DataSource getDataSource() throws SQLException {
        try {
            if (dataSource == null) {
                dataSource = (DataSource) (new InitialContext())
                        .lookup(jndiName);
            }
        } catch (NamingException exception) {
            throw new SQLException(exception);
        }
        return dataSource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = null;
        getDataSource();
        connection = dataSource.getConnection();
        return connection;
    }
}
