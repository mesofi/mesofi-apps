/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core.util;

import static mx.com.mesofi.services.util.SimpleCommonActions.getStandardClassName;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.mesofi.framework.util.TemplateManager;
import mx.com.mesofi.plugins.project.core.AbstractCommonBuilder;
import mx.com.mesofi.plugins.project.core.files.FileType;
import mx.com.mesofi.plugins.project.core.files.PlainFile;
import mx.com.mesofi.plugins.project.core.maven.PomDependency;
import mx.com.mesofi.plugins.project.core.maven.PomRepository;
import mx.com.mesofi.plugins.project.core.maven.PomSectionContent;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderVo;
import mx.com.mesofi.plugins.project.core.technologies.Hibernate;
import mx.com.mesofi.plugins.project.core.technologies.PrimeFaces;
import mx.com.mesofi.plugins.project.core.technologies.SpringCore;
import mx.com.mesofi.plugins.project.core.technologies.SpringMVC;
import mx.com.mesofi.plugins.project.core.web.SpringWebSectionContent;
import mx.com.mesofi.plugins.project.core.web.WebContextParam;
import mx.com.mesofi.plugins.project.core.web.WebListener;
import mx.com.mesofi.plugins.project.core.web.WebSectionContent;
import mx.com.mesofi.services.properties.PropertiesLoader;

/**
 * Utility that complements and add prebuilt funcionality to the default
 * implementation for Technologies.
 * 
 * @author Armando Rivas
 * @since Feb 14 2015.
 * 
 */
public class TechResolver {
    /**
     * The application builder.
     */
    private ApplicationBuilderVo builder;
    /**
     * The pom section content.
     */
    private PomSectionContent pomSectionContent;
    /**
     * Unique instance for this class.
     */
    private static TechResolver techResolver = new TechResolver();

    /**
     * Private constructor for this class.
     */
    private TechResolver() {
        // private constructor.
        String configLocation = "mx/com/mesofi/web/admin/techplugin/templates/preferences.properties";
        PropertiesLoader.getInstance().setResource(configLocation);
    }

    /**
     * Gets the only instance for this class.
     * 
     * @return The singleton.
     */
    public static TechResolver getInstance() {
        return techResolver;

    }

    /**
     * Sets the application builder.
     * 
     * @param applicationBuilderVo
     *            The application builder.
     */
    public void setApplicationBuilder(ApplicationBuilderVo applicationBuilderVo) {
        this.builder = applicationBuilderVo;
    }

    /**
     * Set the pom section content
     * 
     * @param pomSectionContent
     *            The pom section content.
     */
    public void setPomSectionContent(PomSectionContent pomSectionContent) {
        this.pomSectionContent = pomSectionContent;
    }

    /**
     * The application builder.
     * 
     * @return the builder
     */
    public ApplicationBuilderVo getBuilder() {
        return builder;
    }

    /**
     * This method 'adds' (if found) additional dependencies to those passed as
     * parameters of this method. If the conditions are not applicable, then the
     * original dependencies are not altered. custom technology.
     * 
     * @param userDependencies
     *            Existing dependencies that may be altered to add more of them.
     */
    public List<PomDependency> addDependenciesByTechnology(List<PomDependency> userDependencies) {
        List<PomDependency> dependencies = new ArrayList<PomDependency>();
        Class<?> viewLayerClass = null;
        Class<?> persistenceLayerClass = null;

        // className for default PrimeFaces technology
        viewLayerClass = TechManager.getInstance().getViewLayer().getClass();
        // className for default Hibernate technology
        persistenceLayerClass = TechManager.getInstance().getPersistenceLayer().getClass();

        String springGroup = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.spring.groupid");
        String springArt = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.spring.artifactid");
        String springVer = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.spring.version");
        String springCom = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.spring.comment");

        String aopGroup = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.spring-aop.groupid");
        String aopArt = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.spring-aop.artifactid");
        String aopVer = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.spring-aop.version");
        String aopCom = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.spring-aop.comment");

        String aopJrtGroup = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.aspectj-rt.groupid");
        String aopJrtArt = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.aspectj-rt.artifactid");
        String aopJrtVer = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.aspectj-rt.version");
        String aopJrtCom = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.aspectj-rt.comment");

        String wGroup = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.aspectj-weaver.groupid");
        String weArt = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.aspectj-weaver.artifactid");
        String weaveVer = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.aspectj-weaver.version");
        String weaveCom = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.aspectj-weaver.comment");

        String vltrGroup = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.validator.groupid");
        String vltrArt = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.validator.artifactid");
        String vltrVer = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.validator.version");
        String vltrCom = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.validator.comment");

        // spring core, adds some spring dependencies.
        PomDependency springCore = new PomDependency(springGroup, springArt, springVer);
        springCore.addComment(springCom);
        dependencies.add(springCore);

        // spring aop, adds some spring aop dependencies.
        PomDependency springAopCore = new PomDependency(aopGroup, aopArt, aopVer);
        springAopCore.addComment(aopCom);
        dependencies.add(springAopCore);

        // aspect weaver dependencies.
        PomDependency aspectJrt = new PomDependency(aopJrtGroup, aopJrtArt, aopJrtVer);
        aspectJrt.addComment(aopJrtCom);
        dependencies.add(aspectJrt);

        // spring aop, adds some spring aop dependencies.
        PomDependency weaver = new PomDependency(wGroup, weArt, weaveVer);
        weaver.addComment(weaveCom);
        dependencies.add(weaver);

        // creates dependencies for the validations.
        PomDependency validator = new PomDependency(vltrGroup, vltrArt, vltrVer);
        validator.addComment(vltrCom);
        dependencies.add(validator);

        if (PrimeFaces.class.isAssignableFrom(viewLayerClass)) {

            String primeGroup = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.prime.groupid");
            String primeArt = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.prime.artifactid");
            String primeVer = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.prime.version");
            String primeCom = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.prime.comment");

            String themeGroup = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.prime-theme.groupid");
            String themeArt = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.prime-theme.artifactid");
            String themeVer = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.prime-theme.version");
            String themeCom = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.prime-theme.comment");

            String jsfGroup = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.jsf-api.groupid");
            String jsfArt = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.jsf-api.artifactid");
            String jsfVer = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.jsf-api.version");
            String jsfCom = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.jsf-api.comment");

            String webGroup = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.spring-web.groupid");
            String webArt = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.spring-web.artifactid");
            String webVer = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.spring-web.version");
            String webCom = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.spring-web.comment");

            // creates dependencies for PrimeFaces technology.
            // primefaces dependency
            PomDependency primeFaces = new PomDependency(primeGroup, primeArt, primeVer);
            primeFaces.addComment(primeCom);
            // primefaces themes
            PomDependency primeTheme = new PomDependency(themeGroup, themeArt, themeVer);
            primeTheme.addComment(themeCom);
            // JSF Dependency
            PomDependency jsfApi = new PomDependency(jsfGroup, jsfArt, jsfVer);
            jsfApi.addComment(jsfCom);

            // add the database dependency.
            dependencies.add(primeFaces);
            dependencies.add(jsfApi);
            dependencies.add(primeTheme);

            // spring web, adds some spring web dependencies.
            PomDependency springWebCore = new PomDependency(webGroup, webArt, webVer);
            springWebCore.addComment(webCom);
            dependencies.add(springWebCore);

        }
        if (SpringMVC.class.isAssignableFrom(viewLayerClass)) {

            String mvcGroup = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.spring-webmvc.groupid");
            String mvcArt = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.spring-webmvc.artifactid");
            String mvcVer = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.spring-webmvc.version");
            String mvcCom = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.spring-webmvc.comment");

            String tilesGroup = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.tiles.groupid");
            String tilesArt = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.tiles.artifactid");
            String tilesVer = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.tiles.version");
            String tilesCom = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.tiles.comment");

            // creates dependencies for SpringMVC technology.
            // spring mvc dependency
            PomDependency springMVC = new PomDependency(mvcGroup, mvcArt, mvcVer);
            springMVC.addComment(mvcCom);
            dependencies.add(springMVC);

            // creates dependencies for tiles technology.
            PomDependency tiles = new PomDependency(tilesGroup, tilesArt, tilesVer);
            tiles.addComment(tilesCom);
            dependencies.add(tiles);

        }

        if (Hibernate.class.isAssignableFrom(persistenceLayerClass)) {

            String hGroup = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.hibernate-core.groupid");
            String hArt = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.hibernate-core.artifactid");
            String hVer = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.hibernate-core.version");
            String hCom = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.hibernate-core.comment");

            String ormGroup = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.spring-orm.groupid");
            String ormArti = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.spring-orm.artifactid");
            String ormVersion = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.spring-orm.version");
            String ormComment = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.spring-orm.comment");

            // creates dependencies for Hibernate technology.
            // hibernate core dependency
            PomDependency hibernateCore = new PomDependency(hGroup, hArt, hVer);
            hibernateCore.addComment(hCom);
            dependencies.add(hibernateCore);

            // spring orm, adds some spring orm dependencies.
            PomDependency springOrm = new PomDependency(ormGroup, ormArti, ormVersion);
            springOrm.addComment(ormComment);
            dependencies.add(springOrm);
        }

        if (!dependencies.isEmpty()) {
            // add these new dependencies to the existing ones.
            if (userDependencies == null) {
                userDependencies = dependencies;
            } else {
                userDependencies.addAll(dependencies);
            }
        }
        return userDependencies;
    }

    /**
     * Add commons sources to the exisring technologies.
     * 
     * @param commonSources
     *            The commons sources.
     * @param packageName
     *            The package name.
     * @return The resulting common sources.
     */
    public List<PlainFile> addCommonSourcesByTechnology(List<PlainFile> commonSources, String packageName) {
        List<PlainFile> sources = new ArrayList<PlainFile>();
        Class<?> viewLayerClass = null;

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("packageName", packageName);
        params.put("date", new Date());
        params.put("year", Calendar.getInstance().get(Calendar.YEAR));
        params.put("projectName", this.getBuilder().getProjectName());

        FileType fileType = FileType.JAVA;

        String appContextListener = "/mx/com/mesofi/web/admin/techplugin/templates/primefaces/java/application-context-listener.vm";
        String springContext = "/mx/com/mesofi/web/admin/techplugin/templates/spring/java/spring-context.vm";
        String appLoggerAspect = "/mx/com/mesofi/web/admin/techplugin/templates/spring/java/application-logger-aspect.vm";
        String appLoggerConfig = "/mx/com/mesofi/web/admin/techplugin/templates/spring/java/application-logger-config.vm";

        // spring core, adds some spring features.
        sources.add(new PlainFile(fileType, "ApplicationContextListener", TemplateManager.getInstance()
                .evaluateTemplate(appContextListener, params)));
        sources.add(new PlainFile(fileType, "SpringContext", TemplateManager.getInstance().evaluateTemplate(
                springContext, params)));
        sources.add(new PlainFile(fileType, "ApplicationLoggerAspect", TemplateManager.getInstance().evaluateTemplate(
                appLoggerAspect, params)));
        sources.add(new PlainFile(fileType, "ApplicationLoggerConfig", TemplateManager.getInstance().evaluateTemplate(
                appLoggerConfig, params)));

        // className for default PrimeFaces technology
        viewLayerClass = TechManager.getInstance().getViewLayer().getClass();
        if (PrimeFaces.class.isAssignableFrom(viewLayerClass)) {
            String abstractCrtler = "/mx/com/mesofi/web/admin/techplugin/templates/primefaces/java/common-controller.vm";
            sources.add(new PlainFile(fileType, "AbstractController", TemplateManager.getInstance().evaluateTemplate(
                    abstractCrtler, params)));
        }
        // className for default SpringMVC technology
        if (SpringMVC.class.isAssignableFrom(viewLayerClass)) {
            String initializerMvc = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/java/spring-web-app-initializer.vm";
            String appContextConfig = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/java/application-context-config.vm";
            String webAppContextConfig = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/java/web-application-context-config.vm";
            String abstractCrtler = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/java/common-controller.vm";
            String errorResponse = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/java/error-response.vm";
            String failResponse = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/java/fail-response.vm";
            String successResponse = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/java/success-response.vm";
            String response = "/mx/com/mesofi/web/admin/techplugin/templates/springmvc/java/response.vm";

            List<String> locations = getSpringLocations(packageName);
            params.put("locations", locations);

            sources.add(new PlainFile(fileType, "SpringWebAppInitializer", TemplateManager.getInstance()
                    .evaluateTemplate(initializerMvc, params)));
            sources.add(new PlainFile(fileType, "ApplicationContextConfig", TemplateManager.getInstance()
                    .evaluateTemplate(appContextConfig, params)));
            sources.add(new PlainFile(fileType, "WebApplicationContextConfig", TemplateManager.getInstance()
                    .evaluateTemplate(webAppContextConfig, params)));
            sources.add(new PlainFile(fileType, "AbstractController", TemplateManager.getInstance().evaluateTemplate(
                    abstractCrtler, params)));
            sources.add(new PlainFile(fileType, "ErrorResponse", TemplateManager.getInstance().evaluateTemplate(
                    errorResponse, params)));
            sources.add(new PlainFile(fileType, "FailResponse", TemplateManager.getInstance().evaluateTemplate(
                    failResponse, params)));
            sources.add(new PlainFile(fileType, "SuccessResponse", TemplateManager.getInstance().evaluateTemplate(
                    successResponse, params)));
            sources.add(new PlainFile(fileType, "Response", TemplateManager.getInstance().evaluateTemplate(response,
                    params)));
        }

        if (!sources.isEmpty()) {
            // add these new sources to the existing ones.
            if (commonSources == null) {
                commonSources = sources;
            } else {
                commonSources.addAll(sources);
            }
        }
        return commonSources;
    }

    /**
     * Retrieves all the application spring locations.
     * 
     * @param packageName
     *            The package name.
     * @return List of locations.
     */
    private List<String> getSpringLocations(String packageName) {
        List<String> list = new ArrayList<String>();
        packageName = packageName.replaceAll("\\.", "/");
        String classpath = "classpath*:";

        StringBuilder sb = null;

        sb = new StringBuilder();
        sb.append(classpath);
        sb.append(packageName);
        sb.append("/springframework/applicationContext.xml");
        list.add(sb.toString());

        sb = new StringBuilder();
        sb.append(classpath);
        sb.append(packageName);
        sb.append("/springframework/**/*-config.xml");
        list.add(sb.toString());

        return list;
    }

    /**
     * Adds daos sources to the existing technologies.
     * 
     * @param daos
     *            Daos to be added.
     * @param The
     *            module name.
     * @param isDynamicModule
     *            This module is dynamic or not.
     * @return The resulting daos.
     */
    public List<PlainFile> addDaoSourcesByTechnology(List<PlainFile> daos, String basePackageName, String moduleName,
            boolean isDynamicModule) {
        List<PlainFile> sources = new ArrayList<PlainFile>();
        Class<?> businessLayerClass = null;
        AbstractCommonBuilder builderPersistence = null;
        AbstractCommonBuilder builderModel = null;

        // className for default PrimeFaces technology
        businessLayerClass = TechManager.getInstance().getBusinessLayer().getClass();

        builderPersistence = (AbstractCommonBuilder) TechManager.getInstance().getPersistenceLayer();
        builderModel = (AbstractCommonBuilder) TechManager.getInstance().getBusinessLayer();

        String packageName = builderPersistence.createFullPackageName(basePackageName, moduleName, isDynamicModule,
                false);

        if (SpringCore.class.isAssignableFrom(businessLayerClass)) {
            if (isDynamicModule) {
                Map<String, Object> params = new HashMap<String, Object>();
                String dynaDao = "/mx/com/mesofi/web/admin/techplugin/templates/spring/java/dyna-catalog-dao.vm";
                FileType fileType = FileType.JAVA;
                String className = getStandardClassName(moduleName, builderPersistence.getSuffixSourceName());

                params.put("packageName", packageName);
                params.put("suffixPackageName", builderPersistence.getSuffixPackageName());
                params.put("className", className);

                params.put("date", new Date());
                params.put("year", Calendar.getInstance().get(Calendar.YEAR));

                params.put("suffixModelPackageName", builderModel.getSuffixPackageName());
                params.put("classNameModel", getStandardClassName(moduleName, builderModel.getSuffixSourceName()));
                params.put("classNameSearch", getStandardClassName(moduleName, "Search"));

                params.put("templateName", dynaDao);

                sources.add(new PlainFile(fileType, className, TemplateManager.getInstance().evaluateTemplate(dynaDao,
                        params)));
            }
        }
        if (!sources.isEmpty()) {
            // add these new sources to the existing ones.
            if (daos == null) {
                daos = sources;
            } else {
                daos.addAll(sources);
            }
        }
        return daos;
    }

    /**
     * Adds repositories by technologies.
     * 
     * @param repositories
     *            The repositories.
     * @return Repositories by technology.
     */
    public List<PomRepository> addRepositoriesByTechnology(List<PomRepository> repositories) {
        List<PomRepository> newRepositories = new ArrayList<PomRepository>();
        Class<?> viewLayerClass = null;

        // className for default PrimeFaces technology
        viewLayerClass = TechManager.getInstance().getViewLayer().getClass();
        if (PrimeFaces.class.isAssignableFrom(viewLayerClass)) {

            String repoName = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.prime.repo-name");
            String repoId = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.prime.repo-id");
            String repoUrl = PropertiesLoader.getInstance().getProperty("mesofi.config.plugin.prime.repo-url");

            newRepositories.add(new PomRepository(repoName, repoId, repoUrl));
        }
        if (!newRepositories.isEmpty()) {
            // add these new sources to the existing ones.
            if (repositories == null) {
                repositories = newRepositories;
            } else {
                repositories.addAll(newRepositories);
            }
        }
        return repositories;
    }

    /**
     * Adds listeners by technologies.
     * 
     * @param listeners
     *            The Listentenes.
     * @return Listeners by technology.
     */
    public List<WebListener> addListenersByTechnology(List<WebListener> listeners) {
        List<WebListener> newLlisteners = new ArrayList<WebListener>();
        Class<?> viewLayerClass = null;

        // className for default PrimeFaces technology
        viewLayerClass = TechManager.getInstance().getViewLayer().getClass();
        if (PrimeFaces.class.isAssignableFrom(viewLayerClass)) {
            // spring listener
            WebListener springContextListener = new WebListener("org.springframework.web.context.ContextLoaderListener");
            springContextListener.setComments("Spring Listener, this one is required when using Spring Framework");
            newLlisteners.add(springContextListener);
        }

        // application startup listener
        WebListener appListener = new WebListener(pomSectionContent.getGroupId() + "."
                + pomSectionContent.getArtifactId() + ".common.ApplicationContextListener");
        appListener.setComments("Web application listener, this one gets activated when app starts or shutdowns");
        newLlisteners.add(appListener);

        if (!newLlisteners.isEmpty()) {
            // add these new sources to the existing ones.
            if (listeners == null) {
                listeners = newLlisteners;
            } else {
                listeners.addAll(newLlisteners);
            }
        }
        return listeners;
    }

    /**
     * This method adds context parameters into the web.xml when the PrimeFaces
     * is being used.
     * 
     * @param contextParams
     *            The list of context parameters.
     * @param webSectionContent
     *            WebSection.
     * @param fullPackage
     *            Full package name.
     * @param suffixSourceName
     *            The suffix source name.
     * @return List of Web context params.
     */
    public List<WebContextParam> addContextParamByTechnology(List<WebContextParam> contextParams,
            WebSectionContent webSectionContent, String fullPackage, String suffixSourceName) {
        List<WebContextParam> newContextParams = new ArrayList<WebContextParam>();
        if (webSectionContent instanceof SpringWebSectionContent) {
            WebContextParam springContextParam = null;
            springContextParam = ((SpringWebSectionContent) webSectionContent).getSpringWebContextParam(fullPackage,
                    suffixSourceName);
            newContextParams.add(springContextParam);
        } else {
            Class<?> viewLayerClass = null;

            // className for default PrimeFaces technology
            viewLayerClass = TechManager.getInstance().getViewLayer().getClass();
            if (PrimeFaces.class.isAssignableFrom(viewLayerClass)) {

                // uses the default implementation for Spring framework due to
                // PrimeFaces is being used.
                newContextParams = addContextParamByTechnology(newContextParams, new SpringWebSectionContent(),
                        fullPackage, suffixSourceName);
            }
        }
        if (!newContextParams.isEmpty()) {
            // add these new sources to the existing ones.
            if (contextParams == null) {
                contextParams = newContextParams;
            } else {
                contextParams.addAll(newContextParams);
            }
        }
        return contextParams;
    }

    /**
     * Gets the welcome file based on the technology selected.
     * 
     * @return The welcome file.
     */
    public String getWelcomeFileByTechnology() {
        Class<?> viewLayerClass = null;

        // className for default PrimeFaces technology
        viewLayerClass = TechManager.getInstance().getViewLayer().getClass();
        if (PrimeFaces.class.isAssignableFrom(viewLayerClass)) {
            return "index.jsf";
        } else {
            return "index.jsp";
        }
    }
}
