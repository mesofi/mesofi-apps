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

/**
 * Clase que contiene los parametros necesarios relativos a los proveedores de
 * bases de datos, estos datos incluyen su estructura url y el nombre de driver
 * para la conexion mediante jdbc.
 * 
 * @author mesofi
 * 
 */
public enum DataBaseProvider {

    /**
     * Proveedor para MySQL.
     */
    MYSQL("jdbc:mysql://" + ProviderConstants.SERVER + ":"
            + ProviderConstants.PORT + "/" + ProviderConstants.DATABASE,
            "com.mysql.jdbc.Driver"),
    /**
     * Proveedor para oracle thin.
     */
    ORACLE_THIN("jdbc:oracle:thin:@" + ProviderConstants.SERVER + ":"
            + ProviderConstants.PORT + ":" + ProviderConstants.DATABASE,
            "oracle.jdbc.driver.OracleDriver"),
    /**
     * Proveedor para DB2.
     */
    DB2("jdbc:db2://" + ProviderConstants.SERVER + ":" + ProviderConstants.PORT
            + "/" + ProviderConstants.DATABASE, "com.ibm.db2.jcc.DB2Driver"),
    /**
     * MS-Access usando un DSN.
     */
    MS_ACCESS_DSN("jdbc:odbc:" + ProviderConstants.DSN,
            "sun.jdbc.odbc.JdbcOdbcDriver"),
    /**
     * MS-Access sin usar DSN.
     */
    MS_ACCESS("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ="
            + ProviderConstants.DATABASE + ";DriverID=22;READONLY=true",
            "sun.jdbc.odbc.JdbcOdbcDriver");
    /**
     * Url del proveedor de base de datos.
     */
    private String providerURL;
    /**
     * DriverClassName del proveedor.
     */
    private String driverClassName;

    /**
     * Constructor privado para esta clase.
     * 
     * @param providerURL
     *            url.
     * @param driverClassName
     *            driverclassname.
     */
    private DataBaseProvider(final String providerURL,
            final String driverClassName) {
        this.providerURL = providerURL;
        this.driverClassName = driverClassName;
    }

    /**
     * Obtiene el driverClassName perteneciente al proveedor de base de datos.
     * 
     * @return DriverClassName.
     */
    public String getDriverClassName() {
        return driverClassName;
    }

    /**
     * Obtiene el url del proveedor especificado.
     * 
     * @param host
     *            Nombre o ip del servidor en el que reside la base de datos.
     * @param port
     *            Numero de puerto de conexion.
     * @param instanceDataBase
     *            Nombre de la instancia de la base de datos.
     * @return Url del proveedor.
     */
    public String getUrl(final String host, final int port,
            final String instanceDataBase) {
        isNull(host, "El host no debe ser null");
        isNull(instanceDataBase,
                "El nombre de la base de datos no debe ser null");
        this.providerURL = this.providerURL.replaceFirst(
                ProviderConstants.SERVER, host);
        this.providerURL = this.providerURL.replaceFirst(
                ProviderConstants.PORT, port + "");
        this.providerURL = this.providerURL.replaceFirst(
                ProviderConstants.DATABASE, instanceDataBase);
        this.providerURL = this.providerURL.replaceFirst(ProviderConstants.DSN,
                instanceDataBase);

        return this.providerURL;
    }
}

/**
 * Contiene constantes para el proveedor.
 * 
 * @author mesofi
 * 
 */
class ProviderConstants {
    /**
     * Server.
     */
    public static final String SERVER = "<server>";
    /**
     * Port.
     */
    public static final String PORT = "<port>";
    /**
     * database.
     */
    public static final String DATABASE = "<database>";
    /**
     * dns.
     */
    public static final String DSN = "<dsn>";
}
