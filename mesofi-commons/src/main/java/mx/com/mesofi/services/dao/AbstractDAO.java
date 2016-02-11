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

import static mx.com.mesofi.services.dao.JdbcUtil.close;
import static mx.com.mesofi.services.dao.JdbcUtil.rollback;
import static mx.com.mesofi.services.util.SimpleCommonActions.getFieldFromMethod;
import static mx.com.mesofi.services.util.SimpleCommonActions.isAnnotated;
import static mx.com.mesofi.services.util.SimpleValidator.isNull;
import static mx.com.mesofi.services.util.SimpleValidator.isValid;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.mesofi.services.dao.annotation.EnsureCompatibility;
import mx.com.mesofi.services.dao.annotation.ResultSetPosition;
import mx.com.mesofi.services.dao.config.DataBaseConfig;
import mx.com.mesofi.services.dao.exception.DataAccessException;
import mx.com.mesofi.services.dao.exception.DataAccessRuntimeException;
import mx.com.mesofi.services.dao.exception.DataTypeConversionException;
import mx.com.mesofi.services.service.annotation.DataTransferObject;
import mx.com.mesofi.services.util.SimpleCommonActions;

import org.apache.log4j.Logger;

/**
 * Clase que funciona como padre para cualquier DAO que hagas las operaciones
 * basicas (CRUD) de acceso a base de datos. Cualquier DAO que desee utilizar
 * esta clase, debe ser extendida para heredar su funcionalidad.
 * 
 * @author mesofi
 */
public abstract class AbstractDAO implements DataBaseAccess {
    /**
     * Log4j.
     */
    private Logger logger = Logger.getLogger(AbstractDAO.class);
    /**
     * Referencia a DataBaseConfig que contiene informacion relacionada a la
     * conexion a la base de datos.
     */
    private DataBaseConfig dataBaseConfig;
    /**
     * Mapa que contiene los tipos de datos por defecto de algunos tipos
     * primitivos.
     */
    private static Map<String, Object> types = null;
    static {
        // inicializacion de los valores por defecto.
        types = new HashMap<String, Object>();
        types.put(Boolean.TYPE.getName(), false);
        types.put(Boolean.class.getName(), false);

        types.put(Byte.TYPE.getName(), 0);
        types.put(Byte.class.getName(), 0);

        types.put(Short.TYPE.getName(), 0);
        types.put(Short.class.getName(), 0);

        types.put(Integer.TYPE.getName(), 0);
        types.put(Integer.class.getName(), 0);

        types.put(Long.TYPE.getName(), 0);
        types.put(Long.class.getName(), 0);

        types.put(Float.TYPE.getName(), 0.0f);
        types.put(Float.class.getName(), 0.0f);

        types.put(Double.TYPE.getName(), 0.0f);
        types.put(Double.class.getName(), 0.0f);

    }

    /**
     * Define una referencia hacia la configuracion de la base de datos en su
     * conexion.
     * 
     * @param dataBaseConfig
     *            Conectividad hacia la base de datos.
     */
    public void setDataBaseConfig(final DataBaseConfig dataBaseConfig) {
        this.dataBaseConfig = dataBaseConfig;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Connection getDataBaseConnection() {
        // Hace uso de DataBaseConfig para obtener la conexion.
        isNull(this.dataBaseConfig,
                "Se debe especificar una configuracion para la conexion a la base de datos");
        Connection connection = null;
        try {
            connection = dataBaseConfig.getConnection();
        } catch (SQLException sqlException) {
            throw new DataAccessRuntimeException(
                    "No se pudo obtener la conexion a la base de datos debido a: "
                            + sqlException.getMessage());
        }
        return connection;
    }

    /**
     * Metodo que popula automaticamente un JavaBean con el resultado de una
     * consulta hacia la base de datos. Se debe especificar una referencia a
     * ResultSetHolder. Notese que si se recuperan mas registros de uno,
     * entonces solo presenta el primer registro.
     * 
     * @param <T>
     *            Tipo de Dato.
     * @param resultSetHolder
     *            Referencia a resultsetholder.
     * @param sql
     *            Consulta sql para recuperar los datos.
     * @param params
     *            Parametros asociados a la consulta, estos parametros son
     *            usados por el PreparedStatement.
     * @see #queryForJavaBean(ResultSetHolder, String, Object...)
     * @return Objeto como resultado de la consulta a base de datos.
     */
    protected <T> T queryForSingleJavaBean(
            final ResultSetHolder<T> resultSetHolder, final String sql,
            final Object... params) {
        T t = null;
        // recupera solo el primer registro.
        List<T> list = queryForJavaBeanDelegate(true, resultSetHolder, sql,
                params);
        if (list.size() >= 1) {
            t = list.get(0);
        }
        return t;
    }

    /**
     * Metodo que popula automaticamente un JavaBean con el resultado de una
     * consulta hacia la base de datos. Se debe especificar una referencia a un
     * objeto que implemente a ResultSetholder y popular dicho objeto. Se
     * recuperan todos los registros existentes.
     * 
     * @param <T>
     *            Tipo de Dato.
     * @param resultSetHolder
     *            Referencia a resultsetholder.
     * @param sql
     *            Consulta sql para recuperar los datos.
     * @param params
     *            Parametros asociados a la consulta, estos parametros son
     *            usados por el PreparedStatement.
     * @see #queryForSingleJavaBean(ResultSetHolder, String, Object...)
     * @return Lista de objetos como resultado de la consulta realizada.
     */
    protected <T> List<T> queryForJavaBean(
            final ResultSetHolder<T> resultSetHolder, final String sql,
            final Object... params) {
        // delega al metodo para recuperar los registros existentes.
        return queryForJavaBeanDelegate(false, resultSetHolder, sql, params);
    }

    /**
     * Metodo que popula automaticamente un JavaBean con el resultado de una
     * consulta hacia la base de datos. Se debe especificar una referencia a un
     * objeto que implemente a ResultSetholder y popular dicho objeto.
     * 
     * @param <T>
     *            Tipo de Dato.
     * @param fetchOnlyFirstRow
     *            Indica si se recuperan el primer elemento de la consulta o no.
     * @param resultSetHolder
     *            Referencia a resultsetholder.
     * @param sql
     *            Consulta sql para recuperar los datos.
     * @param params
     *            Parametros asociados a la consulta, estos parametros son
     *            usados por el PreparedStatement.
     * @return Lista de objetos como resultado de la consulta realizada.
     */
    private <T> List<T> queryForJavaBeanDelegate(
            final boolean fetchOnlyFirstRow,
            final ResultSetHolder<T> resultSetHolder, final String sql,
            final Object... params) {

        // obtiene la conexion a la base de datos.
        Connection conn = getDataBaseConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<T>();

        try {
            ps = conn.prepareStatement(sql);
            startSettingParameters(ps, params);
            rs = ps.executeQuery();
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
                list.add(resultSetHolder.getObjectResult(rs, rowCount));
                if (fetchOnlyFirstRow) {
                    // solo recupera el primer registro en caso de existir.
                    break;
                }
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            close(rs, ps, conn);
        }
        return list;
    }

    /**
     * Metodo que popula automaticamente un JavaBean con el resultado de una
     * consulta hacia la base de datos. Se debe especificar una clase que cumpla
     * con dicho estandar. Notese que si se recuperan mas registros de uno,
     * entonces solo presenta el primer registro.
     * 
     * @param <T>
     *            Tipo de Dato.
     * @param clazz
     *            Clase mediante la cual se crearan objetos como resultado de la
     *            consulta hacia la base de datos. Es importante considerar que
     *            las propiedades de esta clase deben estar anotadas por @ResultSetPosition
     *            ya que con esta anotacion se indicara la posicion en la cual
     *            el resultSet alamacenara los datos.
     * @param sql
     *            Consulta sql para recuperar los datos.
     * @param params
     *            Parametros asociados a la consulta, estos parametros son
     *            usados por el PreparedStatement.
     * @see #queryForJavaBean(Class, String, Object...)
     * @return Objeto como resultado de la consulta realizada.
     */
    protected <T> T queryForSingleJavaBean(final Class<T> clazz,
            final String sql, final Object... params) {
        T t = null;
        // recupera solo el primer registro.
        List<T> list = queryForJavaBeanDelegate(true, clazz, sql, params);
        if (list.size() >= 1) {
            t = list.get(0);
        }
        return t;
    }

    /**
     * Metodo que popula automaticamente una lista JavaBean con el resultado de
     * una consulta hacia la base de datos. Se debe especificar una clase que
     * cumpla con dicho estandar.
     * 
     * @param <T>
     *            Tipo de Dato.
     * @param clazz
     *            Clase mediante la cual se crearan objetos como resultado de la
     *            consulta hacia la base de datos. Es importante considerar que
     *            las propiedades de esta clase deben estar anotadas por @ResultSetPosition
     *            ya que con esta anotacion se indicara la posicion en la cual
     *            el resultSet alamacenara los datos.
     * @param sql
     *            Consulta sql para recuperar los datos.
     * @param params
     *            Parametros asociados a la consulta, estos parametros son
     *            usados por el PreparedStatement.
     * @see #queryForSingleJavaBean(Class, String, Object...)
     * @return Lista de objetos como resultado de la consulta realizada.
     */
    protected <T> List<T> queryForJavaBean(final Class<T> clazz,
            final String sql, final Object... params) {
        // delega al metodo para recuperar los registros existentes.
        return queryForJavaBeanDelegate(false, clazz, sql, params);
    }

    /**
     * Metodo al cual es delegado la operacion de recuperacion de datos, permite
     * traer o no solamente el primer registro.
     * 
     * @param <T>
     *            Tipo de Dato.
     * @param fetchOnlyFirstRow
     *            Indica si se recupera solo el primer registro o no.
     * @param clazz
     *            Clase mediante la cual se crearan objetos como resultado de la
     *            consulta hacia la base de datos.
     * @param sql
     *            Consulta sql para recuperar los datos.
     * @param params
     *            Parametros asociados a la consulta, estos parametros son
     *            usados por el PreparedStatement.
     * @return Lista de objetos como resultado de la consulta realizada.
     */
    private <T> List<T> queryForJavaBeanDelegate(
            final boolean fetchOnlyFirstRow, final Class<T> clazz,
            final String sql, final Object... params) {
        // valida la clase.
        validateClass(clazz);

        // obtiene la conexion a la base de datos.
        Connection conn = getDataBaseConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<T>();
        try {
            ps = conn.prepareStatement(sql);
            startSettingParameters(ps, params);
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnasCount = metaData.getColumnCount();

            T obj = null;
            Object dbObjectValue = null;
            String dbObjectType = null;
            while (rs.next()) {
                obj = createInstanceFromClass(clazz);
                for (int i = 1; i <= columnasCount; i++) {
                    dbObjectValue = rs.getObject(metaData.getColumnName(i));
                    dbObjectType = metaData.getColumnTypeName(i);
                    setDataTransferObjectValues(obj, dbObjectType,
                            dbObjectValue, i);
                }
                list.add(obj);
                if (fetchOnlyFirstRow) {
                    // solo recupera el primer registro en caso de existir.
                    break;
                }
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
            throw new DataAccessRuntimeException(e);
        } finally {
            close(rs, ps, conn);
        }
        return list;
    }

    /**
     * Actualiza un o varios registros en base de datos, esta operacion es
     * aplicable para cualquier cambio o afectacion sobre la base de datos ya
     * sea INSERT, DELETE, UPDATE. Esta operacion no incluye
     * transaccionabilidad, por lo que cualquier afectacion a la base de datos
     * no estara contempada dentro de una transaccion y todas las afectaciones
     * tienen commit por defecto sin poder revertir la operacion.
     * 
     * @param sql
     *            SQL a ejecutar.
     * @param params
     *            Parametros de la sentencia sql.
     * @return Numero de registros afectados.
     * @throws DataAccessException
     *             Si ocurre un error al actualizar la base.
     * @see #update(int, String, Object...)
     */
    protected int update(final String sql, final Object... params)
            throws DataAccessException {
        Connection conn = getDataBaseConnection();
        PreparedStatement ps = null;
        int afectedRowNumber = -1;
        try {
            ps = conn.prepareStatement(sql);
            startSettingParameters(ps, params);
            afectedRowNumber = ps.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException(exception);
        } finally {
            close(ps, conn);
        }
        return afectedRowNumber;
    }

    /**
     * Actualiza un o varios registros en base de datos, esta operacion es
     * aplicable para cualquier cambio o afectacion sobre la base de datos ya
     * sea INSERT, DELETE, UPDATE. Esta operacion incluye transaccionabilidad,
     * por lo que cualquier afectacion a la base de datos estara contempada
     * dentro de una transaccion y todas las afectaciones podran ser revertidas
     * mediante un rollback en caso de que la transaccion no se lleve a cabo.
     * 
     * @param transactionLevel
     *            Nivel de Transaccion.
     * @param sql
     *            SQL a ejecutar.
     * @param params
     *            Parametros de la sentencia sql.
     * @return Numero de registros afectados.
     * @throws DataAccessException
     *             Si ocurre un error al actualizar la base.
     * @see #update(String, Object...)
     */
    protected int update(final int transactionLevel, final String sql,
            final Object... params) throws DataAccessException {
        Connection conn = getDataBaseConnection();
        PreparedStatement ps = null;
        int afectedRowNumber = -1;
        try {
            conn.setTransactionIsolation(transactionLevel);
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            startSettingParameters(ps, params);
            afectedRowNumber = ps.executeUpdate();
        } catch (SQLException exception) {
            rollback(conn);
            throw new DataAccessException(exception);
        } finally {
            close(ps, conn);
        }
        return afectedRowNumber;
    }

    /**
     * Crea una instancia de la clase especificada en los parametros.
     * 
     * @param <T>
     *            Tipo generico.
     * @param clazz
     *            Clase de la cual se desea crear la instancia.
     * @return nuevo objeto creado.
     */
    private <T> T createInstanceFromClass(final Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    /**
     * Obtiene un objeto que es compatible con el valor devuelto por la base de
     * datos.
     * 
     * @param javaType
     *            Tipo de datos especifico del lenguaje java.
     * @param field
     *            Campo en el cual se prueba si esta anotado o no para verificar
     *            su compatibilidad con los tipos de datos de la base de datos.
     * @param dbObjectValue
     *            Valor devuelto de la base de datos.
     * @return Valor compatible o el mismo si no hay compatibilidad, este valor
     *         nunca deberia ser null.
     */
    private Object getPossibleCompatibleType(final String javaType,
            final Field field, final Object dbObjectValue) {
        // reasigna el mismo tipo.
        Object compatibleValue = dbObjectValue;

        if (isAnnotated(field.getAnnotations(), EnsureCompatibility.class)) {
            if (javaType.equals(String.class.getName())) {
                // intenta convertir el valor en string.
                compatibleValue = dbObjectValue.toString();
            }
            if (javaType.equals(Boolean.TYPE.getName())
                    || javaType.equals(Boolean.class.getName())) {
                // intenta convertir el valor en boolean.
                compatibleValue = Boolean
                        .parseBoolean(dbObjectValue.toString());
            }
            if (javaType.equals(Byte.TYPE.getName())
                    || javaType.equals(Byte.class.getName())) {
                // intenta convertir el valor en byte.
                compatibleValue = Byte.parseByte(dbObjectValue.toString());
            }
            if (javaType.equals(Short.TYPE.getName())
                    || javaType.equals(Short.class.getName())) {
                // intenta convertir el valor en Short.
                compatibleValue = Short.parseShort(dbObjectValue.toString());
            }
            if (javaType.equals(Integer.TYPE.getName())
                    || javaType.equals(Integer.class.getName())) {
                // intenta convertir el valor en entero.
                compatibleValue = Integer.parseInt(dbObjectValue.toString());
            }
            if (javaType.equals(Long.TYPE.getName())
                    || javaType.equals(Long.class.getName())) {
                // intenta convertir el valor en Long.
                compatibleValue = Long.parseLong(dbObjectValue.toString());
            }
            if (javaType.equals(Float.TYPE.getName())
                    || javaType.equals(Float.class.getName())) {
                // intenta convertir el valor en Float.
                compatibleValue = Float.parseFloat(dbObjectValue.toString());
            }
            if (javaType.equals(Double.TYPE.getName())
                    || javaType.equals(Double.class.getName())) {
                // intenta convertir el valor en Double.
                compatibleValue = Double.parseDouble(dbObjectValue.toString());

            }

        }
        return compatibleValue;
    }

    /**
     * Asigna un valor devuelto por la base de datos a un metodo de una clase.
     * 
     * @param <T>
     *            Tipo de dato.
     * @param obj
     *            Objeto al cual se el asignara un valor en algun de sus
     *            propiedades.
     * @param dbObjectType
     *            Representacion del tipo de dato de la columna de la base de
     *            datos.
     * @param dbObjectValue
     *            Valor devuelto por la base de datos en alguna de sus columnas.
     * @param posResultSet
     *            Posicion del metodo encontrado en el resultSet.
     */
    private <T> void setDataTransferObjectValues(final T obj,
            String dbObjectType, Object dbObjectValue, int posResultSet) {
        String javaType = null;

        for (Method method : obj.getClass().getMethods()) {
            if (method.getName().startsWith(
                    SimpleCommonActions.PREFIX_SETTER.toString())) {
                // solo aplican los metodos setters.
                Field field = getFieldFromMethod(obj.getClass(), method);
                Annotation ann = field.getAnnotation(ResultSetPosition.class);
                if (ann instanceof ResultSetPosition) {
                    ResultSetPosition annotation = (ResultSetPosition) ann;
                    if (annotation.position() == posResultSet) {
                        javaType = method.getParameterTypes()[0].getName();
                        if (dbObjectValue == null) {
                            dbObjectValue = types.get(javaType);
                        }
                        // obtiene el campo a partir de su metodo get-set
                        try {
                            method.invoke(obj, getPossibleCompatibleType(
                                    javaType, field, dbObjectValue));
                        } catch (IllegalArgumentException e) {
                            String msg = "No se puede asignar el objeto de tipo: "
                                    + dbObjectType
                                    + " ("
                                    + dbObjectValue
                                    + ") al metodo: "
                                    + method
                                    + " debido a: \n";
                            throw new DataTypeConversionException(msg + e);
                        } catch (IllegalAccessException e) {
                            throw new DataTypeConversionException(e);
                        } catch (InvocationTargetException e) {
                            throw new DataTypeConversionException(e);
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * Asigna los parametros al PreparedStatement, comunmente estos parametros
     * se sustituyen por el caracter '?' especificado en la consulta SQL.
     * 
     * @param ps
     *            PreparedStatement.
     * @param params
     *            Parametros de la consulta.
     * @throws SQLException
     *             En caso de ocurrir una exception al asignar los parametros
     *             correspondientes.
     */
    private void startSettingParameters(final PreparedStatement ps,
            final Object[] params) throws SQLException {
        Object object = null;
        isNull(ps, "Debe especificar una referencia valida a PreparedStatement");
        // asigna los paramet ros a la consulta.
        for (int i = 1; i <= params.length; i++) {
            object = params[i - 1];
            if (object instanceof Date) {
                ps.setDate(i, new java.sql.Date(((Date) object).getTime()));
            } else {
                ps.setObject(i, object);
            }
        }
    }

    /**
     * Valida la clase pasada como parametro, esta validacion consiste en probar
     * si tal clase es de un cierto tipo y tiene propiedades en la cual
     * almacenar informacion.
     * 
     * @param <T>
     *            Tipo de dato.
     * @param clazz
     *            Clase a probar.
     */
    private <T> void validateClass(final Class<T> clazz) {
        // comprueba que la clase este anotada como DTO.
        boolean isAnnotated = false;
        // prueba que tenga la anotacion.
        isAnnotated = isAnnotated(clazz.getAnnotations(),
                DataTransferObject.class);

        isValid(isAnnotated, "La clase \"" + clazz.getName()
                + "\" no esta anotada como un " + DataTransferObject.class
                + ", debe especificar una clase anotada bajo dicho tipo");

        // valida que el objeto a popular tenga declaradas al menos una
        // propiedad en la cual almacenar informacion.
        isValid(clazz.getDeclaredFields().length != 0, "La clase \""
                + clazz.getName() + "\" debe tener declaradas al menos "
                + "una propiedad para almacenar informacion");
    }
}
