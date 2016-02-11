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

import static mx.com.mesofi.services.util.SimpleCommonActions.fromNullToCustomValue;
import static mx.com.mesofi.services.util.SimpleValidator.hasLength;
import static mx.com.mesofi.services.util.SimpleValidator.isNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import mx.com.mesofi.services.util.CommonConstants;
import mx.com.mesofi.services.util.SimpleValidator;

import org.apache.log4j.Logger;

/**
 * This class can navigate in the structure of files to manage the files.
 * 
 * @author Armando Rivas
 * @since Mar 05 2014.
 * 
 */
public class SystemFilePointer implements FilePointer {
    /**
     * log4j.
     */
    private static final Logger log = Logger.getLogger(SystemFilePointer.class);

    /**
     * Current pointer to the file.
     */
    private File currentFile;
    /**
     * Initial location.
     */
    private File initialFile;
    /**
     * Contains a pointer at an specific location.
     */
    private File currentBreakPointLocation;

    /**
     * Create a system pointer using an existing location.
     * 
     * @param directory
     *            Initial directory.
     */
    public SystemFilePointer(File directory) {
        SimpleValidator.isNull(directory, "You must specify a valid location");

        if (directory.exists() && directory.isDirectory()) {
            this.currentFile = directory;
        }
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                throw new IllegalStateException("Could not create a valid directory at this location [" + directory
                        + "], you must specify another one");
            } else {
                this.currentFile = directory;
            }
        }
        this.initialFile = this.currentFile;
        this.currentBreakPointLocation = this.initialFile;
    }

    /**
     * Adds a new directory in the current location. If the directory cannot be
     * created, then just prints a warning message. This method can create
     * several directories if the directoryName contains slash character "/".
     * 
     * @param directoryName
     *            If this parameter contains "/" then creates several
     *            directories.
     * @return The current location.
     */
    @Override
    public FilePointer addDir(String directoryName) {
        hasLength(directoryName, "You must specify a valid directory name...");
        if (directoryName.contains(CommonConstants.SLASH_CHAR.toString())) {
            String directories[] = directoryName.split(CommonConstants.SLASH_CHAR.toString());
            for (String currDirectory : directories) {
                addDir(currDirectory);
            }
        } else {
            this.currentFile = new File(this.currentFile, directoryName);
            if (!this.currentFile.mkdir()) {
                if (log.isDebugEnabled()) {
                    log.warn("Could not create a directory at this location " + this.currentFile);
                }
            }
        }
        return this;
    }

    /**
     * Set the pointer of the file in its initial state.
     * 
     * @return This instance.
     */
    @Override
    public FilePointer reset() {
        this.currentFile = initialFile;
        return this;
    }

    /**
     * The pointer moves to the parent directory.
     * 
     * @return This instance.
     */
    @Override
    public FilePointer goBack() {
        this.currentFile = this.currentFile.getParentFile();
        return this;
    }

    /**
     * Moves the pointer to some directory.
     * 
     * @param directoryName
     *            The existing directory, if the directory does not exist, then
     *            throws an exception.
     * @return This instance.
     */
    @Override
    public FilePointer goTo(String directoryName) {
        hasLength(directoryName, "You must specify a valid directory name...");
        boolean found = false;
        for (File currFile : this.currentFile.listFiles()) {
            if (currFile.isDirectory()) {
                if (currFile.getName().equals(directoryName)) {
                    this.currentFile = currFile;
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            throw new IllegalStateException("Cannot go to this directory [" + directoryName
                    + "] because it was not found");
        }
        return this;
    }

    /**
     * Moves the pointer to some parent directory.
     * 
     * @param directoryName
     *            The existing parent directory, if the directory does not
     *            exist, then throws an exception.
     * @return This instance.
     */
    @Override
    public FilePointer goToParent(String directoryName) {
        hasLength(directoryName, "You must specify a valid parent directory name...");
        File parent = null;
        if (!this.currentFile.getName().equals(directoryName)) {
            for (;;) {
                parent = this.currentFile.getParentFile();
                if (parent == null) {
                    throw new IllegalStateException("Cannot go to this directory [" + directoryName
                            + "] because it does not exist");
                }
                if (parent.getName().equals(directoryName)) {
                    this.currentFile = parent;
                    break;
                } else {
                    this.currentFile = parent;
                }
            }
        }

        return this;
    }

    /**
     * Creates a plain file in the current location.
     * 
     * @param fileName
     *            File name.
     * @param fileContent
     *            Content of the file.
     */
    @Override
    public void createFile(String fileName, InputStream fileContent) {
        hasLength(fileName, "The file name must be a valid file...");
        isNull(fileContent, "Content of the stream is null");
        File file = new File(this.currentFile, fileName);
        Path target = Paths.get(file.toString());
        try {
            Files.copy(fileContent, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot copy the new file because: " + e.getMessage());
        } finally {
            try {
                if (fileContent != null) {
                    fileContent.close();
                }
            } catch (IOException e) {
                throw new IllegalStateException("Cannot close the buffer");
            }
        }
    }

    /**
     * Creates a plain file in the current location.
     * 
     * @param fileName
     *            File name.
     * @param fileContent
     *            Content of the file.
     */
    @Override
    public void createFile(String fileName, String fileContent) {
        hasLength(fileName, "The file name must be a valid file...");
        fileContent = fromNullToCustomValue(fileContent);
        File file = new File(this.currentFile, fileName);
        BufferedWriter bw = null;
        try {
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            bw.write(fileContent);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot create the new file because: " + e.getMessage());
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                throw new IllegalStateException("Cannot close the buffer");
            }
        }
    }

    /**
     * Gets the current file.
     */
    @Override
    public File getFile() {
        return this.currentFile;
    }

    /**
     * Creates a breakpoint in the current location, a breakpoint is a valid
     * location.
     */
    @Override
    public void createBreakPoint() {
        this.currentBreakPointLocation = this.currentFile;
    }

    /**
     * By invoking this method, the pointer will go to the saved breakpoint, if
     * the breakpoint does not exist, then it does nothing.
     */
    @Override
    public void goToBreakPoint() {
        this.currentFile = this.currentBreakPointLocation;
    }

    /**
     * Get the representation string of this object.
     */
    @Override
    public String toString() {
        return this.currentFile.toString();
    }

}
