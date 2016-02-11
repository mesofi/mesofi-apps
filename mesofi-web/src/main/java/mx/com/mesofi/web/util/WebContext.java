/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This singleton contains some important information related to the current
 * request. This class is available as long as the request lasts.
 * 
 * @author Armando Rivas.
 * @since Nov 21 2013
 */
public class WebContext {

    /**
     * Creates a new instance when this class is first loaded.
     */
    private static WebContext webContext = new WebContext();

    /**
     * Current request.
     */
    private HttpServletRequest servletRequest;

    /**
     * Creates an instance so that this is the only class than can create it.
     */
    private WebContext() {
    }

    /**
     * Gets a single instance of this class.
     * 
     * @return Single instance.
     */
    public static WebContext getInstance() {
        return webContext;
    }

    /**
     * Get the actual session.
     * 
     * @param createSession
     *            If this flag is true, gets the current session, if the session
     *            does not exist, then creates one. If this flag is false, then
     *            get the current session.
     * @return HTTP session.
     */
    public HttpSession getHttpSession(boolean createSession) {
        return servletRequest.getSession(createSession);
    }

    /**
     * Gets the current session.
     * 
     * @return Current session.
     */
    public HttpSession getHttpSession() {
        return servletRequest.getSession();
    }

    /**
     * Gets an specific attribute value from the session.
     * 
     * @param attibuteName
     *            Attribute name.
     * @return Value of the attribute from the session.
     */
    public Object getSession(String attibuteName) {
        return servletRequest.getSession(true).getAttribute(attibuteName);
    }

    /**
     * Set a value in session.
     * 
     * @param attName
     *            Attribute name.
     * @param value
     *            Value for a particular attribute.
     */
    public void setSession(String attName, Object value) {
        servletRequest.getSession(true).setAttribute(attName, value);
    }

    /**
     * Gets the request.
     * 
     * @return The current request.
     */
    public HttpServletRequest getRequest() {
        return servletRequest;
    }

    /**
     * Gets an specific attribute value from the request.
     * 
     * @param attibuteName
     *            Attribute name.
     * @return Value of the attribute from the request.
     */
    public Object getRequest(String attibuteName) {
        return servletRequest.getAttribute(attibuteName);
    }

    /**
     * Sets a value in the request.
     * 
     * @param attName
     *            Attribute name.
     * @param value
     *            Value of the attribute.
     */
    public void setRequest(String attName, Object value) {
        servletRequest.setAttribute(attName, value);
    }

    /**
     * Gets an specific attribute value from the servlet context.
     * 
     * @param attibuteName
     *            Attribute name.
     * @return Value of the attribute from the servlet context.
     */
    public Object getApplication(String attibuteName) {
        return servletRequest.getServletContext().getAttribute(attibuteName);
    }

    /**
     * Gets the servlet context from the application.
     * 
     * @return Servlet context.
     */
    public ServletContext getApplication() {
        return servletRequest.getServletContext();
    }

    /**
     * Sets an specific attribute value from the servlet context.
     * 
     * @param attName
     *            Attribute name.
     * @param value
     *            Attribute value.
     */
    public void setApplication(String attName, Object value) {
        servletRequest.getServletContext().setAttribute(attName, value);
    }

    /**
     * Set the request.
     * 
     * @param req
     *            HTTP Servlet request.
     */
    public void setHttpRequest(HttpServletRequest req) {
        this.servletRequest = req;
    }

}
