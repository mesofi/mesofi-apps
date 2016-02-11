/*
 * COPYRIGHT. Mesofi 2015. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.services.zip;

import static mx.com.mesofi.services.util.CommonConstants.SLASH_CHAR;
import static mx.com.mesofi.services.util.CommonConstants.UNKNOWN_CHAR;
import static mx.com.mesofi.services.util.SimpleValidator.isNull;
import static mx.com.mesofi.services.util.SimpleValidator.isValid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import mx.com.mesofi.services.files.FileUtil;

/**
 * Clase que funciona para leer un archivo de propierdades especificando su
 * ubicacion en el classpath.
 * 
 * @author mesofi
 */
public class ZipUtil {
    /**
     * Referencia estatica de esta clase.
     */
    private static ZipUtil zip = new ZipUtil();

    /**
     * Constructor privado.
     */
    private ZipUtil() {
    }

    /**
     * Obtiene una instancia de esta clase.
     * 
     * @return Instancia de esta clase.
     */
    public static ZipUtil getInstance() {
        return zip;
    }

    /**
     * Zip the current folder in the file system.
     * 
     * @param folderName
     *            The folder to be zipped.
     */
    public void zipFolder(File folderName) {
        isNull(folderName, "The folder name must not be null");
        isValid(folderName.isDirectory() && folderName.exists(), "Please specify a valid directory to zip");

        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;

        try {
            fileWriter = new FileOutputStream(folderName + ".zip");
            zip = new ZipOutputStream(fileWriter);
            addFolderToZip(UNKNOWN_CHAR.toString(), folderName.getPath(), zip);
            zip.flush();
        } catch (Exception e) {
            isValid(false, e.getMessage());
        } finally {
            FileUtil.close(zip);
            FileUtil.close(fileWriter);
        }
    }

    /**
     * Add a folder into the zip file.
     * 
     * @param path
     *            The path.
     * @param srcFolder
     *            The source folder.
     * @param zipOutputStream
     *            The actual zip file.
     */
    private void addFolderToZip(String path, String srcFolder, ZipOutputStream zipOutputStream) {
        File folder = new File(srcFolder);

        for (String fileName : folder.list()) {
            if (path.equals(UNKNOWN_CHAR.toString())) {
                addFileToZip(folder.getName(), srcFolder + SLASH_CHAR + fileName, zipOutputStream);
            } else {
                addFileToZip(path + SLASH_CHAR + folder.getName(), srcFolder + SLASH_CHAR + fileName, zipOutputStream);
            }
        }
    }

    /**
     * Add a file into the zip file.
     * 
     * @param path
     *            The path.
     * @param srcFile
     *            The source file.
     * @param zip
     *            The actual zip file.
     */
    private void addFileToZip(String path, String srcFile, ZipOutputStream zip) {
        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            addFolderToZip(path, srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            int len = 0;
            FileInputStream in = null;
            try {
                in = new FileInputStream(srcFile);
                zip.putNextEntry(new ZipEntry(path + SLASH_CHAR + folder.getName()));
                while ((len = in.read(buf)) > 0) {
                    zip.write(buf, 0, len);
                }
            } catch (Exception e) {
                isValid(false, e.getMessage());
            } finally {
                FileUtil.close(in);
            }
        }
    }
}
