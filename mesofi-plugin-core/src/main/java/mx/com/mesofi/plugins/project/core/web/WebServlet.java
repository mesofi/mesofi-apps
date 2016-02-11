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

/**
 * This class represents a Servlet in the web.xml.
 * 
 * @author Armando Rivas
 * @since Mar 12 2014.
 */
public class WebServlet {

    private String servletName;
    private String servletClass;
    private String[] urlPattern;
    private Integer loadOnStartUp;

    public WebServlet(String servletName, String servletClass, String urlPattern, String... moreUrlPatterns) {
        this.servletName = servletName;
        this.servletClass = servletClass;
        int total = 1;
        if (moreUrlPatterns != null) {
            total += moreUrlPatterns.length;
        }
        this.urlPattern = new String[total];
        this.urlPattern[0] = urlPattern;
        if (moreUrlPatterns != null) {
            for (int i = 1; i <= moreUrlPatterns.length; i++) {
                this.urlPattern[i] = moreUrlPatterns[i - 1];
            }
        }
    }

    /**
     * @return the servletName
     */
    public String getServletName() {
        return servletName;
    }

    /**
     * @param servletName
     *            the servletName to set
     */
    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    /**
     * @return the servletClass
     */
    public String getServletClass() {
        return servletClass;
    }

    /**
     * @param servletClass
     *            the servletClass to set
     */
    public void setServletClass(String servletClass) {
        this.servletClass = servletClass;
    }

    /**
     * @return the urlPattern
     */
    public String[] getUrlPattern() {
        return urlPattern;
    }

    /**
     * @param urlPattern
     *            the urlPattern to set
     */
    public void setUrlPattern(String[] urlPattern) {
        this.urlPattern = urlPattern;
    }

    /**
     * @return the loadOnStartUp
     */
    public Integer getLoadOnStartUp() {
        return loadOnStartUp;
    }

    /**
     * @param loadOnStartUp
     *            the loadOnStartUp to set
     */
    public void setLoadOnStartUp(Integer loadOnStartUp) {
        this.loadOnStartUp = loadOnStartUp;
    }

}
