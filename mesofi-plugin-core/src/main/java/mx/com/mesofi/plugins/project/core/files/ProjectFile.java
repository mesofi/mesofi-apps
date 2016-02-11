/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core.files;

/**
 * This class represents a file in a project because it contains the filename
 * and its content.
 * 
 * @author Armando Rivas
 * @since Mar 14, 2014
 */
public abstract class ProjectFile {
    /**
     * The file type.
     */
    protected FileType type;
    /**
     * The file name.
     */
    protected String fileName;

    /**
     * Get the full name of the file including the extension.
     * 
     * @return The full name.
     */
    public String getFullName() {
        return fileName + "." + type.toString();
    }

    /**
     * The representation of the file.
     */
    public String toString() {
        return getFullName();
    }
}
