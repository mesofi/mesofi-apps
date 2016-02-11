package mx.com.mesofi.web.admin.security.dao;

import java.util.List;

import mx.com.mesofi.web.admin.security.vo.MenuVo;
import mx.com.mesofi.web.admin.vo.WebUserVo;

public interface AuthDao {

    WebUserVo searchUser(WebUserVo webUserVo);

    List<MenuVo> getMenus(WebUserVo webUserVo);

}
