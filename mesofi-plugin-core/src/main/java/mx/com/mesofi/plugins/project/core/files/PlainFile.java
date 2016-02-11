/*
 * COPYRIGHT. Mesofi 2015. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core.files;

import mx.com.mesofi.services.util.SimpleValidator;

/**
 * This class represents a file in a project because it contains the filename
 * and its content.
 * 
 * @author Armando Rivas
 * @since Oct 28, 2015
 */
public class PlainFile extends ProjectFile {
    /**
     * The content of the file.
     */
    private String content;
    /**
     * If this flag is true, then this file is created at the root of the
     * classpath as long as it's applicable.
     */
    private boolean ignorePackageNameIfExists;

    /**
     * Creates a project file using the type of the file, its name and its
     * content.
     * 
     * @param type
     *            The type of the file or extension.
     * @param fileName
     *            The file name.
     * @param content
     *            The content.
     */
    public PlainFile(FileType type, String fileName, String content) {
        SimpleValidator.isNull(type, "The file type should not be null");
        SimpleValidator.isEmpty(fileName, "The file name must not be empty");
        SimpleValidator.isNull(content, "The content must be not null");
        this.type = type;
        this.fileName = fileName;
        this.content = content;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @return the ignorePackageNameIfExists
     */
    public boolean isIgnorePackageNameIfExists() {
        return ignorePackageNameIfExists;
    }

    /**
     * @param ignorePackageNameIfExists
     *            the ignorePackageNameIfExists to set
     */
    public void setIgnorePackageNameIfExists(boolean ignorePackageNameIfExists) {
        this.ignorePackageNameIfExists = ignorePackageNameIfExists;
    }
}
