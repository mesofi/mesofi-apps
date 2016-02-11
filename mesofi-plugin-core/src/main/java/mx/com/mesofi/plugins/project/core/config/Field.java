package mx.com.mesofi.plugins.project.core.config;

import static mx.com.mesofi.services.util.SimpleCommonActions.randomNumber;
import static mx.com.mesofi.services.util.SimpleValidator.isEmptyString;

public abstract class Field {
    private String tagType = "";
    private String type = "";
    private String label = "";
    private String id = "";
    private String name = "";
    private String value = "";
    private String classCss = "";

    public Field() {
        this.id = "default" + randomNumber(100);
    }

    /**
     * @return the tagType
     */
    public String getTagType() {
        return tagType;
    }

    /**
     * @param tagType
     *            the tagType to set
     */
    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label
     *            the label to set
     */
    public void setLabel(String label) {
        this.label = label;
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
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the classCss
     */
    public String getClassCss() {
        return classCss;
    }

    /**
     * @param classCss
     *            the classCss to set
     */
    public void setClassCss(String classCss) {
        this.classCss = classCss;
    }

    String getStartingTag() {
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        sb.append(getTagType());
        sb.append(" ");
        sb.append("type=\"");
        sb.append(getType());
        sb.append("\"");
        sb.append(" ");
        if (!isEmptyString(getId())) {
            sb.append("id=\"");
            sb.append(getId());
            sb.append("\"");
            sb.append(" ");
        }
        if (!isEmptyString(getName())) {
            sb.append("name=\"");
            sb.append(getName());
            sb.append("\"");
            sb.append(" ");
        }
        if (!isEmptyString(getValue())) {
            sb.append("value=\"");
            sb.append(getValue());
            sb.append("\"");
        }
        if (!isEmptyString(getClassCss())) {
            sb.append("class=\"");
            sb.append(getClassCss());
            sb.append("\"");
        }
        return sb.toString();
    }

    String getClosingTag() {
        StringBuilder sb = new StringBuilder();
        sb.append("</");
        sb.append(getTagType());
        sb.append(">");
        return sb.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((classCss == null) ? 0 : classCss.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((tagType == null) ? 0 : tagType.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }
}
