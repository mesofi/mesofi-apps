/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core.layers;

import java.util.List;
import java.util.Map;

import mx.com.mesofi.plugins.project.core.files.PlainFile;
import mx.com.mesofi.plugins.project.core.files.ProjectFile;

/**
 * This interface defines the view layer in the multiple tier application, This
 * layer should contain logic related to build the presentation for the user.
 * 
 * @author Armando Rivas
 * @since Mar 10, 2014
 */
public interface ViewLayer extends Layer {
    /**
     * This method returns the initial page for the application, Tipically this
     * must be the 'index.jsp' in the application.
     * 
     * @param moduleName
     *            The module name.
     * @param artifactId
     *            The artifactId.
     * @return The initial page in the application.
     */
    PlainFile getProjectInitialFileContent(String moduleName, String artifactId);

    /**
     * This method returns a set of resources for the user view. Using this
     * method is possible to return view resources located inside directories.
     * If we want to specify subdirectories it's necesary to put a key in the
     * following format: "directory/subdirectory"
     * 
     * @param moduleName
     *            The module name.
     * @param artifactId
     *            The artifactId.
     * @return List of resources located in directories or subdirectories.
     */
    Map<String, List<ProjectFile>> getViewPages(String moduleName, String artifactId);

    /**
     * This method returns all files that should be located inside WEB-INF
     * folder in a web application. This may contain configuration files.
     * 
     * @param basePackageName
     *            The main package name in the application, this parameter might
     *            be useful if it's needed for configuration purposes.
     * @return List of files inside WEB-INF folder.
     */
    List<PlainFile> getWebInfFiles(String basePackageName);

    /**
     * This method gets all files for the controller sources.
     * 
     * @param basePackageName
     *            The package name for this sources.
     * @param moduleName
     *            The module name.
     * @param isDynamicModule
     *            States if this module is dynamic or not.
     * @return List of sources.
     */
    List<PlainFile> getJavaControllerSources(String basePackageName, String moduleName, boolean isDynamicModule);

    /**
     * This method returns the messages for this application.
     * 
     * @return Messages for this application.
     */
    List<PlainFile> getI18nMessages();

    /**
     * This method returns the configuration for this application.
     * 
     * @return Configuration for this application.
     */
    PlainFile getViewConfig();
}
