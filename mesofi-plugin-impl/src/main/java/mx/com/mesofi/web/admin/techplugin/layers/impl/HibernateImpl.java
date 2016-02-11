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
import static mx.com.mesofi.services.util.SimpleCommonActions.fromERFormatToBeanFormat;
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
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderScreenVo;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderVo;
import mx.com.mesofi.plugins.project.core.technologies.SpringCore;
import mx.com.mesofi.plugins.project.core.util.VelocityGeneratorCode;
import mx.com.mesofi.services.files.FileProcessor;
import mx.com.mesofi.web.admin.techplugin.layers.common.AbstractHibernate;

/**
 * This class is the default implementation for the following technology:
 * Hibernate. Any plugin is free to use this class in order to avoid the
 * creation a new class from scratch.
 * 
 * @author Armando Rivas
 * @since April 05 2015.
 */
public class HibernateImpl extends AbstractHibernate implements SpringCore {
    /**
     * Predefined templates.
     */
    private static final String HIBERNATE_CONFIG = "/mx/com/mesofi/web/admin/techplugin/templates/spring/resources/hibernate-config.vm";

    private static final String HIBERNATE_CFG = "/mx/com/mesofi/web/admin/techplugin/templates/hibernate/resources/hibernate.cfg.vm";
    private static final String HIBERNATE_MAPPING = "/mx/com/mesofi/web/admin/techplugin/templates/hibernate/resources/hibernate-mapping.vm";

    private static final String DYNA_DAO_IMPL = "/mx/com/mesofi/web/admin/techplugin/templates/hibernate/java/dyna-catalog-dao-impl.vm";
    private static final String DYNA_DAO = "/mx/com/mesofi/web/admin/techplugin/templates/hibernate/resources/dyna-catalog-dao.vm";

    /**
     * Creates a default implementation for Hibernate.
     * 
     * @param builder
     *            The application builder.
     */
    public HibernateImpl(ApplicationBuilderVo builder) {
        super(builder);
    }

    @Override
    public List<PlainFile> getPersistenceSources(String basePackageName, String moduleName, boolean isDynamicModule) {
        List<PlainFile> list = new ArrayList<PlainFile>();
        FileType fileName = FileType.JAVA;
        Map<String, Object> params = new HashMap<String, Object>();

        String className = getStandardClassName(moduleName, getSuffixSourceName());
        String partialPackageName = createFullPackageName(basePackageName, moduleName, isDynamicModule, false);
        params.put("packageName", partialPackageName);
        params.put("suffixPackageName", getSuffixPackageName());
        params.put("className", className);

        if (!isDynamicModule) {

        } else {
            String suffixModelPackageName = ((AbstractCommonBuilder) getBusinessLayer()).getSuffixPackageName();
            String suffixModelSourceName = ((AbstractCommonBuilder) getBusinessLayer()).getSuffixSourceName();
            String classNameImpl = className + "Impl";

            params.put("date", new Date());
            params.put("year", Calendar.getInstance().get(Calendar.YEAR));

            params.put("suffixModelPackageName", suffixModelPackageName);

            params.put("classNameModel", getStandardClassName(moduleName, suffixModelSourceName));
            params.put("classNameSearch", getStandardClassName(moduleName, "Search"));
            params.put("classNamePersistence", getStandardClassName(moduleName, getSuffixSourceName()));

            params.put("fields", getBuilder().getScreenFields(moduleName));

            params.put("templateName", DYNA_DAO_IMPL);

            StringBuilder templateContent = new StringBuilder(new FileProcessor().readPlainContentByLine(DYNA_DAO_IMPL));
            VelocityGeneratorCode.getInstance().mergeTemplateContent(templateContent,
                    getBuilder().getGeneratedFinalCode());
            String content = getInstance().evaluateRawString(templateContent.toString(), params);
            list.add(new PlainFile(fileName, classNameImpl, content));
        }
        return list;
    }

    @Override
    public List<PlainFile> getPersistenceResources(String basePackageName, String moduleName, boolean isDynamicModule) {
        List<PlainFile> list = new ArrayList<PlainFile>();
        FileType fileName = FileType.XML;
        Map<String, Object> params = new HashMap<String, Object>();

        if (!isDynamicModule) {

        } else {
            String suffixModelSourceName = ((AbstractCommonBuilder) getBusinessLayer()).getSuffixSourceName();
            String classNamePersistence = getStandardClassName(moduleName, getSuffixSourceName());
            String classNameModel = getStandardClassName(moduleName, suffixModelSourceName);

            params.put("classNamePersistence", classNamePersistence);
            params.put("classNameModel", classNameModel);
            params.put("fields", getBuilder().getScreenFields(moduleName));
            params.put("templateName", DYNA_DAO);

            StringBuilder templateContent = new StringBuilder(new FileProcessor().readPlainContentByLine(DYNA_DAO));
            VelocityGeneratorCode.getInstance().mergeTemplateContent(templateContent,
                    getBuilder().getGeneratedFinalCode());
            String content = getInstance().evaluateRawString(templateContent.toString(), params);
            list.add(new PlainFile(fileName, classNamePersistence, content));

        }
        return list;
    }

    @Override
    public List<PlainFile> getJavaModelSources(String basePackageName, String moduleName, boolean isDynamicModule) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<PlainFile> getServiceSources(String packageName, String moduleName, boolean isDynamicModule) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlainFile> getSpringConfig(String packageName, String moduleName) {
        List<PlainFile> list = new ArrayList<PlainFile>();
        FileType fileType = FileType.XML;
        Map<String, Object> params = new HashMap<String, Object>();
        boolean createHibernateConfig = getBuilder().getPreferences().isCreateHibernateConfig();
        boolean useAnnotations = getBuilder().getPreferences().isAnnotatedClasses();

        params.put("createConfigLocation", createHibernateConfig);

        if (createHibernateConfig) {
            String[] folders = packageName.split("\\.");
            StringBuilder sb = new StringBuilder("classpath:");
            for (String folder : folders) {
                sb.append(folder);
                sb.append("/");
            }
            sb.append("hibernate");
            sb.append("/");
            params.put("hibernateLocation", sb.toString());
        } else {
            params.put("annotateClasses", useAnnotations);
            if (useAnnotations) {
                List<String> pkgScanned = new ArrayList<String>();
                for (ApplicationBuilderScreenVo vo : getBuilder().getScreens()) {
                    pkgScanned.add(createFullPackageName(packageName, moduleName, vo.getScreenName()));
                }
                params.put("packagesScanned", pkgScanned);
            }

            params.put("dbSqlDialect", getBuilder().getConnection().getDatabaseType().getSqlDialect());
            params.put("mappingResources", getMappingResources(packageName, getBuilder().getScreens()));
        }

        list.add(new PlainFile(fileType, "hibernate-config", getInstance().evaluateTemplate(HIBERNATE_CONFIG, params)));

        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlainFile getHibernateConfig(String basePackageName, String moduleName) {
        PlainFile config = null;
        FileType fileType = FileType.XML;
        Map<String, Object> params = new HashMap<String, Object>();
        boolean createHibernateConfig = getBuilder().getPreferences().isCreateHibernateConfig();

        if (createHibernateConfig) {
            params.put("dbUrl", getBuilder().getConnection().getUrl());
            params.put("dbDriver", getBuilder().getConnection().getDatabaseType().getDriverClassName());
            params.put("dbUserName", getBuilder().getConnection().getUser());
            params.put("dbPassword", getBuilder().getConnection().getPassword());
            params.put("dbSqlDialect", getBuilder().getConnection().getDatabaseType().getSqlDialect());

            boolean useAnnotations = getBuilder().getPreferences().isAnnotatedClasses();
            params.put("annotatedEntity", useAnnotations);
            if (useAnnotations) {
                params.put("mappingClasses", getMappingClasses(basePackageName, getBuilder().getScreens(), moduleName));
            } else {
                params.put("mappingResources", getMappingResources(basePackageName, getBuilder().getScreens()));
            }

            config = new PlainFile(fileType, "hibernate.cfg", getInstance().evaluateTemplate(HIBERNATE_CFG, params));
        }

        return config;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlainFile> getHibernateMappings(String packageName, String moduleName) {
        List<PlainFile> list = new ArrayList<PlainFile>();
        FileType fileType = FileType.XML;
        Map<String, Object> params = new HashMap<String, Object>();
        String fileName = null;
        String beanName = null;
        String className = null;

        for (ApplicationBuilderScreenVo vo : getBuilder().getScreens()) {
            beanName = fromERFormatToBeanFormat(vo.getScreenName());
            fileName = beanName + ".hbm";
            className = beanName + ((AbstractCommonBuilder) getBusinessLayer()).getSuffixSourceName();

            params.put("className", className);
            params.put("tableName", vo.getScreenName());
            params.put("packageName", createFullPackageName(packageName, moduleName, vo.getScreenName()));
            params.put("fields", vo.getAppFields());
            list.add(new PlainFile(fileType, fileName, getInstance().evaluateTemplate(HIBERNATE_MAPPING, params)));
        }

        return list;
    }

    /**
     * Gets a list that contains a list of mapping resources.
     * 
     * @param packageName
     *            The package name.
     * @param screens
     *            List of screens.
     * @return List of mapping resources.
     */
    private List<String> getMappingResources(String packageName, List<ApplicationBuilderScreenVo> screens) {
        List<String> mappings = new ArrayList<String>();
        String[] folders = packageName.split("\\.");
        StringBuilder sb = new StringBuilder();
        for (String folder : folders) {
            sb.append(folder);
            sb.append("/");
        }
        sb.append("hibernate/mappings/");

        for (ApplicationBuilderScreenVo vo : screens) {
            mappings.add(sb.toString() + fromERFormatToBeanFormat(vo.getScreenName()) + ".hbm." + FileType.XML);
        }
        return mappings;
    }

    /**
     * Gets a list that contains a list of mapping classes.
     * 
     * @param packageName
     *            The package name.
     * @param screens
     *            List of screens.
     * @param moduleName
     *            The module name.
     * @return List of mapping classes.
     */
    private List<String> getMappingClasses(String packageName, List<ApplicationBuilderScreenVo> screens,
            String moduleName) {
        List<String> mappings = new ArrayList<String>();
        String qualifiedName = "";
        String tmp = null;

        StringBuilder sb = null;
        for (ApplicationBuilderScreenVo vo : screens) {
            sb = new StringBuilder();
            tmp = fromERFormatToBeanFormat(vo.getScreenName());

            sb.append(createFullPackageName(packageName, moduleName, vo.getScreenName()));
            sb.append(".");
            sb.append(tmp);
            sb.append(((AbstractCommonBuilder) getBusinessLayer()).getSuffixSourceName());

            qualifiedName = sb.toString();
            mappings.add(qualifiedName);
        }
        return mappings;
    }

}
