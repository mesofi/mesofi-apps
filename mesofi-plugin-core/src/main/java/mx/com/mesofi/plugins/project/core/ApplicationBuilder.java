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

import java.io.File;

import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderVo;

/**
 * This interface defines the methods needed to create a common web application.
 * 
 * @author Armando Rivas
 * @since Mar 10 2014.
 */
public interface ApplicationBuilder {
    /**
     * Creates a web application using a combination of layers.
     * 
     * @param project
     *            The project to build.
     * @param app
     *            The application data.
     */
    void createWebApplication(ProjectStructureBuilder project, ApplicationBuilderVo app);

    /**
     * Gets the default directory for the application.
     * 
     * @return The default application.
     */
    File getDefaultDirectory();
}
