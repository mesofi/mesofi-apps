package mx.com.mesofi.web.admin.datasource.service;

import static mx.com.mesofi.services.util.SimpleCommonActions.fromNullToCustomValue;
import static mx.com.mesofi.services.util.SimpleValidator.isNull;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.mesofi.framework.stereotype.Inject;
import mx.com.mesofi.framework.stereotype.Service;
import mx.com.mesofi.framework.stereotype.Transaction;
import mx.com.mesofi.web.admin.builder.service.BuilderAppService;
import mx.com.mesofi.web.admin.datasource.DataSourceManager;
import mx.com.mesofi.web.admin.datasource.dao.DataSourceDao;
import mx.com.mesofi.web.admin.datasource.error.RemoteConnectionException;
import mx.com.mesofi.web.admin.datasource.vo.ColumnDetailsVo;
import mx.com.mesofi.web.admin.datasource.vo.ColumnFullDetailsVo;
import mx.com.mesofi.web.admin.datasource.vo.ColumnNameVo;
import mx.com.mesofi.web.admin.datasource.vo.ConnectionDetailsVo;
import mx.com.mesofi.web.admin.datasource.vo.DataSourceSimpleVo;
import mx.com.mesofi.web.admin.datasource.vo.DataSourceVo;
import mx.com.mesofi.web.admin.datasource.vo.SchemaDatabaseDetailsVo;
import mx.com.mesofi.web.admin.datasource.vo.SchemaDatabaseFullDetailsVo;
import mx.com.mesofi.web.admin.datasource.vo.SchemaDatabaseVo;

@Service
public class DataSourceServiceImpl implements DataSourceService {

    @Inject
    private DataSourceDao dataSourceDao;

    @Inject
    private BuilderAppService builderAppService;

    @Override
    public ConnectionDetailsVo createOrUpdateRemoteDataSource(DataSourceVo vo) {
        createAditionalParams(vo);
        ConnectionDetailsVo connectionDetailsVo = new ConnectionDetailsVo();
        connectionDetailsVo.setDataSourceVo(vo);
        if (vo.getId() == 0) {
            // it is a new connection
            try {
                DataSource dataSource = dataSourceDao.connectToDatabase(vo);
                int idDataSource = dataSourceDao.persistConnection(vo);
                // saves the dataSource to be used later on.
                DataSourceManager dsManager = DataSourceManager.getInstance();
                dsManager.addDataSource(idDataSource, dataSource);
                Map<String, Object> metadata = dataSourceDao.getDatabaseInfo(idDataSource);
                connectionDetailsVo.getDataSourceVo().setId(idDataSource);
                connectionDetailsVo.setMetadata(metadata);
                connectionDetailsVo.setSuccess(true);
            } catch (RemoteConnectionException e) {
                connectionDetailsVo.setSuccess(false);
                connectionDetailsVo.setErrorDescription(e.getMessage());
            }
        } else {
            // update existing connection.
            dataSourceDao.updateConnection(vo);
            // updates the dataSource to be used later on.
            connectionDetailsVo.setSuccess(true);
        }
        return connectionDetailsVo;
    }

    @Override
    public List<String> getTableNames(Integer connId, String databaseSchema, Boolean isDatabase) {
        return dataSourceDao.getTableNames(connId, databaseSchema, isDatabase);
    }

    @Override
    public List<ColumnNameVo> getColumnNames(Integer connId, String databaseSchema, String tableName, Boolean isDatabase) {
        return dataSourceDao.getColumnNames(connId, databaseSchema, tableName, isDatabase);
    }

    @Override
    public List<DataSourceSimpleVo> getAvailableConnections() {
        return dataSourceDao.getConnections();
    }

    @Override
    public DataSourceVo getAvailableConnection(Integer connId) {
        isNull(connId, "Connection identifier must be not null");
        DataSourceVo dataSourceVo = dataSourceDao.getConnection(connId);
        if (dataSourceVo == null) {
            throw new IllegalStateException("Could not find a valid connection given this identifier: [" + connId + "]");
        }
        return dataSourceVo;
    }

    @Override
    public List<SchemaDatabaseVo> getAvailableSchemaDatabase(Integer connId) throws RemoteConnectionException {
        List<SchemaDatabaseVo> list = null;
        // get database details to connect to some specific database
        DataSourceVo dataSourceVo = dataSourceDao.getConnection(connId == null ? 0 : connId);
        // tries to connect
        DataSource dataSource = dataSourceDao.connectToDatabase(dataSourceVo);
        // saves the dataSource to be user later on.
        DataSourceManager dsManager = DataSourceManager.getInstance();
        dsManager.addDataSource(connId, dataSource);
        list = dataSourceDao.getSchemaDatabase(dataSourceVo, connId);

        return list;
    }

    @Override
    public ConnectionDetailsVo getConnectionDetails(Integer connId) {
        // get database details to connect to some specific database
        DataSourceVo dataSourceVo = dataSourceDao.getConnection(connId == null ? 0 : connId);

        ConnectionDetailsVo connectionDetailsVo = new ConnectionDetailsVo();
        Map<String, Object> metadata = dataSourceDao.getDatabaseInfo(connId);
        connectionDetailsVo.setMetadata(metadata);
        connectionDetailsVo.setSuccess(true);
        connectionDetailsVo.setDataSourceVo(dataSourceVo);
        return connectionDetailsVo;
    }

    /**
     * {@inheritDoc}
     */
    @Transaction
    @Override
    public int deleteRemoteDataSource(List<Integer> connIds) {
        for (Integer connId : connIds) {
            dataSourceDao.deleteConnection(connId);
        }
        return connIds.size();
    }

    /**
     * {@inheritDoc}
     */
    @Transaction
    @Override
    public int duplicateRemoteDataSource(List<Integer> connIds) {
        DataSourceVo dataSourceVo = null;
        for (Integer connId : connIds) {
            dataSourceVo = dataSourceDao.getConnection(connId);
            dataSourceVo.setDbConnName(dataSourceVo.getDbConnName() + " (copy)");
            dataSourceDao.persistConnection(dataSourceVo);
        }
        return connIds.size();
    }

    private void createAditionalParams(DataSourceVo vo) {
        String url = null;
        switch (vo.getDatabaseType()) {
        case DB2:
            break;
        case HSQLDB:
            url = "jdbc:hsqldb:mem:" + vo.getDatabaseName();
            break;
        case MY_SQL:
            url = "jdbc:mysql://" + vo.getHost() + ":" + vo.getPort() + "/" + vo.getDatabaseName();
            break;
        case ORACLE:
            String fixedPart = "jdbc:oracle:thin:@";
            url = vo.isServiceNameInUse() ? fixedPart + "//" + vo.getHost() + ":" + vo.getPort() + "/"
                    + vo.getDatabaseName() : fixedPart + vo.getHost() + ":" + vo.getPort() + ":" + vo.getDatabaseName();
            break;
        case POSTGRESQL:
            throw new IllegalStateException("This configuration has not been implemented yet " + vo.getDatabaseType());
        case SQL_SERVER:
            throw new IllegalStateException("This configuration has not been implemented yet " + vo.getDatabaseType());
        case DERBY:
            url = "jdbc:derby://" + vo.getHost() + ":" + vo.getPort() + "/" + vo.getDatabaseName() + ";create=true";
            break;
        default:
            break;
        }
        vo.setUrl(url);
    }

    @Override
    public SchemaDatabaseFullDetailsVo getSchemaDatabaseDetails(Integer connId, String databaseSchema,
            Boolean isDatabase) {
        SchemaDatabaseFullDetailsVo vo = null;
        vo = dataSourceDao.getSchemaDatabaseDetails(connId, databaseSchema, isDatabase);
        int totalTables = 0;
        int totalAlias = 0;
        int totalGlobalTemporary = 0;
        int totalLocalTemporary = 0;
        int totalSynonym = 0;
        int totalViews = 0;
        int totalSystemTables = 0;
        for (SchemaDatabaseDetailsVo details : vo.getMetadata()) {
            if (details.getType().equals("TABLE")) {
                totalTables++;
            } else if (details.getType().equals("VIEW")) {
                totalViews++;
            } else if (details.getType().equals("SYSTEM TABLE")) {
                totalSystemTables++;
            } else if (details.getType().equals("GLOBAL TEMPORARY")) {
                totalGlobalTemporary++;
            } else if (details.getType().equals("ALIAS")) {
                totalAlias++;
            } else if (details.getType().equals("SYNONYM")) {
                totalSynonym++;
            } else if (details.getType().equals("LOCAL TEMPORARY")) {
                totalLocalTemporary++;
            }
            details.setRemarks(fromNullToCustomValue(details.getRemarks()));
            details.setCat(fromNullToCustomValue(details.getCat()));
        }
        vo.setTotalTables(totalTables);
        vo.setTotalAlias(totalAlias);
        vo.setTotalGlobalTemporary(totalGlobalTemporary);
        vo.setTotalLocalTemporary(totalLocalTemporary);
        vo.setTotalSynonym(totalSynonym);
        vo.setTotalViews(totalViews);
        vo.setTotalSystemTables(totalSystemTables);
        vo.setSchemaOrDatabase(databaseSchema);
        return vo;
    }

    @Override
    public ColumnFullDetailsVo getTableDetails(Integer connId, String databaseSchema, String tableName,
            Boolean isDatabase) {
        ColumnFullDetailsVo vo = null;
        vo = dataSourceDao.getTableDetails(connId, databaseSchema, tableName, isDatabase);
        int totalPrimaryKeys = 0;
        for (ColumnDetailsVo details : vo.getMetadata()) {
            if (details.isPrimaryKey()) {
                totalPrimaryKeys++;
            }
            details.setRemarks(fromNullToCustomValue(details.getRemarks()));
        }
        vo.setTotalPrimaryKeys(totalPrimaryKeys);
        vo.setTotalColumns(vo.getMetadata().size());
        // sets back the value of the requested data.
        vo.setTableName(tableName);
        vo.setConnId(connId);
        vo.setDatabaseSchemaName(databaseSchema);
        vo.setDatabase(isDatabase);
        vo.setConfigSaved(builderAppService.isConfigurationPersisted(connId, databaseSchema, tableName));
        return vo;
    }

}
