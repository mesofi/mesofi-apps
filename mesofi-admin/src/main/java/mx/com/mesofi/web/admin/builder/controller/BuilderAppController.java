/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.builder.controller;

import static mx.com.mesofi.services.util.SimpleCommonActions.fromNullToCustomValue;
import static mx.com.mesofi.services.util.SimpleValidator.isEmptyString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.mesofi.framework.context.Messages;
import mx.com.mesofi.framework.error.ValidationBusinessException;
import mx.com.mesofi.framework.stereotype.Inject;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderPreferencesVo;
import mx.com.mesofi.web.admin.builder.service.BuilderAppService;
import mx.com.mesofi.web.admin.builder.vo.BuilderAppCompoundVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderAppVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderCodeGenVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderPluginVo;
import mx.com.mesofi.web.admin.datasource.service.DataSourceService;
import mx.com.mesofi.web.admin.datasource.vo.DataSourceSimpleVo;
import mx.com.mesofi.web.admin.datasource.vo.DataSourceVo;
import mx.com.mesofi.web.request.PageParameters;
import mx.com.mesofi.web.response.WebResponse;
import mx.com.mesofi.web.response.types.JsonResponse;
import mx.com.mesofi.web.response.types.StreamResponse;
import mx.com.mesofi.web.stereotype.Controller;
import mx.com.mesofi.web.stereotype.GET;
import mx.com.mesofi.web.stereotype.POST;

import org.apache.log4j.Logger;

/**
 * This class contains methods to handle setting up the application.
 * 
 * @author Armando Rivas
 * @since Mar 01 2014.
 * 
 */
@Controller
public class BuilderAppController {
    /**
     * log4j.
     */
    private static final Logger log = Logger.getLogger(BuilderAppController.class);
    /**
     * Services injected.
     */
    @Inject
    private BuilderAppService builderAppService;

    /**
     * Services injected.
     */
    @Inject
    private DataSourceService dataSourceService;

    /**
     * Messages.
     */
    @Inject
    private Messages messages;

    /**
     * Saves the configuration for the application.
     * 
     * @param pageParameters
     *            Page parameters.
     * @return Response for the page.
     */
    public WebResponse preBuildApp(PageParameters pageParameters) {
        if (log.isDebugEnabled()) {
            log.debug("Saving the current configuration...");
        }
        Integer connId = pageParameters.getValue("conn-id", Integer.class);
        String databaseSchema = pageParameters.getValue("database-schema");
        Boolean isDatabase = pageParameters.getValue("is-database", Boolean.class);
        String tableName = pageParameters.getValue("table-name");

        // before persisting configuration, gets the details of the connection.
        DataSourceVo dataSource = dataSourceService.getAvailableConnection(connId);

        BuilderAppVo vo = new BuilderAppVo();
        vo.setConnId(connId);
        vo.setDatabaseSchema(databaseSchema);
        vo.setDatabase(isDatabase);
        vo.setTableName(tableName);
        builderAppService.persistConfiguration(dataSource.getDatabaseType(), vo);
        return new JsonResponse("");
    }

    /**
     * Retrieves the configuration of the application before to be processed.
     * 
     * @param pageParameters
     *            Page parameters.
     * @return Response for the page.
     */
    @GET
    public WebResponse previewApp(PageParameters pageParameters) {
        if (log.isDebugEnabled()) {
            log.debug("Showing the tables to be processed...");
        }
        List<BuilderAppVo> list = builderAppService.getAllConfiguration();
        return new JsonResponse(list);
    }

    /**
     * Updates the status of the table.
     * 
     * @param pageParameters
     *            Page parameters.
     * @return Response for the page.
     */
    @POST
    public WebResponse updateTableProcessed(PageParameters pageParameters) {
        if (log.isDebugEnabled()) {
            log.debug("Table to be updated...");
        }
        Integer tableId = pageParameters.getValue("table-id", Integer.class);
        Boolean processed = pageParameters.getValue("checked", Boolean.class);

        builderAppService.updateTableToBeProcessed(tableId, processed);
        return new JsonResponse("");
    }

    /**
     * Process the existing configuration and build the application.
     * 
     * @param pageParameters
     *            Page parameters.
     * @return Response for the page.
     */
    @GET
    public WebResponse processApp(PageParameters pageParameters) {
        if (log.isDebugEnabled()) {
            log.debug("Processing current application...");
        }
        Integer connId = pageParameters.getValue("conn-selected", Integer.class);
        Integer pluginId = pageParameters.getValue("plugin-selected", Integer.class);

        // extracts the elements from the form
        String webProjectName = pageParameters.getValue("webProjectName");
        String webGroupId = pageParameters.getValue("webGroupId");
        String webArtifactId = pageParameters.getValue("webArtifactId");
        String webVersion = pageParameters.getValue("webVersion");

        String moduleName = pageParameters.getValue("mainModuleName");
        String authModuleName = pageParameters.getValue("authModuleName");

        Boolean existAdditionalContent = pageParameters.getValue("webAdditionalConfig", Boolean.class);
        String webFieldType = pageParameters.getValue("webFieldType");

        BuilderAppCompoundVo vo = null;
        vo = validateInputData(webProjectName, webGroupId, webArtifactId, webVersion, moduleName, authModuleName);
        vo.setConnId(connId);
        vo.getBuilderPluginVo().setId(pluginId);

        vo = validateInputData(vo, existAdditionalContent, webFieldType, pageParameters);

        builderAppService.buildApplication(vo);
        return new JsonResponse("");
    }

    /**
     * Process the existing configuration and build the application.
     * 
     * @param pageParameters
     *            Page parameters.
     * @return Response for the page.
     */
    @GET
    public WebResponse postProcessApp(PageParameters pageParameters) {
        String webProjectName = pageParameters.getValue("webProjectName");
        if (log.isDebugEnabled()) {
            log.debug("Post Processing current application...[" + webProjectName + "]");
        }

        builderAppService.compressApplication(webProjectName);
        return new JsonResponse("");
    }

    /**
     * Download the application.
     * 
     * @param pageParameters
     *            Page parameters.
     * @return Response for the page.
     */
    @GET
    public WebResponse downloadApp(PageParameters pageParameters) {
        return new StreamResponse(builderAppService.getFinalApplication());
    }

    private BuilderAppCompoundVo validateInputData(BuilderAppCompoundVo vo, Boolean existAdditionalContent,
            String webFieldType, PageParameters pageParameters) {
        if (existAdditionalContent != null && existAdditionalContent) {
            // exists more content to validate it.
            String fieldType = "";
            String fieldName = "";
            for (String pair : webFieldType.split("\\|")) {
                if (pair.contains("~")) {
                    String[] typeName = null;
                    typeName = pair.split("~");
                    fieldType = typeName[0];
                    fieldName = typeName[1];

                    switch (fieldType) {
                    case "checkbox":
                        Boolean currentValue = pageParameters.getValue(fieldName, Boolean.class);
                        currentValue = fromNullToCustomValue(String.valueOf(currentValue), false);

                        if (fieldName.equals("annotatedClasses")) {
                            vo.getApplicationBuilderPreferencesVo().setAnnotatedClasses(currentValue);
                        } else if (fieldName.equals("createHibernateConfig")) {
                            vo.getApplicationBuilderPreferencesVo().setCreateHibernateConfig(currentValue);
                        }
                        break;
                    case "text":
                    default:
                        break;
                    }
                }
            }
            Map<String, List<String>> validations = new HashMap<String, List<String>>();

            if (!vo.getApplicationBuilderPreferencesVo().isAnnotatedClasses()
                    && !vo.getApplicationBuilderPreferencesVo().isCreateHibernateConfig()) {
                List<String> list = new ArrayList<String>();
                list.add(messages.getMessage("builder.form.val.annotatedClasses.invalid"));
                validations.put("annotatedClasses", list);
            }
            if (!validations.isEmpty()) {
                throw new ValidationBusinessException(validations);
            }
        }
        return vo;
    }

    private BuilderAppCompoundVo validateInputData(String webProjectName, String webGroupId, String webArtifactId,
            String webVersion, String mainModuleName, String authModuleName) {
        BuilderAppCompoundVo vo = new BuilderAppCompoundVo();
        Map<String, List<String>> validations = new HashMap<String, List<String>>();

        // validation for the webProjectName field.
        if (isEmptyString(webProjectName)) {
            List<String> list = new ArrayList<String>();
            list.add(messages.getMessage("builder.form.val.webProjectName.empty"));
            validations.put("webProjectName", list);
        } else if (webProjectName.length() > 100) {
            List<String> list = new ArrayList<String>();
            list.add(messages.getMessage("builder.form.val.webProjectName.len", "100"));
            validations.put("webProjectName", list);
        }

        // validation for the webGroupId field.
        if (isEmptyString(webGroupId)) {
            List<String> list = new ArrayList<String>();
            list.add(messages.getMessage("builder.form.val.webGroupId.empty"));
            validations.put("webGroupId", list);
        } else if (webGroupId.length() > 50) {
            List<String> list = new ArrayList<String>();
            list.add(messages.getMessage("builder.form.val.webGroupId.len", "50"));
            validations.put("webGroupId", list);
        }

        // validation for the webArtifactId field.
        if (isEmptyString(webArtifactId)) {
            List<String> list = new ArrayList<String>();
            list.add(messages.getMessage("builder.form.val.webArtifactId.empty"));
            validations.put("webArtifactId", list);
        } else if (webArtifactId.length() > 20) {
            List<String> list = new ArrayList<String>();
            list.add(messages.getMessage("builder.form.val.webArtifactId.len", "20"));
            validations.put("webArtifactId", list);
        }

        // validation for the webVersion field.
        if (isEmptyString(webVersion)) {
            List<String> list = new ArrayList<String>();
            list.add(messages.getMessage("builder.form.val.webVersion.empty"));
            validations.put("webVersion", list);
        } else if (webVersion.length() > 20) {
            List<String> list = new ArrayList<String>();
            list.add(messages.getMessage("builder.form.val.webVersion.len", "20"));
            validations.put("webVersion", list);
        }

        // validation for the mainModuleName field.
        if (isEmptyString(mainModuleName)) {
            List<String> list = new ArrayList<String>();
            list.add(messages.getMessage("builder.form.val.mainModuleName.empty"));
            validations.put("mainModuleName", list);
        } else if (mainModuleName.length() > 20) {
            List<String> list = new ArrayList<String>();
            list.add(messages.getMessage("builder.form.val.mainModuleName.len", "20"));
            validations.put("mainModuleName", list);
        }

        // validation for the authModuleName field.
        if (isEmptyString(authModuleName)) {
            List<String> list = new ArrayList<String>();
            list.add(messages.getMessage("builder.form.val.authModuleName.empty"));
            validations.put("authModuleName", list);
        } else if (authModuleName.length() > 20) {
            List<String> list = new ArrayList<String>();
            list.add(messages.getMessage("builder.form.val.authModuleName.len", "20"));
            validations.put("authModuleName", list);
        }

        if (!validations.isEmpty()) {
            throw new ValidationBusinessException(validations);
        }
        vo.setProjectName(webProjectName);
        BuilderPluginVo pluginVo = new BuilderPluginVo();
        pluginVo.setGroupId(webGroupId);
        pluginVo.setArtifactId(webArtifactId);
        pluginVo.setVersion(webVersion);
        vo.setBuilderPluginVo(pluginVo);
        ApplicationBuilderPreferencesVo pref = new ApplicationBuilderPreferencesVo();
        pref.setModuleName(mainModuleName);
        pref.setAuthModuleName(authModuleName);

        vo.setApplicationBuilderPreferencesVo(pref);
        return vo;
    }

    /**
     * Retrieves the databases saved based on the connection.
     * 
     * @param pageParameters
     *            Page parameters.
     * @return Response for the page.
     */
    @GET
    public WebResponse retrieveDatabases(PageParameters pageParameters) {
        if (log.isDebugEnabled()) {
            log.debug("Retrieving database information...");
        }
        Integer connId = pageParameters.getValue("conn-selected", Integer.class);

        List<BuilderAppVo> list = builderAppService.getAllConfiguration(connId);
        return new JsonResponse(list);
    }

    /**
     * Retrieves plugin information so that can be displayed on the screen.
     * 
     * @param pageParameters
     *            Page parameters.
     * @return Response for the page.
     */
    @GET
    public WebResponse retrievePluginInfo(PageParameters pageParameters) {
        if (log.isDebugEnabled()) {
            log.debug("Retrieving plugin information...");
        }
        Integer pluginId = pageParameters.getValue("plugin-selected", Integer.class);

        BuilderPluginVo vo = builderAppService.getBuilderPluginConfig(pluginId);
        BuilderCodeGenVo builderCodeGenVo = builderAppService.getBuilderPluginCodeGen(pluginId);
        Map<String, Object> data = new HashMap<String, Object>();

        data.put("plugin", vo);
        data.put("additionalConfig", builderCodeGenVo);
        return new JsonResponse(data);
    }

    /**
     * Retrieves the tables saved based on the connection.
     * 
     * @param pageParameters
     *            Page parameters.
     * @return Response for the page.
     */
    @GET
    public WebResponse retrieveTables(PageParameters pageParameters) {
        if (log.isDebugEnabled()) {
            log.debug("Retrieving table information...");
        }
        Integer connId = pageParameters.getValue("conn-selected", Integer.class);
        String databaseSchema = pageParameters.getValue("database-schema");

        List<BuilderAppVo> list = builderAppService.getAllConfiguration(connId, databaseSchema);
        builderAppService.updateTableToBeProcessed(list);
        return new JsonResponse(list);
    }

    /**
     * Pre Process the existing application, this method retrieves information
     * just before download the application.
     * 
     * @param pageParameters
     *            Page parameters.
     * @return Response for the page.
     */
    @GET
    public WebResponse preProcessApp(PageParameters pageParameters) {
        if (log.isDebugEnabled()) {
            log.debug("Preprocess my application...");
        }
        List<DataSourceSimpleVo> connections = dataSourceService.getAvailableConnections();
        List<BuilderPluginVo> plugins = builderAppService.getBuilderPluginConfig();
        ApplicationBuilderPreferencesVo pref = builderAppService.getPreferences();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("connections", connections);
        data.put("plugins", plugins);
        data.put("mainPreferences", pref);
        return new JsonResponse(data);
    }
}
