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

import static mx.com.mesofi.services.util.CommonConstants.DASH;
import static mx.com.mesofi.services.util.SimpleCommonActions.fromERFormatToMethodFormat;
import static mx.com.mesofi.services.util.SimpleCommonActions.getQualifiedClassName;
import static mx.com.mesofi.services.util.SimpleCommonActions.removeAndReplaceSpaces;
import static mx.com.mesofi.services.util.SimpleValidator.isEmpty;
import static mx.com.mesofi.services.util.SimpleValidator.isNull;
import static mx.com.mesofi.services.util.SimpleValidator.isValid;
import static mx.com.mesofi.web.util.WebUtils.fromERFormatToLabelFormat;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.mesofi.framework.error.ValidationBusinessException;
import mx.com.mesofi.framework.stereotype.Inject;
import mx.com.mesofi.framework.stereotype.Service;
import mx.com.mesofi.framework.stereotype.Transaction;
import mx.com.mesofi.plugins.project.core.AbstractCommonBuilder;
import mx.com.mesofi.plugins.project.core.ApplicationBuilder;
import mx.com.mesofi.plugins.project.core.DatabaseType;
import mx.com.mesofi.plugins.project.core.ProjectBuilderConfigurable;
import mx.com.mesofi.plugins.project.core.ProjectStructureBuilder;
import mx.com.mesofi.plugins.project.core.WebApplicationBuilder;
import mx.com.mesofi.plugins.project.core.config.CheckBoxField;
import mx.com.mesofi.plugins.project.core.layers.BusinessLayer;
import mx.com.mesofi.plugins.project.core.layers.CommonLayer;
import mx.com.mesofi.plugins.project.core.layers.PersistenceLayer;
import mx.com.mesofi.plugins.project.core.layers.ViewLayer;
import mx.com.mesofi.plugins.project.core.maven.MavenProjectStructureBuilderImpl;
import mx.com.mesofi.plugins.project.core.maven.PomResourceContent;
import mx.com.mesofi.plugins.project.core.maven.PomSectionContent;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderDataSourceVo;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderMappingCodeTypeVo;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderPreferencesVo;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderScreenFieldVo;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderScreenVo;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderVo;
import mx.com.mesofi.plugins.project.core.util.TechManager;
import mx.com.mesofi.plugins.project.core.util.TechResolver;
import mx.com.mesofi.plugins.project.core.web.LoggerResourceContent;
import mx.com.mesofi.plugins.project.core.web.WebResourceContent;
import mx.com.mesofi.plugins.project.core.web.WebSectionContent;
import mx.com.mesofi.services.util.SimpleValidator;
import mx.com.mesofi.services.zip.ZipUtil;
import mx.com.mesofi.web.admin.builder.dao.BuilderAppDao;
import mx.com.mesofi.web.admin.builder.error.ApplicationBuilderException;
import mx.com.mesofi.web.admin.builder.vo.BuilderAppCompoundVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderAppConfigVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderAppVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderCodeGenVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderMappingTypeVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderPluginVo;
import mx.com.mesofi.web.admin.datasource.service.DataSourceService;
import mx.com.mesofi.web.admin.datasource.vo.ColumnDetailsVo;
import mx.com.mesofi.web.admin.datasource.vo.ColumnFullDetailsVo;
import mx.com.mesofi.web.admin.datasource.vo.DataSourceVo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

@Service
public class BuilderAppServiceImpl implements BuilderAppService {
    /**
     * log4j.
     */
    private static final Logger log = Logger.getLogger(BuilderAppServiceImpl.class);
    /**
     * Mapping for the types.
     */

    @Inject
    private BuilderAppDao builderAppDao;

    @Inject
    private DataSourceService dataSourceService;

    /**
     * If this property is not provided, then a default value is an empty
     * string.
     */
    @Value("${mesofi.config.builder.default.plugin:}")
    private String builderClassName;

    /**
     * If this property is not provided, then a default value is an empty
     * string.
     */
    @Value("${mesofi.config.builder.initial.folder:}")
    private String builderDefaultLocation;

    @Override
    @Transaction
    public Map<Long, String> persistConfiguration(DatabaseType databaseType, BuilderAppVo vo) {
        // retrieves the columns of the remote database and save them.
        ColumnFullDetailsVo columns = null;
        columns = dataSourceService.getTableDetails(vo.getConnId(), vo.getDatabaseSchema(), vo.getTableName(),
                vo.getDatabase());

        // gets the details of the mapping
        boolean found = false;
        String currentType = null;
        int currentTypeId = 0;
        List<BuilderMappingTypeVo> types = builderAppDao.getMappingDatabase(databaseType);
        for (ColumnDetailsVo columnDetailsVo : columns.getMetadata()) {
            currentType = columnDetailsVo.getTypeName();
            currentTypeId = columnDetailsVo.getTypeId();
            found = false;
            for (BuilderMappingTypeVo mapping : types) {
                if (currentType.equals(mapping.getDatabase())) {
                    found = true;
                    // assign the id of the referenced type
                    columnDetailsVo.setColumnType((int) mapping.getId());
                    break;
                }
            }
            if (!found) {
                // if not found, then populate the list
                int id = builderAppDao.persistMappingDatabase(databaseType, currentType, currentTypeId);
                // assign the id of the referenced type
                columnDetailsVo.setColumnType(id);
                BuilderMappingTypeVo mapVo = new BuilderMappingTypeVo();
                mapVo.setId(id);
                mapVo.setDatabaseType(databaseType);
                mapVo.setDatabase(currentType);
                types.add(mapVo);
            }
        }

        BuilderAppVo refVo = null;
        for (ColumnDetailsVo columnDetailsVo : columns.getMetadata()) {
            if (columnDetailsVo.isForeignKey()) {
                refVo = createRefBuilderAppVo(vo, columnDetailsVo.getRefTableName());
                // Recursively saves the tree structure of tables.
                Map<Long, String> ids = persistConfiguration(databaseType, refVo);
                for (Long currId : ids.keySet()) {
                    if (ids.get(currId).equals(columnDetailsVo.getRefColumnName())) {
                        columnDetailsVo.setRefTableId(currId);
                        break;
                    }
                }
            }
        }
        Map<Long, String> ids = new HashMap<Long, String>();
        // before persisting table information, test if this table has been
        // saved before.
        if (!builderAppDao.isConfigurationPersisted(vo.getConnId(), vo.getDatabaseSchema(), vo.getTableName())) {
            // persists the table and column information.
            int tableId = builderAppDao.persistTableConfig(vo);
            long columnIdPersisted = 0;
            for (ColumnDetailsVo column : columns.getMetadata()) {
                // persists column information.
                columnIdPersisted = builderAppDao.persistColumnConfig(tableId, column);
                if (column.isPrimaryKey()) {
                    ids.put(columnIdPersisted, column.getDescription());
                }
            }
        }

        return ids;
    }

    /**
     * Create a reference object to store and save some other tables.
     * 
     * @param oldVo
     *            Old builder object.
     * @param tableName
     *            The new reference table name.
     * @return The new referenced object to store.
     */
    private BuilderAppVo createRefBuilderAppVo(BuilderAppVo oldVo, String tableName) {
        BuilderAppVo vo = new BuilderAppVo();
        vo.setConnId(oldVo.getConnId());
        vo.setDatabaseSchema(oldVo.getDatabaseSchema());
        vo.setDatabase(oldVo.getDatabase());
        vo.setTableName(tableName);
        return vo;
    }

    @Override
    public boolean isConfigurationPersisted(Integer connId, String databaseSchema, String tableName) {
        return builderAppDao.isConfigurationPersisted(connId, databaseSchema, tableName);
    }

    @Override
    public void updateTableToBeProcessed(Integer tableId, Boolean processed) {
        builderAppDao.updateTableToBeProcessed(tableId, processed);
    }

    @Override
    @Transaction
    public void updateTableToBeProcessed(List<BuilderAppVo> list) {
        for (BuilderAppVo vo : list) {
            builderAppDao.updateTableToBeProcessed((int) vo.getId(), true);
        }
    }

    @Override
    public List<BuilderAppVo> getAllConfiguration() {
        return builderAppDao.getTableConfig();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BuilderAppVo> getAllConfiguration(Integer connId) {
        return builderAppDao.getTableConfig(connId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BuilderAppVo> getAllConfiguration(Integer connId, String databaseSchema) {
        return builderAppDao.getTableConfig(connId, databaseSchema);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BuilderPluginVo> getBuilderPluginConfig() {
        return builderAppDao.getBuilderPluginConfig();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuilderCodeGenVo getBuilderPluginCodeGen(Integer pluginId) {
        BuilderCodeGenVo builderCodeGenVo = new BuilderCodeGenVo();
        CheckBoxField checkBoxField1 = new CheckBoxField("  Create Hibernate.xml");
        CheckBoxField checkBoxField2 = new CheckBoxField("  Annotate classes");

        checkBoxField1.setName("createHibernateConfig");
        checkBoxField2.setName("annotatedClasses");

        builderCodeGenVo.addField(checkBoxField1);
        builderCodeGenVo.addField(checkBoxField2);

        return builderCodeGenVo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ApplicationBuilderPreferencesVo getPreferences() {
        return builderAppDao.getPreferences();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BuilderPluginVo getBuilderPluginConfig(Integer pluginId) {
        BuilderPluginVo vo = builderAppDao.getBuilderPluginConfig(pluginId);
        ProjectBuilderConfigurable builder = validateBuilderClassName(vo.getClassImplementation(), false);
        vo.setClassImplementation(null);
        PomSectionContent pomSectionContent = builder.getPomSectionContent();
        if (pomSectionContent != null) {
            vo.setArtifactId(pomSectionContent.getArtifactId(true));
            vo.setGroupId(pomSectionContent.getGroupId(true));
            vo.setVersion(pomSectionContent.getVersion());
        }
        return vo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void buildApplication(BuilderAppCompoundVo builderAppCompoundVo) {
        if (log.isDebugEnabled()) {
            log.debug("Process my application under this location..." + builderDefaultLocation);
        }
        try {
            int pluginId = (int) builderAppCompoundVo.getBuilderPluginVo().getId();
            if (pluginId == 0) {
                throw new ValidationBusinessException("Cannot build an application since there is not plugin selected");
            }
            // gets all the information stored to create the application.
            List<BuilderAppConfigVo> dataConfig = builderAppDao.getBuilderAppConfig();
            if (dataConfig.isEmpty()) {
                throw new ValidationBusinessException(
                        "Please add tables into the configuration, cannot create application without them");
            }

            // gets the information regarding connection
            DataSourceVo dataSource = dataSourceService.getAvailableConnection(builderAppCompoundVo.getConnId());
            // gets the information regarding types.
            List<BuilderMappingTypeVo> dataType = builderAppDao.getMappingDatabase(dataSource.getDatabaseType());
            // gets the information regarding types & code.
            List<ApplicationBuilderMappingCodeTypeVo> codeType = builderAppDao.getCodeMappingDatabase();
            // gets the information regarding configuration
            ApplicationBuilderPreferencesVo pref = builderAppCompoundVo.getApplicationBuilderPreferencesVo();
            // gets the implementation plugin and set into another object
            BuilderPluginVo builderPlugin = builderAppDao.getBuilderPluginConfig(pluginId);
            builderAppCompoundVo.getBuilderPluginVo().setClassImplementation(builderPlugin.getClassImplementation());
            // sets the data into an object that can be handled by the plugin
            ApplicationBuilderVo applicationBuilderVo = createApplicationBuilderVo(
                    builderAppCompoundVo.getProjectName(), pref, dataConfig, dataSource, dataType, codeType);
            // creates the application using the prefedined configuration
            createCustomApplication(applicationBuilderVo, builderAppCompoundVo);
        } catch (ApplicationBuilderException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void compressApplication(String webProjectName) {
        ApplicationBuilder applicationBuilder = null;
        applicationBuilder = new WebApplicationBuilder(builderDefaultLocation);
        File baseDirectory = applicationBuilder.getDefaultDirectory();

        // cleans up the existing files in this directory
        for (File currentProject : baseDirectory.listFiles()) {
            if (currentProject.isFile() && !currentProject.isHidden()) {
                currentProject.delete();
            }
        }

        // now creates the corresponding zip file.
        for (File currentProject : baseDirectory.listFiles()) {
            if (currentProject.isDirectory()
                    && currentProject.getName().equals(removeAndReplaceSpaces(webProjectName, DASH.toString()))) {
                ZipUtil.getInstance().zipFolder(currentProject);
            }
        }
    }

    public File getFinalApplication() {
        File file = null;
        ApplicationBuilder applicationBuilder = null;
        applicationBuilder = new WebApplicationBuilder(builderDefaultLocation);
        File baseDirectory = applicationBuilder.getDefaultDirectory();

        // cleans up the existing files in this directory
        for (File currentFile : baseDirectory.listFiles()) {
            if (currentFile.isFile() && !currentFile.isHidden() && currentFile.getName().endsWith(".zip")) {
                file = currentFile;
                break;
            }
        }
        isNull(file, "Cannot download project because it does not exist a file with .zip extension");
        return file;
    }

    /**
     * Based on the data stored related to the table, a new object is created
     * with the necessary information to create the application with its screen.
     * 
     * @param projectName
     *            The project name.
     * @param preferences
     *            The preferences.
     * @param dataConfig
     *            Data from database.
     * @param dataSource
     *            The data source.
     * @param dataType
     *            Types for a given data base.
     * @param codeType
     *            The mapping code and types.
     * @return The application builder.
     */
    private ApplicationBuilderVo createApplicationBuilderVo(String projectName,
            ApplicationBuilderPreferencesVo preferences, List<BuilderAppConfigVo> dataConfig, DataSourceVo dataSource,
            List<BuilderMappingTypeVo> dataType, List<ApplicationBuilderMappingCodeTypeVo> codeType) {
        isValid(!dataConfig.isEmpty(), "Cannot create an application because "
                + "there is no enough information available to generate it");
        ApplicationBuilderVo applicationBuilderVo = new ApplicationBuilderVo();
        // sets the project name.
        applicationBuilderVo.setProjectName(projectName);

        // sets the project configuration
        applicationBuilderVo.setPreferences(preferences);

        String screenName = null;
        List<ApplicationBuilderScreenVo> screens = new ArrayList<ApplicationBuilderScreenVo>();

        for (BuilderAppConfigVo vo : dataConfig) {
            // validate the type.
            validateDatabaseTypes(vo.getType(), dataType, dataSource.getDatabaseType());

            if (vo.getTableName().equals(screenName)) {
                screens.get(screens.size() - 1).addAppField(createField(vo));
            } else {
                // first iteration
                ApplicationBuilderScreenVo firstScreen = new ApplicationBuilderScreenVo();
                firstScreen.setScreenName(vo.getTableName());
                firstScreen.setPageName(vo.getTableName().toLowerCase()); // calculated
                firstScreen.addAppField(createField(vo));
                screens.add(firstScreen);
                screenName = vo.getTableName();
            }
        }
        // sets the screen details.
        applicationBuilderVo.setScreens(screens);
        // sets connection details.
        ApplicationBuilderDataSourceVo source = new ApplicationBuilderDataSourceVo();
        source.setDatabaseName(dataSource.getDatabaseName());
        source.setDatabaseType(dataSource.getDatabaseType());
        source.setDbConnName(dataSource.getDbConnName());
        source.setHost(dataSource.getHost());
        source.setPassword(dataSource.getPassword());
        source.setPort(dataSource.getPort());
        source.setUrl(dataSource.getUrl());
        source.setUser(dataSource.getUser());

        applicationBuilderVo.setConnection(source);
        applicationBuilderVo.setMappingCode(codeType);
        return applicationBuilderVo;
    }

    private void validateDatabaseTypes(String type, List<BuilderMappingTypeVo> dataType, DatabaseType databaseType) {
        boolean found = false;
        String errorMessage = "Cannot create an application because this type [" + type + "] on " + databaseType
                + " does not have a corresponding mapping for";
        for (BuilderMappingTypeVo vo : dataType) {
            if (vo.getDatabase().equals(type)) {
                found = true;
                isValid(vo.getSql() != null, errorMessage + " SQL type");
                isValid(vo.getJava() != null, errorMessage + " Java type");
                break;
            }
        }
        isValid(found, "Cannot create an application because could not find a valid type for [" + type + "] on "
                + databaseType);
    }

    /**
     * Based on the application configuration, creates the screen configuration.
     * 
     * @param vo
     *            New screen configuration.
     * @return The new object.
     */
    private ApplicationBuilderScreenFieldVo createField(BuilderAppConfigVo vo) {
        ApplicationBuilderScreenFieldVo field = new ApplicationBuilderScreenFieldVo();
        field.setFieldName(vo.getColumnName());
        field.setFieldLabel(fromERFormatToLabelFormat(vo.getColumnName()));
        field.setFieldJavaType(fromERFormatToMethodFormat(vo.getColumnName()));
        field.setFieldDataBaseType(vo.getType().toUpperCase());
        field.setFieldJavaCastType(vo.getFieldJavaCastType());
        field.setFieldType(vo.getFieldJavaCastType());
        field.setFieldFullyNameType(getQualifiedClassName(vo.getFieldJavaCastType()));
        field.setFieldSqlType(vo.getSqlType());
        if (vo.getScale() != null) {
            field.setFractionDigits(vo.getScale());
        }
        field.setPermittedValues(vo.getPermittedValues());
        field.setSize(vo.getSize());
        field.setOptional(vo.isNullable());
        field.setPrimaryKey(vo.isPrimaryKey());
        field.setAutoIncrement(vo.isAutoIncrement());
        return field;
    }

    /**
     * This method creates the generated application based on the configuration.
     * 
     * @param app
     *            Configuration for the generated application.
     * @param builder
     *            The builder.
     * @throws ApplicationBuilderException
     *             If occurs an error while creating the application.
     */
    private void createCustomApplication(ApplicationBuilderVo app, BuilderAppCompoundVo builder)
            throws ApplicationBuilderException {
        try {
            // overrides implementation from preferences.properties.
            builderClassName = builder.getBuilderPluginVo().getClassImplementation();

            isEmpty(builderClassName, "There is not default plugin found, cannot create application without a default "
                    + "implementation of class [" + ProjectBuilderConfigurable.class + "]");
            Class<?> clazz = Class.forName(builderClassName);

            ProjectBuilderConfigurable projectBuilderConfigurable = (ProjectBuilderConfigurable) clazz.newInstance();
            projectBuilderConfigurable.setUpApplicationBuilder(app);
            PomSectionContent pomSectionContent = projectBuilderConfigurable.getPomSectionContent();
            WebSectionContent webSectionContent = projectBuilderConfigurable.getWebSectionContent();

            isNull(pomSectionContent, "PomSectionContent is missing, define a valid reference");
            isNull(webSectionContent, "WebSectionContent is missing, define a valid reference");

            pomSectionContent.setGroupId(builder.getBuilderPluginVo().getGroupId());
            pomSectionContent.setArtifactId(builder.getBuilderPluginVo().getArtifactId());
            pomSectionContent.setVersion(builder.getBuilderPluginVo().getVersion());

            PomResourceContent pomResourceContent = new PomResourceContent(pomSectionContent);
            WebResourceContent webResourceContent = new WebResourceContent(webSectionContent);
            LoggerResourceContent loggerResourceContent = new LoggerResourceContent();

            webResourceContent.setDisplayName(app.getProjectName());
            webResourceContent.setPackageName(pomSectionContent.getGroupId(true));
            webResourceContent.setArtifactId(pomSectionContent.getArtifactId(true));

            loggerResourceContent.setProjectName(app.getProjectName());
            loggerResourceContent.setPackageName(pomSectionContent.getGroupId());

            ProjectStructureBuilder project = null;
            project = new MavenProjectStructureBuilderImpl(app, pomResourceContent, webResourceContent,
                    loggerResourceContent);

            // gets all the layers in the application.
            ViewLayer viewLayer = projectBuilderConfigurable.getViewLayer();
            BusinessLayer businessLayer = projectBuilderConfigurable.getBusinessLayer();
            PersistenceLayer persistenceLayer = projectBuilderConfigurable.getPersistenceLayer();
            CommonLayer commonLayer = projectBuilderConfigurable.getCommonLayer();

            isNull(businessLayer, "No BusinessLayer has been defined, please implement a concrete class");
            isNull(viewLayer, "No ViewLayer has been defined, please implement a concrete class");
            isNull(persistenceLayer, "No PersistenceLayer has been defined, please implement a concrete class");

            // sets the dependency among layers
            AbstractCommonBuilder commonBuilder = null;

            commonBuilder = (AbstractCommonBuilder) viewLayer;
            commonBuilder.setBusinessLayer(businessLayer);
            commonBuilder.setCommonLayer(commonLayer);

            commonBuilder = (AbstractCommonBuilder) businessLayer;
            commonBuilder.setPersistenceLayer(persistenceLayer);

            commonBuilder = (AbstractCommonBuilder) persistenceLayer;
            commonBuilder.setBusinessLayer(businessLayer);

            // The TechManager configuration is initialized.
            TechManager.getInstance().setViewLayer(viewLayer);
            TechManager.getInstance().setBusinessLayer(businessLayer);
            TechManager.getInstance().setPersistenceLayer(persistenceLayer);
            TechManager.getInstance().setCommonLayer(commonLayer);

            // The TechResolver is initialized
            TechResolver.getInstance().setApplicationBuilder(app);
            TechResolver.getInstance().setPomSectionContent(pomSectionContent);

            ApplicationBuilder applicationBuilder = null;
            applicationBuilder = new WebApplicationBuilder(viewLayer, businessLayer, persistenceLayer, commonLayer,
                    builderDefaultLocation);
            applicationBuilder.createWebApplication(project, app);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new ApplicationBuilderException("Cannot create the application due to: " + e);
        }

    }

    /**
     * Validates the builderClassName for the selected plugin.
     * 
     * @param builderClassName
     *            The class Name.
     * @return Ins instance compatible for ProjectBuilderConfigurable
     */
    private ProjectBuilderConfigurable validateBuilderClassName(String builderClassName, boolean isBusinessException) {
        String message = "The builder class cannot be empty";
        if (isBusinessException) {
            throw new ValidationBusinessException(message);
        } else {
            SimpleValidator.isEmpty(builderClassName, message);
        }

        Class<?> clazz = null;
        try {
            clazz = Class.forName(builderClassName);
        } catch (ClassNotFoundException e) {
            message = e.toString();
            if (isBusinessException) {
                throw new ValidationBusinessException("There is not plugin using this class: " + e.getMessage());
            } else {
                SimpleValidator.isValid(false, message);
            }
        }

        if (!ProjectBuilderConfigurable.class.isAssignableFrom(clazz)) {
            message = "This [" + clazz + "] is not a valid type for [" + ProjectBuilderConfigurable.class + "]";
            if (isBusinessException) {
                throw new ValidationBusinessException(message);
            } else {
                SimpleValidator.isValid(false, message);
            }
        }

        ProjectBuilderConfigurable projectBuilderConfigurable = null;
        try {
            projectBuilderConfigurable = (ProjectBuilderConfigurable) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            message = e.toString();
            if (isBusinessException) {
                throw new ValidationBusinessException(message);
            } else {
                SimpleValidator.isValid(false, message);
            }
        }
        return projectBuilderConfigurable;
    }

}
