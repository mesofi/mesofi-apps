package mx.com.mesofi.web.admin.datasource.service;

import java.util.List;

import mx.com.mesofi.web.admin.datasource.error.RemoteConnectionException;
import mx.com.mesofi.web.admin.datasource.vo.ColumnFullDetailsVo;
import mx.com.mesofi.web.admin.datasource.vo.ColumnNameVo;
import mx.com.mesofi.web.admin.datasource.vo.ConnectionDetailsVo;
import mx.com.mesofi.web.admin.datasource.vo.DataSourceSimpleVo;
import mx.com.mesofi.web.admin.datasource.vo.DataSourceVo;
import mx.com.mesofi.web.admin.datasource.vo.SchemaDatabaseFullDetailsVo;
import mx.com.mesofi.web.admin.datasource.vo.SchemaDatabaseVo;

public interface DataSourceService {

    int deleteRemoteDataSource(List<Integer> connId);

    int duplicateRemoteDataSource(List<Integer> connId);

    ConnectionDetailsVo createOrUpdateRemoteDataSource(DataSourceVo vo);

    List<String> getTableNames(Integer connId, String databaseSchema, Boolean isDatabase);

    List<ColumnNameVo> getColumnNames(Integer connId, String databaseSchema, String tableName, Boolean isDatabase);

    List<DataSourceSimpleVo> getAvailableConnections();

    DataSourceVo getAvailableConnection(Integer connId);

    List<SchemaDatabaseVo> getAvailableSchemaDatabase(Integer connId) throws RemoteConnectionException;

    ConnectionDetailsVo getConnectionDetails(Integer connId);

    SchemaDatabaseFullDetailsVo getSchemaDatabaseDetails(Integer connId, String databaseSchema, Boolean isDatabase);

    ColumnFullDetailsVo getTableDetails(Integer connId, String databaseSchema, String tableName, Boolean isDatabase);
}
