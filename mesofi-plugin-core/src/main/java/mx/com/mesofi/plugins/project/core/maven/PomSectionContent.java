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

import static mx.com.mesofi.services.util.SimpleCommonActions.fromNullToCustomValue;
import static mx.com.mesofi.services.util.SimpleValidator.isNull;
import static mx.com.mesofi.services.util.SimpleValidator.isValid;

import java.util.ArrayList;
import java.util.List;

import mx.com.mesofi.plugins.project.core.util.TechResolver;

/**
 * Default implementation for the pom.xml.
 * 
 * @author Armando Rivas
 * @since Mar 06 2014.
 */
public abstract class PomSectionContent {
    /**
     * Dependency for the database.
     */
    private PomDependency dataBaseDependency;

    /**
     * Creates a section of the POM, using as a base the database dependency.
     * 
     * @param dataBaseDependency
     *            Database dependency.
     */
    protected PomSectionContent(PomDependency dataBaseDependency) {
        isNull(dataBaseDependency, "A valid database dependency must be specified in order to create a valid project");
        this.dataBaseDependency = dataBaseDependency;
    }

    /**
     * The group Id.
     * 
     * @return The group Id.
     */
    public abstract String getGroupId();

    /**
     * The group Id.
     * 
     * @param groupId
     *            The group Id.
     */
    public abstract void setGroupId(String groupId);

    /**
     * The artifact Id.
     * 
     * @return The artifact Id.
     */
    public abstract String getArtifactId();

    /**
     * Gets the groupId, this one may or not be validated. This valid groupId is
     * validated in order to create a valid Maven project, if this groupId is
     * not well formed, then throws some validations.
     * <p>
     * If you specify a valid groupId, the then you can choose whatever name you
     * want with lowercase letters and no strange symbols
     * <p>
     * Some valid examples of artifacts are: <code>maven</code>,
     * 
     * 
     * @param validate
     *            If this groupId will be validated.
     * @return The groupId.
     */
    public String getGroupId(boolean validate) {
        if (validate) {
            String groupId = getGroupId();
            isNull(groupId, "Group Id cannot be null, please specify a valid name");
            groupId = groupId.trim();
            int index = groupId.indexOf(" ");
            isValid(index == -1, "The groupId [" + groupId + "] must no contain spaces, please remove them");
            return groupId;
        } else {
            return getGroupId();
        }
    }

    /**
     * Gets the artifact, this one may or not be validated. This valid
     * artifactId is validated in order to create a valid Maven project, if this
     * artifactId is not well formed, then throws some validations.
     * <p>
     * If you specify a valid artifactId, the then you can choose whatever name
     * you want with lowercase letters and no strange symbols
     * <p>
     * Some valid examples of artifacts are: <code>maven</code>,
     * <code>commons-math</code>
     * 
     * @param validate
     *            If this artifact will be validated.
     * @return The artifact.
     */
    public String getArtifactId(boolean validate) {
        if (validate) {
            String userArtifactId = getArtifactId();
            isNull(userArtifactId, "Artifact Id cannot be null, please specify a valid name");
            userArtifactId = userArtifactId.trim();
            int index = userArtifactId.indexOf(" ");
            isValid(index == -1, "The artifactId [" + userArtifactId + "] must no contain spaces, please remove them");
            return userArtifactId;
        } else {
            return getArtifactId();
        }
    }

    /**
     * The artifact Id.
     * 
     * @param artifactId
     *            The artifact Id.
     */
    public abstract void setArtifactId(String artifactId);

    /**
     * The version Id.
     * 
     * @return The version Id.
     */
    public abstract String getVersion();

    /**
     * The version Id.
     * 
     * @param version
     *            The version Id.
     */
    public abstract void setVersion(String version);

    /**
     * Gets the source encoding used by the pom.xml.
     * 
     * @return The source encoding.
     */
    protected String getSourceEncoding() {
        return System.getProperty("file.encoding");
    }

    /**
     * Get the organization URL.
     * 
     * @return The organization.
     */
    protected String getOrganizationUrl() {
        String groupId = fromNullToCustomValue(getGroupId());
        String org = "";
        if (groupId.length() > 0) {
            String[] elements = groupId.split("\\.");
            StringBuilder sb = new StringBuilder("www.");
            for (int i = elements.length; i > 0; i--) {
                sb.append(elements[i - 1]);
                sb.append(".");
            }
            if (elements.length > 0) {
                sb.delete(sb.length() - 1, sb.length());
            }
            org = sb.toString();
        }

        return org;
    }

    /**
     * Gets the organization name.
     * 
     * @return The organization name.
     */
    protected String getOrganizationName() {
        return System.getProperty("java.vendor");
    }

    /**
     * The Java version.
     * 
     * @return The java version.
     */
    protected String getJavaVersion() {
        return System.getProperty("java.specification.version");
    }

    /**
     * Gets the developer name for this project.
     * 
     * @return Developer name.
     */
    protected String getDeveloperName() {
        return System.getProperty("user.name");
    }

    /**
     * Gets the developer email.
     * 
     * @return The developer email.
     */
    protected String getDeveloperEmail() {
        String groupId = fromNullToCustomValue(getGroupId());
        String email = "";
        if (groupId.length() > 0) {
            String[] elements = groupId.split("\\.");
            StringBuilder sb = new StringBuilder(getDeveloperName());
            sb.append("@");
            for (int i = elements.length; i > 0; i--) {
                sb.append(elements[i - 1]);
                sb.append(".");
            }
            if (elements.length > 0) {
                sb.delete(sb.length() - 1, sb.length());
            }
            email = sb.toString();
        }
        return email;
    }

    /**
     * Gets a list of all dependencies for this particular combination of
     * technologies.
     * 
     * @return List of dependencies.
     */
    public List<PomDependency> getAllDependencies() {
        // takes the existing dependencies and adds the database dependency.
        List<PomDependency> dependencies = getDependencies();
        dependencies = TechResolver.getInstance().addDependenciesByTechnology(dependencies);
        if (dependencies == null) {
            dependencies = new ArrayList<PomDependency>();
        }
        String comment = "This dependency is used to connect to the corresponding database";
        dataBaseDependency.addComment(comment);
        dependencies.add(dataBaseDependency);
        // based on the default technology selected, create some prebuild
        // dependencies.

        return dependencies;
    }

    /**
     * Returns a list of repositories, if the user wants to specify another
     * different from spring, must override this method.
     * 
     * @return List of repositories.
     */
    protected List<PomRepository> getRepositories() {
        // by default an empty repository is specified.
        List<PomRepository> repositories = new ArrayList<PomRepository>();
        repositories = TechResolver.getInstance().addRepositoriesByTechnology(repositories);
        return repositories;
    }

    /**
     * Gets a list of dependencies for this particular combination of
     * technologies.
     * 
     * @return List of dependencies.
     */
    protected abstract List<PomDependency> getDependencies();
}
