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

import java.util.HashMap;
import java.util.Map;

import mx.com.mesofi.framework.util.TemplateManager;
import mx.com.mesofi.plugins.project.core.content.DefaultResourceFileContent;
import mx.com.mesofi.plugins.project.core.files.FileType;

/**
 * This class is the direct implementation for the log4j.xml.
 * 
 * @author Armando Rivas
 * @since Mar 07 2014.
 * 
 */
public class LoggerResourceContent extends DefaultResourceFileContent {
    /**
     * Location for the log4j.xml
     */
    private static final String LOGGER_XML = "/mx/com/mesofi/web/admin/techplugin/templates/common/resources/log4j.vm";

    /**
     * The display name of the web.xml.
     */
    private String projectName;
    /**
     * The package name for this project.
     */
    private String packageName;

    @Override
    public String getFileContent(String suffixSourceName) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("packageName", packageName);
        params.put("projectName", projectName);
        return TemplateManager.getInstance().evaluateTemplate(LOGGER_XML, params);
    }

    @Override
    public String getFileName() {
        return "log4j";
    }

    @Override
    public FileType getFileType() {
        return FileType.PROPERTIES;
    }

    /**
     * @param projectName
     *            the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @param packageName
     *            the packageName to set
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

}
