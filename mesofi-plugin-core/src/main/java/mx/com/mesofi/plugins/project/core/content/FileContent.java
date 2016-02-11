package mx.com.mesofi.plugins.project.core.content;

import mx.com.mesofi.plugins.project.core.files.FileType;

public interface FileContent {
    String getFileContent(String suffixSourceName);

    String getFileName();

    FileType getFileType();
}
