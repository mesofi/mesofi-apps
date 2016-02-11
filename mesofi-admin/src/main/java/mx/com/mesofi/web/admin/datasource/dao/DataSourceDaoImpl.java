package mx.com.mesofi.web.admin.datasource.dao;

import static mx.com.mesofi.services.dao.JdbcUtil.close;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import mx.com.mesofi.framework.jdbc.JdbcAbstractDao;
import mx.com.mesofi.framework.jdbc.JdbcRowMapper;
import mx.com.mesofi.framework.stereotype.DAO;
import mx.com.mesofi.framework.stereotype.Inject;
import mx.com.mesofi.plugins.project.core.DatabaseType;
import mx.com.mesofi.services.dao.exception.DataAccessRuntimeException;
import static mx.com.mesofi.services.util.SimpleCommonActions.serializeString;
import mx.com.mesofi.web.admin.datasource.DataSourceManager;
import mx.com.mesofi.web.admin.datasource.error.RemoteConnectionException;
import mx.com.mesofi.web.admin.datasource.vo.ColumnDetailsVo;
import mx.com.mesofi.web.admin.datasource.vo.ColumnFullDetailsVo;
import mx.com.mesofi.web.admin.datasource.vo.ColumnNameVo;
import mx.com.mesofi.web.admin.datasource.vo.DataSourceSimpleVo;
import mx.com.mesofi.web.admin.datasource.vo.DataSourceVo;
import mx.com.mesofi.web.admin.datasource.vo.SchemaDatabaseDetailsVo;
import mx.com.mesofi.web.admin.datasource.vo.SchemaDatabaseFullDetailsVo;
import mx.com.mesofi.web.admin.datasource.vo.SchemaDatabaseVo;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

@DAO
public class DataSourceDaoImpl extends JdbcAbstractDao implements DataSourceDao {
    /**
     * log4j.
     */
    private static final Logger log = Logger.getLogger(DataSourceDaoImpl.class);
    /**
     * SQL statements.
     */
    @Inject
    private Properties dataSourceDao;

    @Inject
    public DataSourceDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public DataSource connectToDatabase(DataSourceVo dataSourceVo) throws RemoteConnectionException {
        if (log.isDebugEnabled()) {
            log.debug("Trying to connect to " + dataSourceVo.getDatabaseType().name() + " using the following URL: "
                    + dataSourceVo.getUrl());
        }

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(dataSourceVo.getUrl());
        dataSource.setDriverClassName(dataSourceVo.getDatabaseType().getDriverClassName());
        dataSource.setUsername(dataSourceVo.getUser());
        dataSource.setPassword(dataSourceVo.getPassword());

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            if (log.isDebugEnabled()) {
                log.debug("Connection to " + dataSourceVo.getDatabaseType().name() + " sucessfull!!! ");
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
            throw new RemoteConnectionException(e);
        } finally {
            close(connection);
        }
        return dataSource;
    }

    @Override
    public Map<String, Object> getDatabaseInfo(Integer connId) {
        DataSource dataSource = null;
        Map<String, Object> data = null;
        if (DataSourceManager.getInstance().existDataSource(connId)) {
            dataSource = DataSourceManager.getInstance().getDataSource(connId);

            data = new HashMap<String, Object>();
            Connection connection = null;
            try {
                connection = dataSource.getConnection();
                DatabaseMetaData metaData = connection.getMetaData();
                data.put("name", metaData.getDatabaseProductName());
                data.put("version", metaData.getDatabaseProductVersion());
                data.put("driverName", metaData.getDriverName());
                data.put("driverVersion", metaData.getDriverVersion());
            } catch (SQLException e) {
                log.warn(e.getMessage());
                throw new DataAccessRuntimeException(e);
            } finally {
                close(connection);
            }
        }
        return data;
    }

    @Override
    public List<String> getTableNames(Integer connId, String schemaOrDatabaseName, Boolean isDatabase) {
        List<String> tables = new ArrayList<String>();
        DataSource dataSource = DataSourceManager.getInstance().getDataSource(connId);
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            if (isDatabase) {
                rs = metaData.getTables(schemaOrDatabaseName, null, null, new String[] { "TABLE", "SYSTEM TABLE" });
            } else {
                rs = metaData.getTables(null, schemaOrDatabaseName, null, new String[] { "TABLE", "SYSTEM TABLE" });
            }
            while (rs.next()) {
                tables.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
            throw new DataAccessRuntimeException(e);
        } finally {
            close(rs);
            close(connection);
        }
        return tables;
    }

    @Override
    public List<ColumnNameVo> getColumnNames(Integer connId, String schemaOrDatabaseName, String tableName,
            Boolean isDatabase) {
        List<ColumnNameVo> columns = new ArrayList<ColumnNameVo>();
        DataSource dataSource = DataSourceManager.getInstance().getDataSource(connId);
        Connection connection = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        try {
            connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();

            if (isDatabase) {
                rs2 = metaData.getPrimaryKeys(schemaOrDatabaseName, null, tableName);
                rs1 = metaData.getColumns(schemaOrDatabaseName, null, tableName, null);
            } else {
                rs2 = metaData.getPrimaryKeys(null, schemaOrDatabaseName, tableName);
                rs1 = metaData.getColumns(null, schemaOrDatabaseName, tableName, null);
            }
            while (rs1.next()) {
                ColumnNameVo columnNameVo = new ColumnNameVo();
                columnNameVo.setDescription(rs1.getString("COLUMN_NAME"));
                columns.add(columnNameVo);
            }
            String column = null;
            while (rs2.next()) {
                column = rs2.getString("COLUMN_NAME");
                for (ColumnNameVo vo : columns) {
                    if (vo.getDescription().equals(column)) {
                        vo.setPrimaryKey(true);
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
            throw new DataAccessRuntimeException(e);
        } finally {
            close(rs1);
            close(rs2);
            close(connection);
        }
        return columns;
    }

    @Override
    public List<DataSourceSimpleVo> getConnections() {
        List<DataSourceSimpleVo> list = new ArrayList<DataSourceSimpleVo>();
        String sql = dataSourceDao.getProperty("select.all.connections");
        list = query(sql, new JdbcRowMapper<DataSourceSimpleVo>() {
            @Override
            public DataSourceSimpleVo mapRow(ResultSet rs, int n) throws SQLException {
                DataSourceSimpleVo connectionVo = new DataSourceSimpleVo();
                connectionVo.setId(rs.getLong("ID"));
                connectionVo.setDescription(rs.getString("CONN_NAME"));
                int dataBaseTypeId = rs.getInt("ID_DATABASE_TYPE");

                DatabaseType databaseType = DatabaseType.valueOf(dataBaseTypeId);
                connectionVo.setDatabaseType(databaseType);
                connectionVo.setDatabaseFormalName(DatabaseType.getFormalName(dataBaseTypeId));
                return connectionVo;
            }
        });
        return list;
    }

    @Override
    public DataSourceVo getConnection(int connId) {
        List<DataSourceVo> list = new ArrayList<DataSourceVo>();
        DataSourceVo vo = null;
        String sql = dataSourceDao.getProperty("select.connection");
        list = query(sql, new JdbcRowMapper<DataSourceVo>() {
            @Override
            public DataSourceVo mapRow(ResultSet rs, int n) throws SQLException {
                DataSourceVo dataSourceVo = new DataSourceVo();
                dataSourceVo.setDbConnName(rs.getString("CONN_NAME"));
                dataSourceVo.setDatabaseType(DatabaseType.valueOf(rs.getInt("ID_DATABASE_TYPE")));
                dataSourceVo.setDatabaseTypeId(rs.getInt("ID_DATABASE_TYPE"));
                dataSourceVo.setDatabaseName(rs.getString("DATABASE_NAME"));
                dataSourceVo.setUser(rs.getString("USER_NAME"));
                dataSourceVo.setPassword(rs.getString("PASSWORD"));
                dataSourceVo.setUrl(rs.getString("URL"));
                dataSourceVo.setPort(rs.getInt("PORT"));
                dataSourceVo.setHost(rs.getString("HOST"));
                dataSourceVo.setServiceNameInUse(rs.getBoolean("SERVICE"));
                return dataSourceVo;
            }
        }, connId);
        vo = list.isEmpty() ? null : list.get(0);
        return vo;
    }

    @SuppressWarnings("resource")
    @Override
    public List<SchemaDatabaseVo> getSchemaDatabase(DataSourceVo vo, int connId) {
        List<SchemaDatabaseVo> databases = new ArrayList<SchemaDatabaseVo>();
        DataSource dataSource = DataSourceManager.getInstance().getDataSource(connId);
        Connection connection = null;
        boolean hasSchemas = false;
        ResultSet rs = null;
        String catalog = null;
        try {
            connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            rs = metaData.getSchemas();
            while (rs.next()) {
                hasSchemas = true;
                SchemaDatabaseVo schemaVo = new SchemaDatabaseVo();
                catalog = rs.getString(1);
                schemaVo.setSchemaDatabase(catalog);
                schemaVo.setDefaultSchemaDb(vo.getUser().equals(catalog));
                schemaVo.setDatabase(false);
                databases.add(schemaVo);
            }
            if (!hasSchemas) {
                rs = metaData.getCatalogs();
                while (rs.next()) {
                    SchemaDatabaseVo databaseVo = new SchemaDatabaseVo();
                    catalog = rs.getString("TABLE_CAT");
                    databaseVo.setSchemaDatabase(catalog);
                    databaseVo.setDefaultSchemaDb(vo.getDatabaseName().equals(catalog));
                    databaseVo.setDatabase(true);
                    databases.add(databaseVo);
                }
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
            throw new DataAccessRuntimeException(e);
        } finally {
            close(rs);
            close(connection);
        }
        return databases;
    }

    @Override
    public int persistConnection(DataSourceVo vo) {
        String sql = dataSourceDao.getProperty("insert.connection");
        List<Object> params = new ArrayList<Object>();
        params.add(vo.getDatabaseType().getId(vo.getDatabaseType().name()));
        params.add(vo.getDbConnName());
        params.add(vo.getUser());
        params.add(vo.getPassword());
        params.add(vo.getHost());
        params.add(vo.getPort());
        params.add(vo.getDatabaseName());
        params.add(vo.isServiceNameInUse());
        params.add(vo.getUrl());

        return (int) insert(sql, "ID", params.toArray());
    }

    @Override
    public void updateConnection(DataSourceVo vo) {
        String sql = dataSourceDao.getProperty("update.connection");
        List<Object> params = new ArrayList<Object>();
        params.add(vo.getDatabaseType().getId(vo.getDatabaseType().name()));
        params.add(vo.getDbConnName());
        params.add(vo.getUser());
        params.add(vo.getPassword());
        params.add(vo.getHost());
        params.add(vo.getPort());
        params.add(vo.getDatabaseName());
        params.add(vo.isServiceNameInUse());
        params.add(vo.getUrl());
        params.add(vo.getId());

        update(sql, params);
    }

    @Override
    public int deleteConnection(Integer connId) {
        return update(dataSourceDao.getProperty("delete.connection"), connId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SchemaDatabaseFullDetailsVo getSchemaDatabaseDetails(Integer connId, String schemaOrDatabaseName,
            Boolean isDatabase) {
        List<SchemaDatabaseDetailsVo> detailsSchema = new ArrayList<SchemaDatabaseDetailsVo>();
        DataSource dataSource = DataSourceManager.getInstance().getDataSource(connId);
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            if (isDatabase) {
                rs = metaData.getTables(schemaOrDatabaseName, null, null, null);
            } else {
                rs = metaData.getTables(null, schemaOrDatabaseName, null, null);
            }
            while (rs.next()) {
                SchemaDatabaseDetailsVo vo = new SchemaDatabaseDetailsVo();
                vo.setCat(rs.getString("TABLE_CAT"));
                vo.setSchem(rs.getString("TABLE_SCHEM"));
                vo.setName(rs.getString("TABLE_NAME"));
                vo.setType(rs.getString("TABLE_TYPE"));
                vo.setRemarks(rs.getString("REMARKS"));
                detailsSchema.add(vo);
            }
        } catch (SQLException e) {
            log.warn(e.getMessage());
            throw new DataAccessRuntimeException(e);
        } finally {
            close(rs);
            close(connection);
        }
        SchemaDatabaseFullDetailsVo vo = new SchemaDatabaseFullDetailsVo();
        vo.setMetadata(detailsSchema);
        return vo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ColumnFullDetailsVo getTableDetails(Integer connId, String schemaOrDatabaseName, String tableName,
            Boolean isDatabase) {
        if (log.isDebugEnabled()) {
            log.debug("Getting table details from database: [" + schemaOrDatabaseName + "]");
        }
        List<ColumnDetailsVo> metadata = new ArrayList<ColumnDetailsVo>();
        DataSource dataSource = DataSourceManager.getInstance().getDataSource(connId);
        Connection connection = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;

        try {
            connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();

            if (isDatabase) {
                rs3 = metaData.getImportedKeys(schemaOrDatabaseName, null, tableName);
                rs2 = metaData.getPrimaryKeys(schemaOrDatabaseName, null, tableName);
                rs1 = metaData.getColumns(schemaOrDatabaseName, null, tableName, null);
            } else {
                rs3 = metaData.getImportedKeys(null, schemaOrDatabaseName, tableName);
                rs2 = metaData.getPrimaryKeys(null, schemaOrDatabaseName, tableName);
                rs1 = metaData.getColumns(null, schemaOrDatabaseName, tableName, null);
            }

            if (log.isDebugEnabled()) {
                log.debug("Getting column details from table: [" + tableName + "]");
            }
            String increment = null;
            while (rs1.next()) {
                ColumnDetailsVo columnNameVo = new ColumnDetailsVo();
                columnNameVo.setDescription(rs1.getString("COLUMN_NAME"));
                columnNameVo.setSize(rs1.getInt("COLUMN_SIZE"));
                columnNameVo.setScale(rs1.getString("DECIMAL_DIGITS"));
                columnNameVo.setRemarks(rs1.getString("REMARKS"));
                columnNameVo.setNullable(rs1.getInt("NULLABLE") == 0 ? false : true);
                columnNameVo.setTypeName(rs1.getString("TYPE_NAME"));
                columnNameVo.setTypeId(rs1.getInt("DATA_TYPE"));

                increment = rs1.getString("IS_AUTOINCREMENT");
                if (increment.equals("YES")) {
                    columnNameVo.setAutoIncrement(true);
                } else if (increment.equals("NO")) {
                    columnNameVo.setAutoIncrement(false);
                }
                metadata.add(columnNameVo);
            }

            if (log.isDebugEnabled()) {
                log.debug("Getting primary key details from table: [" + tableName + "]");
            }

            String refColumnName = null;
            while (rs2.next()) {
                refColumnName = rs2.getString("COLUMN_NAME");
                for (ColumnDetailsVo vo : metadata) {
                    if (vo.getDescription().equals(refColumnName)) {
                        vo.setPrimaryKey(true);
                        if (log.isDebugEnabled()) {
                            log.debug("Primary key found at column: [" + tableName + "." + refColumnName + "]");
                        }
                        break;
                    }
                }
            }
            if (log.isDebugEnabled()) {
                log.debug("Getting imported key details from table: [" + tableName + "]");
            }

            while (rs3.next()) {
                refColumnName = rs3.getString("FKCOLUMN_NAME");
                for (ColumnDetailsVo vo : metadata) {
                    if (vo.getDescription().equals(refColumnName)) {
                        vo.setForeignKey(true);
                        vo.setReferences(rs3.getString("PKTABLE_NAME").toUpperCase(), rs3.getString("PKCOLUMN_NAME"));
                        if (log.isDebugEnabled()) {
                            log.debug("Foreign key found at column: [" + tableName + "." + refColumnName + "]");
                        }
                        break;
                    }
                }
            }

        } catch (SQLException e) {
            log.warn(e.getMessage());
            throw new DataAccessRuntimeException(e);
        } finally {
            close(rs1);
            close(rs2);
            close(rs3);
            close(connection);
        }
        // determines if this column contains predefined values
        for (ColumnDetailsVo vo : metadata) {
            if (vo.getTypeName().equals("ENUM") && vo.getTypeId() == 1) {
                // this is a MySQL type, this might be extended to other
                // databases
                vo.setPermittedValues(retrievePermittedValues(connId, schemaOrDatabaseName, tableName, vo));
            }
        }
        ColumnFullDetailsVo columnFullDetailsVo = new ColumnFullDetailsVo();
        columnFullDetailsVo.setMetadata(metadata);
        return columnFullDetailsVo;
    }

    /**
     * This method retrieves a list of permitted values that a certain column
     * may content. Usually this column can be a ENUM type, hence this method
     * retrieves all the valid values that the method can support.
     * 
     * @param connId
     *            The connectionId.
     * @param schemaOrDatabaseName
     *            Schema or database name.
     * @param tableName
     *            The table name.
     * @param columnDetailsVo
     *            Column details.
     * @return List of permitted values.
     */
    private List<String> retrievePermittedValues(Integer connId, String schemaOrDatabaseName, String tableName,
            ColumnDetailsVo columnDetailsVo) {
        List<String> values = null;

        DataSource dataSource = DataSourceManager.getInstance().getDataSource(connId);
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement stmt = null;
        try {
            connection = dataSource.getConnection();
            String sql = dataSourceDao.getProperty("select.connection.table.column.values");
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, schemaOrDatabaseName);
            stmt.setString(2, tableName);
            stmt.setString(3, columnDetailsVo.getDescription());
            rs = stmt.executeQuery();
            String plainValues = null;
            while (rs.next()) {
                plainValues = rs.getString("ENUM_VALUES");
            }
            values = serializeString(plainValues);
        } catch (SQLException e) {
            log.warn(e.getMessage());
            throw new DataAccessRuntimeException(e);
        } finally {
            close(rs);
            close(stmt);
            close(connection);
        }
        return values;
    }
}
