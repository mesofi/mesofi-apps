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
import static mx.com.mesofi.plugins.project.core.files.FileType.EOT;
import static mx.com.mesofi.plugins.project.core.files.FileType.GIF;
import static mx.com.mesofi.plugins.project.core.files.FileType.JAVA;
import static mx.com.mesofi.plugins.project.core.files.FileType.JS;
import static mx.com.mesofi.plugins.project.core.files.FileType.JSP;
import static mx.com.mesofi.plugins.project.core.files.FileType.PNG;
import static mx.com.mesofi.plugins.project.core.files.FileType.PROPERTIES;
import static mx.com.mesofi.plugins.project.core.files.FileType.SVG;
import static mx.com.mesofi.plugins.project.core.files.FileType.TTF;
import static mx.com.mesofi.plugins.project.core.files.FileType.WOFF;
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
import mx.com.mesofi.plugins.project.core.files.BinaryFile;
import mx.com.mesofi.plugins.project.core.files.FileType;
import mx.com.mesofi.plugins.project.core.files.PlainFile;
import mx.com.mesofi.plugins.project.core.files.ProjectFile;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderScreenVo;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderVo;
import mx.com.mesofi.plugins.project.core.util.VelocityGeneratorCode;
import mx.com.mesofi.services.files.FileProcessor;
import mx.com.mesofi.web.admin.techplugin.layers.common.AbstractSpringMVC;

/**
 * This class is the default implementation for the following technology:
 * SpringMVC. Any plugin is free to use this class in order to avoid the
 * creation a new class from scratch.
 * 
 * @author Armando Rivas
 * @since September 27 2015.
 */
public class SpringMVCImpl extends AbstractSpringMVC {
    /**
     * Predefined templates.
     */
    private static final String INDEX = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/webapp/index.vm";

    private static final String DEFAULT = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/webapp/default.vm";
    private static final String TEMPLATE = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/webapp/template.vm";
    private static final String MENU = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/webapp/menu.vm";
    private static final String CATALOG = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/webapp/dyna-catalog.vm";

    private static final String JQUERY_TABLE_JS = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/webapp/dynatable/js/jquery.dynatable.vm";
    private static final String JQUERY_MIN = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/webapp/bootstrap/js/jquery.min.vm";
    private static final String BOOTSTRAP_MIN_JS = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/webapp/bootstrap/js/bootstrap.min.vm";
    private static final String BOOTSTRAP_DLG_JS = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/webapp/bootstrap-dialog/js/bootstrap-dialog.vm";
    private static final String SCRIPT = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/webapp/script.vm";
    private static final String DYNA_SCRIPT = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/webapp/dyna-script.vm";
    private static final String TILES = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/webapp/tiles.vm";

    private static final String JQUERY_TBL_CSS = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/webapp/dynatable/css/jquery.dynatable.vm";
    private static final String BOOTSTRAP_MIN_CSS = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/webapp/bootstrap/css/bootstrap.min.vm";
    private static final String BOOTSTRAP_DLG_CSS = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/webapp/bootstrap-dialog/css/bootstrap-dialog.vm";
    private static final String STYLE = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/webapp/style.vm";

    private static final String LOGIN_CONTROLLER = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/java/login-controller.vm";
    private static final String DEFAULT_MENU_ITEM = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/java/default-menu-item.vm";
    private static final String DYNA_CATALOG_CONTROLLER = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/java/dyna-catalog-controller.vm";
    private static final String DYNA_CATALOG_FORM_MODEL = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/java/dyna-catalog-vo-form-model.vm";

    private static final String VALIDATION_MESSAGES = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/resources/validation-messages.vm";
    private static final String VALIDATION_MESSAGES_ES = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/resources/validation-messages-es.vm";
    private static final String MESSAGES = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/resources/messages.vm";
    private static final String MESSAGES_ES = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/resources/messages-es.vm";

    private static final String IMG_LOADING = "mx/com/mesofi/web/admin/techplugin/resources/images/ajax-loader.gif";
    private static final String GLYPHICONS_WHITE = "mx/com/mesofi/web/admin/techplugin/resources/bootstrap/img/glyphicons-halflings-white.png";
    private static final String GLYPHICONS_BLACK = "mx/com/mesofi/web/admin/techplugin/resources/bootstrap/img/glyphicons-halflings.png";
    private static final String GLYPHICONS_EOT = "mx/com/mesofi/web/admin/techplugin/resources/bootstrap/fonts/glyphicons-halflings-regular.eot";
    private static final String GLYPHICONS_SVG = "mx/com/mesofi/web/admin/techplugin/resources/bootstrap/fonts/glyphicons-halflings-regular.svg";
    private static final String GLYPHICONS_TTF = "mx/com/mesofi/web/admin/techplugin/resources/bootstrap/fonts/glyphicons-halflings-regular.ttf";
    private static final String GLYPHICONS_WOFF = "mx/com/mesofi/web/admin/techplugin/resources/bootstrap/fonts/glyphicons-halflings-regular.woff";

    /**
     * Creates a default implementation for SpringMVC.
     * 
     * @param builder
     *            The application builder.
     */
    public SpringMVCImpl(ApplicationBuilderVo builder) {
        super(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlainFile getProjectInitialFileContent(String moduleName, String artifactId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("projectName", getBuilder().getProjectName());
        params.put("artifactId", artifactId);

        String content = getInstance().evaluateTemplate(INDEX, params);
        return new PlainFile(JSP, "index", content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, List<ProjectFile>> getViewPages(String moduleName, String artifactId) {
        Map<String, List<ProjectFile>> pages = new HashMap<String, List<ProjectFile>>();
        List<ProjectFile> viewTemplate = new ArrayList<ProjectFile>();
        List<ProjectFile> viewCatalog = new ArrayList<ProjectFile>();
        List<ProjectFile> viewCssBootstrap = new ArrayList<ProjectFile>();
        List<ProjectFile> viewCssBootDlg = new ArrayList<ProjectFile>();
        List<ProjectFile> viewJsBootstrap = new ArrayList<ProjectFile>();
        List<ProjectFile> viewJsBootstrapDlg = new ArrayList<ProjectFile>();
        List<ProjectFile> viewImgBootstrap = new ArrayList<ProjectFile>();
        List<ProjectFile> viewFntBootstrap = new ArrayList<ProjectFile>();
        List<ProjectFile> viewCssDynatable = new ArrayList<ProjectFile>();
        List<ProjectFile> viewJsDynatable = new ArrayList<ProjectFile>();
        List<ProjectFile> viewCss = new ArrayList<ProjectFile>();
        List<ProjectFile> viewJs = new ArrayList<ProjectFile>();
        List<ProjectFile> viewImg = new ArrayList<ProjectFile>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("artifactId", artifactId);
        params.put("scriptName", getBuilder().getProjectName());

        params.put("classNameBean", getStandardClassName(moduleName, getSuffixSourceName()));

        viewTemplate.add(new PlainFile(JSP, "default", getInstance().evaluateTemplate(DEFAULT)));
        viewTemplate.add(new PlainFile(JSP, "template", getInstance().evaluateTemplate(TEMPLATE, params)));
        viewTemplate.add(new PlainFile(JSP, "menu", getInstance().evaluateTemplate(MENU, params)));

        viewJsBootstrap.add(new PlainFile(JS, "jquery.min", getInstance().evaluateTemplate(JQUERY_MIN)));
        viewJsBootstrapDlg.add(new PlainFile(JS, "bootstrap-dialog", getInstance().evaluateTemplate(BOOTSTRAP_DLG_JS)));
        viewJsBootstrap.add(new PlainFile(JS, "bootstrap.min", getInstance().evaluateTemplate(BOOTSTRAP_MIN_JS)));
        viewJsDynatable.add(new PlainFile(JS, "jquery.dynatable", getInstance().evaluateTemplate(JQUERY_TABLE_JS)));

        viewCssBootDlg.add(new PlainFile(CSS, "bootstrap-dialog", getInstance().evaluateTemplate(BOOTSTRAP_DLG_CSS)));
        viewCssBootstrap.add(new PlainFile(CSS, "bootstrap.min", getInstance().evaluateTemplate(BOOTSTRAP_MIN_CSS)));
        viewCssDynatable.add(new PlainFile(CSS, "jquery.dynatable", getInstance().evaluateTemplate(JQUERY_TBL_CSS)));
        viewCss.add(new PlainFile(CSS, "style", getInstance().evaluateTemplate(STYLE)));
        viewJs.add(new PlainFile(JS, "script", getInstance().evaluateTemplate(SCRIPT)));

        ClassLoader classLoader = SpringMVCImpl.class.getClassLoader();
        String imgName = "glyphicons-halflings-regular";
        String imgNameIcon1 = "glyphicons-halflings-white";
        String imgNameIcon2 = "glyphicons-halflings";

        viewImg.add(new BinaryFile(GIF, "ajax-loader", classLoader.getResourceAsStream(IMG_LOADING)));
        viewImgBootstrap.add(new BinaryFile(PNG, imgNameIcon1, classLoader.getResourceAsStream(GLYPHICONS_WHITE)));
        viewImgBootstrap.add(new BinaryFile(PNG, imgNameIcon2, classLoader.getResourceAsStream(GLYPHICONS_BLACK)));
        viewFntBootstrap.add(new BinaryFile(EOT, imgName, classLoader.getResourceAsStream(GLYPHICONS_EOT)));
        viewFntBootstrap.add(new BinaryFile(SVG, imgName, classLoader.getResourceAsStream(GLYPHICONS_SVG)));
        viewFntBootstrap.add(new BinaryFile(TTF, imgName, classLoader.getResourceAsStream(GLYPHICONS_TTF)));
        viewFntBootstrap.add(new BinaryFile(WOFF, imgName, classLoader.getResourceAsStream(GLYPHICONS_WOFF)));

        String moduleNameBase = "";
        String suffixModelSourceName = ((AbstractCommonBuilder) getBusinessLayer()).getSuffixSourceName();

        // creates the screens
        for (ApplicationBuilderScreenVo screenVo : getBuilder().getScreens()) {
            moduleNameBase = fromERFormatToMethodFormat(screenVo.getScreenName());

            params.put("classNameConfigModel", getStandardClassName(moduleNameBase + "Config", suffixModelSourceName));
            params.put("classNameFormModel", getStandardClassName(moduleNameBase, "FormModel"));
            params.put("classNameModel", getStandardClassName(moduleNameBase, suffixModelSourceName));

            params.put("fields", screenVo.getAppFields());
            params.put("pageName", screenVo.getPageName());

            StringBuilder templateContent = new StringBuilder(new FileProcessor().readPlainContentByLine(CATALOG));
            VelocityGeneratorCode.getInstance().mergeTemplateContent(templateContent,
                    getBuilder().getGeneratedFinalCode());
            String content = getInstance().evaluateRawString(templateContent.toString(), params);
            viewCatalog.add(new PlainFile(JSP, screenVo.getPageName(), content));
            viewJs.add(new PlainFile(JS, screenVo.getPageName(), getInstance().evaluateTemplate(DYNA_SCRIPT, params)));

        }
        pages.put("templates", viewTemplate);
        pages.put("catalogs", viewCatalog);

        pages.put("resources/dynatable/js", viewJsDynatable);
        pages.put("resources/dynatable/css", viewCssDynatable);
        pages.put("resources/bootstrap/js", viewJsBootstrap);
        pages.put("resources/bootstrap/css", viewCssBootstrap);
        pages.put("resources/bootstrap/img", viewImgBootstrap);
        pages.put("resources/bootstrap/fonts", viewFntBootstrap);
        pages.put("resources/bootstrap-dialog/js", viewJsBootstrapDlg);
        pages.put("resources/bootstrap-dialog/css", viewCssBootDlg);

        pages.put("resources/" + artifactId + "/css", viewCss); // sub directory
        pages.put("resources/" + artifactId + "/js", viewJs); // sub directory
        pages.put("resources/" + artifactId + "/img", viewImg); // sub directory

        return pages;
    }

    @Override
    public List<PlainFile> getWebInfFiles(String basePackageName) {
        List<PlainFile> list = new ArrayList<PlainFile>();
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("screens", getBuilder().getScreens());
        String content = getInstance().evaluateTemplate(TILES, params);

        list.add(new PlainFile(XML, "tiles", content));
        return list;
    }

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
            String classNameService = getStandardClassName(moduleName, "Service");

            params.put("screens", getBuilder().getScreens());
            params.put("classNameService", classNameService);
            list.add(new PlainFile(fileType, className, getInstance().evaluateTemplate(LOGIN_CONTROLLER, params)));
            list.add(new PlainFile(fileType, "DefaultMenuItem", getInstance().evaluateTemplate(DEFAULT_MENU_ITEM,
                    params)));
        } else {
            String suffixCommonPackageName = ((AbstractCommonBuilder) getCommonLayer()).getSuffixPackageName();
            String classNameFormModel = getStandardClassName(moduleName, "FormModel");

            params.put("suffixCommonPackageName", suffixCommonPackageName);
            params.put("basePackageName", basePackageName);

            params.put("classNameFormModel", classNameFormModel);
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
            // the following file is created in controller sources to support
            // multiple binding beans.
            list.add(new PlainFile(fileType, classNameFormModel, getInstance().evaluateTemplate(
                    DYNA_CATALOG_FORM_MODEL, params)));
        }
        return list;
    }

    @Override
    public List<PlainFile> getI18nMessages() {
        List<PlainFile> messages = new ArrayList<PlainFile>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("projectName", getBuilder().getProjectName());
        params.put("allFields", getBuilder().getScreens());

        PlainFile plainFileValidation = new PlainFile(PROPERTIES, "ValidationMessages", getInstance().evaluateTemplate(
                VALIDATION_MESSAGES));
        plainFileValidation.setIgnorePackageNameIfExists(true);
        PlainFile plainFileValidationEs = new PlainFile(PROPERTIES, "ValidationMessages_es", getInstance()
                .evaluateTemplate(VALIDATION_MESSAGES_ES));
        plainFileValidationEs.setIgnorePackageNameIfExists(true);

        messages.add(plainFileValidation);
        messages.add(plainFileValidationEs);
        messages.add(new PlainFile(PROPERTIES, "messages", getInstance().evaluateTemplate(MESSAGES, params)));
        messages.add(new PlainFile(PROPERTIES, "messages_es", getInstance().evaluateTemplate(MESSAGES_ES, params)));
        return messages;
    }

    @Override
    public PlainFile getViewConfig() {
        // not yet implemented
        return null;
    }

}
