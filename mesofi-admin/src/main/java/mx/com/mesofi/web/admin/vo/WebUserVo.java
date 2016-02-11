package mx.com.mesofi.web.admin.vo;

import mx.com.mesofi.framework.util.EntityVo;

public class WebUserVo extends EntityVo {

    private String email;
    private String pass;

    public WebUserVo(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public WebUserVo() {

    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass
     *            the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "WebUserVo [id=" + getId() + ", email=" + email + ", pass=" + pass + "]";
    }

}
