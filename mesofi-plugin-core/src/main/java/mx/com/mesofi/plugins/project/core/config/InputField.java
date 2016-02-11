package mx.com.mesofi.plugins.project.core.config;

import static mx.com.mesofi.services.util.SimpleValidator.isEmptyString;

public class InputField extends Field {
    public InputField(String label) {
        this();
        this.setLabel(label);
    }

    public InputField() {
        this.setTagType("input");
        this.setType("text");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getStartingTag());
        if (!isEmptyString(getLabel())) {
            sb.append("placeHolder=\"");
            sb.append(getLabel());
            sb.append("\"");
        }
        sb.append(">");
        sb.append(getClosingTag());

        return sb.toString();
    }

    public static void main(String[] args) {
        InputField d = new InputField("sssss");
        System.out.println(d);
    }
}
