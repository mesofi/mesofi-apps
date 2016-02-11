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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Clase que se encarga de obtener la conexion a la base de datos no importando
 * la fuente de datos.
 * 
 * @author mesofi.
 * 
 */
public abstract class DataBaseConfig {
    /**
     * Contiene los datos de conexion.
     */
    public final static String JDBC_FILE = "mx/com/mesofi/services/dao/jdbc.properties";

    /**
     * Obtiene la conexion de la base de datos.
     * 
     * @return Conexion de la base de datos.
     * @throws SQLException
     *             En caso de que haya un problema al obtener la conexion, se
     *             lanza esta exeption.
     */
    public abstract Connection getConnection() throws SQLException;

    /**
     * Valida que existan ciertas propiedades en el archivo de configuracion
     * hacia la base de datos.
     * 
     * @param propertyNames
     *            Propiedades encontradas que fueron leidas del archivo de
     *            configuracion.
     * @param propertyNamesToTest
     *            Propiedades que se desean ser encontradas.
     */
    protected void existPropertiesNames(List<String> propertyNames,
            String... propertyNamesToTest) {
        for (String prop : propertyNamesToTest) {
            if (!propertyNames.contains(prop)) {
                throw new IllegalArgumentException(
                        "Debe existir una propiedad llamada: \"" + prop
                                + "\" en la configuracion para la "
                                + "base de datos");
            }
        }
    }
}
