/*******************************************************************************
 * Create on 2016年1月7日 上午10:11:35
 * Copyright (c) 2014 深圳市小牛电子商务有限公司版权所有. 粤ICP备13089339号
 * 注意：本内容仅限于深圳市小牛电子商务有限公司内部传阅，禁止外泄以及用于其他商业目的!
 ******************************************************************************/
package x.y.z.bill.adapter.config;

import javax.net.ssl.SSLContext;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.alpha.core.model.BaseObject;
import io.alpha.logging.Logger;
import io.alpha.logging.LoggerFactory;

/**
 *
 */
@JsonIgnoreProperties({ "accountConfig", "trustCert", "privateCert", "sslContext", "LOGGER" })
public abstract class BaseChannelConfig extends BaseObject {
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());
    @JsonIgnore
    protected AccountConfig accountConfig;
    @JsonIgnore
    protected byte[] trustCert;
    @JsonIgnore
    protected byte[] privateCert;
    @JsonIgnore
    protected SSLContext sslContext;

    public byte[] getTrustCert() {
        return trustCert;
    }

    public void setTrustCert(byte[] trustCert) {
        this.trustCert = trustCert;
    }

    public byte[] getPrivateCert() {
        return privateCert;
    }

    public void setPrivateCert(byte[] privateCert) {
        this.privateCert = privateCert;
    }

    public AccountConfig getAccountConfig() {
        return accountConfig;
    }

    public BaseChannelConfig setAccountConfig(AccountConfig accountConfig) {
        this.accountConfig = accountConfig;
        return this;
    }

    public SSLContext getSslContext() {
        return sslContext;
    }

    public BaseChannelConfig setSslContext(SSLContext sslContext) {
        this.sslContext = sslContext;
        return this;
    }

    public abstract SSLContext initSSLContext();

    // 加载配置后，需要做什么就做什么吧！
    public abstract void afterLoadConfig();
}
