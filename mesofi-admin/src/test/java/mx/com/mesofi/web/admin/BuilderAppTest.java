package mx.com.mesofi.web.admin;

import java.util.HashMap;
import java.util.Map;

import mx.com.mesofi.web.admin.builder.controller.BuilderAppController;
import mx.com.mesofi.web.request.PageParameters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles(profiles = "dev")
@ContextConfiguration(locations = { "classpath*:mx/com/mesofi/mesofi-admin-applicationContext.xml",
        "classpath*:mx/com/mesofi/springframework/applicationContext.xml",
        "classpath*:mx/com/mesofi/springframework/**/*-config.xml", "classpath*:mx/com/mesofi/**/*-dao.xml" })
public class BuilderAppTest {

    @Autowired
    private BuilderAppController builderAppController;

    @Test
    public void testUserNotNull() {
        Map<String, String[]> parameterMap = new HashMap<String, String[]>();
        parameterMap.put("conn-selected", new String[] { "19" });
        parameterMap.put("plugin-selected", new String[] { "3" });

        parameterMap.put("webProjectName", new String[] { "my first project" });
        parameterMap.put("webGroupId", new String[] { "mx.com.mesofi" });
        parameterMap.put("webArtifactId", new String[] { "sample" });
        parameterMap.put("webVersion", new String[] { "1.0" });

        parameterMap.put("mainModuleName", new String[] { "login" });
        parameterMap.put("authModuleName", new String[] { "catalogs" });

        parameterMap.put("webAdditionalConfig", new String[] { "true" });
        parameterMap.put("webFieldType", new String[] { "|checkbox~createHibernateConfig|checkbox~annotatedClasses" });
        parameterMap.put("annotatedClasses", new String[] { "on" });

        PageParameters pageParameters = new PageParameters(parameterMap);
        builderAppController.processApp(pageParameters);
    }
}
