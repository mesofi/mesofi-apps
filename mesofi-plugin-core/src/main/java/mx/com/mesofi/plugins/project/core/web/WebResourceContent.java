/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core.web;

import static mx.com.mesofi.services.util.SimpleValidator.isNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.mesofi.framework.util.TemplateManager;
import mx.com.mesofi.plugins.project.core.content.DefaultResourceFileContent;
import mx.com.mesofi.plugins.project.core.files.FileType;
import mx.com.mesofi.plugins.project.core.util.TechResolver;

/**
 * This class is the direct implementation for the web.xml.
 * 
 * @author Armando Rivas
 * @since Mar 07 2014.
 * 
 */
public class WebResourceContent extends DefaultResourceFileContent {
    /**
     * Location for the web.xml
     */
    private static final String WEB_XML = "/mx/com/mesofi/web/admin/techplugin/templates/common/resources/web.vm";
    /**
     * Section of the web.xml.
     */
    private WebSectionContent webSectionContent;
    /**
     * The display name of the web.xml.
     */
    private String displayName;
    /**
     * The package name for this project.
     */
    private String packageName;
    /**
     * The artifactId for this project.
     */
    private String artifactId;

    /**
     * Creates an object using the default sections in the pom.xml
     * 
     * @param pomSectionContent
     *            Section for the pom.xml.
     */
    public WebResourceContent(WebSectionContent webSectionContent) {
        isNull(webSectionContent, "Web section content must not be null, you need to create "
                + "a class that extends from [" + WebSectionContent.class + "] or any existing subclass");
        this.webSectionContent = webSectionContent;
    }

    @Override
    public String getFileContent(String suffixSourceName) {
        Map<String, Object> params = new HashMap<String, Object>();
        // sets some main features of the web.xml
        params.put("displayName", displayName);
        List<WebListener> listeners = webSectionContent.getAllListeners();
        isNull(listeners, "Cannot create a web.xml because the listeners specified are null...");

        // process the listener section.
        params.put("allListeners", listeners);

        List<WebContextParam> contextParams = new ArrayList<WebContextParam>();
        String fullPackage = packageName + "." + artifactId;
        contextParams = TechResolver.getInstance().addContextParamByTechnology(contextParams, webSectionContent,
                fullPackage, suffixSourceName);

        List<WebContextParam> allContextParams = webSectionContent.getAllWebContextParams();
        isNull(allContextParams, "Cannot create a web.xml because the context parameters specified are null...");
        contextParams.addAll(allContextParams);

        // process the web context section.
        params.put("allWebContext", contextParams);

        List<WebServlet> allServlets = webSectionContent.getAllServlets();
        isNull(allServlets, "Cannot create a web.xml because the servlets specified are null...");
        // process the web context section.
        params.put("allServlets", allServlets);

        String welcomeFile = webSectionContent.getWelcomeFile();
        params.put("welcomeFile", welcomeFile);

        return TemplateManager.getInstance().evaluateTemplate(WEB_XML, params);
    }

    @Override
    public String getFileName() {
        return "web";
    }

    @Override
    public FileType getFileType() {
        return FileType.XML;
    }

    /**
     * @param displayName
     *            the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @param packageName
     *            the packageName to set
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * @param artifactId
     *            the artifactId to set
     */
    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

}
