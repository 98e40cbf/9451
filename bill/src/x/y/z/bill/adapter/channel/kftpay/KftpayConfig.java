package x.y.z.bill.adapter.channel.kftpay;

import java.io.File;

import javax.net.ssl.SSLContext;

import x.y.z.bill.adapter.config.BaseChannelConfig;
import x.y.z.bill.adapter.util.IPNetworkUtil;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lycheepay.gateway.client.GBPService;
import com.lycheepay.gateway.client.KftService;
import com.lycheepay.gateway.client.security.KeystoreSignProvider;
import com.lycheepay.gateway.client.security.SignProvider;
import io.alpha.core.model.BaseObject;

/**
 * Created by cyh on 2016/3/29.
 */
@JsonIgnoreProperties({ "gbpService", "kftService", "configPath" })
public class KftpayConfig extends BaseChannelConfig {
    private String version;
    private String certType;
    private String merchantId;
    private KftConfigService authTreatyService;
    private KftConfigService confirmAuthTreatyService;
    private KftConfigService queryTreatyService;
    private KftConfigService cancelTreatyService;
    private KftConfigService rechargeService;
    private KftConfigService withdrawService;
    private KftConfigService tradeQueryService;
    private KftConfigService dishonorQueryService;
    private KftConfigService reconQueryService;
    private KftConfigService balanceQueryService;
    private KftConfigService balanceChangeQuery;
    private String certConfigPath;
    @JsonIgnore
    private volatile GBPService gbpService;
    @JsonIgnore
    private volatile KftService kftService;

    public GBPService getGbpService() throws Exception {
        if (this.gbpService == null) {
            synchronized (KftpayConfig.class) {
                if (this.gbpService == null) {
                    File pfxFile = new File(certConfigPath);
                    String pwd = super.getAccountConfig().getPrivateCertPwd();
                    SignProvider keystoreSignProvider = new KeystoreSignProvider(certType, pfxFile.getAbsolutePath(),
                            pwd.toCharArray(), null, pwd.toCharArray());
                    this.gbpService = new GBPService(keystoreSignProvider, IPNetworkUtil.getIPAddress(), "zh_CN", null);
                }
            }
        }
        return gbpService;
    }

    public KftService getKftService() throws Exception {
        if (this.kftService == null) {
            synchronized (KftpayConfig.class) {
                if (this.kftService == null) {
                    File pfxFile = new File(certConfigPath);
                    String pwd = super.getAccountConfig().getPrivateCertPwd();
                    SignProvider keystoreSignProvider = null;
                    keystoreSignProvider = new KeystoreSignProvider(certType, pfxFile.getAbsolutePath(),
                            pwd.toCharArray(), null, pwd.toCharArray());
                    this.kftService = new KftService(keystoreSignProvider, IPNetworkUtil.getIPAddress(), "zh_CN", null);
                }
            }
        }
        return kftService;
    }

    @Override
    public SSLContext initSSLContext() {
        return null;
    }

    @Override
    public void afterLoadConfig() {

    }

    public String getVersion() {
        return version;
    }

    public KftpayConfig setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public KftpayConfig setMerchantId(String merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public KftConfigService getRechargeService() {
        return rechargeService;
    }

    public KftpayConfig setRechargeService(KftConfigService rechargeService) {
        this.rechargeService = rechargeService;
        return this;
    }

    public KftConfigService getWithdrawService() {
        return withdrawService;
    }

    public KftpayConfig setWithdrawService(KftConfigService withdrawService) {
        this.withdrawService = withdrawService;
        return this;
    }

    public KftConfigService getTradeQueryService() {
        return tradeQueryService;
    }

    public KftpayConfig setTradeQueryService(KftConfigService tradeQueryService) {
        this.tradeQueryService = tradeQueryService;
        return this;
    }

    public KftConfigService getDishonorQueryService() {
        return dishonorQueryService;
    }

    public KftpayConfig setDishonorQueryService(KftConfigService dishonorQueryService) {
        this.dishonorQueryService = dishonorQueryService;
        return this;
    }

    public KftConfigService getReconQueryService() {
        return reconQueryService;
    }

    public KftpayConfig setReconQueryService(KftConfigService reconQueryService) {
        this.reconQueryService = reconQueryService;
        return this;
    }

    public KftConfigService getBalanceQueryService() {
        return balanceQueryService;
    }

    public KftpayConfig setBalanceQueryService(KftConfigService balanceQueryService) {
        this.balanceQueryService = balanceQueryService;
        return this;
    }

    public KftConfigService getBalanceChangeQuery() {
        return balanceChangeQuery;
    }

    public KftpayConfig setBalanceChangeQuery(KftConfigService balanceChangeQuery) {
        this.balanceChangeQuery = balanceChangeQuery;
        return this;
    }

    public String getCertType() {
        return certType;
    }

    public KftpayConfig setCertType(String certType) {
        this.certType = certType;
        return this;
    }

    public String getCertConfigPath() {
        return certConfigPath;
    }

    public KftpayConfig setCertConfigPath(String certConfigPath) {
        this.certConfigPath = certConfigPath;
        return this;
    }

    public KftConfigService getAuthTreatyService() {
        return authTreatyService;
    }

    public KftpayConfig setAuthTreatyService(KftConfigService authTreatyService) {
        this.authTreatyService = authTreatyService;
        return this;
    }

    public KftConfigService getConfirmAuthTreatyService() {
        return confirmAuthTreatyService;
    }

    public KftpayConfig setConfirmAuthTreatyService(KftConfigService confirmAuthTreatyService) {
        this.confirmAuthTreatyService = confirmAuthTreatyService;
        return this;
    }

    public KftConfigService getQueryTreatyService() {
        return queryTreatyService;
    }

    public KftpayConfig setQueryTreatyService(KftConfigService queryTreatyService) {
        this.queryTreatyService = queryTreatyService;
        return this;
    }

    public KftConfigService getCancelTreatyService() {
        return cancelTreatyService;
    }

    public KftpayConfig setCancelTreatyService(KftConfigService cancelTreatyService) {
        this.cancelTreatyService = cancelTreatyService;
        return this;
    }

    public static class KftConfigService extends BaseObject {

        private String serviceName;
        private String productNo;
        private String downloadDirectory;

        public KftConfigService() {
        }

        public KftConfigService(String serviceName) {
            this.serviceName = serviceName;
        }

        public KftConfigService(String serviceName, String productNo) {
            this.serviceName = serviceName;
            this.productNo = productNo;
        }

        public KftConfigService(String serviceName, String productNo, String downloadDirectory) {
            this.serviceName = serviceName;
            this.productNo = productNo;
            this.downloadDirectory = downloadDirectory;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getProductNo() {
            return productNo;
        }

        public void setProductNo(String productNo) {
            this.productNo = productNo;
        }

        public String getDownloadDirectory() {
            return downloadDirectory;
        }

        public void setDownloadDirectory(String downloadDirectory) {
            this.downloadDirectory = downloadDirectory;
        }
    }
}
