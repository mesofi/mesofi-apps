/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.techplugin.sections;

import java.util.ArrayList;
import java.util.List;

import mx.com.mesofi.plugins.project.core.maven.PomDependency;
import mx.com.mesofi.plugins.project.core.maven.PomRepository;
import mx.com.mesofi.plugins.project.core.maven.PomSectionContent;

/**
 * Implementation to create the PrimeFaces pom.xml for the project. This class
 * must implement the mandatory methods.
 * 
 * @author Armando Rivas
 * @since Mar 06 2014.
 */
public final class PrimeFacesSpringPomSectionContent extends PomSectionContent {
    /**
     * Creates an object using as an input the dependency to database. This
     * dependency must be the driver to connect to the target database.
     * 
     * @param dataBaseDependency
     *            Database dependency.
     */
    public PrimeFacesSpringPomSectionContent(PomDependency dataBaseDependency) {
        super(dataBaseDependency);
    }

    /**
     * Get a list of dependencies to be included in pom.xml.
     */
    public List<PomDependency> getDependencies() {
        List<PomDependency> dependencies = new ArrayList<PomDependency>();
        // primefaces dependency
        PomDependency primeDependency = new PomDependency("org.primefaces", "primefaces", 5.1f);
        primeDependency.addComment("Primefaces library");

        // primefaces themes
        PomDependency primeThemeDependency = new PomDependency("org.primefaces.themes", "glass-x", "1.0.6");

        // JSF Dependency
        PomDependency jsfApiDependency = new PomDependency("com.sun.faces", "jsf-api", "2.2.5");
        // jsfApiDependency.addComment("JSF library");
        // PomDependency jsfImplDependency = new PomDependency("com.sun.faces",
        // "jsf-impl", "2.1.6");

        // Spring Dependencies
        // spring-core.
        PomDependency springCoreDependency = new PomDependency("org.springframework", "spring-context", "3.2.3.RELEASE");
        springCoreDependency.addComment("Application Context (depends on spring-core, spring-expression, spring-aop, "
                + "\n        spring-beans) This is the central artifact for Spring's Dependency Injection "
                + "\n        Container and is generally always defined");

        // spring-jdbc.
        PomDependency springJdbcDependency = new PomDependency("org.springframework", "spring-jdbc", "3.2.3.RELEASE");
        springJdbcDependency.addComment("JDBC Data Access (depends on spring-core, spring-beans, spring-context,"
                + "\n        spring-tx) Define this if you use Spring's "
                + "JdbcTemplate API (org.springframework.jdbc.*)");

        // spring-web.
        PomDependency springWebDependency = new PomDependency("org.springframework", "spring-web", "3.2.3.RELEASE");
        springWebDependency.addComment("Web application development utilities applicable to both Servlet and "
                + "\n        Portlet Environments (depends on spring-core, spring-beans, spring-context) "
                + "\n        Define this if you use Spring MVC, or wish to use Struts, JSF, or another "
                + "\n        web framework with Spring (org.springframework.web.*)");

        // add the database dependency.
        dependencies.add(primeDependency);
        dependencies.add(jsfApiDependency);
        dependencies.add(primeThemeDependency);
        // dependencies.add(jsfImplDependency);
        //
        dependencies.add(springCoreDependency);
        dependencies.add(springWebDependency);
        dependencies.add(springJdbcDependency);
        return dependencies;
    }

    /**
     * Adds the Primefaces repository for the themes.
     */
    public List<PomRepository> getRepositories() {
        List<PomRepository> repositories = new ArrayList<PomRepository>();
        repositories.add(new PomRepository("Prime Repo", "prime-repo", "http://repository.primefaces.org"));
        return repositories;
    }

    /**
     * The group Id.
     */
    @Override
    public String getGroupId() {
        return "mx.com.mesofi";
    }

    /**
     * Artifact name.
     */
    @Override
    public String getArtifactId() {
        return "adminaxamovil";
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

    @Override
    public void setGroupId(String groupId) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setArtifactId(String artifactId) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setVersion(String version) {
        // TODO Auto-generated method stub

    }
}
