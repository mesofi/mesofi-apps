/*
 * COPYRIGHT. Mesofi 2015. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core.technologies;

import java.util.List;

import mx.com.mesofi.plugins.project.core.files.PlainFile;
import mx.com.mesofi.plugins.project.core.layers.PersistenceLayer;

/**
 * This class is the default implementation for the following technology:
 * Hibernate. Any plugin is free to use this class in order to avoid the
 * creation a new class from scratch.
 * 
 * @author Armando Rivas
 * @since April 05 2015.
 */
public interface Hibernate extends Technology, PersistenceLayer {
    /**
     * Gets the hibernate configuration.
     * 
     * @param basePackageName
     *            The package name.
     * @param moduleName
     *            The module name.
     * @return List of resources.
     */
    PlainFile getHibernateConfig(String basePackageName, String moduleName);

    /**
     * Gets a list of configuration files that contains hibernate mappings.
     * 
     * @param packageName
     *            The package name.
     * @param moduleName
     *            The module name.
     * @return List of resources.
     */
    List<PlainFile> getHibernateMappings(String packageName, String moduleName);
}
