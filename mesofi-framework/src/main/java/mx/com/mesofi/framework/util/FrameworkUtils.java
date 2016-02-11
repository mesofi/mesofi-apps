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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import mx.com.mesofi.framework.context.SpringContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * This utility class contains useful methods to validate the user configuration
 * in order to be aware the user some properties are well defined or not.
 * 
 * @author Armando Rivas
 * @since Oct 01 2013
 */
public class FrameworkUtils {
    /**
     * log4j.
     */
    private static Logger log = Logger.getLogger(FrameworkUtils.class);

    /**
     * This property decides whether or not the SQL statements should be
     * displayed in log.
     */
    public static final String SHOW_SQL = "mesofi.config.show_sql";
    /**
     * This property assigns a default value for the property above.
     */
    public static final Boolean SHOW_SQL_DEFAULT = false;

    /**
     * This property decides whether or not the SQL statements should be
     * formatted in log.
     */
    public static final String FORMAT_SQL = "mesofi.config.format_sql";
    /**
     * This property assigns a default value for the property above.
     */
    public static final Boolean FORMAT_SQL_DEFAULT = false;

    /**
     * This property decides whether or not the spring beans should be listed in
     * log.
     */
    public static final String SHOW_BEANS = "mesofi.config.show_spring_beans";
    /**
     * This property assigns a default value for the property above.
     */
    public static final Boolean SHOW_BEANS_DEFAULT = false;

    /**
     * This property indicate the base package of the application.
     */
    public static final String BASE_PACKAGE = "mesofi.config.base_package";
    /**
     * This property assigns a default value for the property above.
     */
    public static final String BASE_PACKAGE_DEFAULT = "mx.com.mesofi.web";

    /**
     * This property indicate whether the application can send mails or not.
     */
    public static final String EMAIL_ENABLED = "mesofi.config.email.enabled";
    /**
     * This property assigns a default value for the property above.
     */
    public static final String EMAIL_ENABLED_DEFAULT = "true";

    /**
     * This property indicate the host for sending outgoing email.
     */
    public static final String EMAIL_HOST = "mesofi.config.email.host";
    /**
     * This property assigns a default value for the property above.
     */
    public static final String EMAIL_HOST_DEFAULT = "smtp.gmail.com";

    /**
     * This property indicate the port for sending outgoing email.
     */
    public static final String EMAIL_PORT = "mesofi.config.email.port";
    /**
     * This property assigns a default value for the property above.
     */
    public static final String EMAIL_PORT_DEFAULT = "25";

    /**
     * This property indicate the user name for sending outgoing email.
     */
    public static final String EMAIL_USERNAME = "mesofi.config.email.username";
    /**
     * This property assigns a default value for the property above.
     */
    public static final String EMAIL_USERNAME_DEFAULT = "rivasarmando271084@gmail.com";

    /**
     * This property indicate the user name for sending outgoing email.
     */
    public static final String EMAIL_PASS = "mesofi.config.email.pass";
    /**
     * This property assigns a default value for the property above.
     */
    public static final String EMAIL_PASS_DEFAULT = "Brocoly271084";

    /**
     * This property indicate whether the request and response should be saved
     * or not.
     */
    public static final String SAVE_REQ_RESP = "mesofi.config.app.save_req_resp";
    /**
     * This property assigns a default value for the property above.
     */
    public static final String SAVE_REQ_RESP_DEFAULT = "false";

    /**
     * This property indicate whether the errors should be saved or not.
     */
    public static final String SAVE_ERRORS = "mesofi.config.app.save_error_trace";
    /**
     * This property assigns a default value for the property above.
     */
    public static final String SAVE_ERRORS_DEFAULT = "false";

    /**
     * Default package from which the messages will be read.
     */
    private static final String DEFAULT_MSG_PACKAGE = "mx/com/mesofi/messages";

    /**
     * Stores the preference and the default value of each property.
     */
    public static Map<String, Object> preferences = new HashMap<String, Object>();

    static {
        preferences.put(SHOW_SQL, SHOW_SQL_DEFAULT);
        preferences.put(FORMAT_SQL, FORMAT_SQL_DEFAULT);
        preferences.put(SHOW_BEANS, SHOW_BEANS_DEFAULT);
        preferences.put(BASE_PACKAGE, BASE_PACKAGE_DEFAULT);
        preferences.put(EMAIL_ENABLED, EMAIL_ENABLED_DEFAULT);
        preferences.put(EMAIL_HOST, EMAIL_HOST_DEFAULT);
        preferences.put(EMAIL_PORT, EMAIL_PORT_DEFAULT);
        preferences.put(EMAIL_USERNAME, EMAIL_USERNAME_DEFAULT);
        preferences.put(EMAIL_PASS, EMAIL_PASS_DEFAULT);
        preferences.put(SAVE_REQ_RESP, SAVE_REQ_RESP_DEFAULT);
        preferences.put(SAVE_ERRORS, SAVE_ERRORS_DEFAULT);
    }

    /**
     * Avoid the creation of new objects.
     */
    private FrameworkUtils() {

    }

    /**
     * Gets a preference value from the config file.
     * 
     * @param preferenceName
     *            Preference name, if this property is not found in config file,
     *            then looks for it in memory using the default value.
     * @return Value of the property.
     */
    public static String getPreferenceValue(final String preferenceName) {
        Object preferences = getSpringBeanFromContext("preferencesProps");
        String value = ((Properties) preferences).getProperty(preferenceName);
        if (value == null) {
            // get the default value if not found in property file.
            value = FrameworkUtils.preferences.get(preferenceName).toString();
        }
        if (value == null) {
            throw new IllegalStateException("Could not find this property [" + preferenceName
                    + "]in preferences config file");
        }
        return value;
    }

    /**
     * Gets a spring bean from the spring context.
     * 
     * @param beanName
     *            Bean name.
     * @return Object or null if this bean does not exists.
     */
    public static Object getSpringBeanFromContext(String beanName) {
        // lookup the bean in SpringContext
        SpringContext springContext = SpringContext.getInstance();
        return springContext.getContext().getBean(beanName);
    }

    /**
     * States if the preference is define or not in the configuration file.
     * 
     * @param preferenceValue
     *            Value of the property, if the property is not defined, the
     *            value might have the following format. ${value} which means
     *            that the value was not found in the configuration file.
     * @return true, is define, otherwise false.
     */
    public static boolean isPreferenceDefined(final String preferenceValue) {
        boolean define = false;
        if (preferenceValue != null) {
            define = !(preferenceValue.startsWith(PropertyPlaceholderConfigurer.DEFAULT_PLACEHOLDER_PREFIX) && preferenceValue
                    .endsWith(PropertyPlaceholderConfigurer.DEFAULT_PLACEHOLDER_SUFFIX));
        }
        return define;
    }

    /**
     * Validates the parameter come from the config file in order to display
     * some validations.
     * 
     * @param prefName
     *            Preference name.
     * @param prefValue
     *            Preference value.
     * @param validationType
     *            Validation type.
     * @return Default value.
     */
    public static String validateConfig(String prefName, String prefValue, ValidationType validationType) {
        String defaultValue = null;
        if (isPreferenceDefined(prefValue)) {
            if (prefValue != null && prefValue.trim().length() == 0) {
                log.warn("A preference property has an empty value [" + prefName
                        + "] in preference config file, please verify");
            }
            defaultValue = prefValue;
        } else {
            Object valueFound = preferences.get(prefName);
            if (valueFound != null) {
                defaultValue = valueFound.toString();
            }

            switch (validationType) {
            case WARNING:
                log.warn("A preference property is missing [" + prefName
                        + "] in preference config file, setting the default value to [" + defaultValue + "]");
                break;
            case ERROR:
                throw new IllegalStateException("In preferences config file, there must be a property called ["
                        + prefName + "], please define it");
            }
        }
        return defaultValue;
    }

    /**
     * Reads all the property files located in a given package so that can be
     * used for all the application.
     * 
     * @return The properties read or and empty object when could not read any
     *         property.
     */
    public static Properties loadAppMessages() {
        Properties properties = new Properties();

        URL enumeration = FrameworkUtils.class.getClassLoader().getResource(DEFAULT_MSG_PACKAGE);
        if (enumeration != null) {
            try {
                File folder = new File(enumeration.toURI());
                File[] contenuti = folder.listFiles();
                for (File actual : contenuti) {
                    properties.load(new FileInputStream(actual));
                    if (log.isDebugEnabled()) {
                        log.debug("Loading properties file from class path resource [" + actual.getAbsolutePath() + "]");
                    }
                }
            } catch (Exception e) {
                log.warn("Could not load the property file due to: " + e.getMessage());
            }
        } else {
            log.warn("The package: [" + DEFAULT_MSG_PACKAGE + "] does not exist. In order to load messages, please "
                    + "create the package structure and put some property files there...");
        }
        return properties;
    }

}
