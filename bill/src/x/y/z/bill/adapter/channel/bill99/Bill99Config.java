package x.y.z.bill.adapter.channel.bill99;

import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import x.y.z.bill.adapter.config.BaseChannelConfig;

/**
 * 快钱的配置文件
 */
public class Bill99Config extends BaseChannelConfig {

    private String merchantId;
    private String authTerminalId;
    private String oneKeyPayTerminalId;
    private String indieAuthUrl;
    private String indieAuthVerifyUrl;
    private String quickPayUrl;
    private String quickPayVerifyUrl;
    private String oneKeyPayUrl;
    private String pciQueryUrl;
    private String pciDeleteUrl;
    private String orderQueryUrl;
    private String callbackUrl;
    private String billDownloadUrl;
    private String billAccount;
    private String billPwd;
    private String billCacheDir;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getAuthTerminalId() {
        return authTerminalId;
    }

    public void setAuthTerminalId(String authTerminalId) {
        this.authTerminalId = authTerminalId;
    }

    public String getOneKeyPayTerminalId() {
        return oneKeyPayTerminalId;
    }

    public void setOneKeyPayTerminalId(String oneKeyPayTerminalId) {
        this.oneKeyPayTerminalId = oneKeyPayTerminalId;
    }

    public String getIndieAuthUrl() {
        return indieAuthUrl;
    }

    public void setIndieAuthUrl(String indieAuthUrl) {
        this.indieAuthUrl = indieAuthUrl;
    }

    public String getIndieAuthVerifyUrl() {
        return indieAuthVerifyUrl;
    }

    public void setIndieAuthVerifyUrl(String indieAuthVerifyUrl) {
        this.indieAuthVerifyUrl = indieAuthVerifyUrl;
    }

    public String getQuickPayUrl() {
        return quickPayUrl;
    }

    public void setQuickPayUrl(String quickPayUrl) {
        this.quickPayUrl = quickPayUrl;
    }

    public String getQuickPayVerifyUrl() {
        return quickPayVerifyUrl;
    }

    public void setQuickPayVerifyUrl(String quickPayVerifyUrl) {
        this.quickPayVerifyUrl = quickPayVerifyUrl;
    }

    public String getOneKeyPayUrl() {
        return oneKeyPayUrl;
    }

    public void setOneKeyPayUrl(String oneKeyPayUrl) {
        this.oneKeyPayUrl = oneKeyPayUrl;
    }

    public String getPciQueryUrl() {
        return pciQueryUrl;
    }

    public void setPciQueryUrl(String pciQueryUrl) {
        this.pciQueryUrl = pciQueryUrl;
    }

    public String getPciDeleteUrl() {
        return pciDeleteUrl;
    }

    public void setPciDeleteUrl(String pciDeleteUrl) {
        this.pciDeleteUrl = pciDeleteUrl;
    }

    public String getOrderQueryUrl() {
        return orderQueryUrl;
    }

    public void setOrderQueryUrl(String orderQueryUrl) {
        this.orderQueryUrl = orderQueryUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getBillDownloadUrl() {
        return billDownloadUrl;
    }

    public void setBillDownloadUrl(String billDownloadUrl) {
        this.billDownloadUrl = billDownloadUrl;
    }

    public String getBillAccount() {
        return billAccount;
    }

    public void setBillAccount(String billAccount) {
        this.billAccount = billAccount;
    }

    public String getBillPwd() {
        return billPwd;
    }

    public void setBillPwd(String billPwd) {
        this.billPwd = billPwd;
    }

    public String getBillCacheDir() {
        return billCacheDir;
    }

    public void setBillCacheDir(String billCacheDir) {
        this.billCacheDir = billCacheDir;
    }

    @Override
    public SSLContext initSSLContext() {
        SSLContext sslContext = null;
        String password = this.getAccountConfig().getPrivateCertPwd();
        try {
            // 验证密钥源
            // 访问Java密钥库，JKS是keytool创建的Java密钥库，保存密钥。
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new ByteArrayInputStream(this.getPrivateCert()), password.toCharArray());
            // 创建用于管理JKS密钥库的密钥管理器
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, password.toCharArray());
            // 同位体验证可信任的证书来源
            TrustManager[] tm = { new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            } };
            // 初始化安全套接字（构造SSL环境，指定SSL版本为3.0）
            sslContext = SSLContext.getInstance("SSL");
            // 初始化SSL环境。第二个参数是告诉JSSE使用的可信任证书的来源，设置为null是从javax.net.ssl.trustStore中获得证书。
            // 第三个参数是JSSE生成的随机数，这个参数将影响系统的安全性，设置为null是个好选择，可以保证JSSE的安全性。
            sslContext.init(kmf.getKeyManagers(), tm, null);
        } catch (Exception e) {
            logger.error("初始化快钱 SSLContext出错.", e);
        }
        return sslContext;
    }

    @Override
    public void afterLoadConfig() {

    }
}
