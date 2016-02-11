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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * Utileria que maneja los recursos de conexion a bases de datos.
 * 
 * @author mesofi
 * 
 */
public final class JdbcUtil {
    /**
     * log4j.
     */
    private static Logger logger = Logger.getLogger(JdbcUtil.class);

    /**
     * Constructor privado.
     */
    private JdbcUtil() {
        // evita que se creen instancias de esta clase directamente.
    }

    /**
     * Cierra el resultset a la base de datos y en caso de lanzar una exception,
     * esta se silencia.
     * 
     * @see #closeNotQuietly(ResultSet)
     * @param resultSet
     *            ResultSet a la base de datos.
     */
    public static void close(final ResultSet resultSet) {
        try {
            JdbcUtil.closeNotQuietly(resultSet);
        } catch (SQLException exception) {
            // silencia la excepcion.
            logger.warn(exception.getMessage());
        }
    }

    /**
     * Cierra el resultset a la base de datos y en caso de lanzar una exception,
     * esta se lanza.
     * 
     * @see #close(ResultSet)
     * @param resultSet
     *            ResultSet asociado a la conexion de base de datos.
     * @throws SQLException
     *             En caso de generarse una excepcion.
     */
    public static void closeNotQuietly(final ResultSet resultSet)
            throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
    }

    /**
     * Cierra la declaracion de la consulta a la base de datos. En caso de que
     * al cerrar la conexion se lance una exception, esta sera silenciada.
     * 
     * @see #closeNotQuietly(Statement)
     * @param statement
     *            Statement.
     */
    public static void close(final Statement statement) {
        try {
            JdbcUtil.closeNotQuietly(statement);
        } catch (SQLException exception) {
            // silencia la excepcion.
            logger.warn(exception.getMessage());
        }
    }

    /**
     * Cierra la declaracion de la consulta a la base de datos. En caso de que
     * al cerrar la conexion se lance una exception, esta sera lanzada.
     * 
     * @see #close(Statement)
     * @param statement
     *            Statement.
     * @throws SQLException
     *             Exception lanzada al cerrar el recurso.
     */
    public static void closeNotQuietly(final Statement statement)
            throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }

    /**
     * Cierra la conexion de la base de datos y en caso de lanzar exception,
     * esta sera silenciada.
     * 
     * @see #closeNotQuietly(Connection)
     * @param connection
     *            Conexion de la base de datos.
     */
    public static void close(final Connection connection) {
        try {
            JdbcUtil.closeNotQuietly(connection);
        } catch (SQLException exception) {
            // silencia la excepcion.
            logger.warn(exception.getMessage());
        }
    }

    /**
     * Cierra la conexion de la base de datos y se lanza una exception en caso
     * de que haya un error al momento de cerrarla.
     * 
     * @see #close(Connection)
     * @param connection
     *            Conexion a la base de datos.
     * @throws SQLException
     *             En caso de que haya un error.
     */
    public static void closeNotQuietly(final Connection connection)
            throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * Cierra la declaracion de la consulta a la base de datos. En caso de
     * ocurrir una exception, esta se silencia.
     * 
     * @param statement
     *            Statement.
     * @param connection
     *            Conexion a la base de datos.
     * @see #closeNotQuietly(Statement, Connection)
     */
    public static void close(final Statement statement,
            final Connection connection) {
        JdbcUtil.close(statement);
        JdbcUtil.close(connection);
    }

    /**
     * Cierra la declaracion de la consulta a la base de datos. En caso de
     * ocurrir una exception, esta se lanza.
     * 
     * @param statement
     *            Statement.
     * @param connection
     *            Conexion a la base de datos.
     * @see #close(Statement, Connection)
     * @throws SQLException
     *             Error al cerrar los recursos.
     */
    public static void closeNotQuietly(final Statement statement,
            final Connection connection) throws SQLException {
        JdbcUtil.closeNotQuietly(statement);
        JdbcUtil.closeNotQuietly(connection);
    }

    /**
     * Cierra los recursos de la base de datos. En caso de ocurrir una
     * exception, esta se silencia.
     * 
     * @param resultSet
     *            Resultset.
     * @param statement
     *            Statement.
     * @see #closeNotQuietly(ResultSet, Statement)
     */
    public static void close(final ResultSet resultSet,
            final Statement statement) {
        JdbcUtil.close(resultSet);
        JdbcUtil.close(statement);
    }

    /**
     * Cierra los recursos de la base de datos. En caso de ocurrir una
     * exception, esta se lanza.
     * 
     * @param resultSet
     *            Resultset.
     * @param statement
     *            Statement.
     * @see #close(ResultSet, Statement)
     * @throws SQLException
     *             Error al cerrar los recursos.
     */
    public static void closeNotQuietly(final ResultSet resultSet,
            final Statement statement) throws SQLException {
        JdbcUtil.closeNotQuietly(resultSet);
        JdbcUtil.closeNotQuietly(statement);
    }

    /**
     * Cierra los recursos de la base de datos. En caso de ocurrir una
     * exception, esta se silencia.
     * 
     * @param resultSet
     *            Resultset.
     * @param statement
     *            Statement.
     * @param connection
     *            Connection.
     * @see #closeNotQuietly(ResultSet, Statement, Connection)
     */
    public static void close(final ResultSet resultSet,
            final Statement statement, final Connection connection) {
        JdbcUtil.close(resultSet);
        JdbcUtil.close(statement);
        JdbcUtil.close(connection);
    }

    /**
     * Cierra los recursos de la base de datos. En caso de ocurrir una
     * exception, esta es lanzada.
     * 
     * @param resultSet
     *            ResultSet.
     * @param statement
     *            Statement.
     * @param connection
     *            Connection.
     * @see #close(ResultSet, Statement, Connection)
     * @throws SQLException
     *             Error al cerrar los recursos.
     */
    public static void closeNotQuietly(final ResultSet resultSet,
            final Statement statement, final Connection connection)
            throws SQLException {
        JdbcUtil.closeNotQuietly(resultSet);
        JdbcUtil.closeNotQuietly(statement);
        JdbcUtil.closeNotQuietly(connection);
    }

    /**
     * Cierra un arreglo de estatements en el orden especificado. Si se genera
     * una exception, esta se silencia.
     * 
     * @param statements
     *            Statements.
     * @see #close(ResultSet, Statement...)
     */
    public static void close(final Statement... statements) {
        JdbcUtil.close(null, statements);
    }

    /**
     * Cierra los recursos de la conexion a la base de datos especificando
     * unicamente el resultset y los statements abiertos. Si se genera una
     * exception, esta se silencia.
     * 
     * @param resultSet
     *            Resultset.
     * @param statements
     *            Arreglo de statements que fueron abiertos.
     */
    public static void close(final ResultSet resultSet,
            final Statement... statements) {
        JdbcUtil.close(resultSet);
        for (Statement statement : statements) {
            JdbcUtil.close(statement);
        }
    }

    /**
     * Aplica un rollback a la conexion de la base de datos, notese que la
     * conexion debe ser una conexion valida, es decir, no null. Si ocurre una
     * exception al hacer el rollback, esta es silenciada.
     * 
     * @see #rollbackNotQuietly(Connection)
     * @param connection
     *            Conexion a base de datos.
     */
    public static void rollback(final Connection connection) {
        try {
            JdbcUtil.rollbackNotQuietly(connection);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
    }

    /**
     * Aplica un rollback a la conexion de la base de datos, notese que la
     * conexion debe ser una conexion valida, es decir, no null. Si ocurre una
     * exception al hacer el rollback, esta es lanzada.
     * 
     * @param connection
     *            Conexion a base de datos.
     * @see #rollback(Connection)
     * @throws SQLException
     *             En caso de que ocurra una exception al rollback la conexion.
     */
    public static void rollbackNotQuietly(final Connection connection)
            throws SQLException {
        if (connection != null) {
            connection.rollback();
        }
    }

    /**
     * Carga y registra el draverClassName.
     * 
     * @param driverClassName
     *            Driver a ser cargado.
     * @return true, si se cargo correctamente el driver. De otra forma regresa
     *         false.
     */
    public static boolean loadDriver(final String driverClassName) {
        boolean loaded = true;
        try {
            Class.forName(driverClassName).newInstance();
        } catch (Throwable e) {
            logger.warn(e.getMessage());
            loaded = false;
        }
        return loaded;
    }
}
