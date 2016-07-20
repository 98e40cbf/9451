package x.y.z.bill.adapter.config;

import io.alpha.core.model.BaseObject;

/**
 *
 */
public class AccountConfig extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String channelCode;
    private String channelName;
    private String privateCertPwd;
    private String privateCertAlias;

    public String getPrivateCertAlias() {
        return privateCertAlias;
    }

    public void setPrivateCertAlias(String privateCertAlias) {
        this.privateCertAlias = privateCertAlias;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public AccountConfig setChannelCode(String channelCode) {
        this.channelCode = channelCode;
        return this;
    }

    public String getChannelName() {
        return channelName;
    }

    public AccountConfig setChannelName(String channelName) {
        this.channelName = channelName;
        return this;
    }

    public String getPrivateCertPwd() {
        return privateCertPwd;
    }

    public AccountConfig setPrivateCertPwd(String privateCertPwd) {
        this.privateCertPwd = privateCertPwd;
        return this;
    }
}
