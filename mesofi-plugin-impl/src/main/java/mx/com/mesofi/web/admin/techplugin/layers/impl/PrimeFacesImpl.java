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
import static mx.com.mesofi.plugins.project.core.files.FileType.CSS;
import static mx.com.mesofi.plugins.project.core.files.FileType.JAVA;
import static mx.com.mesofi.plugins.project.core.files.FileType.JS;
import static mx.com.mesofi.plugins.project.core.files.FileType.PROPERTIES;
import static mx.com.mesofi.plugins.project.core.files.FileType.XHTML;
import static mx.com.mesofi.plugins.project.core.files.FileType.XML;
import static mx.com.mesofi.services.util.SimpleCommonActions.fromERFormatToMethodFormat;
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
import mx.com.mesofi.plugins.project.core.files.ProjectFile;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderScreenVo;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderVo;
import mx.com.mesofi.plugins.project.core.util.VelocityGeneratorCode;
import mx.com.mesofi.services.files.FileProcessor;
import mx.com.mesofi.web.admin.techplugin.layers.common.AbstractPrimeFaces;

/**
 * This class is the default implementation for the following technology:
 * PrimeFaces. Any plugin is free to use this class in order to avoid the
 * creation a new class from scratch.
 * 
 * @author Armando Rivas
 * @since Feb 12 2015.
 */
public class PrimeFacesImpl extends AbstractPrimeFaces {
    /**
     * Predefined templates.
     */
    private static final String INDEX = "/mx/com/mesofi/web/admin/techplugin/templates/primefaces/webapp/index.vm";

    private static final String DEFAULT = "/mx/com/mesofi/web/admin/techplugin/templates/primefaces/webapp/default.vm";
    private static final String TEMPLATE = "/mx/com/mesofi/web/admin/techplugin/templates/primefaces/webapp/template.vm";
    private static final String MENU = "/mx/com/mesofi/web/admin/techplugin/templates/primefaces/webapp/menu.vm";
    private static final String CATALOG = "/mx/com/mesofi/web/admin/techplugin/templates/primefaces/webapp/dyna-catalog.vm";
    private static final String STYLE = "/mx/com/mesofi/web/admin/techplugin/templates/primefaces/webapp/style.vm";
    private static final String SCRIPT = "/mx/com/mesofi/web/admin/techplugin/templates/primefaces/webapp/script.vm";

    private static final String FACES_CONFIG = "/mx/com/mesofi/web/admin/techplugin/templates/primefaces/webapp/faces-config.vm";

    private static final String LOGIN_CONTROLLER = "/mx/com/mesofi/web/admin/techplugin/templates/primefaces/java/login-controller.vm";
    private static final String DYNA_CATALOG_CONTROLLER = "/mx/com/mesofi/web/admin/techplugin/templates/primefaces/java/dyna-catalog-controller.vm";

    private static final String MESSAGES = "/mx/com/mesofi/web/admin/techplugin/templates/primefaces/resources/messages.vm";

    private static final String VIEW_CONFIG = "/mx/com/mesofi/web/admin/techplugin/templates/primefaces/resources/view-config.vm";

    /**
     * Creates a default implementation for PrimeFaces.
     * 
     * @param builder
     *            The application builder.
     */
    public PrimeFacesImpl(ApplicationBuilderVo builder) {
        super(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlainFile getProjectInitialFileContent(String moduleName, String artifactId) {
        Map<String, Object> params = new HashMap<String, Object>();
        String suffixModelSourceName = ((AbstractCommonBuilder) getBusinessLayer()).getSuffixSourceName();

        params.put("classNameBean", getStandardClassName(moduleName, getSuffixSourceName()));
        params.put("classNameModel", getStandardClassName(moduleName, suffixModelSourceName));

        String content = getInstance().evaluateTemplate(INDEX, params);
        return new PlainFile(XHTML, "index", content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, List<ProjectFile>> getViewPages(String moduleName, String artifactId) {
        Map<String, List<ProjectFile>> pages = new HashMap<String, List<ProjectFile>>();
        List<ProjectFile> viewTemplate = new ArrayList<ProjectFile>();
        List<ProjectFile> viewCatalog = new ArrayList<ProjectFile>();
        List<ProjectFile> viewCss = new ArrayList<ProjectFile>();
        List<ProjectFile> viewJs = new ArrayList<ProjectFile>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("scriptName", getBuilder().getProjectName());

        params.put("classNameBean", getStandardClassName(moduleName, getSuffixSourceName()));

        viewTemplate.add(new PlainFile(XHTML, "default", getInstance().evaluateTemplate(DEFAULT)));
        viewTemplate.add(new PlainFile(XHTML, "template", getInstance().evaluateTemplate(TEMPLATE, params)));
        viewTemplate.add(new PlainFile(XHTML, "menu", getInstance().evaluateTemplate(MENU, params)));

        viewCss.add(new PlainFile(CSS, "style", getInstance().evaluateTemplate(STYLE)));
        viewJs.add(new PlainFile(JS, getBuilder().getProjectName(), getInstance().evaluateTemplate(SCRIPT)));

        params.clear();
        String moduleNameBase = "";
        String className = "";

        String suffixModelSourceName = ((AbstractCommonBuilder) getBusinessLayer()).getSuffixSourceName();

        // creates the screens
        for (ApplicationBuilderScreenVo screenVo : getBuilder().getScreens()) {
            moduleNameBase = fromERFormatToMethodFormat(screenVo.getScreenName());
            className = getStandardClassName(moduleNameBase, getSuffixSourceName());

            params.put("className", className);
            params.put("classNameSearch", getStandardClassName(moduleNameBase, "Search"));
            params.put("classNameConfigModel", getStandardClassName(moduleNameBase + "Config", suffixModelSourceName));
            params.put("classNameModel", getStandardClassName(moduleNameBase, suffixModelSourceName));

            params.put("fields", screenVo.getAppFields());
            params.put("pageName", screenVo.getPageName());

            StringBuilder templateContent = new StringBuilder(new FileProcessor().readPlainContentByLine(CATALOG));
            VelocityGeneratorCode.getInstance().mergeTemplateContent(templateContent,
                    getBuilder().getGeneratedFinalCode());
            String content = getInstance().evaluateRawString(templateContent.toString(), params);
            viewCatalog.add(new PlainFile(XHTML, screenVo.getPageName(), content));

        }
        pages.put("templates", viewTemplate);
        pages.put("catalogs", viewCatalog);

        pages.put("resources/css", viewCss); // sub directory
        pages.put("resources/js", viewJs); // sub directory
        return pages;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlainFile> getWebInfFiles(String basePackageName) {
        List<PlainFile> list = new ArrayList<PlainFile>();
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("packageName", basePackageName);
        String content = getInstance().evaluateTemplate(FACES_CONFIG, params);

        list.add(new PlainFile(XML, "faces-config", content));
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlainFile> getJavaControllerSources(String basePackageName, String moduleName, boolean isDynamicModule) {
        List<PlainFile> list = new ArrayList<PlainFile>();
        FileType fileType = JAVA;
        Map<String, Object> params = new HashMap<String, Object>();

        String suffixModelSourceName = ((AbstractCommonBuilder) getBusinessLayer()).getSuffixSourceName();
        String suffixModelPackageName = ((AbstractCommonBuilder) getBusinessLayer()).getSuffixPackageName();
        String className = getStandardClassName(moduleName, getSuffixSourceName());
        String partialPackageName = createFullPackageName(basePackageName, moduleName, isDynamicModule, false);

        params.put("date", new Date());
        params.put("year", Calendar.getInstance().get(Calendar.YEAR));

        params.put("packageName", partialPackageName);
        params.put("suffixPackageName", getSuffixPackageName());
        params.put("className", className);
        params.put("classNameModel", getStandardClassName(moduleName, suffixModelSourceName));
        params.put("suffixModelPackageName", suffixModelPackageName);

        if (!isDynamicModule) {
            params.put("screens", getBuilder().getScreens());
            list.add(new PlainFile(fileType, className, getInstance().evaluateTemplate(LOGIN_CONTROLLER, params)));
        } else {
            String suffixCommonPackageName = ((AbstractCommonBuilder) getCommonLayer()).getSuffixPackageName();

            params.put("suffixCommonPackageName", suffixCommonPackageName);
            params.put("basePackageName", basePackageName);

            params.put("classNameConfigModel", getStandardClassName(moduleName + "Config", suffixModelSourceName));
            params.put("classNameSearch", getStandardClassName(moduleName, "Search"));
            params.put("classNameService", getStandardClassName(moduleName, "Service"));
            params.put("fields", getBuilder().getScreenFields(moduleName));
            params.put("pageName", fromMethodFormatToERFormat(moduleName));
            params.put("javaSourcesPackageName", getSuffixPackageName());

            StringBuilder templateContent = null;
            templateContent = new StringBuilder(new FileProcessor().readPlainContentByLine(DYNA_CATALOG_CONTROLLER));
            VelocityGeneratorCode.getInstance().mergeTemplateContent(templateContent,
                    getBuilder().getGeneratedFinalCode());
            String content = getInstance().evaluateRawString(templateContent.toString(), params);
            list.add(new PlainFile(fileType, className, content));
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlainFile> getI18nMessages() {
        List<PlainFile> messages = new ArrayList<PlainFile>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("projectName", getBuilder().getProjectName());
        params.put("allFields", getBuilder().getScreens());
        messages.add(new PlainFile(PROPERTIES, "messages", getInstance().evaluateTemplate(MESSAGES, params)));
        return messages;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlainFile getViewConfig() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("allFields", getBuilder().getScreens());
        return new PlainFile(PROPERTIES, "view-config", getInstance().evaluateTemplate(VIEW_CONFIG, params));
    }

}
