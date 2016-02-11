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

import java.util.List;

/**
 * This class contains the properties to create a custom tree view to show all
 * the existing connections.
 * 
 * @author Armando Rivas
 * @since 15 Feb 2014.
 * 
 */
public class TreeView {
    /**
     * id root.
     */
    private String id = "root-data-sources";
    /**
     * Label for connection.
     */
    private String text = "Connections";
    /**
     * Path where the images for connections.
     */
    private String icon = "resources/mesofi/img/connections.png";
    /**
     * Configuration for the TreeView.
     */
    private StateView state;
    /**
     * List of connections.
     */
    private List<TreeNodeView> children;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     *            the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return the state
     */
    public StateView getState() {
        return state;
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState(StateView state) {
        this.state = state;
    }

    /**
     * @return the children
     */
    public List<TreeNodeView> getChildren() {
        return children;
    }

    /**
     * @param children
     *            the children to set
     */
    public void setChildren(List<TreeNodeView> children) {
        this.children = children;
    }

}
