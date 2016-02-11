/*
 * COPYRIGHT. Mesofi 2014. ALL RIGHTS RESERVED.
 *
 * This software is only to be used for the purpose for which it has been
 * provided. No part of it is to be reproduced, disassembled, transmitted,
 * stored in a retrieval system nor translated in any human or computer
 * language in any way or for any other purposes whatsoever without the
 * prior written consent of Mesofi.
 */
package mx.com.mesofi.plugins.project.core.content;

import static mx.com.mesofi.services.util.SimpleValidator.isNull;
import static mx.com.mesofi.services.util.SimpleValidator.isValid;
import mx.com.mesofi.plugins.project.core.AbstractCommonBuilder;
import mx.com.mesofi.plugins.project.core.files.FileType;
import mx.com.mesofi.plugins.project.core.files.PlainFile;
import mx.com.mesofi.plugins.project.core.layers.PersistenceLayer;

public abstract class DefaultResourceFileContent implements ResourceFileContent {
    public PlainFile getProjectFileContent(PersistenceLayer persistenceLayer) {
        String javaClassType = this.getClass().getSimpleName();
        String suffixSourceName = ((AbstractCommonBuilder) persistenceLayer).getSuffixSourceName();

        String content = getFileContent(suffixSourceName);
        FileType type = getFileType();
        String name = getFileName();

        isNull(name, "You need to specify a valid name for a resource [" + javaClassType + "]");
        isNull(type, "You need to specify a valid type for a resource [" + javaClassType + "]");
        isValid(!type.equals(FileType.JAVA), "This resource type should not be Java [" + javaClassType + "]");

        return new PlainFile(type, name, content);
    }
}
