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

import static mx.com.mesofi.services.util.SimpleCommonActions.fromERFormatToBeanFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.mesofi.services.util.SimpleValidator;

/**
 * This class contains the most commons information in order to create the
 * project.
 * 
 * @author Armando Rivas
 * @since Mar 18 2014.
 */
public class ApplicationBuilderVo {
    /**
     * The project name.
     */
    private String projectName;
    /**
     * All the screens for this particular application.
     */
    private List<ApplicationBuilderScreenVo> screens;
    /**
     * Connection details.
     */
    private ApplicationBuilderDataSourceVo connection;
    /**
     * Relation between types and code.
     */
    private List<ApplicationBuilderMappingCodeTypeVo> mappingCode;
    /**
     * The custom code to be generated.
     */
    private Map<String, String> generatedFinalCode;
    /**
     * Preferences of the application.
     */
    private ApplicationBuilderPreferencesVo preferences;

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
     * @return the screens
     */
    public List<ApplicationBuilderScreenVo> getScreens() {
        return screens;
    }

    /**
     * @param screens
     *            the screens to set
     */
    public void setScreens(List<ApplicationBuilderScreenVo> screens) {
        this.screens = screens;
    }

    /**
     * @return the connection
     */
    public ApplicationBuilderDataSourceVo getConnection() {
        return connection;
    }

    /**
     * @param connection
     *            the connection to set
     */
    public void setConnection(ApplicationBuilderDataSourceVo connection) {
        this.connection = connection;
    }

    /**
     * @return the mappingCode
     */
    public List<ApplicationBuilderMappingCodeTypeVo> getMappingCode() {
        return mappingCode;
    }

    /**
     * @param mappingCode
     *            the mappingCode to set
     */
    public void setMappingCode(List<ApplicationBuilderMappingCodeTypeVo> mappingCode) {
        this.mappingCode = mappingCode;
    }

    /**
     * @return the generatedFinalCode
     */
    public Map<String, String> getGeneratedFinalCode() {
        return generatedFinalCode;
    }

    /**
     * @param generatedFinalCode
     *            the generatedFinalCode to set
     */
    public void setGeneratedFinalCode(Map<String, String> generatedFinalCode) {
        this.generatedFinalCode = generatedFinalCode;
    }

    /**
     * @return the preferences
     */
    public ApplicationBuilderPreferencesVo getPreferences() {
        return preferences;
    }

    /**
     * @param preferences
     *            the preferences to set
     */
    public void setPreferences(ApplicationBuilderPreferencesVo preferences) {
        this.preferences = preferences;
    }

    /**
     * Adds an screen into the application.
     * 
     * @param applicationBuilderScreenVo
     *            A new screen.
     */
    public void addScreen(ApplicationBuilderScreenVo applicationBuilderScreenVo) {
        if (this.screens == null) {
            this.screens = new ArrayList<ApplicationBuilderScreenVo>();
        }
        this.screens.add(applicationBuilderScreenVo);
    }

    /**
     * Gets all the fields related to a single screen o module. The screen might
     * have either format e.g. <code>SOME_MODULE</code> or
     * <code>someModule</code>
     * 
     * @param screenOrModuleName
     *            The screen or module name. Upper or lower case does not
     *            matter.
     * @return List of screens fields, if given a screen name does not exist,
     *         then an exception is thrown.
     */
    public List<ApplicationBuilderScreenFieldVo> getScreenFields(String screenOrModuleName) {
        List<ApplicationBuilderScreenFieldVo> fields = new ArrayList<ApplicationBuilderScreenFieldVo>();

        SimpleValidator.isNull(this.screens, "There are no screens defined, cannot pull screen fields.");
        SimpleValidator.isNull(screenOrModuleName, "Neither the screen nor module name should be null");
        boolean found = false;
        for (ApplicationBuilderScreenVo vo : this.screens) {
            if (fromERFormatToBeanFormat(vo.getScreenName()).equalsIgnoreCase(screenOrModuleName)) {
                found = true;
                fields = vo.getAppFields();
                break;
            }
        }
        SimpleValidator.isValid(found, "The screen [" + screenOrModuleName + "] could not be found");
        return fields;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ApplicationBuilderVo [projectName=" + projectName + ", screens=" + screens + "]";
    }

}
