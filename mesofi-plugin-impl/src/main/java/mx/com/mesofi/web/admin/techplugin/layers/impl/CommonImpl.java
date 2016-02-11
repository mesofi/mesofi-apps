/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.mesofi.web.admin.techplugin.layers.impl;

import java.util.List;

import mx.com.mesofi.plugins.project.core.files.PlainFile;
import mx.com.mesofi.plugins.project.core.metadata.ApplicationBuilderVo;
import mx.com.mesofi.web.admin.techplugin.layers.common.AbstractCommonUtil;

/**
 *
 * @author armando
 */
public class CommonImpl extends AbstractCommonUtil {

    public CommonImpl(ApplicationBuilderVo builder) {
        super(builder);
    }

    @Override
    public List<PlainFile> getCommonSources(String packageName) {
        return null;
    }

}
