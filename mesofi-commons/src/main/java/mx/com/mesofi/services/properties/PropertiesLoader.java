/*
 * COPYRIGHT. Mesofi 2009. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.services.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import mx.com.mesofi.services.files.ClassPathFileHolder;
import mx.com.mesofi.services.util.SimpleValidator;

/**
 * Clase que funciona para leer un archivo de propierdades especificando su
 * ubicacion en el classpath.
 * 
 * @author mesofi
 */
public class PropertiesLoader {
    /**
     * Referencia estatica de esta clase.
     */
    private static PropertiesLoader prop = new PropertiesLoader();
    /**
     * Contiene las propiedades que son leidas.
     */
    private Properties propertiesFile;

    /**
     * Constructor privado.
     */
    private PropertiesLoader() {
    }

    /**
     * Obtiene una instancia de esta clase.
     * 
     * @return Instancia de esta clase.
     */
    public static PropertiesLoader getInstance() {
        return prop;
    }

    /**
     * Asigna el nombre del archivo de propiedades que sera leido.
     * 
     * @param propertyFileLocation
     *            Locacion del archivo de propiedades.
     */
    public void setResource(final String propertyFileLocation) {
        propertiesFile = getPropertiesFromMapping(propertyFileLocation);
    }

    /**
     * Obtiene el valor de la propiedad que es leida.
     * 
     * @param property
     *            Nombre de la propiedad.
     * @return Valor de la propiedad.
     */
    public String getProperty(final String property) {
        validateProperties(propertiesFile);
        return propertiesFile.getProperty(property);
    }

    /**
     * Obtiene una lista que contiene los nombres de las propiedades existentes.
     * 
     * @return Lista de nombres para las propiedades o una lista vacia si no
     *         existen propiedades almacenadas.
     */
    public List<String> getPropertyNames() {
        validateProperties(propertiesFile);
        List<String> elements = new ArrayList<String>();
        Enumeration<?> enumeration = propertiesFile.propertyNames();

        while (enumeration.hasMoreElements()) {
            elements.add(enumeration.nextElement().toString());
        }
        return elements;
    }

    /**
     * Obtiene una referencia de un objeto Properties basado en un archivo del
     * mismo tipo.
     * 
     * @param propertiesFile
     *            Nombre del archivo de propiedades.
     * @return Referencia de Properties o null, si no es valido.
     */
    private Properties getPropertiesFromMapping(final String propertiesFile) {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = ClassPathFileHolder
                    .getInputStreamFromResource(propertiesFile);
            if (inputStream == null) {
                throw new IOException(
                        "No se pudo cargar el recurso especificado con ruta: \""
                                + propertiesFile + "\"");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return properties;
    }

    /**
     * Valida que las propiedades no sean null.
     * 
     * @param properties
     *            Propiedades a ser validadas.
     */
    private void validateProperties(Properties properties) {
        SimpleValidator.isNull(properties,
                "La referencia que mantiene las propiedades es null");
    }
}
