package mx.com.mesofi.plugins.project.core.content;

import mx.com.mesofi.plugins.project.core.files.FileType;
import mx.com.mesofi.plugins.project.core.files.PlainFile;
import mx.com.mesofi.plugins.project.core.files.ProjectFile;

public abstract class DefaultJavaFileContent implements JavaFileContent {
    ProjectFile getProjectFileContent() {
        String content = getFileContent(null);
        FileType type = getFileType();
        String name = getFileName();

        return new PlainFile(type, name, content);
    }

    @Override
    public FileType getFileType() {
        return FileType.JAVA;
    }
}
