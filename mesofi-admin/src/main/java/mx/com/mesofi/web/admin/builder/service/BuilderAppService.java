/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.builder.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import mx.com.mesofi.plugins.project.core.DatabaseType;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderPreferencesVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderAppCompoundVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderAppVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderCodeGenVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderPluginVo;

public interface BuilderAppService {

    Map<Long, String> persistConfiguration(DatabaseType databaseType, BuilderAppVo vo);

    boolean isConfigurationPersisted(Integer connId, String databaseSchema, String tableName);

    void updateTableToBeProcessed(Integer tableId, Boolean processed);

    void updateTableToBeProcessed(List<BuilderAppVo> list);

    List<BuilderAppVo> getAllConfiguration();

    List<BuilderAppVo> getAllConfiguration(Integer connId);

    List<BuilderAppVo> getAllConfiguration(Integer connId, String databaseSchema);

    void buildApplication(BuilderAppCompoundVo builderAppCompoundVo);

    void compressApplication(String webProjectName);

    File getFinalApplication();

    List<BuilderPluginVo> getBuilderPluginConfig();

    BuilderPluginVo getBuilderPluginConfig(Integer pluginId);

    BuilderCodeGenVo getBuilderPluginCodeGen(Integer pluginId);

    ApplicationBuilderPreferencesVo getPreferences();

}
