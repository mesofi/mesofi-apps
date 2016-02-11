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
 * This implementation is the default implementation for spring framework. Any
 * class using spring framework as technology should extend this class instead
 * of {@link WebContextParam}.
 * 
 * @author Armando Rivas
 * @since Mar 12 2014.
 */
public class SpringWebSectionContent extends WebSectionContent {
    /**
     * Gets the spring web context parameter for the web.xml. This method
     * returns the custom configuration using spring framework.
     * 
     * @param packageName
     *            The package name.
     * @return The spring context param.
     */
    public WebContextParam getSpringWebContextParam(String packageName, String suffixSourceName) {
        StringBuilder sb = new StringBuilder();
        packageName = packageName.replaceAll("\\.", "/");
        String classpath = "\n            classpath*:";

        sb.append(classpath);
        sb.append(packageName);
        sb.append("/springframework/applicationContext.xml");
        sb.append(classpath);
        sb.append(packageName);
        sb.append("/springframework/**/*-config.xml");
        sb.append(classpath);
        sb.append(packageName);
        sb.append("/**/*" + suffixSourceName + ".xml\n");
        sb.append("        ");

        String paramValue = sb.toString();
        WebContextParam webContextParam = new WebContextParam("contextConfigLocation", paramValue);
        webContextParam.setComments("Change the applicationContext.xml spring location");
        return webContextParam;
    }
}
