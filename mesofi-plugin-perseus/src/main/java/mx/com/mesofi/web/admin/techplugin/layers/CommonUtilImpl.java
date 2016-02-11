package mx.com.mesofi.web.admin.techplugin.layers;

import static mx.com.mesofi.framework.util.TemplateManager.getInstance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.mesofi.plugins.project.core.AbstractCommonBuilder;
import mx.com.mesofi.plugins.project.core.files.FileType;
import mx.com.mesofi.plugins.project.core.files.PlainFile;
import mx.com.mesofi.plugins.project.core.layers.CommonLayer;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderVo;
import mx.com.mesofi.plugins.project.core.technologies.SpringCore;
import mx.com.mesofi.plugins.project.core.util.TechManager;

public class CommonUtilImpl extends AbstractCommonBuilder implements CommonLayer {

    public CommonUtilImpl(ApplicationBuilderVo applicationBuilderVo) {
        super(applicationBuilderVo);
    }

    @Override
    public List<PlainFile> getCommonSources(String packageName) {

        List<PlainFile> list = new ArrayList<PlainFile>();
        FileType fileName = FileType.JAVA;
        Map<String, Object> params = new HashMap<String, Object>();
        String abstractDao = "/mx/com/mesofi/web/admin/techplugin/templates/java/common-dao.vm";
        String abstractCrtler = "/mx/com/mesofi/web/admin/techplugin/templates/java/common-controller.vm";
        String timestampConverter = "/mx/com/mesofi/web/admin/techplugin/templates/java/timestamp-converter.vm";
        String appContextListener = "/mx/com/mesofi/web/admin/techplugin/templates/java/application-context-listener.vm";

        params.put("packageName", packageName);
        params.put("date", new Date());
        params.put("year", Calendar.getInstance().get(Calendar.YEAR));
        params.put("projectName", this.getBuilder().getProjectName());

        list.add(new PlainFile(fileName, "AbstractDao", getInstance().evaluateTemplate(abstractDao, params)));
        list.add(new PlainFile(fileName, "AbstractController", getInstance().evaluateTemplate(abstractCrtler, params)));
        list.add(new PlainFile(fileName, "TimestampConverter", getInstance().evaluateTemplate(timestampConverter)));
        list.add(new PlainFile(fileName, "ApplicationContextListener", getInstance().evaluateTemplate(
                appContextListener)));

        // if spring core is implemented by some plugin
        if (TechManager.getInstance().isUsingLayer(SpringCore.class)) {
            String appLoggerAspect = "/mx/com/mesofi/web/admin/techplugin/templates/spring/java/application-logger-aspect.vm";
            String appLoggerConfig = "/mx/com/mesofi/web/admin/techplugin/templates/spring/java/application-logger-config.vm";
            String springContext = "/mx/com/mesofi/web/admin/techplugin/templates/java/spring-context.vm";
            list.add(new PlainFile(fileName, "SpringContext", getInstance().evaluateTemplate(springContext)));
            list.add(new PlainFile(fileName, "ApplicationLoggerAspect", getInstance().evaluateTemplate(appLoggerAspect,
                    params)));
            list.add(new PlainFile(fileName, "ApplicationLoggerConfig", getInstance().evaluateTemplate(appLoggerConfig,
                    params)));
        }
        return list;
    }

    @Override
    public String getSuffixSourceName() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String getSuffixPackageName() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
