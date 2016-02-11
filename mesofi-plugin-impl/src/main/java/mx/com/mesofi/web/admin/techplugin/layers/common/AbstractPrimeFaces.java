/*
 * COPYRIGHT. Mesofi 2015. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.web.admin.techplugin.layers.common;

import mx.com.mesofi.plugins.project.core.AbstractCommonBuilder;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderVo;
import mx.com.mesofi.plugins.project.core.technologies.PrimeFaces;

/**
 * This class defaults some values for the PrimeFaces layer.
 * 
 * @author Armando Rivas
 * @since April 12 2015.
 */
public abstract class AbstractPrimeFaces extends AbstractCommonBuilder implements PrimeFaces {
    /**
     * Construct this abstract implementation based on the applicationBuilder.
     * 
     * @param builder
     *            The builder for the application.
     */
    public AbstractPrimeFaces(ApplicationBuilderVo builder) {
        super(builder);
    }

    /**
     * This method return the default suffix name for the sources, in this case
     * is 'Bean'. This suffix can be optional.
     * 
     * @return The valid suffix for the sources.
     */
    @Override
    public String getSuffixSourceName() {
        return "Bean";
    }

    /**
     * This method return the default suffix name for the package name, in this
     * case is 'beans'.
     * 
     * @return A valid suffix name.
     */
    @Override
    public String getSuffixPackageName() {
        return "beans";
    }
}
