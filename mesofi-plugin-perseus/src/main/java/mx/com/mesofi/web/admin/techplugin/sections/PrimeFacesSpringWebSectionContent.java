/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.techplugin.sections;

import java.util.ArrayList;
import java.util.List;

import mx.com.mesofi.plugins.project.core.web.SpringWebSectionContent;
import mx.com.mesofi.plugins.project.core.web.WebContextParam;
import mx.com.mesofi.plugins.project.core.web.WebListener;
import mx.com.mesofi.plugins.project.core.web.WebServlet;

public class PrimeFacesSpringWebSectionContent extends SpringWebSectionContent {
    /**
     * The package name to create a section in configuration file.
     */
    private String startUpListener;

    /**
     * Creates a section web using the startup listener as a require element.
     * 
     * @param startUpListener
     */
    public PrimeFacesSpringWebSectionContent(String startUpListener) {
        this.startUpListener = startUpListener;
    }

    public List<WebListener> getAllListeners() {
        List<WebListener> listeners = new ArrayList<WebListener>();
        // spring listener
        WebListener springContextListener = new WebListener("org.springframework.web.context.ContextLoaderListener");
        springContextListener.setComments("Spring Listener, this one is required when using Spring Framework");
        // application startup listener
        WebListener appListener = new WebListener(startUpListener);
        appListener.setComments("Web application listener, this one is activated when app starts or shutdowns");

        listeners.add(springContextListener);
        listeners.add(appListener);

        return listeners;
    }

    @Override
    public List<WebContextParam> getAllWebContextParams() {
        List<WebContextParam> contextParams = new ArrayList<WebContextParam>();

        WebContextParam primeThemeContextParam = new WebContextParam("primefaces.THEME", "glass-x");
        primeThemeContextParam.setComments("Theme for primefaces, overrides the default");
        WebContextParam primeStageContextParam = new WebContextParam("javax.faces.PROJECT_STAGE", "Development");
        primeStageContextParam.setComments("Project Stage Level");
        WebContextParam primeDateTimeContextParam = new WebContextParam(
                "javax.faces.DATETIMECONVERTER_DEFAULT_TIMEZONE_IS_SYSTEM_TIMEZONE", "true");
        primeDateTimeContextParam.setComments("Configuration for date time");

        contextParams.add(primeThemeContextParam);
        contextParams.add(primeStageContextParam);
        contextParams.add(primeDateTimeContextParam);

        return contextParams;
    }

    @Override
    public List<WebServlet> getAllServlets() {
        List<WebServlet> webServlets = new ArrayList<WebServlet>();
        // WebServlet webServlet = new WebServlet("Faces Servlet",
        // "javax.faces.webapp.FacesServlet", "/faces/*",
        // "*.xhtml", "*.jsf", "*.iface", "*.faces", "*.jspx", "/icefaces/*");
        //
        // webServlet.setLoadOnStartUp(1);
        // webServlets.add(webServlet);
        return webServlets;
    }

    /**
     * {@inheritDoc}
     */
    public String getWelcomeFile() {
        return "index.jsf";
    }
}
