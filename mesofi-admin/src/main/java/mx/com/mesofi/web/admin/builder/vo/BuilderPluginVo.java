/*
 * COPYRIGHT. Mesofi 2015. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.builder.vo;

import mx.com.mesofi.framework.util.EntityVo;

/**
 * This class contains information regarding plugin configuration.
 * 
 * @author Armando Rivas
 * @since April 26 2015.
 */
public class BuilderPluginVo extends EntityVo {
    /**
     * The implemented class.
     */
    private String classImplementation;
    /**
     * Titlte for this plugin.
     */
    private String title;
    /**
     * Descripton of this plugin.
     */
    private String description;
    /**
     * If this plugin is selected or not.
     */
    private boolean selected;
    /**
     * The groupId.
     */
    private String groupId;
    /**
     * The artifactId.
     */
    private String artifactId;
    /**
     * The version.
     */
    private String version;

    /**
     * @return the classImplementation
     */
    public String getClassImplementation() {
        return classImplementation;
    }

    /**
     * @param classImplementation
     *            the classImplementation to set
     */
    public void setClassImplementation(String classImplementation) {
        this.classImplementation = classImplementation;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected
     *            the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     *            the groupId to set
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the artifactId
     */
    public String getArtifactId() {
        return artifactId;
    }

    /**
     * @param artifactId
     *            the artifactId to set
     */
    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

}
