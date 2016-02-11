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
 * the existing nodes.
 * 
 * @author Armando Rivas
 * @since 15 Feb 2014.
 * 
 */
public class TreeNodeView {
    /**
     * Identifier.
     */
    private String id;
    /**
     * Label for connection.
     */
    private String text;
    /**
     * Icon for the node.
     */
    private String icon;
    /**
     * If this node has children or not.
     */
    private boolean children = true;

    public TreeNodeView() {

    }

    public TreeNodeView(String id, String text, String icon) {
        this.id = id;
        this.text = text;
        this.icon = icon;
    }

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
     * @return the children
     */
    public boolean isChildren() {
        return children;
    }

    /**
     * @param children
     *            the children to set
     */
    public void setChildren(boolean children) {
        this.children = children;
    }

}
