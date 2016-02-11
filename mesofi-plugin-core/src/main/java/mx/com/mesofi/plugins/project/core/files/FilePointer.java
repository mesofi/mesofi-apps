package mx.com.mesofi.plugins.project.core.files;

import java.io.File;
import java.io.InputStream;

public interface FilePointer {

    FilePointer addDir(String directoryName);

    /**
     * Returns to the initial directory.
     */
    FilePointer reset();

    FilePointer goBack();

    FilePointer goTo(String directoryName);

    FilePointer goToParent(String directoryName);

    void createFile(String fileName, String fileContent);

    void createFile(String fileName, InputStream fileContent);

    File getFile();

    void createBreakPoint();

    void goToBreakPoint();
}
