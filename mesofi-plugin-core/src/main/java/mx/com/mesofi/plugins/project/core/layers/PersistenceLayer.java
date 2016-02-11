/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core.layers;

import java.util.List;

import mx.com.mesofi.plugins.project.core.files.PlainFile;

/**
 * This interface defines the persistence layer in the multiple tier
 * application, This layer should contain access only to an specific database.
 * 
 * @author Armando Rivas
 * @since Mar 10, 2014
 */
public interface PersistenceLayer extends Layer {
    /**
     * Get the persistence files for the access to the database.
     * 
     * @param basePackageName
     *            The package name.
     * @param moduleName
     *            The module name.
     * @param isDynamicModule
     *            States if this source is dynamic or not.
     * @return List of files.
     */
    List<PlainFile> getPersistenceSources(String basePackageName, String moduleName, boolean isDynamicModule);

    /**
     * Get the persistence resources for the access to the database.
     * 
     * @param basePackageName
     *            The package name.
     * @param moduleName
     *            The module name.
     * @param isDynamicModule
     *            States if this source is dynamic or not.
     * @return List of files.
     */
    List<PlainFile> getPersistenceResources(String basePackageName, String moduleName, boolean isDynamicModule);
}
