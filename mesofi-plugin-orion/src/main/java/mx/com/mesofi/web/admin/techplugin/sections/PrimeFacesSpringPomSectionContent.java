/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.mesofi.web.admin.techplugin.sections;

import java.util.List;

import mx.com.mesofi.plugins.project.core.maven.PomDependency;
import mx.com.mesofi.plugins.project.core.maven.PomSectionContent;

/**
 *
 * @author armando
 */
public class PrimeFacesSpringPomSectionContent extends PomSectionContent {
    private String groupId = "mx.com.mesofi";
    private String artifactId = "sample";
    private String version = "1.0";

    public PrimeFacesSpringPomSectionContent(PomDependency dataBaseDependency) {
        super(dataBaseDependency);
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    @Override
    public String getArtifactId() {
        return artifactId;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public List<PomDependency> getDependencies() {
        return null;
    }

    /**
     * Override the java version in order to support some other features in Java
     * 1.7
     * 
     * @return
     */
    @Override
    protected String getJavaVersion() {
        return "1.7";
    }

}
