/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core;

import mx.com.mesofi.plugins.project.core.layers.BusinessLayer;
import mx.com.mesofi.plugins.project.core.layers.CommonLayer;
import mx.com.mesofi.plugins.project.core.layers.PersistenceLayer;
import mx.com.mesofi.plugins.project.core.layers.ViewLayer;
import mx.com.mesofi.plugins.project.core.maven.PomSectionContent;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderVo;
import mx.com.mesofi.plugins.project.core.web.WebSectionContent;

/**
 * This interface must be implemented by all those classes that want to create a
 * new project using different technologies organized by view, business and
 * persistence.
 * 
 * @author Armando Rivas
 * @since Mar 08 2014.
 */
public interface ProjectBuilderConfigurable {
    /**
     * Setup the dynamic content for the project, this object must contain all
     * the data needed to create the project.
     * 
     * @param app
     *            The Application builder.
     */
    void setUpApplicationBuilder(ApplicationBuilderVo app);

    PomSectionContent getPomSectionContent();

    WebSectionContent getWebSectionContent();

    ViewLayer getViewLayer();

    BusinessLayer getBusinessLayer();

    PersistenceLayer getPersistenceLayer();

    CommonLayer getCommonLayer();

}
