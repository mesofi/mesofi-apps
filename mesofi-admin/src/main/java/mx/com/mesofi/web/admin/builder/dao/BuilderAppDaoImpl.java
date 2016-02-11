/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.builder.dao;

import static mx.com.mesofi.services.util.SimpleCommonActions.deserializeList;
import static mx.com.mesofi.services.util.SimpleCommonActions.serializeString;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import mx.com.mesofi.framework.jdbc.JdbcAbstractDao;
import mx.com.mesofi.framework.jdbc.JdbcRowMapper;
import mx.com.mesofi.framework.stereotype.DAO;
import mx.com.mesofi.framework.stereotype.Inject;
import mx.com.mesofi.plugins.project.core.DatabaseType;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderMappingCodeTypeVo;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderPreferencesVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderAppConfigVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderAppVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderMappingTypeVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderPluginVo;
import mx.com.mesofi.web.admin.datasource.vo.ColumnDetailsVo;

import org.apache.log4j.Logger;

/**
 * @author armando
 * 
 */
@DAO
public class BuilderAppDaoImpl extends JdbcAbstractDao implements BuilderAppDao {
    /**
     * log4j.
     */
    private static final Logger log = Logger.getLogger(BuilderAppDaoImpl.class);
    /**
     * SQL statements.
     */
    @Inject
    private Properties builderAppDao;

    @Inject
    public BuilderAppDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public int persistTableConfig(BuilderAppVo vo) {
        if (log.isDebugEnabled()) {
            log.debug("Saving configuration for table [" + vo.getTableName() + "]");
        }
        String sql = builderAppDao.getProperty("insert.table.configuration");
        List<Object> params = new ArrayList<Object>();
        params.add(vo.getConnId());
        params.add(vo.getDatabase());
        params.add(vo.getDatabaseSchema());
        params.add(vo.getTableName());
        return (int) insert(sql, "ID", params.toArray());
    }

    @Override
    public long persistColumnConfig(int tableId, ColumnDetailsVo column) {
        if (log.isDebugEnabled()) {
            log.debug("Saving configuration for column [" + column.getDescription() + "]");
        }
        String sql = builderAppDao.getProperty("insert.column.configuration");
        List<Object> params = new ArrayList<Object>();
        params.add(tableId);
        params.add(column.isPrimaryKey());
        params.add(column.isForeignKey());
        params.add(column.getRefTableId());
        params.add(column.getDescription());
        params.add(column.getColumnType());
        params.add(deserializeList(column.getPermittedValues()));
        params.add(column.getSize());
        params.add(column.getScale());
        params.add(column.getNullable());
        params.add(column.getAutoIncrement());

        return insert(sql, "ID", params.toArray());
    }

    @Override
    public boolean isConfigurationPersisted(Integer connId, String databaseSchema, String tableName) {
        String sql = builderAppDao.getProperty("count.single.configuration");
        return queryForInt(sql, connId, databaseSchema, tableName) == 0 ? false : true;
    }

    @Override
    public void updateTableToBeProcessed(Integer tableId, Boolean processed) {
        String sql = builderAppDao.getProperty("update.table.configuration.processed");
        List<Object> params = new ArrayList<Object>();
        params.add(processed);
        params.add(tableId);
        update(sql, params);
    }

    @Override
    public List<BuilderAppVo> getTableConfig() {
        List<BuilderAppVo> list = new ArrayList<BuilderAppVo>();
        String sql = builderAppDao.getProperty("select.all.configuration.summary");
        list = query(sql, new JdbcRowMapper<BuilderAppVo>() {
            @Override
            public BuilderAppVo mapRow(ResultSet rs, int n) throws SQLException {
                BuilderAppVo builderAppVo = new BuilderAppVo();
                builderAppVo.setId(rs.getLong("ID"));
                builderAppVo.setTableName(rs.getString("TABLE_NAME"));
                return builderAppVo;
            }
        });
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BuilderAppVo> getTableConfig(Integer connId) {
        List<BuilderAppVo> list = new ArrayList<BuilderAppVo>();
        String sql = builderAppDao.getProperty("select.all.configuration.detail.by.conn");
        List<Object> params = new ArrayList<Object>();
        params.add(connId);

        list = query(sql, new JdbcRowMapper<BuilderAppVo>() {
            @Override
            public BuilderAppVo mapRow(ResultSet rs, int n) throws SQLException {
                BuilderAppVo builderAppVo = new BuilderAppVo();
                builderAppVo.setDatabaseSchema(rs.getString("DATABASE_SCHEMA"));
                return builderAppVo;
            }
        }, params.toArray());
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BuilderAppVo> getTableConfig(Integer connId, String databaseSchema) {
        List<BuilderAppVo> list = new ArrayList<BuilderAppVo>();
        String sql = builderAppDao.getProperty("select.all.configuration.detail.by.conn-database");
        List<Object> params = new ArrayList<Object>();
        params.add(connId);
        params.add(databaseSchema);

        list = query(sql, new JdbcRowMapper<BuilderAppVo>() {
            @Override
            public BuilderAppVo mapRow(ResultSet rs, int n) throws SQLException {
                BuilderAppVo builderAppVo = new BuilderAppVo();
                builderAppVo.setId(rs.getLong("ID"));
                builderAppVo.setTableName(rs.getString("TABLE_NAME"));
                return builderAppVo;
            }
        }, params.toArray());
        return list;
    }

    @Override
    public List<BuilderAppConfigVo> getBuilderAppConfig() {
        List<BuilderAppConfigVo> list = new ArrayList<BuilderAppConfigVo>();
        String sql = builderAppDao.getProperty("select.all.configuration.detail");
        list = query(sql, new JdbcRowMapper<BuilderAppConfigVo>() {
            @Override
            public BuilderAppConfigVo mapRow(ResultSet rs, int n) throws SQLException {
                BuilderAppConfigVo tableDetailsVo = new BuilderAppConfigVo();
                tableDetailsVo.setConnName(rs.getString("CONN_NAME"));
                tableDetailsVo.setTableName(rs.getString("TABLE_NAME"));
                tableDetailsVo.setColumnName(rs.getString("COLUMN_NAME"));
                tableDetailsVo.setPermittedValues(serializeString(rs.getString("PERMITTED_VALUES")));
                tableDetailsVo.setType(rs.getString("DATABASE_TYPE"));
                tableDetailsVo.setSize(rs.getInt("SIZE"));
                tableDetailsVo.setScale((Integer) rs.getObject("SCALE"));
                tableDetailsVo.setNullable(rs.getBoolean("NULLABLE"));
                tableDetailsVo.setPrimaryKey(rs.getBoolean("PRIMARY_KEY"));
                tableDetailsVo.setAutoIncrement(rs.getBoolean("AUTO_INCREMENT"));
                tableDetailsVo.setSqlType(rs.getInt("ID_SQL_TYPE"));
                tableDetailsVo.setFieldJavaCastType(rs.getString("JAVA_TYPE"));
                return tableDetailsVo;
            }
        });
        return list;
    }

    @Override
    public List<BuilderMappingTypeVo> getMappingDatabase(DatabaseType databaseType) {
        List<BuilderMappingTypeVo> list = new ArrayList<BuilderMappingTypeVo>();
        String sql = builderAppDao.getProperty("select.mapping.types.by.database");
        list = query(sql, new JdbcRowMapper<BuilderMappingTypeVo>() {
            @Override
            public BuilderMappingTypeVo mapRow(ResultSet rs, int n) throws SQLException {
                BuilderMappingTypeVo mappingTypeVo = new BuilderMappingTypeVo();
                mappingTypeVo.setId(rs.getInt("ID"));
                mappingTypeVo.setDatabaseType(DatabaseType.valueOf(rs.getInt("ID_DATABASE_TYPE")));
                mappingTypeVo.setDatabase(rs.getString("DATABASE_TYPE"));
                mappingTypeVo.setIdSql(rs.getInt("ID_SQL_TYPE"));
                mappingTypeVo.setSql(rs.getString("SQL_TYPE"));
                mappingTypeVo.setJava(rs.getString("JAVA_TYPE"));

                return mappingTypeVo;
            }
        }, databaseType.getId(databaseType.toString()));

        return list;
    }

    @Override
    public int persistMappingDatabase(DatabaseType databaseType, String typeName, int typeId) {
        if (log.isDebugEnabled()) {
            log.debug("Creating initial mapping using database [" + databaseType + "] and type [" + typeName + "]");
        }
        String sql = builderAppDao.getProperty("insert.mapping.configuration");
        List<Object> params = new ArrayList<Object>();
        params.add(databaseType.getId(databaseType.toString()));
        params.add(typeName);
        params.add(typeId);
        // gets the corresponding SQL type.
        try {
            for (Field field : Types.class.getFields()) {
                if (typeId == (Integer) field.get(null)) {
                    params.add(field.getName());
                    break;
                }
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("There was an error when trying to determine SQL_TYPE due to: " + e);
            }
        }
        params.add(false);
        return (int) insert(sql, "ID", params.toArray());
    }

    @Override
    public List<ApplicationBuilderMappingCodeTypeVo> getCodeMappingDatabase() {
        return getCodeMappingDatabaseByPointcutName(null);
    }

    @Override
    public List<ApplicationBuilderMappingCodeTypeVo> getCodeMappingDatabase(String pointcutName) {
        return getCodeMappingDatabaseByPointcutName(pointcutName);
    }

    private List<ApplicationBuilderMappingCodeTypeVo> getCodeMappingDatabaseByPointcutName(String pointcutName) {

        List<ApplicationBuilderMappingCodeTypeVo> list = new ArrayList<ApplicationBuilderMappingCodeTypeVo>();
        String sqlBase = builderAppDao.getProperty("select.mapping.code.types");
        String order = builderAppDao.getProperty("select.mapping.code.types.orderby");
        String cond = builderAppDao.getProperty("select.mapping.code.types.by.pointcut");

        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        if (pointcutName == null) {
            sql.append(sqlBase);
            sql.append(" ");
            sql.append(order);
        } else {
            sql.append(sqlBase);
            sql.append(" ");
            sql.append(cond);
            sql.append(" ");
            sql.append(order);
            params.add(pointcutName);
        }

        list = query(sql.toString(), new JdbcRowMapper<ApplicationBuilderMappingCodeTypeVo>() {
            @Override
            public ApplicationBuilderMappingCodeTypeVo mapRow(ResultSet rs, int n) throws SQLException {
                ApplicationBuilderMappingCodeTypeVo mappingCodeTypeVo = new ApplicationBuilderMappingCodeTypeVo();

                mappingCodeTypeVo.setId(rs.getInt("ID_CODE"));
                mappingCodeTypeVo.setPointCutName(rs.getString("POINT_CUT_NAME"));
                mappingCodeTypeVo.setCode(rs.getString("CODE"));
                mappingCodeTypeVo.setIdType(rs.getInt("ID_TYPE"));
                mappingCodeTypeVo.setJavaType(rs.getString("JAVA_TYPE"));

                return mappingCodeTypeVo;
            }
        }, params.toArray());
        List<ApplicationBuilderMappingCodeTypeVo> finalList = new ArrayList<ApplicationBuilderMappingCodeTypeVo>();
        ApplicationBuilderMappingCodeTypeVo builderMappingCodeTypeVo = null;
        for (ApplicationBuilderMappingCodeTypeVo vo : list) {
            if (builderMappingCodeTypeVo == null) {
                builderMappingCodeTypeVo = new ApplicationBuilderMappingCodeTypeVo();
                builderMappingCodeTypeVo.setId(vo.getId());
                builderMappingCodeTypeVo.setPointCutName(vo.getPointCutName());
                builderMappingCodeTypeVo.setCode(vo.getCode());
                builderMappingCodeTypeVo.put(vo.getIdType(), vo.getJavaType());
                finalList.add(builderMappingCodeTypeVo);
            } else {
                if (vo.equals(builderMappingCodeTypeVo)) {
                    finalList.get(finalList.size() - 1).getTypes().put(vo.getIdType(), vo.getJavaType());
                } else {
                    builderMappingCodeTypeVo = new ApplicationBuilderMappingCodeTypeVo();
                    builderMappingCodeTypeVo.setId(vo.getId());
                    builderMappingCodeTypeVo.setPointCutName(vo.getPointCutName());
                    builderMappingCodeTypeVo.setCode(vo.getCode());
                    builderMappingCodeTypeVo.put(vo.getIdType(), vo.getJavaType());
                    finalList.add(builderMappingCodeTypeVo);
                }
            }
            builderMappingCodeTypeVo = vo;
        }
        return finalList;

    }

    @Override
    public ApplicationBuilderPreferencesVo getPreferences() {
        ApplicationBuilderPreferencesVo vo = new ApplicationBuilderPreferencesVo();
        List<TmpVo> list = new ArrayList<TmpVo>();
        String sql = builderAppDao.getProperty("select.pref.config.all");

        list = query(sql, new JdbcRowMapper<TmpVo>() {
            @Override
            public TmpVo mapRow(ResultSet rs, int n) throws SQLException {
                TmpVo prop = new TmpVo();
                prop.setName(rs.getString("PROPERTY_NAME"));
                prop.setValue(rs.getString("PROPERTY_VALUE"));
                return prop;
            }
        });
        for (TmpVo p : list) {
            if (p.getName().equals("authModuleName")) {
                vo.setAuthModuleName(p.getValue());
            } else if (p.getName().equals("mainModuleName")) {
                vo.setModuleName(p.getValue());
            }
        }
        return vo;
    }

    @Override
    public List<BuilderPluginVo> getBuilderPluginConfig() {
        List<BuilderPluginVo> list = new ArrayList<BuilderPluginVo>();
        String sql = builderAppDao.getProperty("select.plugin.config.all");
        list = query(sql, new JdbcRowMapper<BuilderPluginVo>() {
            @Override
            public BuilderPluginVo mapRow(ResultSet rs, int n) throws SQLException {
                BuilderPluginVo builderPluginVo = new BuilderPluginVo();
                builderPluginVo.setId(rs.getInt("ID"));
                builderPluginVo.setTitle(rs.getString("TITLE"));
                builderPluginVo.setDescription(rs.getString("DESCRIPTION"));
                builderPluginVo.setSelected(rs.getBoolean("PRE_SELECTED"));

                return builderPluginVo;
            }
        });
        return list;
    }

    @Override
    public BuilderPluginVo getBuilderPluginConfig(Integer pluginId) {
        String sql = builderAppDao.getProperty("select.plugin.config.single");
        List<Object> params = new ArrayList<Object>();
        params.add(pluginId);

        return queryForObject(sql, new JdbcRowMapper<BuilderPluginVo>() {
            @Override
            public BuilderPluginVo mapRow(ResultSet rs, int n) throws SQLException {
                BuilderPluginVo builderPluginVo = new BuilderPluginVo();
                builderPluginVo.setClassImplementation(rs.getString("CLASS_IMPLEMENTATION"));
                return builderPluginVo;
            }
        }, params.toArray());
    }
}

class TmpVo {
    private String name;
    private String value;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

}