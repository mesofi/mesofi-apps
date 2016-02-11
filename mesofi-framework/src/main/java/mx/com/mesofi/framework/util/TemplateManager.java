/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.framework.util;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * This utility class contains useful methods to validate the user configuration
 * in order to be aware the user some properties are well defined or not.
 * 
 * @author Armando Rivas
 * @since Mar 06 2014
 */
public class TemplateManager {
    /**
     * keyword where the
     */
    public static final String POINT_CUT = "@Pointcut";
    /**
     * Static instance.
     */
    private static TemplateManager templateManager;
    /**
     * log4j.
     */
    private static final Logger log = Logger.getLogger(TemplateManager.class);
    /**
     * Velocity context.
     */
    private final VelocityContext context;
    /**
     * Velocity Engine.
     */
    private final VelocityEngine ve = new VelocityEngine();

    /**
     * This constructor avoids the creation of objects.
     */
    private TemplateManager() {
        if (log.isDebugEnabled()) {
            log.debug("Initializing the template manager...");
        }
        this.context = new VelocityContext();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

        ve.init();
    }

    /**
     * Gets one instance of this type, if the instance does not exists then
     * creates one, otherwise returns the same.
     * 
     * @return Same instance.
     */
    public static TemplateManager getInstance() {
        if (templateManager == null) {
            templateManager = new TemplateManager();
        }
        return templateManager;
    }

    /**
     * Destroyers this instance.
     */
    public void reset() {
        templateManager = null;
    }

    /**
     * Gets the evaluation of the velocity template in a specified location.
     * 
     * @param templateName
     *            Template name.
     * @param params
     *            Map of parameters.
     * @return Result of the evaluation.
     */
    public String evaluateTemplate(String templateName, Map<String, Object> params) {
        for (String key : params.keySet()) {
            context.put(key, params.get(key));
        }
        Template t = ve.getTemplate(templateName);
        StringWriter writer = new StringWriter();
        t.merge(context, writer);
        String finalContent = writer.toString();

        // remove unused parameters
        cleanUpParameters(params);
        return finalContent;
    }

    /**
     * Removes all the parameters set to this template.
     * 
     * @param params
     *            The parameters.
     */
    private void cleanUpParameters(Map<String, Object> params) {
        for (String key : params.keySet()) {
            context.remove(key);
        }
    }

    /**
     * Gets the evaluation of the velocity template in a specified location.
     * 
     * @param templateName
     *            Template name.
     * 
     * @return Result of the evaluation.
     */
    public String evaluateTemplate(String templateName) {
        return evaluateTemplate(templateName, new HashMap<String, Object>());
    }

    /**
     * Gets the evaluation of a raw string that may content some directives from
     * Velocity.
     * 
     * @param rawString
     *            String to be evaluated.
     * @param params
     *            Parameters.
     * @return String evaluated.
     */
    public String evaluateRawString(String rawString, Map<String, Object> params) {
        for (String key : params.keySet()) {
            context.put(key, params.get(key));
        }
        StringWriter writer = new StringWriter();
        Velocity.evaluate(context, writer, "log", rawString);
        String finalContent = writer.toString();

        // remove unused parameters
        cleanUpParameters(params);
        return finalContent;
    }

    /**
     * Gets the evaluation of a raw string that may content some directives from
     * Velocity.
     * 
     * @param rawString
     *            String to be evaluated.
     * @return String evaluated.
     */
    public String evaluateRawString(String rawString) {
        return evaluateRawString(rawString, new HashMap<String, Object>());
    }

}
