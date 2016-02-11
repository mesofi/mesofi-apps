package mx.com.mesofi.web.response.types;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/test")
public class TestServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // String fileName = "dao.jar";
        String fileName = "eclipse-jee-kepler-SR1-win32-x86_64.zip";
        File file = new File("C:\\" + fileName);
        int bufferSize = 1024;

        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Length", String.valueOf(file.length()));
        resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        FileInputStream inputStream = null;
        ServletOutputStream sos = resp.getOutputStream();

        try {
            inputStream = new FileInputStream(file);
            byte[] buffer = new byte[bufferSize];
            int bytesRead = 0;
            do {
                bytesRead = inputStream.read(buffer, 0, buffer.length);
                sos.write(buffer, 0, bytesRead);
            } while (bytesRead == buffer.length);
            sos.flush();
        } finally {
            if (inputStream != null) {
                inputStream.close();
                sos.close();
            }
        }
    }
}
