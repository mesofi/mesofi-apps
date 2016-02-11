package mx.com.mesofi.web.admin.techplugin.layers;

import static mx.com.mesofi.framework.util.TemplateManager.getInstance;
import static mx.com.mesofi.services.util.SimpleCommonActions.getStandardClassName;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.mesofi.plugins.project.core.AbstractCommonBuilder;
import mx.com.mesofi.plugins.project.core.files.FileType;
import mx.com.mesofi.plugins.project.core.files.PlainFile;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderVo;
import mx.com.mesofi.plugins.project.core.technologies.SpringCore;

public class SpringCoreImpl extends AbstractCommonBuilder implements SpringCore {

    public SpringCoreImpl(ApplicationBuilderVo applicationBuilderVo) {
        super(applicationBuilderVo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlainFile> getJavaModelSources(String packageName, String moduleName, boolean isDynamicModule) {
        List<PlainFile> list = new ArrayList<PlainFile>();
        FileType fileName = FileType.JAVA;
        Map<String, Object> params = new HashMap<String, Object>();

        if (!isDynamicModule) {
            String loginValueObject = "/mx/com/mesofi/web/admin/builder/templates/java/login-vo.vm";
            params.put("packageName", packageName);

            list.add(new PlainFile(fileName, "LoginVo", getInstance().evaluateTemplate(loginValueObject, params)));
        } else {
            String dynaCatValueObject = "/mx/com/mesofi/web/admin/techplugin/templates/java/dyna-catalog-vo.vm";
            String dynaCatConfigValueObject = "/mx/com/mesofi/web/admin/techplugin/templates/java/dyna-catalog-config-vo.vm";
            String className = getStandardClassName(moduleName, "Vo");
            String classNameConfigVo = getStandardClassName(moduleName, "ConfigVo");

            params.put("className", className);
            params.put("classNameConfigVo", classNameConfigVo);
            params.put("moduleName", moduleName);
            params.put("date", new Date());
            params.put("year", Calendar.getInstance().get(Calendar.YEAR));
            params.put("fields", getBuilder().getScreenFields(moduleName));

            list.add(new PlainFile(fileName, className, getInstance().evaluateTemplate(dynaCatValueObject, params)));
            list.add(new PlainFile(fileName, classNameConfigVo, getInstance().evaluateTemplate(
                    dynaCatConfigValueObject, params)));
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlainFile> getServiceSources(String packageName, String moduleName, boolean isDynamicModule) {
        List<PlainFile> list = new ArrayList<PlainFile>();
        FileType fileName = FileType.JAVA;
        Map<String, Object> params = new HashMap<String, Object>();

        if (!isDynamicModule) {

        } else {
            String dynaService = "/mx/com/mesofi/web/admin/techplugin/templates/java/dyna-catalog-service.vm";
            String dynaServiceImpl = "/mx/com/mesofi/web/admin/techplugin/templates/java/dyna-catalog-service-impl.vm";

            String className = getStandardClassName(moduleName, "Service");
            String classNameImpl = className + "Impl";

            params.put("packageName", packageName);
            params.put("className", className);
            params.put("moduleName", moduleName);
            params.put("date", new Date());
            params.put("year", Calendar.getInstance().get(Calendar.YEAR));

            params.put("classNameDao", getStandardClassName(moduleName, "Dao"));
            params.put("classNameSearch", getStandardClassName(moduleName, "Search"));

            list.add(new PlainFile(fileName, className, getInstance().evaluateTemplate(dynaService, params)));
            list.add(new PlainFile(fileName, classNameImpl, getInstance().evaluateTemplate(dynaServiceImpl, params)));
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlainFile> getSpringConfig(String packageName, String moduleName) {
        List<PlainFile> list = new ArrayList<PlainFile>();
        FileType fileName = FileType.XML;
        Map<String, Object> params = new HashMap<String, Object>();

        String appContext = "/mx/com/mesofi/web/admin/techplugin/templates/resources/application-context.vm";
        String dataSourceConfig = "/mx/com/mesofi/web/admin/techplugin/templates/resources/data-source-config.vm";

        params.put("packageName", packageName);
        params.put("dbUrl", getBuilder().getConnection().getUrl());
        params.put("dbDriver", getBuilder().getConnection().getDatabaseType().getDriverClassName());
        params.put("dbUserName", getBuilder().getConnection().getUser());
        params.put("dbPassword", getBuilder().getConnection().getPassword());

        list.add(new PlainFile(fileName, "applicationContext", getInstance().evaluateTemplate(appContext, params)));
        list.add(new PlainFile(fileName, "dataSource-config", getInstance().evaluateTemplate(dataSourceConfig, params)));

        return list;
    }

    @Override
    public String getSuffixPackageName() {
        throw new UnsupportedOperationException("Should not be invoked");

    }

    @Override
    public String getSuffixSourceName() {
        throw new UnsupportedOperationException("Should not be invoked");

    }
}
