/*
 * COPYRIGHT. Mesofi 2015. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core;

import static mx.com.mesofi.services.util.SimpleCommonActions.fromERFormatToMethodFormat;
import mx.com.mesofi.plugins.project.core.layers.BusinessLayer;
import mx.com.mesofi.plugins.project.core.layers.CommonLayer;
import mx.com.mesofi.plugins.project.core.layers.PersistenceLayer;
import mx.com.mesofi.plugins.project.core.layers.ViewLayer;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderVo;
import mx.com.mesofi.services.util.SimpleValidator;

/**
 * This class must be extended by those classes that want to use data from the
 * configuration.
 * 
 * @author Armando Rivas
 * @since Feb 12 2015.
 */
public abstract class AbstractCommonBuilder {
    /**
     * The application builder.
     */
    private ApplicationBuilderVo builder;
    /**
     * If needed, the view layer is injected.
     */
    ViewLayer viewLayer;
    /**
     * If needed, the business layer is injected.
     */
    BusinessLayer businessLayer;
    /**
     * If needed, the persistence layer is injected.
     */
    PersistenceLayer persistenceLayer;
    /**
     * If needed, the common layer is injected.
     */
    CommonLayer commonLayer;

    /**
     * Construct the application builder by passing data from the main
     * application.
     * 
     * @param builder
     *            The application builder.
     */
    public AbstractCommonBuilder(final ApplicationBuilderVo builder) {
        SimpleValidator.isNull(builder, "The application builder must not be null");
        this.builder = builder;
    }

    /**
     * Gets the application builder.
     * 
     * @return The application builder.
     */
    protected ApplicationBuilderVo getBuilder() {
        return builder;
    }

    /**
     * @param viewLayer
     *            the viewLayer to set
     */
    public void setViewLayer(ViewLayer viewLayer) {
        this.viewLayer = viewLayer;
    }

    /**
     * @param businessLayer
     *            the businessLayer to set
     */
    public void setBusinessLayer(BusinessLayer businessLayer) {
        this.businessLayer = businessLayer;
    }

    /**
     * @param persistenceLayer
     *            the persistenceLayer to set
     */
    public void setPersistenceLayer(PersistenceLayer persistenceLayer) {
        this.persistenceLayer = persistenceLayer;
    }

    /**
     * @param commonLayer
     *            the commonLayer to set
     */
    public void setCommonLayer(CommonLayer commonLayer) {
        this.commonLayer = commonLayer;
    }

    /**
     * @return the viewLayer
     */
    protected ViewLayer getViewLayer() {
        return viewLayer;
    }

    /**
     * @return the businessLayer
     */
    protected BusinessLayer getBusinessLayer() {
        return businessLayer;
    }

    /**
     * @return the persistenceLayer
     */
    protected PersistenceLayer getPersistenceLayer() {
        return persistenceLayer;
    }

    /**
     * @return the commonLayer
     */
    protected CommonLayer getCommonLayer() {
        return commonLayer;
    }

    /**
     * Creates the full package name for this a particular layer.
     * 
     * @param basePackageName
     *            The base package name.
     * @param moduleName
     *            The module name.
     * @param isDynamicModule
     *            If this module is dynamic or not.
     * @return The full package name.
     */
    public String createFullPackageName(String basePackageName, String moduleName, boolean isDynamicModule) {
        return createPackageName(basePackageName, moduleName, isDynamicModule, true);
    }

    /**
     * Creates the full package name for this a particular layer.It's possible
     * to create a package name with or without prefix.
     * 
     * @param basePackageName
     *            The base package name.
     * @param moduleName
     *            The module name.
     * @param isDynamicModule
     *            If this module is dynamic or not.
     * @param appendSuffix
     *            Appends the package suffix or not.
     * @return The full package name.
     */
    public String createFullPackageName(String basePackageName, String moduleName, boolean isDynamicModule,
            boolean appendSuffix) {
        return createPackageName(basePackageName, moduleName, isDynamicModule, appendSuffix);
    }

    /**
     * Creates the full package name for this a particular layer using the
     * screen name.
     * 
     * @param basePackageName
     *            The base package name.
     * @param moduleName
     *            The module name.
     * @param screenName
     *            The screen name.
     * @return
     */
    public String createFullPackageName(String basePackageName, String moduleName, String screenName) {
        StringBuffer sb = new StringBuffer();
        sb.append(basePackageName);
        sb.append(".");
        sb.append(moduleName.toLowerCase());
        sb.append(".");
        sb.append(fromERFormatToMethodFormat(screenName).toLowerCase());
        sb.append(".");
        sb.append(((AbstractCommonBuilder) getBusinessLayer()).getSuffixPackageName());
        return sb.toString();
    }

    /**
     * This method must return the suffix of the model file. The implementation
     * of this method might be optional.
     * 
     * @return The valid suffix for the sources.
     */
    public String getSuffixSourceName() {
        return null;
    }

    /**
     * This method must return the suffix of the package name.
     * 
     * @return The valid suffix for the package name.
     */
    public abstract String getSuffixPackageName();

    /**
     * Creates the full package name for this a particular layer. It's possible
     * to create a package name with or without prefix.
     * 
     * @param basePackageName
     *            The base package name.
     * @param moduleName
     *            The module name.
     * @param isDynamicModule
     *            If this module is dynamic or not.
     * @param appendSuffix
     *            Appends the package suffix or not.
     * @return The full package name.
     */
    private String createPackageName(String basePackageName, String moduleName, boolean isDynamicModule,
            boolean appendSuffix) {
        StringBuffer sb = new StringBuffer();
        sb.append(basePackageName);
        sb.append(".");
        if (isDynamicModule) {
            sb.append(getBuilder().getPreferences().getModuleName().toLowerCase());
            sb.append(".");
        }
        sb.append(moduleName.toLowerCase());
        if (appendSuffix) {
            sb.append(".");
            sb.append(getSuffixPackageName());
        }

        return sb.toString();
    }
}
