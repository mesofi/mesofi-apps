/*
 * COPYRIGHT. Mesofi 2015. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.services.files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import mx.com.mesofi.services.files.exception.FileAccessRuntimeException;

/**
 * Finds all the files for a given source.
 * 
 * @author Armando Rivas.
 * @since Jan 06 2015
 */
public class FileFinder {
    /**
     * list files in the given directory and subdirs (with recursion)
     * 
     * @param paths
     * @return
     */
    public static List<File> getFiles(String paths) {
        List<File> filesList = new ArrayList<File>();
        for (final String path : paths.split(File.pathSeparator)) {
            final File file = new File(path);
            if (file.isDirectory()) {
                recurse(filesList, file);
            } else {
                filesList.add(file);
            }
        }
        return filesList;
    }

    private static void recurse(List<File> filesList, File f) {
        File list[] = f.listFiles();
        for (File file : list) {
            if (file.isDirectory()) {
                recurse(filesList, file);
            } else {
                filesList.add(file);
            }
        }
    }

    /**
     * Gets a map containing the resource name and the content asociated to this
     * resources.
     * 
     * @param jarPath
     *            The absolute path for the jar file.
     * @param filterByExtension
     *            Extension for files
     * @return All the resources.
     */
    public static Map<String, String> getJarContent(String jarPath, String filterByExtension) {
        Map<String, String> jarContent = new HashMap<String, String>();
        try {
            JarFile jarFile = new JarFile(jarPath);
            Enumeration<JarEntry> e = jarFile.entries();
            JarEntry entry = null;
            String content = null;
            FileProcessor fileProcessor = new FileProcessor();
            while (e.hasMoreElements()) {
                entry = (JarEntry) e.nextElement();
                if (!entry.isDirectory()) {
                    if (entry.getName().endsWith(filterByExtension)) {
                        content = fileProcessor.readPlainContentByLine(jarFile.getInputStream(entry));
                        jarContent.put(entry.getName(), content);
                    }
                }
            }
            jarFile.close();
        } catch (IOException e) {
            throw new FileAccessRuntimeException("Could not read from this resource: [" + jarPath + "] due to:" + e);
        }
        return jarContent;
    }
}
