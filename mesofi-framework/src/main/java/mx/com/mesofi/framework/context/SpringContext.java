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

import org.springframework.context.ApplicationContext;

/**
 * Singleton that maintains a reference to the spring context. This can be
 * useful if spring context can be used in all the application.
 * 
 * @author Armando Rivas
 * @since Oct 01 2013
 */
public class SpringContext {
    /**
     * Spring application context.
     */
    private ApplicationContext context;

    /**
     * Singleton for this object.
     */
    private static SpringContext instance;

    /**
     * Make sure it cannot create objects from outside.
     */
    private SpringContext() {

    }

    /**
     * Gets a single instance for this class.
     * 
     * @return Singleton.
     */
    public static SpringContext getInstance() {
        if (instance == null) {
            instance = new SpringContext();
        }
        return instance;
    }

    /**
     * @return the context
     */
    public ApplicationContext getContext() {
        return context;
    }

    /**
     * @param context
     *            the context to set
     */
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

}
