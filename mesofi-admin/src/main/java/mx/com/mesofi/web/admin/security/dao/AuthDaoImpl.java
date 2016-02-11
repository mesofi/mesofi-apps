package mx.com.mesofi.web.admin.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import mx.com.mesofi.framework.jdbc.JdbcAbstractDao;
import mx.com.mesofi.framework.jdbc.JdbcRowMapper;
import mx.com.mesofi.framework.stereotype.DAO;
import mx.com.mesofi.framework.stereotype.Inject;
import mx.com.mesofi.web.admin.security.vo.MenuVo;
import mx.com.mesofi.web.admin.vo.WebUserVo;

@DAO
public class AuthDaoImpl extends JdbcAbstractDao implements AuthDao {

    @Inject
    private Properties sqlUsersDao;

    @Inject
    public AuthDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebUserVo searchUser(WebUserVo webUserVo) {
        List<WebUserVo> list = new ArrayList<WebUserVo>();
        WebUserVo vo = null;
        String sql = sqlUsersDao.getProperty("select.all.users");
        list = query(sql, new JdbcRowMapper<WebUserVo>() {
            @Override
            public WebUserVo mapRow(ResultSet rs, int n) throws SQLException {
                WebUserVo userVo = new WebUserVo();
                userVo.setId(rs.getLong("ID"));
                userVo.setEmail(rs.getString("EMAIL"));
                userVo.setPass(rs.getString("PASSWORD"));
                return userVo;
            }
        }, webUserVo.getEmail(), webUserVo.getPass());
        vo = list.isEmpty() ? null : list.get(0);
        return vo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MenuVo> getMenus(WebUserVo webUserVo) {
        List<MenuVo> list = new ArrayList<MenuVo>();
        String sql = sqlUsersDao.getProperty("select.all.menus.per.user");
        list = query(sql, new JdbcRowMapper<MenuVo>() {
            @Override
            public MenuVo mapRow(ResultSet rs, int n) throws SQLException {
                MenuVo menuVo = new MenuVo();
                menuVo.setId(rs.getLong("ID"));
                menuVo.setIdMenuParent(rs.getLong("ID_MENU_PARENT"));
                menuVo.setMenu(rs.getString("DS_MENU"));
                return menuVo;
            }
        }, webUserVo.getId());
        return list;
    }
}
