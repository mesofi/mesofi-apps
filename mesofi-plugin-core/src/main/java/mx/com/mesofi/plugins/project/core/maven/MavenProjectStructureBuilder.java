/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core.maven;

import mx.com.mesofi.plugins.project.core.ProjectStructureBuilder;
import mx.com.mesofi.plugins.project.core.layers.PersistenceLayer;

/**
 * Contains methods to handle the Maven structure.
 * 
 * @author Armando Rivas
 * @since Mar 05 2014.
 */
public interface MavenProjectStructureBuilder extends ProjectStructureBuilder {

    void createProjectModelObject(PersistenceLayer persistenceLayer);

    void createBasicWebProject();

    void findWebInfFolder();

    /**
     * Finds any folder in Maven structure.
     * 
     * @param folderName
     *            The folder name.
     */
    void findFolderInMavenStructure(String folderName);
}
