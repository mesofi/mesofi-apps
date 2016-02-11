/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.framework.jdbc;

import static mx.com.mesofi.framework.util.FrameworkUtils.FORMAT_SQL;
import static mx.com.mesofi.framework.util.FrameworkUtils.SHOW_SQL;
import static mx.com.mesofi.framework.util.FrameworkUtils.validateConfig;
import static mx.com.mesofi.services.util.SimpleValidator.isEmpty;
import static mx.com.mesofi.services.util.SimpleValidator.isNull;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import mx.com.mesofi.framework.jdbc.util.SqlParser;
import mx.com.mesofi.framework.stereotype.DAO;
import mx.com.mesofi.framework.util.ValidationType;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * This abstract class is meant to be used only for extension. Every class
 * annotated with {@link DAO} annotation MUST extend from this class in order
 * simplify the use of interaction with databases. By extending this class makes
 * even easier the use of {@link JdbcTemplate} from Spring framework.
 * 
 * <p>
 * This class also adds some more functionality like showing the real SQL to be
 * executed.
 * 
 * @author Armando Rivas
 * @since 24 Sep 2013
 */
public abstract class JdbcAbstractDao {
    /**
     * log4j.
     */
    private static Logger log = Logger.getLogger(JdbcAbstractDao.class);

    /**
     * This makes possible to show or not the statements to be execute against
     * the data base, if this flag is set in true, then every SQL will be shown
     * in console using their evaluated parameters.
     */
    @Value("${" + SHOW_SQL + "}")
    private String showSql;

    /**
     * If set, this flag makes possible to format every statement so that the
     * query has a better look and can be read easily.
     */
    @Value("${" + FORMAT_SQL + "}")
    private String formatSql;

    /**
     * jdbcTemplate.
     */
    private JdbcTemplate jdbcTemplate;

    /**
     * Constructs this object by passing the Data source.
     * 
     * @param dataSource
     *            DataSource associated.
     */
    public JdbcAbstractDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Gets the data source associated.
     * 
     * @return The data source.
     */
    protected JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    // -------------------------------------------------------------------------
    // Methods dealing with queryForInt
    // -------------------------------------------------------------------------

    /**
     * Execute a query that results in an int value, given static SQL.
     * <p>
     * This method is useful for running static SQL with a known outcome. The
     * query is expected to be a single row/single column query that results in
     * an int value.
     * 
     * @param sql
     *            SQL query to execute
     * @param params
     *            The parameters to be bound in the sql execution.
     * @return the int value, or 0 in case of SQL NULL
     * @see #queryForInt(String, Object...)
     * @see #queryForInt(String)
     */
    protected int queryForInt(String sql, List<Object> params) {
        isNull(params, "The specified params must not be null");
        return queryForInt(sql, params.toArray());
    }

    /**
     * Execute a query that results in an int value, given static SQL.
     * <p>
     * This method is useful for running static SQL with a known outcome. The
     * query is expected to be a single row/single column query that results in
     * an int value.
     * <p>
     * If the specified parameters are null or empty, then it is invoked the
     * version without parameters.
     * {@link JdbcAbstractDAO#queryForTemplate(String, Object...)}
     * 
     * @param sql
     *            SQL query to execute
     * @param params
     *            The parameters to be bound in the sql execution.
     * @return the int value, or 0 in case of SQL NULL
     * @see #queryForInt(String, List)
     * @see #queryForInt(String)
     */
    protected int queryForInt(String sql, Object... params) {
        if (isEmpty(params)) {
            return queryForTemplate(Integer.class, sql);
        } else {
            return queryForTemplate(Integer.class, sql, params);
        }
    }

    /**
     * Execute a query that results in an int value, given static SQL.
     * <p>
     * This method is useful for running static SQL with a known outcome. The
     * query is expected to be a single row/single column query that results in
     * an int value.
     * 
     * @param sql
     *            SQL query to execute
     * @return the int value, or 0 in case of SQL NULL
     * @see #queryForInt(String, List)
     * @see #queryForInt(String, Object...)
     */
    protected int queryForInt(String sql) {
        return queryForTemplate(Integer.class, sql);
    }

    // -------------------------------------------------------------------------
    // Methods dealing with queryForLong
    // -------------------------------------------------------------------------

    /**
     * Execute a query that results in an long value, given static SQL.
     * <p>
     * This method is useful for running static SQL with a known outcome. The
     * query is expected to be a single row/single column query that results in
     * an long value.
     * 
     * @param sql
     *            SQL query to execute
     * @param params
     *            The parameters to be bound in the sql execution.
     * @return the long value, or 0 in case of SQL NULL
     * @see #queryForLong(String, Object...)
     * @see #queryForLong(String)
     */
    protected long queryForLong(String sql, List<Object> params) {
        isNull(params, "The specified params must not be null");
        return queryForLong(sql, params.toArray());
    }

    /**
     * Execute a query that results in an long value, given static SQL.
     * <p>
     * This method is useful for running static SQL with a known outcome. The
     * query is expected to be a single row/single column query that results in
     * an long value.
     * <p>
     * If the specified parameters are null or empty, then it is invoked the
     * version without parameters.
     * {@link JdbcAbstractDAO#queryForTemplate(String, Object...)}
     * 
     * @param sql
     *            SQL query to execute
     * @param params
     *            The parameters to be bound in the sql execution.
     * @return the long value, or 0 in case of SQL NULL
     * @see #queryForLong(String, List)
     * @see #queryForLong(String)
     */
    protected long queryForLong(String sql, Object... params) {
        if (isEmpty(params)) {
            return queryForTemplate(Long.class, sql);
        } else {
            return queryForTemplate(Long.class, sql, params);
        }
    }

    /**
     * Execute a query that results in an long value, given static SQL.
     * <p>
     * This method is useful for running static SQL with a known outcome. The
     * query is expected to be a single row/single column query that results in
     * an long value.
     * 
     * @param sql
     *            SQL query to execute
     * @return the long value, or 0 in case of SQL NULL
     * @see #queryForLong(String, List)
     * @see #queryForLong(String, Object...)
     */
    protected long queryForLong(String sql) {
        return queryForTemplate(Long.class, sql);
    }

    // -------------------------------------------------------------------------
    // Methods dealing with queryForObject
    // -------------------------------------------------------------------------

    /**
     * Query given SQL to create a prepared statement from SQL and a list of
     * arguments to bind to the query, expecting a result object.
     * <p>
     * The query is expected to be a single row/single column query; the
     * returned result will be directly mapped to the corresponding object type.
     * 
     * @param sql
     *            SQL query to execute
     * @param type
     *            the type that the result object is expected to match
     * @param params
     *            arguments to bind to the query (leaving it to the
     *            PreparedStatement to guess the corresponding SQL type);
     * @return the result object of the required type, or {@code null} in case
     *         of SQL NULL
     * @see #queryForObject(String, Class, Object...)
     * @see #queryForObject(String, Class)
     */
    protected <T> T queryForObject(String sql, Class<T> type, List<Object> params) {
        isNull(params, "The specified params must not be null");
        return queryForObject(sql, type, params.toArray());
    }

    /**
     * Query given SQL to create a prepared statement from SQL and a list of
     * arguments to bind to the query, expecting a result object.
     * <p>
     * The query is expected to be a single row/single column query; the
     * returned result will be directly mapped to the corresponding object type.
     * 
     * @param sql
     *            SQL query to execute
     * @param type
     *            the type that the result object is expected to match
     * @param params
     *            arguments to bind to the query (leaving it to the
     *            PreparedStatement to guess the corresponding SQL type);
     * @return the result object of the required type, or {@code null} in case
     *         of SQL NULL
     * @see #queryForObject(String, Class, List)
     * @see #queryForObject(String, Class)
     */
    protected <T> T queryForObject(String sql, Class<T> type, Object... params) {
        if (isEmpty(params)) {
            return queryForTemplate(sql, type);
        } else {
            return queryForTemplate(sql, type, params);
        }
    }

    /**
     * Query given SQL to create a prepared statement from SQL and a list of
     * arguments to bind to the query, expecting a result object.
     * <p>
     * The query is expected to be a single row/single column query; the
     * returned result will be directly mapped to the corresponding object type.
     * 
     * @param sql
     *            SQL query to execute
     * @param type
     *            the type that the result object is expected to match
     * @param params
     *            arguments to bind to the query (leaving it to the
     *            PreparedStatement to guess the corresponding SQL type);
     * @return the result object of the required type, or {@code null} in case
     *         of SQL NULL
     * @see #queryForObject(String, Class, List)
     * @see #queryForObject(String, Class, Object...)
     */
    protected <T> T queryForObject(String sql, Class<T> type) {
        return queryForTemplate(sql, type);
    }

    /**
     * Query given SQL to create a prepared statement from SQL and a list of
     * arguments to bind to the query, mapping a single result row to a Java
     * object via a RowMapper.
     * 
     * @param sql
     *            SQL query to execute
     * @param rowMapper
     *            object that will map one object per row
     * @param params
     *            arguments to bind to the query (leaving it to the
     *            PreparedStatement to guess the corresponding SQL type)
     * @return the single mapped object
     * @see #queryForObject(String, Class, List)
     * @see #queryForObject(String, Class, Object...)
     * @see #queryForObject(String, Class)
     */
    protected <T> T queryForObject(String sql, JdbcRowMapper<T> mapper, Object... params) {
        if (new Boolean(showSql)) {
            printParameters(sql, params);
        }
        return this.jdbcTemplate.queryForObject(sql, mapper, params);
    }

    /**
     * Performs the actual execution using JdbcTemplate.
     * 
     * @param sql
     *            SQL query to execute
     * @param type
     *            Type of the object to be queried.
     * @param params
     *            The parameters to be bound in the sql execution.
     * @return Object retrieved.
     */
    private <T> T queryForTemplate(String sql, Class<T> type, Object... params) {
        if (params != null) {
            if (new Boolean(showSql)) {
                printParameters(sql, params);
            }
            return this.jdbcTemplate.queryForObject(sql, type, params);
        } else {
            return this.jdbcTemplate.queryForObject(sql, type);
        }
    }

    /**
     * Performs the actual execution using JdbcTemplate.
     * 
     * @param clazz
     *            Type to be executed..
     * @param sql
     *            SQL query to execute
     * @param params
     *            The parameters to be bound in the sql execution.
     * @return the int value, or 0 in case of SQL NULL
     */
    private <T> T queryForTemplate(Class<T> clazz, String sql, Object... params) {
        if (params != null) {
            if (new Boolean(showSql)) {
                printParameters(sql, params);
            }
            return this.jdbcTemplate.queryForObject(sql, params, clazz);
        } else {
            return this.jdbcTemplate.queryForObject(sql, clazz);
        }
    }

    // -------------------------------------------------------------------------
    // Methods dealing with query, update, etc.
    // -------------------------------------------------------------------------

    /**
     * Execute a query given static SQL, mapping each row to a Java object via a
     * RowMapper.
     * <p>
     * Uses a JDBC Statement, not a PreparedStatement. If you want to execute a
     * static query with a PreparedStatement, use the overloaded {@code query}
     * method with {@code null} as argument array.
     * 
     * @param sql
     *            SQL query to execute
     * @param mapper
     *            object that will map one object per row
     * @param params
     *            Parameters.
     * @return the result List, containing mapped objects
     */
    protected <T> List<T> query(String sql, JdbcRowMapper<T> mapper, Object... params) {
        if (new Boolean(showSql)) {
            printParameters(sql, params);
        }
        return this.jdbcTemplate.query(sql, mapper, params);
    }

    /**
     * Issue a single SQL update operation (such as an insert, update or delete
     * statement) via a prepared statement, binding the given arguments. If the
     * query does not affect any record, throws a validation.
     * 
     * @param sql
     *            SQL containing bind parameters
     * @param params
     *            arguments to bind to the query (leaving it to the
     *            PreparedStatement to guess the corresponding SQL type); may
     *            also contain {@link SqlParameterValue} objects which indicate
     *            not only the argument value but also the SQL type and
     *            optionally the scale
     * @return the number of rows affected
     * @see #update(String, List)
     */
    protected int update(String sql, Object... params) {
        if (new Boolean(showSql)) {
            printParameters(sql, params);
        }
        return validateAffectedRecords(this.jdbcTemplate.update(sql, params));
    }

    /**
     * Issue a single SQL update operation (such as an insert, update or delete
     * statement) via a prepared statement, binding the given arguments. If the
     * query does not affect any record, throws a validation.
     * 
     * @param sql
     *            SQL containing bind parameters
     * @param params
     *            arguments to bind to the query (leaving it to the
     *            PreparedStatement to guess the corresponding SQL type); may
     *            also contain {@link SqlParameterValue} objects which indicate
     *            not only the argument value but also the SQL type and
     *            optionally the scale
     * @return the number of rows affected
     * @see #update(String, Object...)
     */
    protected int update(String sql, List<Object> params) {
        return update(sql, params.toArray());
    }

    /**
     * * Issue a single SQL update operation (such as an insert, update or
     * delete statement) via a prepared statement, binding the given arguments.
     * If the query does not affect any record, throws a validation.
     * 
     * @param sql
     *            SQL containing bind parameters.
     * @param params
     *            arguments to bind to the query (leaving it to the
     *            PreparedStatement to guess the corresponding SQL type); may
     *            also contain {@link SqlParameterValue} objects which indicate
     *            not only the argument value but also the SQL type and
     *            optionally the scale
     * @return the number of rows affected
     * @see #insert(String, List)
     * @see #insert(String, String, Object...)
     */
    protected int insert(String sql, Object... params) {
        return update(sql, params);
    }

    /**
     * Issue a single SQL update operation (such as an insert, update or delete
     * statement) via a prepared statement, binding the given arguments. If the
     * query does not affect any record, throws a validation.
     * 
     * @param sql
     *            SQL containing bind parameters.
     * @param params
     *            arguments to bind to the query (leaving it to the
     *            PreparedStatement to guess the corresponding SQL type); may
     *            also contain {@link SqlParameterValue} objects which indicate
     *            not only the argument value but also the SQL type and
     *            optionally the scale
     * @return the number of rows affected
     * @see #insert(String, Object...)
     * @see #insert(String, String, Object...)
     */
    protected int insert(String sql, List<Object> params) {
        return update(sql, params.toArray());
    }

    /**
     * Performs an specific insert into the database and returns the generated
     * id.
     * 
     * @param sqlInsert
     *            Insert statement.
     * @param idColumnName
     *            column identifier that acts as primary key.
     * @param params
     *            Parameters in the insert statement.
     * @return generated id after the insertion.
     */
    protected long insert(final String sqlInsert, final String idColumnName, final Object... params) {
        if (new Boolean(showSql)) {
            printParameters(sqlInsert, params);
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sqlInsert, new String[] { idColumnName });
                int pos = 1;
                for (Object param : params) {
                    ps.setObject(pos++, param);
                }
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    /**
     * Issue a single SQL execute, typically a DDL statement.
     * 
     * @param sql
     *            static SQL to execute
     */
    protected void execute(String sql) {
        this.jdbcTemplate.execute(sql);
    }

    /**
     * This method is invoked to evaluate the parameters against some velocity
     * template.
     * 
     * @param sql
     *            Sql to evaluate.
     * @param params
     *            Parameters in velocity context.
     * @return sql evaluated.
     */
    protected static String evaluateSql(String sql, Map<String, Object> params) {
        String newSql = sql;

        if (params != null && !params.keySet().isEmpty()) {
            VelocityContext context = new VelocityContext();
            for (String key : params.keySet()) {
                context.put(key, params.get(key));
            }
            StringWriter swOut = new StringWriter();
            Velocity.evaluate(context, swOut, "log", sql);
            newSql = swOut.toString();
        }

        return newSql;
    }

    /**
     * Validates the affected records. If the affected value is 0 then it throws
     * an exception showing the message error.
     * 
     * @param affected
     *            Total number of objects to be affected.
     * @return total objects affected.
     */
    protected int validateAffectedRecords(int affected) {
        if (affected == 0) {
            throw new IllegalStateException("The performed transaction did not update any record");
        }
        return affected;
    }

    /**
     * Prints the parameters evaluated.
     * 
     * @param sql
     *            SQL containing bind parameters
     * @param params
     *            arguments to bind to the query
     */
    void printParameters(final String sql, final Object[] params) {
        SqlParser sqlParser = new SqlParser();
        sqlParser.setParams(params);
        sqlParser.setSql(sql);

        sqlParser.printSqlEvaluation();
        if (new Boolean(formatSql)) {
            sqlParser.formatSql();
            if (log.isDebugEnabled()) {
                log.debug("Details in SQL statements formatted\nSQL>" + sqlParser.getSql());
            }
        }
    }

    @PostConstruct
    void validateIntegrity() {
        showSql = validateConfig(SHOW_SQL, showSql, ValidationType.WARNING);
        formatSql = validateConfig(FORMAT_SQL, formatSql, ValidationType.WARNING);
    }
}
