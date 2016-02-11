/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core.util;

import static mx.com.mesofi.services.util.SimpleValidator.isNull;
import mx.com.mesofi.plugins.project.core.layers.BusinessLayer;
import mx.com.mesofi.plugins.project.core.layers.CommonLayer;
import mx.com.mesofi.plugins.project.core.layers.Layer;
import mx.com.mesofi.plugins.project.core.layers.PersistenceLayer;
import mx.com.mesofi.plugins.project.core.layers.ViewLayer;

/**
 * Utility that manages all related to Layers in the application.
 * 
 * @author Armando Rivas
 * @since August 18 2014.
 * 
 */
public class TechManager {
    /**
     * Unique instance for this class.
     */
    private static TechManager techManager = new TechManager();
    /**
     * All layers in application.
     */
    private Layer[] allLayers = new Layer[4];

    /**
     * Private constructor for this class.
     */
    private TechManager() {
        // private constructor.
    }

    /**
     * Gets the only instance for this class.
     * 
     * @return The singleton.
     */
    public static TechManager getInstance() {
        return techManager;
    }

    /**
     * This method states whether this application is using a particular layer
     * or not.
     * 
     * @param layer
     *            The layer.
     * @return true, it is using the layer, otherwise false.
     */
    public boolean isUsingLayer(Class<?> layer) {
        boolean isFound = false;

        isNull(allLayers[0], "ViewLayer has not been initialized in Tech Manager configuration");
        isNull(allLayers[1], "BusinessLayer has not been initialized in Tech Manager configuration");
        isNull(allLayers[2], "PersistenceLayer has not been initialized in Tech Manager configuration");
        isNull(allLayers[3], "CommonLayer has not been initialized in Tech Manager configuration");

        if (layer != null && Layer.class.isAssignableFrom(layer)) {
            for (Layer l : allLayers) {
                if (layer.isAssignableFrom(l.getClass())) {
                    isFound = true;
                    break;
                }
            }
        }
        return isFound;
    }

    /**
     * Sets the view layer for this application.
     * 
     * @param viewLayer
     *            The view layer.
     */
    public void setViewLayer(ViewLayer viewLayer) {
        isNull(viewLayer, "ViewLayer cannot be null when building this application");
        allLayers[0] = viewLayer;
    }

    /**
     * Gets the view layer implemented by this application.
     * 
     * @return The view layer.
     */
    public ViewLayer getViewLayer() {
        return (ViewLayer) allLayers[0];
    }

    /**
     * Gets the business layer implemented by this application.
     * 
     * @return The business layer.
     */
    public BusinessLayer getBusinessLayer() {
        return (BusinessLayer) allLayers[1];
    }

    /**
     * Gets the persistence layer implemented by this application.
     * 
     * @return The persistence layer.
     */
    public PersistenceLayer getPersistenceLayer() {
        return (PersistenceLayer) allLayers[2];
    }

    /**
     * Sets the business layer for this application.
     * 
     * @param businessLayer
     *            The business layer.
     */
    public void setBusinessLayer(BusinessLayer businessLayer) {
        isNull(businessLayer, "BusinessLayer cannot be null when building this application");
        allLayers[1] = businessLayer;
    }

    /**
     * Sets the persistence layer for this application.
     * 
     * @param persistenceLayer
     *            The Persistence layer.
     */
    public void setPersistenceLayer(PersistenceLayer persistenceLayer) {
        isNull(persistenceLayer, "PersistenceLayer cannot be null when building this application");
        allLayers[2] = persistenceLayer;
    }

    /**
     * Sets the common layer for this application.
     * 
     * @param commonLayer
     *            The CommonLayer layer.
     */
    public void setCommonLayer(CommonLayer commonLayer) {
        isNull(commonLayer, "CommonLayer cannot be null when building this application");
        allLayers[3] = commonLayer;
    }
}
