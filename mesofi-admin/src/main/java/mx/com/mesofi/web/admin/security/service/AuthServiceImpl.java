package mx.com.mesofi.web.admin.security.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.mesofi.framework.context.Messages;
import mx.com.mesofi.framework.email.Composer;
import mx.com.mesofi.framework.email.MailComposerService;
import mx.com.mesofi.framework.error.ValidationBusinessException;
import mx.com.mesofi.framework.stereotype.Inject;
import mx.com.mesofi.framework.stereotype.Service;
import mx.com.mesofi.web.admin.security.dao.AuthDao;
import mx.com.mesofi.web.admin.security.vo.MenuVo;
import mx.com.mesofi.web.admin.vo.WebUserVo;

import org.apache.log4j.Logger;

@Service
public class AuthServiceImpl implements AuthService {
    /**
     * log4j.
     */
    private static Logger log = Logger.getLogger(AuthServiceImpl.class);

    @Inject
    private AuthDao authDao;

    @Inject
    private Messages messages;

    @Inject
    private MailComposerService mail;

    @Override
    public WebUserVo authorizeUser(WebUserVo webUserVo) {
        WebUserVo vo = null;
        vo = authDao.searchUser(webUserVo);
        if (vo == null) {
            String msg = messages.getMessage("security.user_not_found");
            throw new ValidationBusinessException(msg);
        }
        // save in logs
        // send an email
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user", "ssssaa");

        Composer composer = mail.createMailComposer();
        composer.addTo("rivasarmando271084@gmail.com");
        composer.setBodyTemplate("mesofi.config.email.welcome", map);
        composer.setSubject("Welcome...");
        //composer.setBody("sss");
        composer.addAttachment(new File("/Users/armando/SkyDrive/Documents/mesofi/mesofi-apps/mesofi-framework/pom.xml"));
        composer.addAttachment(new File("/Users/armando/SkyDrive/Documents/mesofi/mesofi-apps/mesofi-framework/src/main/java/mx/com/mesofi/framework/email/Composer.java"));
        mail.send(composer);

        return vo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getMenuByUser(WebUserVo webUserVo) {
        List<MenuVo> menuVo = authDao.getMenus(webUserVo);

        log.debug(menuVo);
    }
}
