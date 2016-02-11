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

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface que debe ser implementada por aquellas clases que quieran popular
 * un objeto con un resultSet.
 * 
 * @author mesofi.
 * 
 * @param <T>
 *            Tipo de Dato.
 */
public interface ResultSetHolder<T> {
    /**
     * Obtiene un objeto que deberia ser populado por el ResultSet.
     * 
     * @param rs
     *            ResultSet.
     * @param rowCount
     *            Numero de renglones actuales.
     * @return Objeto populado con el ResultSet.
     * @throws SQLException
     *             En caso de que ocurra un error al asignar los valores.
     */
    public T getObjectResult(ResultSet rs, int rowCount) throws SQLException;
}
