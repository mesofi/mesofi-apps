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

import static mx.com.mesofi.services.dao.JdbcUtil.loadDriver;
import static mx.com.mesofi.services.util.SimpleValidator.isNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import mx.com.mesofi.services.properties.PropertiesLoader;

import org.apache.log4j.Logger;

/**
 * Clase que almacena y procesa los datos de la conexion a base de datos para
 * obtener su conexion. Tiene la modalidad de poder conectarse ya sea con datos
 * directos o simplemente con los datos simples de conexion sin los detalles del
 * proveedor de base de datos.
 * 
 * @author mesofi
 * 
 */
public class JdbcDataBaseConfig extends DataBaseConfig {

    /**
     * Log4j.
     */
    private Logger logger = Logger.getLogger(JdbcDataBaseConfig.class);
    /**
     * DriverClassName.
     */
    private String driverClassName;
    /**
     * Url de conexion.
     */
    private String url;
    /**
     * Nombre del usuario.
     */
    private String user;
    /**
     * Password del usuario.
     */
    private String password;
    /**
     * Nombre de la propiedad para el host.
     */
    private String hostName = "jdbc.host";
    /**
     * Nombre de la propiedad para el puerto.
     */
    private String portName = "jdbc.port";
    /**
     * Nombre de la propiedad para la base de datos.
     */
    private String databaseName = "jdbc.database";
    /**
     * Nombre de la propiedad para el usuario.
     */
    private String usernameName = "jdbc.username";
    /**
     * Nombre de la propiedad para el password.
     */
    private String passwordName = "jdbc.password";

    /**
     * Construye un objeto para la conexion a la base de datos. Para esta
     * modalidad, es indispensable que se proporcionen datos referentes al
     * proveedor de base de datos.
     * 
     * @param driverClassName
     *            DriverClassName.
     * @param url
     *            url de conexion.
     * @param user
     *            Nombre de Usuario.
     * @param password
     *            Password.
     */
    public JdbcDataBaseConfig(final String driverClassName, final String url,
            final String user, final String password) {
        // prueba que sean valores no nulos.
        isNull(driverClassName, "El driverClassName no debe ser null");
        isNull(url, "El url no debe ser null");
        isNull(user, "El user no debe ser null");
        isNull(password, "El password no debe ser null");

        // reasigna los datos.
        this.driverClassName = driverClassName;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * Construye un objeto para la conexion a la base de datos. Para esta
     * modalidad, es indispensable que se proporcionen datos indispensables y
     * necesarios referentes a la conexion sin importar los detalles de cada
     * proveedor de base de datos.
     * 
     * @param provider
     *            Referencia a un proveedor de base de datos.
     * @param host
     *            Host o servidor en el cual reside la instancia de base de
     *            datos.
     * @param port
     *            Numero de puerto que escucha las conexiones a base de datos.
     * @param dataBaseInstance
     *            Nombre de la base de datos o tambien llamada instancia de la
     *            base de datos.
     * @param user
     *            Nombre del usuario.
     * @param password
     *            Password del usuario.
     */
    public JdbcDataBaseConfig(final DataBaseProvider provider,
            final String host, final int port, final String dataBaseInstance,
            final String user, final String password) {

        // delega la asignacion y validacion al sig. metodo.
        settingDataProvider(provider, host, port, dataBaseInstance, user,
                password);

    }

    /**
     * Construye un objeto para la conexion a la base de datos. Para esta
     * modalidad, es obligatorio proporcionar datos referentes a la conexion sin
     * importar los detalles de cada proveedor de base de datos. Tipicamente se
     * utiliza este constructor para la conexion directa o mediante un dsn hacia
     * MS-Access.
     * 
     * @param dataBaseNameOrDsnName
     *            Corresponde al nombre absoluto de la base de datos o al nombre
     *            dsn de la misma.
     * @param isDsnRequired
     *            Indica si se requiere conectarse mediante un DSN o no. En caso
     *            de no requerir conectarse mediante un DSN, entonces se debera
     *            proporcionar la ruta entera de la base de datos. Esta ruta
     *            debe estar separada por "/" en sistemas Windows y no \ como
     *            tipicamente se haria.
     */
    public JdbcDataBaseConfig(final String dataBaseNameOrDsnName,
            final boolean isDsnRequired) {
        this(isDsnRequired ? DataBaseProvider.MS_ACCESS_DSN
                : DataBaseProvider.MS_ACCESS, "localhost", -1,
                dataBaseNameOrDsnName, "", "");
    }

    /**
     * Construye un objeto para la conexion a la base de datos. Para esta
     * conexion, es necesario proporcionarse un archivo properties, en el cual
     * contiene los datos referentes a los datos del proveedor. Para obtener los
     * datos del proveedor, se deben de especificar en un archivo (dentro del
     * classpath) llamado "mx/com/mesofi/services/dao/jdbc.properties".
     * 
     * @param provider
     *            Nombre del proveedor de base de datos.
     */
    public JdbcDataBaseConfig(final DataBaseProvider provider) {

        isNull(provider, "El proveedor de acceso a datos no puede ser null");

        String host = null;
        String portString = null;
        String database = null;
        String userString = null;
        String passwordString = null;

        PropertiesLoader.getInstance().setResource(JDBC_FILE);
        existPropertiesNames(PropertiesLoader.getInstance().getPropertyNames(),
                hostName, portName, databaseName, usernameName, passwordName);

        host = PropertiesLoader.getInstance().getProperty(hostName);
        portString = PropertiesLoader.getInstance().getProperty(portName);
        database = PropertiesLoader.getInstance().getProperty(databaseName);
        userString = PropertiesLoader.getInstance().getProperty(usernameName);
        passwordString = PropertiesLoader.getInstance().getProperty(
                passwordName);

        int port = 0;
        try {
            port = Integer.parseInt(portString);
        } catch (NumberFormatException exception) {
            logger.error(exception.getMessage());
            throw new IllegalArgumentException(
                    "Debe especificar un valor numerico para el puerto");
        }
        settingDataProvider(provider, host, port, database, userString,
                passwordString);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = null;
        // intenta cargar el driver especificado.
        if (loadDriver(driverClassName)) {
            connection = DriverManager.getConnection(url, user, password);
        } else {
            throw new SQLException(
                    "No se pudo cargar el driver especificado por: "
                            + driverClassName);
        }
        return connection;
    }

    /**
     * Prueba que los valores pasados como parametros sean validos y son
     * asignados.
     * 
     * @param provider
     *            Proveedor de base de datos.
     * @param host
     *            Nombre del host.
     * @param port
     *            Numero del puerto.
     * @param dataBaseInstance
     *            Nombre de la instancia de base de datos.
     * @param user
     *            Nombre de usuario.
     * @param password
     *            Password del usuario.
     */
    private void settingDataProvider(final DataBaseProvider provider,
            final String host, final int port, final String dataBaseInstance,
            final String user, final String password) {

        // prueba que sean valores no nulos.
        isNull(host, "El host no debe ser null");
        isNull(dataBaseInstance, "El nombre de base de datos no debe ser null");
        isNull(user, "El user no debe ser null");
        isNull(password, "El password no debe ser null");

        this.driverClassName = provider.getDriverClassName();
        this.url = provider.getUrl(host, port, dataBaseInstance);
        this.user = user;
        this.password = password;
    }

}
