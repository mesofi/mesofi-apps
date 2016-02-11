package mx.com.mesofi.web.admin.datasource.dao;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import mx.com.mesofi.web.admin.datasource.error.RemoteConnectionException;
import mx.com.mesofi.web.admin.datasource.vo.ColumnFullDetailsVo;
import mx.com.mesofi.web.admin.datasource.vo.ColumnNameVo;
import mx.com.mesofi.web.admin.datasource.vo.DataSourceSimpleVo;
import mx.com.mesofi.web.admin.datasource.vo.DataSourceVo;
import mx.com.mesofi.web.admin.datasource.vo.SchemaDatabaseFullDetailsVo;
import mx.com.mesofi.web.admin.datasource.vo.SchemaDatabaseVo;

public interface DataSourceDao {

    DataSource connectToDatabase(DataSourceVo vo) throws RemoteConnectionException;

    Map<String, Object> getDatabaseInfo(Integer connId);

    /**
     * 
     * @param connId
     * @param schemaOrDatabaseName
     * @param isDatabase
     * @return
     * @see DataSourceDao#getSchemaDatabaseDetails(Integer, String, Boolean)
     */
    List<String> getTableNames(Integer connId, String schemaOrDatabaseName, Boolean isDatabase);

    List<ColumnNameVo> getColumnNames(Integer connId, String schemaOrDatabaseName, String tableName, Boolean isDatabase);

    List<DataSourceSimpleVo> getConnections();

    DataSourceVo getConnection(int connId);

    List<SchemaDatabaseVo> getSchemaDatabase(DataSourceVo dataSourceVo, int connId);

    int persistConnection(DataSourceVo vo);

    void updateConnection(DataSourceVo vo);

    int deleteConnection(Integer connId);

    /**
     * 
     * @param connId
     * @param schemaOrDatabaseName
     * @param isDatabase
     * @see DataSourceDao#getTableNames(Integer, String, Boolean)
     */
    SchemaDatabaseFullDetailsVo getSchemaDatabaseDetails(Integer connId, String schemaOrDatabaseName, Boolean isDatabase);

    ColumnFullDetailsVo getTableDetails(Integer connId, String databaseSchema, String tableName, Boolean isDatabase);
}
