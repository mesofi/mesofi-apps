package mx.com.mesofi.web.admin.techplugin.service;

import mx.com.mesofi.plugins.project.core.ProjectBuilderConfigurable;
import mx.com.mesofi.plugins.project.core.layers.BusinessLayer;
import mx.com.mesofi.plugins.project.core.layers.CommonLayer;
import mx.com.mesofi.plugins.project.core.layers.PersistenceLayer;
import mx.com.mesofi.plugins.project.core.layers.ViewLayer;
import mx.com.mesofi.plugins.project.core.maven.PomDependency;
import mx.com.mesofi.plugins.project.core.maven.PomSectionContent;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderDataSourceVo;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderVo;
import mx.com.mesofi.plugins.project.core.web.WebSectionContent;
import mx.com.mesofi.web.admin.techplugin.layers.CommonUtilImpl;
import mx.com.mesofi.web.admin.techplugin.layers.PrimeFacesImpl;
import mx.com.mesofi.web.admin.techplugin.layers.SpringCoreImpl;
import mx.com.mesofi.web.admin.techplugin.layers.SpringJDBCImpl;
import mx.com.mesofi.web.admin.techplugin.sections.PrimeFacesSpringPomSectionContent;
import mx.com.mesofi.web.admin.techplugin.sections.PrimeFacesSpringWebSectionContent;

/**
 * This implementation creates a project using as technologies PrimeFaces,
 * Spring core and Spring JDBC.
 * 
 * @author Armando Rivas
 * @since Mar 08 2014.
 */
public class PrimeFacesSpringCoreSpringJdbcProjectBuilderConfigurable implements ProjectBuilderConfigurable {
    /**
     * The application builder.
     */
    private ApplicationBuilderVo applicationBuilderVo;
    /**
     * The pom section.
     */
    private PomSectionContent pomSectionContent;

    @Override
    public void setUpApplicationBuilder(ApplicationBuilderVo app) {
        this.applicationBuilderVo = app;
    }

    @Override
    public PomSectionContent getPomSectionContent() {
        // PomDependency dataBaseDependency = new PomDependency("com.oracle",
        // "ojdbc6","11.2.0");

        ApplicationBuilderDataSourceVo dataSource = null;
        dataSource = this.applicationBuilderVo.getConnection();

        PomDependency dataBaseDependency = null;
        dataBaseDependency = new PomDependency(dataSource.getDatabaseType());

        pomSectionContent = new PrimeFacesSpringPomSectionContent(dataBaseDependency);
        return pomSectionContent;
    }

    @Override
    public WebSectionContent getWebSectionContent() {
        String artifactId = pomSectionContent.getArtifactId();
        String groupId = pomSectionContent.getGroupId();
        String packageName = groupId + "." + artifactId;

        String startUpListener = packageName + ".common.ApplicationContextListener";
        return new PrimeFacesSpringWebSectionContent(startUpListener);
    }

    @Override
    public ViewLayer getViewLayer() {
        return new PrimeFacesImpl(applicationBuilderVo);
    }

    @Override
    public BusinessLayer getBusinessLayer() {
        return new SpringCoreImpl(applicationBuilderVo);
    }

    @Override
    public PersistenceLayer getPersistenceLayer() {
        return new SpringJDBCImpl(applicationBuilderVo);
    }

    @Override
    public CommonLayer getCommonLayer() {
        return new CommonUtilImpl(applicationBuilderVo);
    }

}
