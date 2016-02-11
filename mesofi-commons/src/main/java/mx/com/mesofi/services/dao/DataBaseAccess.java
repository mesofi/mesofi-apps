/*
 * COPYRIGHT. Mesofi 2009. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.services.dao;

import java.sql.Connection;

/**
 * Interface que tiene el comportamiento para el acceso a bases de datos.
 * 
 * @author mesofi
 * 
 */
public interface DataBaseAccess {
    /**
     * Obtiene la conexion a la base de datos independientemente de la fuente de
     * datos.
     * 
     * @return Conexion a la base de datos.
     */
    public Connection getDataBaseConnection();
}
