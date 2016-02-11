/*
 * COPYRIGHT. Mesofi 2009. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.services.files;

import static mx.com.mesofi.services.util.SimpleValidator.isEmpty;
import static mx.com.mesofi.services.util.SimpleValidator.isNull;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import mx.com.mesofi.services.util.CommonConstants;
import mx.com.mesofi.services.util.SimpleValidator;

/**
 * Procesa un archivo que se encuentra dentro del classpath de la aplicacion.
 * 
 * @author mesofi.
 */
public class ClassPathFileHolder extends FileHolder {
    /**
     * Referencia hacia el archivo en el classpath.
     */
    private File file;

    /**
     * Construye un objeto para el alamcenamiento y procesamiento de archivos
     * dentro del classpath.
     * 
     * @param classPathFile
     *            Nombre del recurso dentro del classpath.
     */
    public ClassPathFileHolder(final String classPathFile) {
        String modifiedPath = classPathFile;
        isEmpty(modifiedPath, "Debe proporcionar una ruta valida en el classpath");
        if (modifiedPath.startsWith(CommonConstants.SLASH_CHAR.toString())) {
            modifiedPath = modifiedPath.substring(1);
        }
        URL url = getURIFromResource(modifiedPath);
        isNull(url, "La ruta \"" + modifiedPath + "\" no es una rauta valida existente en el classpath");
        file = new File(url.getFile().replaceAll("%20", CommonConstants.WHITE_SPACE.toString()));
        validateFile(file);
    }

    /**
     * Obtiene una referencia de URL a partir de un nombre de recurso.
     * 
     * @param resourceName
     *            Nombre del recurso.
     * @return URL del recurso o null si no hayn una entrada valida.
     */
    public static URL getURIFromResource(final String resourceName) {
        SimpleValidator.hasLength(resourceName, "Debe especificar un nombre de recurso");
        return ClassPathFileHolder.class.getClassLoader().getResource(resourceName);

    }

    /**
     * Obtiene una referencia de InputStream a partir de un nombre de recurso.
     * si el recurso inicia con '/', entonces es ignorado, quiere decir que acepta
     * recursos que inicien con '/' y sin el.
     * 
     * @param resourceName
     *            Nombre del recurso que se encuentra en el classpath.
     * @return InputStream o null si no hay una entrada valida.
     */
    public static InputStream getInputStreamFromResource(final String resourceName) {
        SimpleValidator.hasLength(resourceName, "Debe especificar un nombre de recurso");
        String internalResourceName = null;
        if (resourceName.startsWith(CommonConstants.SLASH_CHAR.toString())) {
            internalResourceName = resourceName.substring(CommonConstants.SLASH_CHAR.toString().length());
        } else {
            internalResourceName = resourceName;
        }
        return ClassPathFileHolder.class.getClassLoader().getResourceAsStream(internalResourceName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public File getFileHolder() {
        return file;
    }
}
