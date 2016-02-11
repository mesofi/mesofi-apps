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

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all the attributes for creating a new field.
 * 
 * @author Armando Rivas
 * @since Mar 18 2014.
 */
public class ApplicationBuilderScreenVo {
    /**
     * The screen name.
     */
    private String screenName;
    /**
     * The page name.
     */
    private String pageName;
    /**
     * Contains all the fields for a particular screen.
     */
    private List<ApplicationBuilderScreenFieldVo> appFields;

    /**
     * @return the appFields
     */
    public List<ApplicationBuilderScreenFieldVo> getAppFields() {
        return appFields;
    }

    /**
     * @param appFields
     *            the appFields to set
     */
    public void setAppFields(List<ApplicationBuilderScreenFieldVo> appFields) {
        this.appFields = appFields;
    }

    /**
     * @return the screenName
     */
    public String getScreenName() {
        return screenName;
    }

    /**
     * @param screenName
     *            the screenName to set
     */
    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    /**
     * @return the pageName
     */
    public String getPageName() {
        return pageName;
    }

    /**
     * @param pageName
     *            the pageName to set
     */
    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    /**
     * Adds a new field to the screen.
     * 
     * @param field
     *            The new field.
     */
    public void addAppField(ApplicationBuilderScreenFieldVo field) {
        if (appFields == null) {
            appFields = new ArrayList<ApplicationBuilderScreenFieldVo>();
        }
        appFields.add(field);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((screenName == null) ? 0 : screenName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ApplicationBuilderScreenVo)) {
            return false;
        }
        ApplicationBuilderScreenVo other = (ApplicationBuilderScreenVo) obj;
        if (screenName == null) {
            if (other.screenName != null) {
                return false;
            }
        } else if (!screenName.equals(other.screenName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ApplicationBuilderScreenVo [screenName=" + screenName + "]";
    }

}
