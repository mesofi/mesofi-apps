/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.builder.controller;

import static mx.com.mesofi.services.util.SimpleValidator.isEmptyString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import mx.com.mesofi.framework.context.Messages;
import mx.com.mesofi.framework.error.ValidationBusinessException;
import mx.com.mesofi.framework.stereotype.Inject;
import mx.com.mesofi.framework.util.LabelEntityVo;
import mx.com.mesofi.web.admin.builder.service.ConfiguratorAppService;
import mx.com.mesofi.web.request.PageParameters;
import mx.com.mesofi.web.response.WebResponse;
import mx.com.mesofi.web.response.types.JsonResponse;
import mx.com.mesofi.web.stereotype.Controller;
import mx.com.mesofi.web.stereotype.GET;
import mx.com.mesofi.web.stereotype.POST;

import org.apache.log4j.Logger;

/**
 * This class handle all configuration for the app to be generated.
 * 
 * @author Armando Rivas
 * @since Dec 14 2014.
 * 
 */
@Controller
public class ConfiguratorAppController {

    /**
     * log4j.
     */
    private static final Logger log = Logger.getLogger(ConfiguratorAppController.class);
    /**
     * Service associated to this controller.
     */
    @Inject
    private ConfiguratorAppService configuratorAppService;
    /**
     * The Servlet Context.
     */
    @Inject
    private ServletContext context;
    /**
     * Messages.
     */
    @Inject
    private Messages messages;

    /**
     * Retrieve all database connections associated to the application.
     * 
     * @param pageParameters
     *            Parameters from view page.
     * @return Current response.
     */
    @GET
    public WebResponse retrieveDatabasesAndMore(PageParameters pageParameters) {
        if (log.isDebugEnabled()) {
            log.debug("Getting all existing database names...");
        }
        return new JsonResponse(configuratorAppService.getAllDatabaseNames());
    }

    /**
     * Retrieve all point cut associated to the application.
     * 
     * @param pageParameters
     *            Parameters from view page.
     * @return Current response.
     */
    @GET
    public WebResponse retrievePointcutsAndMore(PageParameters pageParameters) {
        if (log.isDebugEnabled()) {
            log.debug("Getting all existing pointcuts...");
        }
        String pointcutName = pageParameters.getValue("pointcut-name");
        return new JsonResponse(configuratorAppService.getAllPointcutNames(pointcutName));

    }

    /**
     * Retrieve all code sections for the generated app.
     * 
     * @param pageParameters
     *            . Parameters from view page.
     * @return Current response.
     */
    @GET
    public WebResponse retrieveSectionsAndMore(PageParameters pageParameters) {
        if (log.isDebugEnabled()) {
            log.debug("Getting all code sections and available types...");
        }
        List<String> sections = configuratorAppService.getAllCodeSections();
        List<LabelEntityVo> allTypes = configuratorAppService.getAllAvailableTypes();
        Object[] allData = new Object[2];
        allData[0] = sections;
        allData[1] = allTypes;
        return new JsonResponse(allData);
    }

    /**
     * Retrieve all code sections by identifier.
     * 
     * @param pageParameters
     *            Parameters from view page.
     * @return Current response.
     */
    @GET
    public WebResponse retrieveCodeBySection(PageParameters pageParameters) {
        String pointcutName = pageParameters.getValue("pointcut-name");
        Boolean onlyCode = pageParameters.getValue("only-code", Boolean.class);
        WebResponse webResponse = null;
        if (log.isDebugEnabled()) {
            log.debug("Getting code by pointcutName [" + pointcutName + "]");
        }
        if (onlyCode != null) {
            if (onlyCode) {
                webResponse = new JsonResponse(configuratorAppService.getCustomCodeBySection(pointcutName, false));
            } else {
                webResponse = new JsonResponse(configuratorAppService.getCustomCodeBySection(pointcutName, true));
            }
        } else {
            webResponse = new JsonResponse(configuratorAppService.getCustomCodeBySection(pointcutName));
        }
        return webResponse;
    }

    /**
     * Retrieve all database types by identifier.
     * 
     * @param pageParameters
     *            Parameter from view.
     * @return Current response.
     */
    @GET
    public WebResponse retrieveJavaTypesByDatabase(PageParameters pageParameters) {
        String databaseId = pageParameters.getValue("id-database");
        if (log.isDebugEnabled()) {
            log.debug("Getting types by [" + databaseId + "]");
        }
        return new JsonResponse(configuratorAppService.getTypesByDatabase(databaseId));
    }

    /**
     * Retrieve all java types related to a code.
     * 
     * @param pageParameters
     *            Parameter from view.
     * @return Current response.
     */
    @GET
    public WebResponse retrieveTypesRelatedByCode(PageParameters pageParameters) {
        String pointcutName = pageParameters.getValue("pointcut-name");
        Integer codeId = pageParameters.getValue("id-code", Integer.class);
        if (log.isDebugEnabled()) {
            log.debug("Getting types by pointcut & codeId [" + pointcutName + ":" + codeId + "]");
        }
        return new JsonResponse(configuratorAppService.getTypesByCode(pointcutName, codeId));
    }

    /**
     * Preview the code given a pointcut.
     * 
     * @param pageParameters
     *            . Parameters from view page.
     * @return Current response.
     */
    @GET
    public WebResponse previewCodeByPointcut(PageParameters pageParameters) {
        if (log.isDebugEnabled()) {
            log.debug("Preview code for this pointcut...");
        }
        final String LIB_DIR = "/WEB-INF/lib/";
        String pointcutSelected = pageParameters.getValue("pointcut-selected");
        List<String> result = new ArrayList<String>();
        String[] content = configuratorAppService.previewCodeByPointcut(context.getRealPath(LIB_DIR), pointcutSelected);
        result.add(content[0]);
        result.add(content[1]);
        if (log.isDebugEnabled()) {
            log.debug("finalTemplateName: " + content[0]);
            log.debug("\n\n" + content[1]);
        }
        return new JsonResponse(result);
    }

    /**
     * Deletes the current relation between types and code.
     * 
     * @param pageParameters
     *            The relation to be removed.
     * @return Current response.
     */
    @POST
    public WebResponse removeTypesRelatedByCode(PageParameters pageParameters) {
        String[] relationIds = pageParameters.getValues("relation-id");
        if (log.isDebugEnabled()) {
            log.debug("Deleting relation with id [" + Arrays.asList(relationIds) + "]");
        }
        configuratorAppService.removeTypesAndCodeById(relationIds);
        return new JsonResponse("");
    }

    /**
     * Creates the current relation between types and code.
     * 
     * @param pageParameters
     *            The relation to be created.
     * @return Current response.
     */
    @POST
    public WebResponse createTypesRelatedByCode(PageParameters pageParameters) {
        Integer codeId = pageParameters.getValue("id-code", Integer.class);
        Integer typeId = pageParameters.getValue("id-type", Integer.class);
        if (log.isDebugEnabled()) {
            log.debug("Creating new relation with codeId [" + codeId + "] and typeId [" + typeId + "]");
        }
        return new JsonResponse(configuratorAppService.createTypesAndCode(codeId, typeId));
    }

    /**
     * Saves the changes from the Java types page.
     * 
     * @param pageParameters
     *            Types to be changed.
     * @return Current response.
     */
    @POST
    public WebResponse saveJavaTypesChanges(PageParameters pageParameters) {
        if (log.isDebugEnabled()) {
            log.debug("Saving java types ...");
        }
        Map<String, String> inputJavaTypes = new HashMap<String, String>();
        String[] identifiers = pageParameters.getValues("rowId");
        String type = null;
        String isEffective = null;
        String values = null;
        for (String nameIdentifier : identifiers) {
            type = pageParameters.getValue("javaType_" + nameIdentifier);
            isEffective = pageParameters.getValue("effectiveType_" + nameIdentifier);
            isEffective = isEffective == null ? Boolean.FALSE.toString() : Boolean.TRUE.toString();
            values = type + "_" + isEffective;
            inputJavaTypes.put(nameIdentifier, values);
        }

        validateInputData(inputJavaTypes);
        configuratorAppService.updateJavaDbTypes(inputJavaTypes);
        return new JsonResponse("");
    }

    /**
     * Saves the changes from the custom code page.
     * 
     * @param pageParameters
     *            Point cut name to be added.
     * @return Current response.
     */
    @POST
    public WebResponse saveCustomCodeChanges(PageParameters pageParameters) {
        if (log.isDebugEnabled()) {
            log.debug("Saving custom code...");
        }
        String pointcutName = pageParameters.getValue("pointcut-name");
        configuratorAppService.updateCustomCode(pointcutName);
        return new JsonResponse("");
    }

    /**
     * Updates the content of the code.
     * 
     * @param pageParameters
     *            The code to be changed.
     * @return Current response.
     */
    @POST
    public WebResponse updateCustomCodeChanges(PageParameters pageParameters) {
        Integer codeId = pageParameters.getValue("id-code", Integer.class);
        if (log.isDebugEnabled()) {
            log.debug("Updating custom code with id [" + codeId + "]");
        }
        String code = pageParameters.getValue("code");
        configuratorAppService.updateCustomCode(codeId, code);
        return new JsonResponse("");
    }

    private void validateInputData(Map<String, String> inputJavaTypes) {
        Map<String, List<String>> validations = new HashMap<String, List<String>>();
        // validation for the JavaTypes fields.
        String value = "";
        for (String idField : inputJavaTypes.keySet()) {
            value = inputJavaTypes.get(idField).toString().split("_")[0];
            if (isEmptyString(value)) {
                List<String> list = new ArrayList<String>();
                list.add(messages.getMessage("config.form.val.javaType.empty"));
                validations.put("javaType_" + idField, list);
            } else if (value.length() > 100) {
                List<String> list = new ArrayList<String>();
                list.add(messages.getMessage("config.form.val.javaType.len", "100"));
                validations.put("javaType_" + idField, list);
            }
        }
        if (!validations.isEmpty()) {
            throw new ValidationBusinessException(validations);
        }
    }
}
