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

/**
 * This class represents a repository in the pom.xml.
 * 
 * @author Armando Rivas
 * @since Mar 07 2014.
 */
public final class PomRepository {
    /**
     * Repository name.
     */
    private String name;
    /**
     * Repository Id.
     */
    private String id;
    /**
     * Repository url.
     */
    private String url;

    /**
     * Creates a repository object.
     * 
     * @param name
     *            The repository name.
     * @param id
     *            The repository Id.
     * @param url
     *            URL repository.
     */
    public PomRepository(String name, String id, String url) {
        this.name = name;
        this.id = id;
        this.url = url;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the override toString method.
     */
    @Override
    public String toString() {
        return "[" + name + "/" + id + "/" + url + "]";
    }

}
