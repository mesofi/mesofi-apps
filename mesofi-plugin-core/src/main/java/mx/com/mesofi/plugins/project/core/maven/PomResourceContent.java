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

import static mx.com.mesofi.services.util.SimpleValidator.isNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.mesofi.framework.util.TemplateManager;
import mx.com.mesofi.plugins.project.core.content.DefaultResourceFileContent;
import mx.com.mesofi.plugins.project.core.files.FileType;

/**
 * This class is the direct implementation for the POM.xml.
 * 
 * @author Armando Rivas
 * @since Mar 05 2014.
 * 
 */
public class PomResourceContent extends DefaultResourceFileContent {
    /**
     * Location for the pom.xml
     */
    private static final String POM_XML = "/mx/com/mesofi/web/admin/techplugin/templates/common/resources/pom.vm";
    /**
     * Section of the pom.xml.
     */
    private PomSectionContent pomSectionContent;

    /**
     * Creates an object using the default sections in the pom.xml
     * 
     * @param pomSectionContent
     *            Section for the pom.xml.
     */
    public PomResourceContent(PomSectionContent pomSectionContent) {
        isNull(pomSectionContent, "Pom section content must not be null, you need to create "
                + "a class that extends from [" + PomSectionContent.class + "] or any existing subclass");
        this.pomSectionContent = pomSectionContent;
    }

    /**
     * Content of the pom.xml.
     */
    @Override
    public String getFileContent(String suffixSourceName) {
        Map<String, Object> params = new HashMap<String, Object>();
        // sets some main features of the pom.xml
        params.put("groupId", pomSectionContent.getGroupId(true));
        params.put("artifactId", pomSectionContent.getArtifactId(true));
        params.put("version", pomSectionContent.getVersion());

        params.put("projectBuildSourceEncoding", pomSectionContent.getSourceEncoding());
        params.put("organizationUrl", pomSectionContent.getOrganizationUrl());
        params.put("organizationName", pomSectionContent.getOrganizationName());
        params.put("javaVersion", pomSectionContent.getJavaVersion());

        // processes the developer section.
        params.put("developerName", pomSectionContent.getDeveloperName());
        params.put("developerEmail", pomSectionContent.getDeveloperEmail());

        // process the repository section.
        params.put("allRepositories", pomSectionContent.getRepositories());

        List<PomDependency> dependencies = pomSectionContent.getAllDependencies();
        isNull(dependencies, "Cannot create a pom.xml because the dependencies are null...");

        // based on the dependency version, creates the corresponding
        // properties.
        List<String> dependencyProperties = new ArrayList<String>();
        StringBuilder sb = null;
        String newDependencyVersion = null;
        for (PomDependency pomDependency : dependencies) {
            newDependencyVersion = pomDependency.getArtifactId() + ".version";
            pomDependency.setVersionFake(newDependencyVersion);
            sb = new StringBuilder();
            sb.append("<");
            sb.append(newDependencyVersion);
            sb.append(">");
            sb.append(pomDependency.getVersion());
            sb.append("</");
            sb.append(newDependencyVersion);
            sb.append(">");
            dependencyProperties.add(sb.toString());
        }
        // processes the property section.
        params.put("allDependencyProperties", dependencyProperties);

        // processes the dependencies section.
        params.put("allDependencies", dependencies);

        return TemplateManager.getInstance().evaluateTemplate(POM_XML, params);
    }

    /**
     * The file name.
     */
    @Override
    public String getFileName() {
        return "pom";
    }

    /**
     * Extension of the pom.xml.
     */
    @Override
    public FileType getFileType() {
        return FileType.XML;
    }

    /**
     * @return the pomSectionContent
     */
    public PomSectionContent getPomSectionContent() {
        return pomSectionContent;
    }

}
