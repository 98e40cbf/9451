package x.y.z.bill.model.account;

import java.util.Date;

import io.alpha.core.model.BaseObject;

public class User extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String mobile;

    private String email = "n/a";

    private String loginPwd;

    private Date createTime = new Date();

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(final String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(final String loginPwd) {
        this.loginPwd = loginPwd == null ? null : loginPwd.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(final Date createTime) {
        this.createTime = createTime;
    }
}