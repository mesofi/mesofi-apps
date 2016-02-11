/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core.metadata;

/**
 * This class contains the preferences of the application when building.
 * 
 * @author Armando Rivas
 * @since April 05 2015.
 */
public class ApplicationBuilderPreferencesVo {
    /**
     * This authentication module name is the one that follows the base package
     * name. Can be overidden by user, by default is 'login'.
     */
    private String authModuleName = "LogiNN";
    /**
     * This module name is the one that follows the base package name. Can be
     * overidden by user, by default is 'catalogs'.
     */
    private String moduleName = "mymoduleNAME";

    /**
     * States whether hibernate.cfg.xml is created.
     */
    private boolean createHibernateConfig;
    /**
     * States whether the mappings will be created using xml files or
     * annotations.
     */
    private boolean annotatedClasses;

    /**
     * @return the authModuleName
     */
    public String getAuthModuleName() {
        return authModuleName;
    }

    /**
     * @param authModuleName
     *            the authModuleName to set
     */
    public void setAuthModuleName(String authModuleName) {
        this.authModuleName = authModuleName;
    }

    /**
     * @return the moduleName
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * @param moduleName
     *            the moduleName to set
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * @return the createHibernateConfig
     */
    public boolean isCreateHibernateConfig() {
        return createHibernateConfig;
    }

    /**
     * @param createHibernateConfig
     *            the createHibernateConfig to set
     */
    public void setCreateHibernateConfig(boolean createHibernateConfig) {
        this.createHibernateConfig = createHibernateConfig;
    }

    /**
     * @return the annotatedClasses
     */
    public boolean isAnnotatedClasses() {
        return annotatedClasses;
    }

    /**
     * @param annotatedClasses
     *            the annotatedClasses to set
     */
    public void setAnnotatedClasses(boolean annotatedClasses) {
        this.annotatedClasses = annotatedClasses;
    }

}
