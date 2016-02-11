package mx.com.mesofi.web.admin.security.controller;

import static mx.com.mesofi.web.util.WebContext.getInstance;

import javax.servlet.http.HttpSession;

import mx.com.mesofi.framework.context.Messages;
import mx.com.mesofi.framework.stereotype.Inject;
import mx.com.mesofi.web.admin.security.service.AuthService;
import mx.com.mesofi.web.admin.vo.WebUserVo;
import mx.com.mesofi.web.request.PageParameters;
import mx.com.mesofi.web.response.WebResponse;
import mx.com.mesofi.web.response.types.HttpResponse;
import mx.com.mesofi.web.response.types.JsonResponse;
import mx.com.mesofi.web.stereotype.Controller;
import mx.com.mesofi.web.stereotype.GET;
import mx.com.mesofi.web.stereotype.POST;
import mx.com.mesofi.web.stereotype.Path;

import org.apache.log4j.Logger;

@Controller
@Path({ "users/authentication", "welcome-user" })
public class LoginController {
    /**
     * log4j.
     */
    private static Logger log = Logger.getLogger(LoginController.class);

    @Inject
    private AuthService authService;

    @Inject
    private Messages messages;

    @POST
    public WebResponse validateCredentials(PageParameters params) {
        if (log.isDebugEnabled()) {
            log.debug("Validating user credentials...");
        }
        String email = params.getValue("email");
        String pass = params.getValue("pass");

        WebUserVo userAuthorized = null;
        userAuthorized = authService.authorizeUser(new WebUserVo(email, pass));
        getInstance().setSession("user-authorized", userAuthorized);
        return new JsonResponse("OK");
    }

    @GET
    public WebResponse welcomeUser(PageParameters params) {
        HttpSession session = getInstance().getHttpSession();
        WebUserVo userAuthorized = (WebUserVo) session.getAttribute("user-authorized");
        if (log.isDebugEnabled()) {
            log.debug("User [" + session.getAttribute("user-authorized") + "] has logged in successfully!!!...");
            log.debug("Searching menus for this user...");
        }
        authService.getMenuByUser(userAuthorized);
        return new HttpResponse(messages.getMessage("pages.main_content"));
    }
}
