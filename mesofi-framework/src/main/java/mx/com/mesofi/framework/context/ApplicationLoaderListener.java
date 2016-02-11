/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.framework.context;

import static mx.com.mesofi.framework.util.FrameworkUtils.BASE_PACKAGE;
import static mx.com.mesofi.framework.util.FrameworkUtils.EMAIL_ENABLED;
import static mx.com.mesofi.framework.util.FrameworkUtils.EMAIL_HOST;
import static mx.com.mesofi.framework.util.FrameworkUtils.EMAIL_PASS;
import static mx.com.mesofi.framework.util.FrameworkUtils.EMAIL_PORT;
import static mx.com.mesofi.framework.util.FrameworkUtils.EMAIL_USERNAME;
import static mx.com.mesofi.framework.util.FrameworkUtils.SAVE_ERRORS;
import static mx.com.mesofi.framework.util.FrameworkUtils.SAVE_REQ_RESP;
import static mx.com.mesofi.framework.util.FrameworkUtils.SHOW_BEANS;
import static mx.com.mesofi.framework.util.FrameworkUtils.isPreferenceDefined;
import static mx.com.mesofi.framework.util.FrameworkUtils.validateConfig;
import static mx.com.mesofi.framework.util.ValidationType.ERROR;
import static mx.com.mesofi.framework.util.ValidationType.WARNING;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import mx.com.mesofi.framework.stereotype.Bean;
import mx.com.mesofi.framework.util.FrameworkUtils;

import org.apache.log4j.Logger;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Default implementation of the {@link PropertyValues} interface. Allows simple
 * manipulation of properties, and provides constructors to support deep copy
 * and construction from a Map.
 * 
 * @author Armando Rivas
 * @since 24 Sep 2013
 */
@Bean
public class ApplicationLoaderListener implements ApplicationListener<ContextRefreshedEvent> {
    /**
     * log4j.
     */
    private static Logger log = Logger.getLogger(ApplicationLoaderListener.class);

    @Value("${" + SHOW_BEANS + "}")
    private String showSpringBeans;

    @Value("${" + BASE_PACKAGE + "}")
    private String basePackage;

    @Value("${" + EMAIL_ENABLED + "}")
    private String emailEnabled;

    @Value("${" + EMAIL_HOST + "}")
    private String emailHost;

    @Value("${" + EMAIL_PORT + "}")
    private String emailPort;

    @Value("${" + EMAIL_USERNAME + "}")
    private String username;

    @Value("${" + EMAIL_PASS + "}")
    private String password;

    @Value("${" + SAVE_REQ_RESP + "}")
    private String saveReqResp;

    @Value("${" + SAVE_ERRORS + "}")
    private String saveErrors;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext context = null;
        context = contextRefreshedEvent.getApplicationContext();
        // saves the spring context into a singleton object so that can be
        // accessible anywhere
        SpringContext.getInstance().setContext(context);

        if (isPreferenceDefined(showSpringBeans)) {
            if (new Boolean(showSpringBeans)) {
                if (log.isDebugEnabled()) {
                    log.debug("Found " + context.getBeanDefinitionCount() + " spring beans");
                }
                for (String s : context.getBeanDefinitionNames()) {
                    if (log.isDebugEnabled()) {
                        log.debug("spring bean found: " + s + " [" + context.getType(s) + "]");
                    }
                }
            }
        }
        if (isPreferenceDefined(basePackage)) {
            Set<Package> packages = new HashSet<Package>();
            Set<Class<? extends Object>> classes = new HashSet<Class<? extends Object>>();

            ResourceScanner scanner = new ResourceScanner();
            scanner.setBasePackageName(basePackage);
            packages = scanner.detectValidPackageNames();
            // classes = scanner.findClassesByPackage(packages,
            // Controller.class);
            // System.out.println(classes);// FIXME review this code
        }
        // loads messages from application given a fixed package.
        Messages messages = contextRefreshedEvent.getApplicationContext().getBean(Messages.class);
        messages.setMessages(FrameworkUtils.loadAppMessages());
    }

    @PostConstruct
    void validateIntegrity() {
        showSpringBeans = validateConfig(SHOW_BEANS, showSpringBeans, WARNING);
        basePackage = validateConfig(BASE_PACKAGE, basePackage, ERROR);
        validateConfig(EMAIL_ENABLED, emailEnabled, WARNING);
        validateConfig(EMAIL_HOST, emailHost, ERROR);
        validateConfig(EMAIL_PORT, emailPort, WARNING);
        validateConfig(EMAIL_USERNAME, username, ERROR);
        validateConfig(EMAIL_PASS, password, ERROR);
        validateConfig(SAVE_REQ_RESP, saveReqResp, WARNING);
        validateConfig(SAVE_ERRORS, saveErrors, WARNING);
    }
}