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
 * This interface defines the business layer in the multiple tier application,
 * This layer should contain only business rules.
 * 
 * @author Armando Rivas
 * @since Mar 10, 2014
 */
public interface BusinessLayer extends Layer {
    /**
     * Gets a list of files that contains all the java model sources for the
     * business layer.
     * 
     * @param basePackageName
     *            This is the base package name, usually this contains the first
     *            part of the full package name.
     * @param moduleName
     *            The module name.
     * @param isDynamicModule
     *            States if this is going to get the sources from a dynamic
     *            module or not.
     * @return List of java model sources.
     */
    List<PlainFile> getJavaModelSources(String basePackageName, String moduleName, boolean isDynamicModule);

    /**
     * Gets a list of files that contains all the services sources for the
     * business layer.
     * 
     * @param basePackageName
     *            This is the base package name, usually this contains the first
     *            part of the full package name.
     * @param moduleName
     *            The module name.
     * @param isDynamicModule
     *            States if this is going to get the sources from a dynamic
     *            module or not.
     * @return List of java services sources.
     */
    List<PlainFile> getServiceSources(String basePackageName, String moduleName, boolean isDynamicModule);

}
