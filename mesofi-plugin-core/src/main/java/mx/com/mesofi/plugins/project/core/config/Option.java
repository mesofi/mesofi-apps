package mx.com.mesofi.plugins.project.core.config;

import static mx.com.mesofi.services.util.SimpleValidator.isEmptyString;

public class Option {
    private String value = "";
    private String text = "";
    private boolean selected;

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
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected
     *            the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("<option");
        sb.append(" ");
        if (!isEmptyString(getValue())) {
            sb.append("value=\"");
            sb.append(getValue());
            sb.append("\"");
            sb.append(" ");
        }
        if (selected) {
            sb.append("selected");
        }
        sb.append(">");
        sb.append(getText());
        sb.append("</option>");

        return sb.toString();
    }
}
