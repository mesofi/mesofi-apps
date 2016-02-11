/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.datasource.view;

/**
 * This class contains the properties to create a custom tree view to show all
 * the existing connections.
 * 
 * @author Armando Rivas
 * @since 15 Feb 2014.
 * 
 */
public class StateView {
    /**
     * States if the node is opened or not.
     */
    private boolean opened;

    /**
     * @return the opened
     */
    public boolean isOpened() {
        return opened;
    }

    /**
     * @param opened
     *            the opened to set
     */
    public void setOpened(boolean opened) {
        this.opened = opened;
    }

}
