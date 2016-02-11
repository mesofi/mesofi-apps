/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.builder.vo;

import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderPreferencesVo;

/**
 * Vo that contains information to create the application.
 * 
 * @author Armando Rivas
 * @since April 27 2015.
 */
public class BuilderAppCompoundVo {
    /**
     * The conn id.
     */
    private Integer connId;
    /**
     * The project name.
     */
    private String projectName;
    /**
     * Contains data for the plugin.
     */
    private BuilderPluginVo builderPluginVo;
    /**
     * Preferences.
     */
    private ApplicationBuilderPreferencesVo applicationBuilderPreferencesVo;

    /**
     * @return the connId
     */
    public Integer getConnId() {
        return connId;
    }

    /**
     * @param connId
     *            the connId to set
     */
    public void setConnId(Integer connId) {
        this.connId = connId;
    }

    /**
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName
     *            the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return the builderPluginVo
     */
    public BuilderPluginVo getBuilderPluginVo() {
        return builderPluginVo;
    }

    /**
     * @param builderPluginVo
     *            the builderPluginVo to set
     */
    public void setBuilderPluginVo(BuilderPluginVo builderPluginVo) {
        this.builderPluginVo = builderPluginVo;
    }

    /**
     * @return the applicationBuilderPreferencesVo
     */
    public ApplicationBuilderPreferencesVo getApplicationBuilderPreferencesVo() {
        return applicationBuilderPreferencesVo;
    }

    /**
     * @param applicationBuilderPreferencesVo
     *            the applicationBuilderPreferencesVo to set
     */
    public void setApplicationBuilderPreferencesVo(ApplicationBuilderPreferencesVo applicationBuilderPreferencesVo) {
        this.applicationBuilderPreferencesVo = applicationBuilderPreferencesVo;
    }

}
