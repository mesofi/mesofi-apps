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

import mx.com.mesofi.plugins.project.core.files.FilePointer;
import mx.com.mesofi.plugins.project.core.layers.BusinessLayer;
import mx.com.mesofi.plugins.project.core.layers.CommonLayer;
import mx.com.mesofi.plugins.project.core.layers.PersistenceLayer;
import mx.com.mesofi.plugins.project.core.layers.ViewLayer;

/**
 * Project structure for any application.
 * 
 * @author Armando Rivas
 * @since Mar 05 2014.
 */
public interface ProjectStructureBuilder {
    /**
     * Initial file name.
     */
    String INITIAL_FILENAME = "mesofi-buids";
    /**
     * File name in the file system.
     */
    File initialFileName = new File(System.getProperty("user.home"), INITIAL_FILENAME);

    /**
     * Creates a initial directory.
     * 
     * @return Pointer to an initial directory.
     */
    FilePointer createInitialDirectory();

    /**
     * Creates a initial directory.
     * 
     * @param initialLocation
     *            Initial location.
     * @return Pointer to an initial directory.
     */
    FilePointer createInitialDirectory(File initialLocation);

    /**
     * Creates the deployment descriptor (web.xml) in the location where the
     * pointer is pointing.
     * 
     * @param persistenceLayer
     *            The persistence layer.
     */
    void createDeploymentDescriptor(PersistenceLayer persistenceLayer);

    /**
     * Create the logger (log.properties) in the location where the pointer is
     * pointing.
     * 
     * @param persistenceLayer
     *            The persistence layer.
     */
    void createApplicationLogger(PersistenceLayer persistenceLayer);

    /**
     * Creates the initial file in the application.
     * 
     * @param moduleName
     *            The module name.
     * 
     * @param viewLayer
     *            The view layer.
     */
    void createInitialFile(String moduleName, ViewLayer viewLayer);

    /**
     * Creates the package structure for this project.
     */
    void createPackageStructure();

    /**
     * Creates a custom structure based on a particular technologies.
     */
    void createCustomStructure();

    /**
     * Creates the sources by some module.
     * 
     * @param moduleName
     *            The module name.
     * @param isDynamicModule
     *            Is this a dynamic module or not.
     * @param viewLayer
     *            The view layer.
     * @param businessLayer
     *            The business layer.
     * @param persistenceLayer
     *            The persistence layer.
     * @see ProjectStructureBuilder#createSourcesByModule(String, String,
     *      boolean, ViewLayer, BusinessLayer, PersistenceLayer)
     */
    void createSourcesByModule(String moduleName, boolean isDynamicModule, ViewLayer viewLayer,
            BusinessLayer businessLayer, PersistenceLayer persistenceLayer);

    /**
     * Creates the sources by some module and submodule.
     * 
     * @param moduleName
     *            The module name.
     * @param subModuleName
     *            The submodule name.
     * @param isDynamicModule
     *            Is this a dynamic module or not.
     * @param viewLayer
     *            The view layer.
     * @param businessLayer
     *            The business layer.
     * @param persistenceLayer
     *            The persistence layer.
     * @see ProjectStructureBuilder#createSourcesByModule(String, boolean,
     *      ViewLayer, BusinessLayer, PersistenceLayer)
     */
    void createSourcesByModule(String moduleName, String subModuleName, boolean isDynamicModule, ViewLayer viewLayer,
            BusinessLayer businessLayer, PersistenceLayer persistenceLayer);

    void createCommonSources(CommonLayer commonLayer);

    /**
     * Creates the resources for the application based on the technologies
     * selected.
     * 
     * @param moduleName
     *            The moduleName.
     * @param viewLayer
     *            The view layer.
     * @param businessLayer
     *            The business layer.
     * @param persistenceLayer
     *            The persistence layer.
     */
    void createResources(String moduleName, ViewLayer viewLayer, BusinessLayer businessLayer,
            PersistenceLayer persistenceLayer);

    /**
     * Creates the resources by some module and submodule.
     */
    void createResourcesByModule(String moduleName, String subModuleName, boolean isDynamicModule,
            PersistenceLayer persistenceLayer);

    /**
     * Creates the files stored in WEB-INF folder.
     * 
     * @param viewLayer
     *            The view Layer.
     */
    void createNoPublicResources(ViewLayer viewLayer);

    /**
     * Creates the public resources for the app.
     * 
     * @param moduleName
     *            The module name.
     * @param viewLayer
     *            The view layer.
     */
    void createPublicResources(String moduleName, ViewLayer viewLayer);

}
