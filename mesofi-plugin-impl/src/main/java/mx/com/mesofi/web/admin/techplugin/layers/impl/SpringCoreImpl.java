/*
 * COPYRIGHT. Mesofi 2015. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.techplugin.layers.impl;

import static mx.com.mesofi.framework.util.TemplateManager.getInstance;
import static mx.com.mesofi.services.util.SimpleCommonActions.fromMethodFormatToERFormat;
import static mx.com.mesofi.services.util.SimpleCommonActions.getStandardClassName;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.mesofi.plugins.project.core.AbstractCommonBuilder;
import mx.com.mesofi.plugins.project.core.files.FileType;
import mx.com.mesofi.plugins.project.core.files.PlainFile;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderVo;
import mx.com.mesofi.web.admin.techplugin.layers.common.AbstractSpringCore;

/**
 * This class is the default implementation for the following technology:
 * SpringCode. Any plugin is free to use this class in order to avoid the
 * creation a new class from scratch.
 * 
 * @author Armando Rivas
 * @since Feb 14 2015.
 */
public class SpringCoreImpl extends AbstractSpringCore {
    /**
     * Predefined templates.
     */
    private static final String LOGIN_MODEL = "/mx/com/mesofi/web/admin/techplugin/templates/model/java/login-model.vm";
    private static final String LOGIN_SERVICE = "/mx/com/mesofi/web/admin/techplugin/templates/spring/java/login-service.vm";
    private static final String LOGIN_SERVICE_IMPL = "/mx/com/mesofi/web/admin/techplugin/templates/spring/java/login-service-impl.vm";

    private static final String DYNA_CATALOG_MODEL = "/mx/com/mesofi/web/admin/techplugin/templates/model/java/dyna-catalog-model.vm";
    private static final String DYNA_CATALOG_CONFIG_MODEL = "/mx/com/mesofi/web/admin/techplugin/templates/model/java/dyna-catalog-config-model.vm";

    private static final String DYNA_CATALOG_SERVICE = "/mx/com/mesofi/web/admin/techplugin/templates/spring/java/dyna-catalog-service.vm";
    private static final String DYNA_CATALOG_SERVICE_IMPL = "/mx/com/mesofi/web/admin/techplugin/templates/spring/java/dyna-catalog-service-impl.vm";

    private static final String APP_CONTEXT = "/mx/com/mesofi/web/admin/techplugin/templates/spring/resources/application-context.vm";
    private static final String DATA_SOURCE_CONFIG = "/mx/com/mesofi/web/admin/techplugin/templates/spring/resources/data-source-config.vm";

    /**
     * Creates a default implementation for SpringCore.
     * 
     * @param builder
     *            The application builder.
     */
    public SpringCoreImpl(ApplicationBuilderVo builder) {
        super(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlainFile> getJavaModelSources(String basePackageName, String moduleName, boolean isDynamicModule) {
        List<PlainFile> list = new ArrayList<PlainFile>();
        FileType fileType = FileType.JAVA;
        Map<String, Object> params = new HashMap<String, Object>();

        String className = getStandardClassName(moduleName, getSuffixSourceName());
        // build the full package name.
        params.put("packageName", createFullPackageName(basePackageName, moduleName, isDynamicModule));
        params.put("className", className);

        if (!isDynamicModule) {
            list.add(new PlainFile(fileType, className, getInstance().evaluateTemplate(LOGIN_MODEL, params)));
        } else {
            String classNameConfig = getStandardClassName(moduleName + "Config", getSuffixSourceName());

            params.put("classNameConfig", classNameConfig);
            params.put("moduleName", moduleName);
            params.put("date", new Date());
            params.put("year", Calendar.getInstance().get(Calendar.YEAR));
            params.put("fields", getBuilder().getScreenFields(moduleName));
            params.put("annotatedEntity", getBuilder().getPreferences().isAnnotatedClasses());
            params.put("tableName", fromMethodFormatToERFormat(moduleName));

            list.add(new PlainFile(fileType, className, getInstance().evaluateTemplate(DYNA_CATALOG_MODEL, params)));
            list.add(new PlainFile(fileType, classNameConfig, getInstance().evaluateTemplate(DYNA_CATALOG_CONFIG_MODEL,
                    params)));
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlainFile> getServiceSources(String basePackageName, String moduleName, boolean isDynamicModule) {
        List<PlainFile> list = new ArrayList<PlainFile>();
        FileType fileType = FileType.JAVA;
        Map<String, Object> params = new HashMap<String, Object>();

        String className = getStandardClassName(moduleName, "Service");
        String classNameImpl = className + "Impl";

        String suffixModelPackageName = getSuffixPackageName();
        String suffixModelSourceName = getSuffixSourceName();

        params.put("packageName", createFullPackageName(basePackageName, moduleName, isDynamicModule, false));
        params.put("className", className);

        params.put("date", new Date());
        params.put("year", Calendar.getInstance().get(Calendar.YEAR));

        params.put("suffixModelPackageName", suffixModelPackageName);

        params.put("classNameModel", getStandardClassName(moduleName, suffixModelSourceName));

        if (!isDynamicModule) {
            params.put("templateName", LOGIN_SERVICE_IMPL);

            list.add(new PlainFile(fileType, className, getInstance().evaluateTemplate(LOGIN_SERVICE, params)));
            list.add(new PlainFile(fileType, classNameImpl, getInstance().evaluateTemplate(LOGIN_SERVICE_IMPL, params)));
        } else {
            String suffixPersistPackageName = ((AbstractCommonBuilder) getPersistenceLayer()).getSuffixPackageName();
            String suffixPersistClassName = ((AbstractCommonBuilder) getPersistenceLayer()).getSuffixSourceName();

            params.put("suffixPersistencePackageName", suffixPersistPackageName);

            params.put("classNamePersistence", getStandardClassName(moduleName, suffixPersistClassName));
            params.put("classNameSearch", getStandardClassName(moduleName, "Search"));

            params.put("templateName", DYNA_CATALOG_SERVICE_IMPL);

            list.add(new PlainFile(fileType, className, getInstance().evaluateTemplate(DYNA_CATALOG_SERVICE, params)));
            list.add(new PlainFile(fileType, classNameImpl, getInstance().evaluateTemplate(DYNA_CATALOG_SERVICE_IMPL,
                    params)));
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlainFile> getSpringConfig(String packageName, String moduleName) {
        List<PlainFile> list = new ArrayList<PlainFile>();
        FileType fileType = FileType.XML;
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("packageName", packageName);
        params.put("dbUrl", getBuilder().getConnection().getUrl());
        params.put("dbDriver", getBuilder().getConnection().getDatabaseType().getDriverClassName());
        params.put("dbUserName", getBuilder().getConnection().getUser());
        params.put("dbPassword", getBuilder().getConnection().getPassword());

        list.add(new PlainFile(fileType, "applicationContext", getInstance().evaluateTemplate(APP_CONTEXT, params)));
        list.add(new PlainFile(fileType, "dataSource-config", getInstance()
                .evaluateTemplate(DATA_SOURCE_CONFIG, params)));

        return list;
    }

}
