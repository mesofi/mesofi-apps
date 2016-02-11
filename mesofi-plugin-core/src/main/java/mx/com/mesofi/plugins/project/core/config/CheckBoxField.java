package mx.com.mesofi.plugins.project.core.config;

public class CheckBoxField extends InputField {
    private boolean checked;

    public CheckBoxField(String label) {
        this();
        this.setLabel(label);
    }

    public CheckBoxField() {
        this.setType("checkbox");
    }

    /**
     * @return the checked
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * @param checked
     *            the checked to set
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getStartingTag());
        if (this.checked) {
            sb.append("checked");
        }
        sb.append(">");
        sb.append(getLabel());
        sb.append(getClosingTag());

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(new CheckBoxField("ss"));
    }
}
