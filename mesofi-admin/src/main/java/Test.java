import java.util.Properties;

public class Test {
    public static void main(String[] args) {
        Properties properties = System.getProperties();
        for (Object s : properties.keySet()) {
            System.out.println(s + "=>" + System.getProperty(s.toString()));

        }
    }
}
