package x.y.z.bill.model.account;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import io.alpha.core.model.BaseObject;

public class LoginHistory extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Long loginIp;

    private Date loginTime;

    private Byte device;

    private String browser;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(final Long loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(final Date loginTime) {
        this.loginTime = loginTime;
    }

    public Byte getDevice() {
        return device;
    }

    public void setDevice(final Byte device) {
        this.device = device;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(final String browser) {
        this.browser = browser == null ? null : browser.trim();
        this.browser = StringUtils.substring(this.browser, 0, 150);
    }
}