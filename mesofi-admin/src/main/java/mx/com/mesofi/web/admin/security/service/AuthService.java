package mx.com.mesofi.web.admin.security.service;

import mx.com.mesofi.web.admin.vo.WebUserVo;

public interface AuthService {

    WebUserVo authorizeUser(WebUserVo webUserVo);

    void getMenuByUser(WebUserVo webUserVo);

}
