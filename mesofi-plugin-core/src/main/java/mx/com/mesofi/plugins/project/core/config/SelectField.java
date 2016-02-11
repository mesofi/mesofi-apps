package mx.com.mesofi.plugins.project.core.config;

import static mx.com.mesofi.services.util.SimpleValidator.isEmptyString;

import java.util.ArrayList;
import java.util.List;

public class SelectField extends Field {

    private List<Option> options = new ArrayList<Option>();

    public SelectField() {
        this.setTagType("select");

        options.add(new Option());
        options.add(new Option());
    }

    /**
     * @return the options
     */
    public List<Option> getOptions() {
        return options;
    }

    /**
     * @param options
     *            the options to set
     */
    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public void addOption(Option newOption) {
        this.options.add(newOption);
    }

    public void removeAllOptions() {
        this.options = new ArrayList<Option>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("<");
        sb.append(getTagType());
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
        }
        sb.append(">");
        for (Option option : options) {
            sb.append(option);
        }
        sb.append(getClosingTag());

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(new SelectField());
    }
}
