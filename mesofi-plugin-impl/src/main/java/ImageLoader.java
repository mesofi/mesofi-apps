import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import mx.com.mesofi.framework.util.FrameworkUtils;

public class ImageLoader {
    public static void main(String[] args) {
        System.out.println("ssss");
        String test = "test1";

        String cmd = null;
        InputStream is = null;
        final String image = "mx/com/mesofi/web/admin/techplugin/resources/images/book.png";

        if ("test1".equals(test)) {
            cmd = "ImageLoader.class.getClassLoader().getResourceAsStream(\"" + image + "\")";
           // is = ImageLoader.class.getClassLoader().getResourceAsStream(image); // YES,
            is = FrameworkUtils.class.getClassLoader().getResourceAsStream(image); // FOUND

        } else if ("test2".equals(test)) {
            cmd = "ImageLoader.class.getResourceAsStream(\"" + image + "\")";
            is = ImageLoader.class.getResourceAsStream(image); // NOT FOUND

        } else if ("test3".equals(test)) {
            cmd = "ImageLoader.class.getResourceAsStream(\"/" + image + "\")";
            is = ImageLoader.class.getResourceAsStream("/" + image); // YES,
                                                                     // FOUND

        } else if ("test4".equals(test)) {
            cmd = "ImageLoader.class.getClassLoader().getResourceAsStream(\"/" + image + "\")";
            is = ImageLoader.class.getClassLoader().getResourceAsStream("/" + image); // NOT
                                                                                      // FOUND

        } else {
            cmd = " ? ";
        }

        System.out.println("With " + cmd + ", stream loaded: " + (is != null));
        Path target = Paths.get("/Users/armando/OneDrive/Documents/mesofi/mesofi-apps/aa");
        try {
            Files.copy(is, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
