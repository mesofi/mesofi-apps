/*
 * COPYRIGHT. Mesofi 2015. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.techplugin.layers.common;

import mx.com.mesofi.plugins.project.core.maven.PomDependency;
import mx.com.mesofi.plugins.project.core.maven.PomSectionContent;

/**
 * This class is the default implementation for the following technology:
 * PrimeFaces. Any plugin is free to use this class in order to avoid the
 * creation a new class from scratch.
 * 
 * @author Armando Rivas
 * @since Feb 18 2015.
 */
public abstract class PrimeFacesPomSectionContent extends PomSectionContent {
    /**
     * Default constructor, this one requires a dependency for the database.
     * 
     * @param dataBaseDependency
     *            Pom dependency for the database.
     */
    protected PrimeFacesPomSectionContent(PomDependency dataBaseDependency) {
        super(dataBaseDependency);
    }

    /**
     * Version of the artifact.
     */
    @Override
    public String getVersion() {
        return "1.0";
    }

    /**
     * Override the java version in order to support some other features in Java
     * 1.7
     */
    @Override
    protected String getJavaVersion() {
        return "1.7";
    }
}
