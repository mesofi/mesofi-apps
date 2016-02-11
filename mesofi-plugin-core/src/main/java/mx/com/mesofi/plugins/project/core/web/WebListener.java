/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core.web;

/**
 * This class represents a listener in the web.xml.
 * 
 * @author Armando Rivas
 * @since Mar 10 2014.
 */
public class WebListener {
    /**
     * Listener class.
     */
    private String listenerClass;
    /**
     * Includes the comments related to the listener.
     */
    private String comments;

    /**
     * Creates a listener using the class name.
     * 
     * @param listenerClass
     *            The listener class.
     */
    public WebListener(String listenerClass) {
        this.listenerClass = listenerClass;
    }

    /**
     * @return the listenerClass
     */
    public String getListenerClass() {
        return listenerClass;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments
     *            the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

}
