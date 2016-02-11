/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core.maven;

import static mx.com.mesofi.services.util.CommonConstants.DASH;
import static mx.com.mesofi.services.util.SimpleCommonActions.removeAndReplaceSpaces;
import static mx.com.mesofi.services.util.SimpleValidator.isEmpty;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import mx.com.mesofi.plugins.project.core.AbstractCommonBuilder;
import mx.com.mesofi.plugins.project.core.ProjectStructureBuilder;
import mx.com.mesofi.plugins.project.core.content.ResourceFileContent;
import mx.com.mesofi.plugins.project.core.files.BinaryFile;
import mx.com.mesofi.plugins.project.core.files.FilePointer;
import mx.com.mesofi.plugins.project.core.files.PlainFile;
import mx.com.mesofi.plugins.project.core.files.ProjectFile;
import mx.com.mesofi.plugins.project.core.files.SystemFilePointer;
import mx.com.mesofi.plugins.project.core.layers.BusinessLayer;
import mx.com.mesofi.plugins.project.core.layers.CommonLayer;
import mx.com.mesofi.plugins.project.core.layers.PersistenceLayer;
import mx.com.mesofi.plugins.project.core.layers.ViewLayer;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderVo;
import mx.com.mesofi.plugins.project.core.technologies.Hibernate;
import mx.com.mesofi.plugins.project.core.technologies.SpringCore;
import mx.com.mesofi.plugins.project.core.util.TechManager;
import mx.com.mesofi.plugins.project.core.util.TechResolver;
import mx.com.mesofi.services.util.CommonConstants;
import mx.com.mesofi.services.util.SimpleValidator;

import org.apache.log4j.Logger;

/**
 * This class construct the project for using Maven.
 * 
 * @author Armando Rivas
 * @since Mar 05 2014.
 * 
 */
public class MavenProjectStructureBuilderImpl implements MavenProjectStructureBuilder {
    /**
     * log4j.
     */
    private static final Logger log = Logger.getLogger(MavenProjectStructureBuilderImpl.class);
    /**
     * The application builder.
     */
    private ApplicationBuilderVo applicationBuilder;
    /**
     * File pointer to manage files and directories.
     */
    private FilePointer filePointer;
    /**
     * Resource for the pom.xml
     */
    private ResourceFileContent pomFileContent;
    /**
     * Resource for the web.xml
     */
    private ResourceFileContent webFileContent;
    /**
     * Resource for the log4j.properties
     */
    private ResourceFileContent loggerFileContent;
    /**
     * The spring framework folder.
     */
    private final String SPRING_FRAMEWORK_FOLDER = "springframework";
    /**
     * The hibernate folder.
     */
    private final String HIBERNATE_FOLDER = "hibernate";

    /**
     * Creates a maven project using as a input the project name, the pom.xml
     * and the web.xml..
     * 
     * @param applicationBuilder
     *            The application builder.
     * @param pomFileContent
     *            The pom content.
     * @param webFileContent
     *            The web content.
     */
    public MavenProjectStructureBuilderImpl(ApplicationBuilderVo applicationBuilder,
            ResourceFileContent pomFileContent, ResourceFileContent webFileContent,
            ResourceFileContent loggerFileContent) {
        this.applicationBuilder = applicationBuilder;
        this.pomFileContent = pomFileContent;
        this.webFileContent = webFileContent;
        this.loggerFileContent = loggerFileContent;
    }

    @Override
    public FilePointer createInitialDirectory(File initialLocation) {
        filePointer = new SystemFilePointer(initialLocation);
        if (log.isDebugEnabled()) {
            log.debug("Structure created in [" + filePointer + "]");
        }
        return filePointer;
    }

    @Override
    public FilePointer createInitialDirectory() {
        if (log.isDebugEnabled()) {
            log.debug("Structure created in [" + filePointer + "]");
        }
        filePointer = new SystemFilePointer(ProjectStructureBuilder.initialFileName);
        return filePointer;
    }

    @Override
    public void createProjectModelObject(PersistenceLayer persistenceLayer) {
        // creates the pom.xml
        PlainFile projectFile = pomFileContent.getProjectFileContent(persistenceLayer);
        filePointer.createFile(projectFile.getFullName(), projectFile.getContent());
    }

    @Override
    public void createBasicWebProject() {
        String projectName = applicationBuilder.getProjectName();
        isEmpty(projectName, "The project name should be not null");

        projectName = removeAndReplaceSpaces(projectName, DASH.toString());

        filePointer.addDir(projectName);
        filePointer.addDir("src").addDir("main").addDir("resources");
        filePointer.goBack();
        filePointer.addDir("java");
        filePointer.goBack();
        filePointer.addDir("webapp").addDir("WEB-INF");
        filePointer.reset();
        filePointer.goTo(projectName);
    }

    @Override
    public void findWebInfFolder() {
        filePointer.reset();
        filePointer.goTo(removeAndReplaceSpaces(applicationBuilder.getProjectName(), DASH.toString()));
        filePointer.goTo("src").goTo("main").goTo("webapp").addDir("WEB-INF");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void findFolderInMavenStructure(String folderName) {
        filePointer.reset();
        filePointer.goTo(removeAndReplaceSpaces(applicationBuilder.getProjectName(), DASH.toString()));
        filePointer.goTo("src").goTo("main").goTo(folderName);
    }

    @Override
    public void createDeploymentDescriptor(PersistenceLayer persistenceLayer) {
        // creates the web.xml
        PlainFile projectFile = webFileContent.getProjectFileContent(persistenceLayer);
        filePointer.createFile(projectFile.getFullName(), projectFile.getContent());
        filePointer.goBack();
    }

    @Override
    public void createApplicationLogger(PersistenceLayer persistenceLayer) {
        // creates the log4j.properties
        PlainFile projectFile = loggerFileContent.getProjectFileContent(persistenceLayer);
        filePointer.createFile(projectFile.getFullName(), projectFile.getContent());
    }

    @Override
    public void createInitialFile(String moduleName, ViewLayer viewLayer) {
        String artifactId = getArtifactId();
        // creates the initial file
        PlainFile projectFile = viewLayer.getProjectInitialFileContent(moduleName, artifactId);
        if (projectFile != null) {
            filePointer.createFile(projectFile.getFullName(), projectFile.getContent());
        }
    }

    @Override
    public void createPackageStructure() {
        String packageName = createMavenPackageName();
        String[] folders = packageName.split("\\.");
        for (String folder : folders) {
            filePointer.addDir(folder); // starts creating the packages.
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createSourcesByModule(String moduleName, boolean isDynamicModule, ViewLayer viewLayer,
            BusinessLayer businessLayer, PersistenceLayer persistenceLayer) {
        // delegates to this method, there is no submodule.
        createSources(moduleName, null, isDynamicModule, viewLayer, businessLayer, persistenceLayer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createSourcesByModule(String moduleName, String subModuleName, boolean isDynamicModule,
            ViewLayer viewLayer, BusinessLayer businessLayer, PersistenceLayer persistenceLayer) {
        // delegates to this method.
        createSources(moduleName, subModuleName, isDynamicModule, viewLayer, businessLayer, persistenceLayer);
    }

    /**
     * Creates all the sources for the web application.
     * 
     * @param moduleName
     *            The module name must be not null.
     * @param subModuleName
     * @param viewLayer
     * @param businessLayer
     * @param persistenceLayer
     */
    private void createSources(String moduleName, String subModuleName, boolean isDynamicModule, ViewLayer viewLayer,
            BusinessLayer businessLayer, PersistenceLayer persistenceLayer) {

        SimpleValidator.isEmpty(moduleName, "The module name must be not null");
        String basePackageName = createMavenPackageName();

        String mainModule = moduleName;
        filePointer.addDir(moduleName.toLowerCase());
        if (!SimpleValidator.isEmptyString(subModuleName)) {
            filePointer.addDir(subModuleName.toLowerCase());
            mainModule = subModuleName;
        }

        String modelPackageName = ((AbstractCommonBuilder) businessLayer).getSuffixPackageName();
        SimpleValidator.isEmpty(modelPackageName, "the java model suffix package name is missing");
        filePointer.addDir(modelPackageName);
        // creates the sources for this package the model
        List<PlainFile> vos = null;
        vos = businessLayer.getJavaModelSources(basePackageName, mainModule, isDynamicModule);
        if (vos != null) {
            for (PlainFile projectFile : vos) {
                filePointer.createFile(projectFile.getFullName(), projectFile.getContent());
            }
        }

        filePointer.goBack();
        String controllerPackageName = ((AbstractCommonBuilder) viewLayer).getSuffixPackageName();
        SimpleValidator.isEmpty(controllerPackageName, "the java controller suffix package name is missing");
        filePointer.addDir(controllerPackageName);
        // creates the sources for this package the controller
        List<PlainFile> controllers = null;
        controllers = viewLayer.getJavaControllerSources(basePackageName, mainModule, isDynamicModule);
        if (controllers != null) {
            for (PlainFile projectFile : controllers) {
                filePointer.createFile(projectFile.getFullName(), projectFile.getContent());
            }
        }

        filePointer.goBack();
        filePointer.addDir("service");
        // creates the sources for this package the service
        List<PlainFile> services = null;
        services = businessLayer.getServiceSources(basePackageName, mainModule, isDynamicModule);
        if (services != null) {
            for (PlainFile projectFile : services) {
                filePointer.createFile(projectFile.getFullName(), projectFile.getContent());
            }
        }

        filePointer.goBack();
        String persistencePackageName = ((AbstractCommonBuilder) persistenceLayer).getSuffixPackageName();
        SimpleValidator.isEmpty(persistencePackageName, "the java persistence suffix package name is missing");
        filePointer.addDir(persistencePackageName);
        // creates the sources for this package the persistence
        List<PlainFile> daos = null;
        daos = persistenceLayer.getPersistenceSources(basePackageName, mainModule, isDynamicModule);
        daos = TechResolver.getInstance().addDaoSourcesByTechnology(daos, basePackageName, mainModule, isDynamicModule);
        if (daos != null) {
            for (PlainFile projectFile : daos) {
                filePointer.createFile(projectFile.getFullName(), projectFile.getContent());
            }
        }

        // reset the pointer.
        filePointer.goBack();
        filePointer.goBack();
        if (!SimpleValidator.isEmptyString(subModuleName)) {
            filePointer.goBack();
        }
    }

    @Override
    public void createCommonSources(CommonLayer commonLayer) {
        String packageName = createMavenPackageName();

        filePointer.addDir("common");
        // creates the sources for this package the service
        List<PlainFile> common = null;
        common = commonLayer.getCommonSources(packageName);
        common = TechResolver.getInstance().addCommonSourcesByTechnology(common, packageName);
        if (common != null) {
            for (PlainFile projectFile : common) {
                filePointer.createFile(projectFile.getFullName(), projectFile.getContent());
            }
        }

        // reset the pointer.
        filePointer.goBack();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createCustomStructure() {
        // if this project is using spring, then add a custom folder.
        if (TechManager.getInstance().isUsingLayer(SpringCore.class)) {
            filePointer.addDir(SPRING_FRAMEWORK_FOLDER);
            filePointer.goBack();
        }

        // if this project is using hibernate, then add a custom folder.
        if (TechManager.getInstance().isUsingLayer(Hibernate.class)) {
            if (applicationBuilder.getPreferences().isCreateHibernateConfig()) {
                filePointer.addDir(HIBERNATE_FOLDER);
                filePointer.goBack();
            }
        }
    }

    @Override
    public void createResourcesByModule(String moduleName, String subModuleName, boolean isDynamicModule,
            PersistenceLayer persistenceLayer) {
        String packageName = createMavenPackageName();

        // if hibernate is being used, no resources get created
        if (!TechManager.getInstance().isUsingLayer(Hibernate.class)) {
            filePointer.addDir(moduleName.toLowerCase());
            if (!SimpleValidator.isEmptyString(subModuleName)) {
                filePointer.addDir(subModuleName.toLowerCase());
            }

            String persistencePackageName = ((AbstractCommonBuilder) persistenceLayer).getSuffixPackageName();
            SimpleValidator.isEmpty(persistencePackageName, "the java persistence suffix package name is missing");
            filePointer.addDir(persistencePackageName);
            // creates the resources for this package the dao
            List<PlainFile> daos = null;
            daos = persistenceLayer.getPersistenceResources(packageName, subModuleName, isDynamicModule);
            if (daos != null) {
                for (PlainFile projectFile : daos) {
                    filePointer.createFile(projectFile.getFullName(), projectFile.getContent());
                }
            }

            // reset the pointer.
            filePointer.goBack();
            filePointer.goBack();
            if (!SimpleValidator.isEmptyString(subModuleName)) {
                filePointer.goBack();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createResources(String moduleName, ViewLayer viewLayer, BusinessLayer businessLayer,
            PersistenceLayer persistenceLayer) {
        String packageName = createMavenPackageName();

        // if spring framework is being used, creates the configuration
        if (TechManager.getInstance().isUsingLayer(SpringCore.class)) {
            if (businessLayer instanceof SpringCore) {
                SpringCore springCore = (SpringCore) businessLayer;
                filePointer.goTo(SPRING_FRAMEWORK_FOLDER);
                for (PlainFile projectFile : springCore.getSpringConfig(packageName, moduleName)) {
                    filePointer.createFile(projectFile.getFullName(), projectFile.getContent());
                }
            }
        }
        // when this project is using hibernate as persistence layer with spring
        // integration.
        if (TechManager.getInstance().isUsingLayer(Hibernate.class)) {
            if (persistenceLayer instanceof Hibernate && persistenceLayer instanceof SpringCore) {
                SpringCore springHibernate = (SpringCore) persistenceLayer;
                for (PlainFile projectFile : springHibernate.getSpringConfig(packageName, moduleName)) {
                    filePointer.createFile(projectFile.getFullName(), projectFile.getContent());
                }
            }
        }

        // if hibernate is being used, creates the configuration
        if (TechManager.getInstance().isUsingLayer(Hibernate.class)) {
            if (persistenceLayer instanceof Hibernate) {
                Hibernate hibernate = (Hibernate) persistenceLayer;
                PlainFile projectFile = hibernate.getHibernateConfig(packageName, moduleName);
                if (projectFile != null) {
                    filePointer.goBack();
                    filePointer.goTo(HIBERNATE_FOLDER);
                    filePointer.createFile(projectFile.getFullName(), projectFile.getContent());
                }
                if (!applicationBuilder.getPreferences().isAnnotatedClasses()) {
                    filePointer.addDir("mappings");
                    for (PlainFile projectMapping : hibernate.getHibernateMappings(packageName, moduleName)) {
                        filePointer.createFile(projectMapping.getFullName(), projectMapping.getContent());
                    }
                    filePointer.goBack();
                }
            }
        }

        filePointer.goBack(); // outside from springframework folder.

        // adds configuration for i18n.
        List<PlainFile> messages = viewLayer.getI18nMessages();
        if (messages != null) {
            filePointer.addDir("i18n");
            for (PlainFile message : messages) {
                if (message.isIgnorePackageNameIfExists()) {
                    filePointer.createBreakPoint();
                    filePointer.goToParent("resources");
                    filePointer.createFile(message.getFullName(), message.getContent());
                    filePointer.goToBreakPoint();
                } else {
                    filePointer.createFile(message.getFullName(), message.getContent());
                }
            }
        }

        if (messages != null) {
            filePointer.goBack(); // outside from i18n folder.
        }

        // adds configuration for view configuration.
        PlainFile viewConfig = viewLayer.getViewConfig();
        if (viewConfig != null) {
            filePointer.addDir("config");
            filePointer.createFile(viewConfig.getFullName(), viewConfig.getContent());
        }
    }

    /**
     * Creates the package structure based on the groupId and the artifactId.
     * 
     * @return The package name.
     */
    private String createMavenPackageName() {
        String packageName = null;
        if (pomFileContent instanceof PomResourceContent) {
            PomResourceContent pomResourceContent = (PomResourceContent) pomFileContent;
            String artifactId = pomResourceContent.getPomSectionContent().getArtifactId(true);
            String groupId = pomResourceContent.getPomSectionContent().getGroupId(true);
            packageName = groupId + "." + artifactId;
        } else {
            throw new IllegalStateException("Could not generate the package name");
        }
        return packageName;
    }

    @Override
    public void createNoPublicResources(ViewLayer viewLayer) {
        // creates all the resources in WEB-INF
        List<PlainFile> projectFiles = viewLayer.getWebInfFiles(createMavenPackageName());
        if (projectFiles != null) {
            for (PlainFile projectFile : projectFiles) {
                filePointer.createFile(projectFile.getFullName(), projectFile.getContent());
            }
        }
    }

    @Override
    public void createPublicResources(String moduleName, ViewLayer viewLayer) {
        String artifactId = getArtifactId();

        // creates all the resources outside WEB-INF folder.
        Map<String, List<ProjectFile>> pageFiles = viewLayer.getViewPages(moduleName, artifactId);
        if (pageFiles != null) {
            Set<String> folders = pageFiles.keySet();
            for (String folder : folders) {
                String[] fs = folder.split(Pattern.quote(CommonConstants.SLASH_CHAR.toString()));
                for (String f : fs) {
                    filePointer.addDir(f);
                }
                for (ProjectFile projectFile : pageFiles.get(folder)) {
                    if (projectFile instanceof PlainFile) {
                        filePointer.createFile(projectFile.getFullName(), ((PlainFile) projectFile).getContent());
                    } else if (projectFile instanceof BinaryFile) {
                        filePointer.createFile(projectFile.getFullName(), ((BinaryFile) projectFile).getContent());
                    } else {
                        throw new IllegalStateException("Projectfile: " + projectFile + " not recognized");
                    }
                }
                for (int i = 0; i < fs.length; i++) {
                    filePointer.goBack();
                }
            }
        }
    }

    /**
     * Gets the artifactId.
     * 
     * @return The artifactId.
     */
    private String getArtifactId() {
        if (pomFileContent instanceof PomResourceContent) {
            PomResourceContent pomResourceContent = (PomResourceContent) pomFileContent;
            return pomResourceContent.getPomSectionContent().getArtifactId(true);
        } else {
            throw new IllegalStateException("Could not get the artifactId");
        }
    }
}
