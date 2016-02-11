/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.builder.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.mesofi.framework.stereotype.Inject;
import mx.com.mesofi.framework.stereotype.Service;
import mx.com.mesofi.framework.stereotype.Transaction;
import mx.com.mesofi.framework.util.LabelEntityVo;
import mx.com.mesofi.framework.util.TemplateManager;
import mx.com.mesofi.plugins.project.core.DatabaseType;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderMappingCodeTypeVo;
import mx.com.mesofi.plugins.project.core.util.VelocityGeneratorCode;
import mx.com.mesofi.services.files.FileFinder;
import mx.com.mesofi.services.util.SimpleValidator;
import mx.com.mesofi.web.admin.builder.dao.BuilderAppDao;
import mx.com.mesofi.web.admin.builder.dao.ConfiguratorAppDao;
import mx.com.mesofi.web.admin.builder.vo.BuilderAvailableTypesVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderCodeTypeVo;
import mx.com.mesofi.web.admin.builder.vo.BuilderMappingTypeVo;

import org.apache.log4j.Logger;

/**
 * Implementation to handle all operations to configure the application.
 * 
 * @author Armando Rivas
 * @since Dec 14 2014.
 * 
 */
@Service
public class ConfiguratorAppServiceImpl implements ConfiguratorAppService {
    /**
     * log4j.
     */
    private static final Logger log = Logger.getLogger(ConfiguratorAppServiceImpl.class);

    /**
     * DAO injected for this service.
     */
    @Inject
    private ConfiguratorAppDao configuratorAppDao;

    /**
     * DAO injected for this service.
     */
    @Inject
    private BuilderAppDao builderAppDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllCodeSections() {
        return configuratorAppDao.getAllCodeSections();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LabelEntityVo> getAllAvailableTypes() {
        return configuratorAppDao.getAllAvailableTypes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> getCustomCodeBySection(String pointcutName) {
        SimpleValidator.isEmpty(pointcutName, "The pointcutName should not be null");
        Map<String, Object> map = new HashMap<String, Object>();

        List<ApplicationBuilderMappingCodeTypeVo> code = builderAppDao.getCodeMappingDatabase(pointcutName);
        map.put("customCode", code);
        map.put("totalCode", code.size());

        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> getCustomCodeBySection(String pointcutName, boolean includeRelations) {
        SimpleValidator.isEmpty(pointcutName, "The pointcutName should not be null");
        Map<String, Object> map = new HashMap<String, Object>();
        if (includeRelations) {
            map = getCustomCodeBySection(pointcutName);
        } else {
            List<LabelEntityVo> code = configuratorAppDao.getCustomCodeBySection(pointcutName);
            map.put("customCode", code);
            map.put("totalCode", code.size());
        }
        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllDatabaseNames() {
        List<Integer> ids = configuratorAppDao.getAllDatabaseNames();
        List<String> list = new ArrayList<String>();
        String databaseType = null;
        for (Integer databaseId : ids) {
            databaseType = DatabaseType.getFormalName(databaseId);
            list.add(databaseId + "~" + databaseType);
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllPointcutNames(String pointcutName) {
        return configuratorAppDao.getAllPointcutNames(pointcutName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BuilderMappingTypeVo> getTypesByDatabase(String databaseId) {
        SimpleValidator.isEmpty(databaseId, "The databaseId should not be null");
        List<BuilderMappingTypeVo> list = configuratorAppDao.getTypesByDatabase(databaseId);
        for (BuilderMappingTypeVo vo : list) {
            if (vo.getJava() == null) {
                vo.setJava("");
            }
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transaction
    public void updateJavaDbTypes(Map<String, String> existingTypes) {
        String[] values = null;
        String newType = null;
        Boolean effectiveType = null;

        for (String key : existingTypes.keySet()) {
            values = existingTypes.get(key).toString().split("_");
            newType = values[0];
            effectiveType = Boolean.parseBoolean(values[1]);
            configuratorAppDao.updateJavaDbTypes(Integer.parseInt(key), newType, effectiveType);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCustomCode(String pointcutName) {
        configuratorAppDao.insertCustomCode(pointcutName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateCustomCode(int codeId, String code) {
        configuratorAppDao.updateCustomCode(codeId, code);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BuilderAvailableTypesVo> getTypesByCode(String pointcutName, Integer codeId) {
        List<LabelEntityVo> allAvailableTypes = configuratorAppDao.getAllAvailableTypes();
        List<BuilderCodeTypeVo> relation = configuratorAppDao.getTypesBySection(pointcutName);
        List<BuilderAvailableTypesVo> list = new ArrayList<BuilderAvailableTypesVo>();

        long id = 0;
        for (LabelEntityVo available : allAvailableTypes) {
            id = hasRelation(available.getId(), codeId, relation);
            if (id != 0) {
                list.add(new BuilderAvailableTypesVo(id, (int) available.getId(), available.getDescription(), true));
                continue;
            } else if (typeAvailable(available.getId(), relation)) {
                list.add(new BuilderAvailableTypesVo(id, (int) available.getId(), available.getDescription(), false));
                continue;
            }
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeTypesAndCodeById(String[] relationIds) {
        for (String id : relationIds) {
            configuratorAppDao.removeTypesAndCodeById(Integer.parseInt(id));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long createTypesAndCode(Integer codeId, Integer typeId) {
        return configuratorAppDao.createTypesAndCode(codeId, typeId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] previewCodeByPointcut(String realPath, String pointcutSelected) {
        SimpleValidator.isEmpty(realPath, "The realPath is not provided");
        SimpleValidator.isEmpty(pointcutSelected, "The pointcut selected is not provided");
        if (log.isDebugEnabled()) {
            log.debug("The pointcut to be processed is [" + pointcutSelected + "]");
        }
        String[] result = { "", "" };

        final String PLUGIN_DIR = "mesofi-plugin-";
        final String PLUGIN_CODE_DIR = "mesofi-plugin-core";
        final String TEMPLATE_NAME_EXT = ".vm";

        File libDirectory = new File(realPath);
        List<String> allTemplateContents = new ArrayList<String>();
        List<String> allTemplateNames = new ArrayList<String>();
        Map<String, String> content = null;
        // reads all mesofi plugins, those pluins should start with
        // "mesofi-plugin-"
        for (File currentFile : libDirectory.listFiles()) {
            if (currentFile.getName().startsWith(PLUGIN_DIR) && !currentFile.getName().startsWith(PLUGIN_CODE_DIR)) {
                content = FileFinder.getJarContent(currentFile.getPath(), TEMPLATE_NAME_EXT);
                for (String fileName : content.keySet()) {
                    allTemplateContents.add(content.get(fileName));
                    allTemplateNames.add(fileName);
                }
            }
        }
        // this list contains all contents of templates
        if (!allTemplateContents.isEmpty()) {
            result = createFinalPreviewCode(pointcutSelected, allTemplateContents, allTemplateNames);
        }
        return result;
    }

    /**
     * Creates the final code based on the existing template content.
     * 
     * @param pointcutSelected
     *            The pointcut selected.
     * 
     * @param allTemplateContents
     *            All content from templates.
     * @param allTemplateNames
     *            All template names.
     * @return The final code and the template name.
     */
    private String[] createFinalPreviewCode(String pointcutSelected, List<String> allTemplateContents,
            List<String> allTemplateNames) {
        String finalCode = "";
        String finalNameTemplate = "";
        StringBuilder mainTemplate = null;
        int i = 0;
        for (String templateContent : allTemplateContents) {
            if (templateContent.contains(pointcutSelected)) {
                mainTemplate = new StringBuilder(templateContent);
                finalNameTemplate = allTemplateNames.get(i);
                break;
            }
            i++;
        }
        if (mainTemplate != null) {
            List<String> existingPointcuts = new ArrayList<String>();
            int initIndex = -1;
            int endIndex = -1;
            int secondIndex = 0;
            final String POINTCUT = TemplateManager.POINT_CUT + "(\"";
            while (true) {
                initIndex = mainTemplate.indexOf(POINTCUT, secondIndex);
                if (initIndex == -1) {
                    break;
                } else {
                    endIndex = mainTemplate.indexOf("\")", initIndex);
                    existingPointcuts.add(mainTemplate.substring(initIndex + POINTCUT.length(), endIndex));
                    secondIndex = endIndex;
                }
            }
            List<ApplicationBuilderMappingCodeTypeVo> codeType = null;
            for (String pointcut : existingPointcuts) {
                codeType = builderAppDao.getCodeMappingDatabase(pointcut);
                Map<String, String> code = VelocityGeneratorCode.getInstance().createCustomCode(codeType);
                VelocityGeneratorCode.getInstance().mergeTemplateContent(mainTemplate, code);
            }
            finalCode = mainTemplate.toString();
        }
        String[] result = new String[2];
        result[0] = finalNameTemplate;
        result[1] = finalCode;
        return result;
    }

    private boolean typeAvailable(long typeId, List<BuilderCodeTypeVo> relation) {
        for (BuilderCodeTypeVo vo : relation) {
            if (typeId == vo.getIdType()) {
                return false;
            }
        }
        return true;
    }

    private long hasRelation(long typeId, Integer codeId, List<BuilderCodeTypeVo> relation) {
        for (BuilderCodeTypeVo vo : relation) {
            if (codeId == vo.getIdCode() && typeId == vo.getIdType()) {
                return vo.getId();
            }
        }
        return 0;
    }

}
