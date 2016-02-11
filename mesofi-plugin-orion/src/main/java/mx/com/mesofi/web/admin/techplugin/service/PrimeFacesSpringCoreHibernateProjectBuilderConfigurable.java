package mx.com.mesofi.web.admin.techplugin.service;

import mx.com.mesofi.plugins.project.core.DatabaseType;
import mx.com.mesofi.plugins.project.core.ProjectBuilderConfigurable;
import mx.com.mesofi.plugins.project.core.layers.BusinessLayer;
import mx.com.mesofi.plugins.project.core.layers.CommonLayer;
import mx.com.mesofi.plugins.project.core.layers.PersistenceLayer;
import mx.com.mesofi.plugins.project.core.layers.ViewLayer;
import mx.com.mesofi.plugins.project.core.maven.PomDependency;
import mx.com.mesofi.plugins.project.core.maven.PomSectionContent;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderVo;
import mx.com.mesofi.plugins.project.core.web.WebSectionContent;
import mx.com.mesofi.web.admin.techplugin.layers.impl.CommonImpl;
import mx.com.mesofi.web.admin.techplugin.layers.impl.HibernateImpl;
import mx.com.mesofi.web.admin.techplugin.layers.impl.PrimeFacesImpl;
import mx.com.mesofi.web.admin.techplugin.layers.impl.SpringCoreImpl;
import mx.com.mesofi.web.admin.techplugin.sections.PrimeFacesSpringPomSectionContent;
import mx.com.mesofi.web.admin.techplugin.sections.PrimeFacesSpringWebSectionContent;

public class PrimeFacesSpringCoreHibernateProjectBuilderConfigurable implements ProjectBuilderConfigurable {
    private ApplicationBuilderVo builder;

    @Override
    public void setUpApplicationBuilder(ApplicationBuilderVo app) {
        this.builder = app;
    }

    @Override
    public PomSectionContent getPomSectionContent() {
        PomDependency pomDependency = new PomDependency(DatabaseType.MY_SQL);
        return new PrimeFacesSpringPomSectionContent(pomDependency);
    }

    @Override
    public WebSectionContent getWebSectionContent() {
        return new PrimeFacesSpringWebSectionContent();
    }

    @Override
    public ViewLayer getViewLayer() {
        // uses a default implementation for PrimeFaces.
        PrimeFacesImpl primeFacesImpl = new PrimeFacesImpl(builder);
        // this layer, depends on common and bussiness layer.
        //primeFacesImpl.setBusinessLayer(getBusinessLayer());
        //primeFacesImpl.setCommonLayer(getCommonLayer());
        return primeFacesImpl;
    }

    @Override
    public BusinessLayer getBusinessLayer() {
        // uses a default implementation for SpringCore..
        SpringCoreImpl springCoreImpl = new SpringCoreImpl(builder);
        // this layer, depends on persistence layer.
        //springCoreImpl.setPersistenceLayer(getPersistenceLayer());
        return springCoreImpl;
    }

    @Override
    public PersistenceLayer getPersistenceLayer() {
        // uses a default implementation for Hibernate..
        HibernateImpl hibernateImpl = new HibernateImpl(builder);
        // this layer, depends on bussiness layer.
        //hibernateImpl.setBusinessLayer(getBusinessLayer());
        return hibernateImpl;
    }

    @Override
    public CommonLayer getCommonLayer() {
        return new CommonImpl(builder);
    }

}
