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
import static mx.com.mesofi.services.util.SimpleValidator.isNull;

import java.io.File;
import java.util.Map;

import mx.com.mesofi.plugins.project.core.layers.BusinessLayer;
import mx.com.mesofi.plugins.project.core.layers.CommonLayer;
import mx.com.mesofi.plugins.project.core.layers.PersistenceLayer;
import mx.com.mesofi.plugins.project.core.layers.ViewLayer;
import mx.com.mesofi.plugins.project.core.maven.MavenProjectStructureBuilder;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderScreenVo;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderVo;
import mx.com.mesofi.plugins.project.core.util.VelocityGeneratorCode;

/**
 * This is the default implementation to create the Web Application.
 * 
 * @author Armando Rivas
 * @since Mar 10 2014.
 */
public class WebApplicationBuilder implements ApplicationBuilder {
    /**
     * The View Layer for the application.
     */
    private ViewLayer viewLayer;
    /**
     * The Business Layer for the application.
     */
    private BusinessLayer businessLayer;
    /**
     * The Persistence Layer for the application.
     */
    private PersistenceLayer persistenceLayer;
    /**
     * The Common Layer for the application.
     */
    private CommonLayer commonLayer;
    /**
     * The initial directory, if this is no provided then the application will
     * be generated in the default location.
     */
    private String initialDirectory;

    /**
     * Creates a web application based on predefined layers and a initial
     * directory.
     * 
     * @param viewLayer
     *            The view layer.
     * @param businessLayer
     *            The business layer.
     * @param persistenceLayer
     *            The persistence layer.
     * @param commonLayer
     *            The common layer.
     * @param initialFolder
     *            The initial directory.
     */
    public WebApplicationBuilder(ViewLayer viewLayer, BusinessLayer businessLayer, PersistenceLayer persistenceLayer,
            CommonLayer commonLayer, String initialFolder) {
        isNull(viewLayer, "A valid implementation of [" + ViewLayer.class + "] was not found");
        isNull(businessLayer, "A valid implementation of [" + BusinessLayer.class + "] was not found");
        isNull(persistenceLayer, "A valid implementation of [" + PersistenceLayer.class + "] was not found");
        isNull(commonLayer, "A valid implementation of [" + CommonLayer.class + "] was not found");

        this.viewLayer = viewLayer;
        this.businessLayer = businessLayer;
        this.persistenceLayer = persistenceLayer;
        this.commonLayer = commonLayer;
        this.initialDirectory = initialFolder;
    }

    /**
     * Creates an object of this class, if you want to process and application,
     * use others contructors.
     * 
     * @param initialFolder
     *            The initial folder.
     */
    public WebApplicationBuilder(String initialFolder) {
        this.initialDirectory = initialFolder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createWebApplication(ProjectStructureBuilder project, ApplicationBuilderVo app) {
        // creates the velocity code
        Map<String, String> code = VelocityGeneratorCode.getInstance().createCustomCode(app.getMappingCode());
        app.setGeneratedFinalCode(code);

        // gets the default directory location where the app will be created.
        File file = getDefaultDirectory();
        boolean isMavenProject = project instanceof MavenProjectStructureBuilder;
        String authModuleName = app.getPreferences().getAuthModuleName();
        String moduleName = app.getPreferences().getModuleName();

        if (isMavenProject) {
            project.createInitialDirectory(file);
            MavenProjectStructureBuilder maven = (MavenProjectStructureBuilder) project;
            // creates the basic project
            maven.createBasicWebProject();
            // creates the pom.xml
            maven.createProjectModelObject(this.persistenceLayer);

            // sets the pointer in source
            maven.findFolderInMavenStructure("java");

            // creates the package for the sources
            project.createPackageStructure();

            // creates the sources for common files
            project.createCommonSources(this.commonLayer);

            // creates the sources for login
            project.createSourcesByModule(authModuleName, false, this.viewLayer, this.businessLayer,
                    this.persistenceLayer);

            for (ApplicationBuilderScreenVo vo : app.getScreens()) {
                // creates the sources for catalogs
                project.createSourcesByModule(moduleName, fromERFormatToMethodFormat(vo.getScreenName()), true,
                        this.viewLayer, this.businessLayer, this.persistenceLayer);
            }

            // sets the pointer in resource folder
            maven.findFolderInMavenStructure("resources");

            // once the resource folder in found, then creates the log4j
            project.createApplicationLogger(this.persistenceLayer);

            // creates the package for the resources
            project.createPackageStructure();

            for (ApplicationBuilderScreenVo vo : app.getScreens()) {
                // creates the resources for catalogs
                project.createResourcesByModule(moduleName, fromERFormatToMethodFormat(vo.getScreenName()), true,
                        this.persistenceLayer);
            }

            // creates custom folder for a particular technology.
            project.createCustomStructure();

            // creates the resources in a Maven project
            project.createResources(moduleName, this.viewLayer, this.businessLayer, this.persistenceLayer);

            // go to WEB-INF folder.
            maven.findWebInfFolder();

            // creates files inside WEB-INF folder.
            project.createNoPublicResources(this.viewLayer);

            // once the basic structure is created, then creates the web.xml
            project.createDeploymentDescriptor(this.persistenceLayer);

            // creates the index.jsp
            project.createInitialFile(authModuleName, this.viewLayer);

            // creates the public resources
            project.createPublicResources(authModuleName, this.viewLayer);

        }
    }

    /**
     * Gets the default directory where the application will be created.
     * 
     * @return The default application directory.
     */
    public File getDefaultDirectory() {
        if (initialDirectory == null || initialDirectory.trim().isEmpty()) {
            throw new IllegalStateException("Please provide a valid directory for the generated application");
        }

        File finalFile = new File(initialDirectory);
        if (!finalFile.exists()) {
            throw new IllegalStateException("This directory does not exist [" + finalFile + "], please create it");
        } else if (!finalFile.isDirectory()) {
            throw new IllegalStateException("This path is no a valid directory [" + finalFile + "]");
        } else if (!finalFile.canWrite()) {
            throw new IllegalStateException("Cannot create application under this path [" + finalFile
                    + "], please specify another directory");
        }
        return finalFile;
    }
}
