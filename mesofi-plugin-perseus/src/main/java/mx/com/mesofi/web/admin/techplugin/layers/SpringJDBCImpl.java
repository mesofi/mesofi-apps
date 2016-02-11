/*
 * COPYRIGHT. Mesofi 2015. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.techplugin.layers;

import static mx.com.mesofi.framework.util.TemplateManager.getInstance;
import static mx.com.mesofi.services.util.SimpleCommonActions.fromMethodFormatToERFormat;
import static mx.com.mesofi.services.util.SimpleCommonActions.getStandardClassName;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.mesofi.plugins.project.core.files.FileType;
import mx.com.mesofi.plugins.project.core.files.PlainFile;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderVo;
import mx.com.mesofi.plugins.project.core.util.VelocityGeneratorCode;
import mx.com.mesofi.services.files.FileProcessor;
import mx.com.mesofi.web.admin.techplugin.layers.common.AbstractSpringJDBC;

public class SpringJDBCImpl extends AbstractSpringJDBC {

    public SpringJDBCImpl(ApplicationBuilderVo applicationBuilderVo) {
        super(applicationBuilderVo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlainFile> getPersistenceSources(String packageName, String moduleName, boolean isDynamicModule) {
        List<PlainFile> list = new ArrayList<PlainFile>();
        FileType fileName = FileType.JAVA;
        Map<String, Object> params = new HashMap<String, Object>();

        if (!isDynamicModule) {

        } else {
            String dynaDao = "/mx/com/mesofi/web/admin/techplugin/templates/java/dyna-catalog-dao.vm";
            String dynaDaoImpl = "/mx/com/mesofi/web/admin/techplugin/templates/java/dyna-catalog-dao-impl.vm";

            String className = getStandardClassName(moduleName, "Dao");
            String classNameImpl = className + "Impl";

            params.put("packageName", packageName);
            params.put("className", className);
            params.put("moduleName", moduleName);
            params.put("date", new Date());
            params.put("year", Calendar.getInstance().get(Calendar.YEAR));
            params.put("fields", getBuilder().getScreenFields(moduleName));

            params.put("classNameVo", getStandardClassName(moduleName, "Vo"));
            params.put("classNameSearch", getStandardClassName(moduleName, "Search"));

            list.add(new PlainFile(fileName, className, getInstance().evaluateTemplate(dynaDao, params)));

            StringBuilder templateContent = new StringBuilder(new FileProcessor().readPlainContentByLine(dynaDaoImpl));
            VelocityGeneratorCode.getInstance().mergeTemplateContent(templateContent,
                    getBuilder().getGeneratedFinalCode());
            String content = getInstance().evaluateRawString(templateContent.toString(), params);
            list.add(new PlainFile(fileName, classNameImpl, content));

            /*
             * list.add(new ProjectFile(fileName, classNameImpl,
             * getInstance().evaluateTemplateAndMerge(dynaDaoImpl,
             * getApplicationBuilderVo().getGeneratedFinalCode(), params)));
             */
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PlainFile> getPersistenceResources(String packageName, String moduleName, boolean isDynamicModule) {
        List<PlainFile> list = new ArrayList<PlainFile>();
        FileType fileName = FileType.XML;
        Map<String, Object> params = new HashMap<String, Object>();

        if (!isDynamicModule) {

        } else {
            String dynaDao = "/mx/com/mesofi/web/admin/techplugin/templates/resources/dyna-catalog-dao.vm";

            String className = getStandardClassName(moduleName, "Dao");

            params.put("classNameDao", className);
            params.put("tableName", fromMethodFormatToERFormat(moduleName));
            params.put("fields", getBuilder().getScreenFields(moduleName));

            StringBuilder templateContent = new StringBuilder(new FileProcessor().readPlainContentByLine(dynaDao));
            VelocityGeneratorCode.getInstance().mergeTemplateContent(templateContent,
                    getBuilder().getGeneratedFinalCode());
            String content = getInstance().evaluateRawString(templateContent.toString(), params);
            list.add(new PlainFile(fileName, className, content));

            // list.add(new ProjectFile(fileName, className,
            // getInstance().evaluateTemplateAndMerge(dynaDao,getApplicationBuilderVo().getGeneratedFinalCode(),
            // params)));
        }
        return list;
    }
}
