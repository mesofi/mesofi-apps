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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import mx.com.mesofi.framework.jdbc.JdbcAbstractDao;
import mx.com.mesofi.framework.jdbc.JdbcRowMapper;
import mx.com.mesofi.framework.stereotype.DAO;
import mx.com.mesofi.framework.stereotype.Inject;
import mx.com.mesofi.framework.util.LabelEntityVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderCodeTypeVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderMappingTypeVo;

import org.apache.log4j.Logger;

/**
 * This class handle all configuration for the app to be generated.
 * 
 * @author Armando Rivas
 * @since Dec 14 2014.
 * 
 */
@DAO
public class ConfiguratorAppDaoImpl extends JdbcAbstractDao implements ConfiguratorAppDao {
    /**
     * log4j.
     */
    private static final Logger log = Logger.getLogger(ConfiguratorAppDaoImpl.class);
    /**
     * SQL statements.
     */
    @Inject
    private Properties configuratorAppDao;

    @Inject
    public ConfiguratorAppDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllCodeSections() {
        if (log.isDebugEnabled()) {
            log.debug("Getting all code sections...");
        }
        List<String> list = new ArrayList<String>();
        String sql = configuratorAppDao.getProperty("select.all.code.sections");
        list = query(sql, new JdbcRowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int n) throws SQLException {
                return rs.getString("POINT_CUT_NAME");
            }
        });
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LabelEntityVo> getAllAvailableTypes() {
        if (log.isDebugEnabled()) {
            log.debug("Getting all available types...");
        }
        List<LabelEntityVo> list = new ArrayList<LabelEntityVo>();
        String sql = configuratorAppDao.getProperty("select.all.available.types");
        list = query(sql, new JdbcRowMapper<LabelEntityVo>() {
            @Override
            public LabelEntityVo mapRow(ResultSet rs, int n) throws SQLException {
                LabelEntityVo vo = new LabelEntityVo();
                vo.setId(rs.getLong("ID"));
                vo.setDescription(rs.getString("JAVA_TYPE"));
                return vo;
            }
        });
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getAllDatabaseNames() {
        if (log.isDebugEnabled()) {
            log.debug("Getting all database names...");
        }
        List<Integer> list = new ArrayList<Integer>();
        String sql = configuratorAppDao.getProperty("select.all.database.names");
        list = query(sql, new JdbcRowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int n) throws SQLException {
                return rs.getInt("ID_DATABASE_TYPE");
            }
        });
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllPointcutNames(String pointcutName) {
        if (log.isDebugEnabled()) {
            log.debug("Getting all pointcut names...");
        }
        List<String> list = new ArrayList<String>();
        List<String> params = new ArrayList<String>();
        StringBuffer sqlAll = new StringBuffer(configuratorAppDao.getProperty("select.all.pointcut.names"));
        if (pointcutName != null && pointcutName.trim().length() != 0) {
            sqlAll.append(" ");
            sqlAll.append(configuratorAppDao.getProperty("select.like.pointcut.names"));
            params.add(pointcutName);
        }
        sqlAll.append(" ");
        sqlAll.append(configuratorAppDao.getProperty("select.order.pointcut.names"));

        list = query(sqlAll.toString(), new JdbcRowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int n) throws SQLException {
                return rs.getString("POINT_CUT_NAME");
            }
        }, params.toArray());
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BuilderMappingTypeVo> getTypesByDatabase(String databaseId) {
        List<BuilderMappingTypeVo> list = new ArrayList<BuilderMappingTypeVo>();
        String sql = configuratorAppDao.getProperty("select.types.by.database");
        list = query(sql, new JdbcRowMapper<BuilderMappingTypeVo>() {
            @Override
            public BuilderMappingTypeVo mapRow(ResultSet rs, int n) throws SQLException {
                BuilderMappingTypeVo vo = new BuilderMappingTypeVo();
                vo.setId(rs.getLong("ID"));
                vo.setDatabase(rs.getString("DATABASE_TYPE"));
                vo.setIdSql(rs.getInt("ID_SQL_TYPE"));
                vo.setSql(rs.getString("SQL_TYPE"));
                vo.setJava(rs.getString("JAVA_TYPE"));
                vo.setEffectiveType(rs.getBoolean("EFFECTIVE_TYPE"));
                return vo;
            }
        }, databaseId);
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LabelEntityVo> getCustomCodeBySection(String pointcutName) {
        List<LabelEntityVo> list = new ArrayList<LabelEntityVo>();
        String sql = configuratorAppDao.getProperty("select.custom.code.by.section");
        list = query(sql, new JdbcRowMapper<LabelEntityVo>() {
            @Override
            public LabelEntityVo mapRow(ResultSet rs, int n) throws SQLException {
                LabelEntityVo vo = new LabelEntityVo();
                vo.setId(rs.getLong("ID"));
                vo.setDescription(rs.getString("CODE"));
                return vo;
            }
        }, pointcutName);
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateJavaDbTypes(int idType, String javaType, boolean effectiveType) {
        String sql = configuratorAppDao.getProperty("update.all.java.database.types");
        update(sql, javaType, effectiveType, idType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertCustomCode(String pointcutName) {
        String sql = configuratorAppDao.getProperty("insert.all.custom.code");
        // inserts an empty code to create the register.
        update(sql, pointcutName, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCustomCode(int codeId, String code) {
        String sql = configuratorAppDao.getProperty("update.all.custom.code");
        // inserts an empty code to create the register.
        update(sql, code, codeId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BuilderCodeTypeVo> getTypesBySection(String pointcutName) {
        String sql = configuratorAppDao.getProperty("select.types.by.section");
        List<BuilderCodeTypeVo> list = new ArrayList<BuilderCodeTypeVo>();
        list = query(sql, new JdbcRowMapper<BuilderCodeTypeVo>() {
            @Override
            public BuilderCodeTypeVo mapRow(ResultSet rs, int n) throws SQLException {
                BuilderCodeTypeVo vo = new BuilderCodeTypeVo();
                vo.setId(rs.getLong("ID"));
                vo.setIdCode(rs.getInt("ID_CODE"));
                vo.setIdType(rs.getInt("ID_TYPE"));
                return vo;
            }
        }, pointcutName);
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTypesAndCodeById(Integer relationId) {
        String sql = configuratorAppDao.getProperty("delete.relation.code.type");
        // deletes all relations
        update(sql, relationId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long createTypesAndCode(int codeId, int typeId) {
        String sql = configuratorAppDao.getProperty("create.relation.code.type");
        // create a new relation
        return insert(sql, "ID", codeId, typeId);
    }

}
