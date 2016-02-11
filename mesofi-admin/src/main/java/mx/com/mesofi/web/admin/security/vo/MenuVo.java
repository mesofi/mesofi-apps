package mx.com.mesofi.web.admin.security.vo;

import mx.com.mesofi.framework.util.EntityVo;

public class MenuVo extends EntityVo {
    private long idMenuParent;
    private String menu;

    /**
     * @return the idMenuParent
     */
    public long getIdMenuParent() {
        return idMenuParent;
    }

    /**
     * @param idMenuParent
     *            the idMenuParent to set
     */
    public void setIdMenuParent(long idMenuParent) {
        this.idMenuParent = idMenuParent;
    }

    /**
     * @return the menu
     */
    public String getMenu() {
        return menu;
    }

    /**
     * @param menu
     *            the menu to set
     */
    public void setMenu(String menu) {
        this.menu = menu;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MenuVo [menu=" + menu + ", getId()=" + getId() + "]";
    }

}
