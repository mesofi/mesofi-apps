package mx.com.mesofi.plugins.project.core.files;

public enum FileType {
    JAVA, XML, JSP, XHTML, PROPERTIES, CSS, JS, PNG, JPG, GIF, EOT, SVG, TTF, WOFF, LAUNCH, TXT, NO_EXTENSION;
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.valueOf(this.name().toLowerCase());
    }
}
