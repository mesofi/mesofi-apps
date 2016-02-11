/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */

package mx.com.mesofi.web.admin;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * This class is used during application startup. Here we can specify some logic
 * when the application first starts and when this is about to shutdown.
 * Currently we just print the spring application beans but we can add some more
 * content to it.
 * 
 * @author Armando Rivas
 * @version 1.0-SNAPSHOT
 * @since 1.0-SNAPSHOT
 */
public class ApplicationContextListener implements ServletContextListener {

    /**
     * log4j.
     */
    private Logger log = Logger.getLogger(ApplicationContextListener.class);
    /**
     * Application Servlet Context.
     */
    private ServletContext context;

    /**
     * {@inheritDoc}
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("Starting the [mesofi-admin] application...");

        context = servletContextEvent.getServletContext();
        ApplicationContext springContext = null;
        springContext = WebApplicationContextUtils.getWebApplicationContext(context);

        if (log.isDebugEnabled()) {
            // we may know if the application context is well loaded or not.
            log.debug(springContext);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("Shutting down the [mesofi-admin] application...");
    }

}
