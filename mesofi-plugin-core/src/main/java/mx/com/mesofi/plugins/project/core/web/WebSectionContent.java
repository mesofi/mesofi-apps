/*
 * COPYRIGHT. Mesofi 2015. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core.web;

import java.util.ArrayList;
import java.util.List;

import mx.com.mesofi.plugins.project.core.util.TechResolver;

/**
 * Default implementation for the web.xml.
 * 
 * @author Armando Rivas
 * @since Feb 16 2015.
 */
public abstract class WebSectionContent {
    /**
     * Returns a list of listener, if the user wants to specify another
     * different, must override this method.
     * 
     * @return List of listeners.
     */
    public List<WebListener> getAllListeners() {
        // by default an empty listener is specified.
        List<WebListener> listeners = new ArrayList<WebListener>();
        listeners = TechResolver.getInstance().addListenersByTechnology(listeners);
        return listeners;
    }

    public List<WebContextParam> getAllWebContextParams() {
        List<WebContextParam> contextParams = new ArrayList<WebContextParam>();
        return contextParams;
    }

    public List<WebServlet> getAllServlets() {
        List<WebServlet> webServlets = new ArrayList<WebServlet>();
        return webServlets;
    }

    /**
     * Gets the welcome file name for the initial file. This name may be
     * overridden.
     * 
     * @return The welcome file name.
     */
    public String getWelcomeFile() {
        return TechResolver.getInstance().getWelcomeFileByTechnology();
    }
}
