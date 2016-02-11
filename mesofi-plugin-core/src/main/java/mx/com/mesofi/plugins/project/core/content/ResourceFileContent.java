package mx.com.mesofi.plugins.project.core.content;

import mx.com.mesofi.plugins.project.core.files.PlainFile;
import mx.com.mesofi.plugins.project.core.layers.PersistenceLayer;

public interface ResourceFileContent extends FileContent {
    PlainFile getProjectFileContent(PersistenceLayer persistenceLayer);
}
