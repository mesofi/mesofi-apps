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
import java.util.List;

import mx.com.mesofi.plugins.project.core.DatabaseType;

/**
 * This class represents a dependency in the pom.xml.
 * 
 * @author Armando Rivas
 * @since Mar 06 2014.
 */
public final class PomDependency {
    /**
     * Group id.
     */
    private String groupId;
    /**
     * Artifact Id.
     */
    private String artifactId;
    /**
     * Version.
     */
    private String version;
    /**
     * Dependency version evaluated.
     */
    private String versionFake;
    /**
     * Includes a list of comments related to the dependency.
     */
    private final List<String> comments = new ArrayList<String>();

    /**
     * Creates a POM dependency.
     * 
     * @param groupId
     *            The group Id.
     * @param artifactId
     *            The artifact Id.
     * @param version
     *            The version.
     */
    public PomDependency(String groupId, String artifactId, String version) {
        createDependency(groupId, artifactId, version);
    }

    /**
     * Creates a new dependency for this project.
     * 
     * @param groupId
     *            The groupId.
     * @param artifactId
     *            The artifactId.
     * @param version
     *            The version for this artifact.
     */
    private void createDependency(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    /**
     * Creates a POM dependency based on the database type. Internally tries to
     * search the best dependency for a given database.
     * 
     * @param databaseType
     *            The database that will create a dependency based on this type.
     */
    public PomDependency(DatabaseType databaseType) {
        isNull(databaseType.getGroupId(), "There's no groupId assigned for DatabaseType " + databaseType);
        isNull(databaseType.getArtifactId(), "There's no artifactId assigned for DatabaseType " + databaseType);
        isNull(databaseType.getVersion(), "There's no version assigned for DatabaseType " + databaseType);

        createDependency(databaseType.getGroupId(), databaseType.getArtifactId(), databaseType.getVersion());
    }

    /**
     * Creates a POM dependency.
     * 
     * @param groupId
     *            The group Id.
     * @param artifactId
     *            The artifact Id.
     * @param version
     *            The version.
     */
    public PomDependency(String groupId, String artifactId, float version) {
        this(groupId, artifactId, String.valueOf(version));
    }

    /**
     * @return the groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @return the artifactId
     */
    public String getArtifactId() {
        return artifactId;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @return the comments
     */
    public List<String> getComments() {
        return comments;
    }

    /**
     * Add a comment to the dependency.
     * 
     * @param comment
     *            A new comment.
     */
    public void addComment(String comment) {
        comments.add(comment);
    }

    /**
     * @return the versionFake
     */
    public String getVersionFake() {
        return versionFake;
    }

    /**
     * @param versionFake
     *            the versionFake to set
     */
    public void setVersionFake(String versionFake) {
        this.versionFake = versionFake;
    }

    /**
     * Gets the override toString method.
     */
    @Override
    public String toString() {
        return "[" + groupId + "/" + artifactId + "/" + version + "]";
    }

}
