/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.techplugin.layers;

import static mx.com.mesofi.framework.util.TemplateManager.getInstance;
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
import mx.com.mesofi.plugins.project.core.technologies.PrimeFaces;
import mx.com.mesofi.plugins.project.core.util.VelocityGeneratorCode;
import mx.com.mesofi.services.files.FileProcessor;

/**
 * This implementation creates all the files using the PrimeFaces technology.
 * 
 * @author Armando Rivas
 * @since Mar 13 2014.
 * 
 */
public class PrimeFacesImpl extends AbstractCommonBuilder implements PrimeFaces {
    /**
     * Location for the initial file.
     */
    private static final String INDEX = "/mx/com/mesofi/web/admin/builder/templates/webapp/index.vm";

    public PrimeFacesImpl(ApplicationBuilderVo applicationBuilderVo) {
        super(applicationBuilderVo);
    }

    @Override
    public List<PlainFile> getJavaControllerSources(String packageName, String moduleName, boolean isDynamicModule) {
        List<PlainFile> list = new ArrayList<PlainFile>();
        FileType fileName = FileType.JAVA;
        Map<String, Object> params = new HashMap<String, Object>();

        if (!isDynamicModule) {
            String loginController = "/mx/com/mesofi/web/admin/builder/templates/java/login-controller.vm";
            params.put("packageName", packageName);
            params.put("screens", getBuilder().getScreens());
            list.add(new PlainFile(fileName, "LoginBean", getInstance().evaluateTemplate(loginController, params)));
        } else {
            String templateName = "/mx/com/mesofi/web/admin/techplugin/templates/java/dyna-catalog-controller.vm";
            String className = getStandardClassName(moduleName, "Bean");

            params.put("className", className);
            params.put("moduleName", moduleName);
            params.put("date", new Date());
            params.put("year", Calendar.getInstance().get(Calendar.YEAR));

            params.put("classNameVo", getStandardClassName(moduleName, "Vo"));
            params.put("classNameConfigVo", getStandardClassName(moduleName, "ConfigVo"));
            params.put("classNameSearch", getStandardClassName(moduleName, "Search"));
            params.put("classNameService", getStandardClassName(moduleName, "Service"));
            params.put("fields", getBuilder().getScreenFields(moduleName));
            params.put("pageName", fromMethodFormatToERFormat(moduleName));

            StringBuilder templateContent = new StringBuilder(new FileProcessor().readPlainContentByLine(templateName));
            VelocityGeneratorCode.getInstance().mergeTemplateContent(templateContent,
                    getBuilder().getGeneratedFinalCode());
            String content = getInstance().evaluateRawString(templateContent.toString(), params);
            list.add(new PlainFile(fileName, className, content));

            // list.add(new PlainFile(fileName, className,
            // getInstance().evaluateTemplateAndMerge(dynaCatValueObject,
            // getApplicationBuilderVo().getGeneratedFinalCode(), params)));
        }

        return list;
    }

    @Override
    public PlainFile getProjectInitialFileContent(String moduleName, String artifactId) {
        String content = getInstance().evaluateTemplate(INDEX);
        PlainFile plainFile = new PlainFile(FileType.XHTML, "index", content);
        return plainFile;
    }

    @Override
    public List<PlainFile> getWebInfFiles(String packageName) {
        List<PlainFile> list = new ArrayList<PlainFile>();
        FileType fileName = FileType.XML;
        Map<String, Object> params = new HashMap<String, Object>();

        String facesConfig = "/mx/com/mesofi/web/admin/builder/templates/resources/faces-config.vm";
        params.put("packageName", packageName);

        list.add(new PlainFile(fileName, "faces-config", getInstance().evaluateTemplate(facesConfig, params)));

        return list;
    }

    @Override
    public String getSuffixPackageName() {
        return "bean";
    }

    @Override
    public String getSuffixSourceName() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Map<String, List<ProjectFile>> getViewPages(String moduleName, String artifactId) {
        String defaultPage = "/mx/com/mesofi/web/admin/builder/templates/resources/default.vm";
        String template = "/mx/com/mesofi/web/admin/builder/templates/resources/template.vm";
        String menu = "/mx/com/mesofi/web/admin/builder/templates/resources/menu.vm";
        String catalog = "/mx/com/mesofi/web/admin/builder/templates/webapp/dyna-catalog.vm";
        String style = "/mx/com/mesofi/web/admin/builder/templates/webapp/style.vm";
        String script = "/mx/com/mesofi/web/admin/builder/templates/webapp/script.vm";

        Map<String, List<ProjectFile>> pages = new HashMap<String, List<ProjectFile>>();
        List<ProjectFile> viewPagesTemplate = new ArrayList<ProjectFile>();
        List<ProjectFile> viewPagesCatalog = new ArrayList<ProjectFile>();
        List<ProjectFile> viewPagesCss = new ArrayList<ProjectFile>();
        List<ProjectFile> viewPagesJs = new ArrayList<ProjectFile>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("scriptName", getBuilder().getProjectName());

        viewPagesTemplate.add(new PlainFile(FileType.XHTML, "default", getInstance().evaluateTemplate(defaultPage)));
        viewPagesTemplate.add(new PlainFile(FileType.XHTML, "template", getInstance()
                .evaluateTemplate(template, params)));
        viewPagesTemplate.add(new PlainFile(FileType.XHTML, "menu", getInstance().evaluateTemplate(menu)));

        viewPagesCss.add(new PlainFile(FileType.CSS, "style", getInstance().evaluateTemplate(style)));

        viewPagesJs.add(new PlainFile(FileType.JS, getBuilder().getProjectName(), getInstance()
                .evaluateTemplate(script)));

        params.clear();
        String moduleNameBase = "";
        String className = "";

        // creates the screens
        for (ApplicationBuilderScreenVo screenVo : getBuilder().getScreens()) {
            moduleNameBase = fromERFormatToMethodFormat(screenVo.getScreenName());
            className = getStandardClassName(moduleNameBase, "Bean");

            params.put("fields", screenVo.getAppFields());
            params.put("className", className);
            params.put("classNameBase", moduleNameBase);
            params.put("pageName", screenVo.getPageName());

            StringBuilder templateContent = new StringBuilder(new FileProcessor().readPlainContentByLine(catalog));
            VelocityGeneratorCode.getInstance().mergeTemplateContent(templateContent,
                    getBuilder().getGeneratedFinalCode());
            String content = getInstance().evaluateRawString(templateContent.toString(), params);
            viewPagesCatalog.add(new PlainFile(FileType.XHTML, screenVo.getPageName(), content));

            // viewPagesCatalog.add(new ProjectFile(FileType.XHTML,
            // screenVo.getPageName(),
            // getInstance().evaluateTemplateAndMerge(catalog,
            // getApplicationBuilderVo().getGeneratedFinalCode(), params)));

        }

        pages.put("templates", viewPagesTemplate);
        pages.put("catalogs", viewPagesCatalog);
        pages.put("resources/css", viewPagesCss); // sub directory
        pages.put("resources/js", viewPagesJs); // sub directory
        return pages;
    }

    @Override
    public List<PlainFile> getI18nMessages() {
        List<PlainFile> messagesList = new ArrayList<PlainFile>();
        String messages = "/mx/com/mesofi/web/admin/builder/templates/resources/messages.vm";
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("projectName", getBuilder().getProjectName());
        params.put("allFields", getBuilder().getScreens());

        messagesList.add(new PlainFile(FileType.PROPERTIES, "messages", getInstance()
                .evaluateTemplate(messages, params)));
        return messagesList;
    }

    @Override
    public PlainFile getViewConfig() {
        String messages = "/mx/com/mesofi/web/admin/builder/templates/resources/view-config.vm";
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("allFields", getBuilder().getScreens());

        return new PlainFile(FileType.PROPERTIES, "view-config", getInstance().evaluateTemplate(messages, params));
    }

}
