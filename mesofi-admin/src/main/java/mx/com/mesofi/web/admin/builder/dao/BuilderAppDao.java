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

import java.util.List;

import mx.com.mesofi.plugins.project.core.DatabaseType;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderMappingCodeTypeVo;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderPreferencesVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderAppConfigVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderAppVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderMappingTypeVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderPluginVo;
import mx.com.mesofi.web.admin.datasource.vo.ColumnDetailsVo;

public interface BuilderAppDao {

    int persistTableConfig(BuilderAppVo vo);

    long persistColumnConfig(int tableId, ColumnDetailsVo column);

    boolean isConfigurationPersisted(Integer connId, String databaseSchema, String tableName);

    void updateTableToBeProcessed(Integer tableId, Boolean processed);

    List<BuilderAppVo> getTableConfig();

    List<BuilderAppConfigVo> getBuilderAppConfig();

    List<BuilderAppVo> getTableConfig(Integer connId);

    List<BuilderAppVo> getTableConfig(Integer connId, String databaseSchema);

    List<BuilderMappingTypeVo> getMappingDatabase(DatabaseType databaseType);

    int persistMappingDatabase(DatabaseType databaseType, String typeName, int typeId);

    List<ApplicationBuilderMappingCodeTypeVo> getCodeMappingDatabase();

    List<ApplicationBuilderMappingCodeTypeVo> getCodeMappingDatabase(String pointcutName);

    ApplicationBuilderPreferencesVo getPreferences();

    List<BuilderPluginVo> getBuilderPluginConfig();

    BuilderPluginVo getBuilderPluginConfig(Integer pluginId);
}
