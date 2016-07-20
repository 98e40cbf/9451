package x.y.z.bill.adapter.config;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import x.y.z.bill.adapter.channel.bill99.Bill99Config;
import x.y.z.bill.adapter.channel.kftpay.KftpayConfig;
import x.y.z.bill.adapter.constant.ChannelCode;
import io.alpha.service.BaseService;
import io.alpha.util.PropertiesUtils;

@Service
public class ConfigManager extends BaseService {

    private static final Map<String, BaseChannelConfig> CHANNEL_CONFIG_MAPPING = new ConcurrentHashMap<>();

    @PostConstruct
    private void loadBankCardBinConfig() throws IOException {
        logger.info("------ 系统初始化加载支付渠道配置文件 ------ ");
        Properties properties = PropertiesUtils.loadProperties("custom_config.properties");
        Bill99Config bill99Config = loadBill99Config(properties);
        KftpayConfig kftpayConfig = loadKftpayConfig(properties);

        CHANNEL_CONFIG_MAPPING.put(bill99Config.getAccountConfig().getChannelCode(), bill99Config);
        CHANNEL_CONFIG_MAPPING.put(kftpayConfig.getAccountConfig().getChannelCode(), kftpayConfig);
    }

    private KftpayConfig loadKftpayConfig(Properties properties) throws IOException {
        KftpayConfig config = new KftpayConfig();

        AccountConfig accountConfig = new AccountConfig();
        accountConfig.setChannelCode(ChannelCode.KFTPAY.getCode());
        accountConfig.setChannelName(ChannelCode.KFTPAY.getDesc());
        accountConfig.setPrivateCertPwd(properties.getProperty("payment.channel.kftpay.private.cert.password"));
        config.setAccountConfig(accountConfig);

        byte[] privateCert = FileUtils
                .readFileToByteArray(new File(properties.getProperty("payment.channel.kftpay.private.cert.path")));
        config.setPrivateCert(privateCert);

        config.setVersion(properties.getProperty("payment.channel.kftpay.version"));
        config.setMerchantId(properties.getProperty("payment.channel.kftpay.merchant.id"));
        config.setCertType("PKCS12");
        config.setCertConfigPath(properties.getProperty("payment.channel.kftpay.private.cert.path"));

        config.setAuthTreatyService(new KftpayConfig.KftConfigService("gbp_treaty_collect_apply"));
        config.setConfirmAuthTreatyService(new KftpayConfig.KftConfigService("gbp_confirm_treaty_collect_apply"));
        config.setQueryTreatyService(new KftpayConfig.KftConfigService("gbp_query_treaty_info"));
        config.setCancelTreatyService(new KftpayConfig.KftConfigService("gbp_cancel_treaty_info"));

        config.setRechargeService(new KftpayConfig.KftConfigService("gbp_treaty_collect", "2ACB0BAB"));
        config.setWithdrawService(new KftpayConfig.KftConfigService("pay_to_bank_account", "2BA00BBB"));
        config.setTradeQueryService(new KftpayConfig.KftConfigService("trade_record_query", "2GCA0AAZ"));
        config.setDishonorQueryService(
                new KftpayConfig.KftConfigService("dishonor_query", "2GCA0AAZ", "/data/app-6010-payment/dishonorFile"));
        config.setReconQueryService(new KftpayConfig.KftConfigService("recon_result_query", "2GCC0AAA",
                "/data/app-6010-payment/reconFile"));
        config.setBalanceQueryService(new KftpayConfig.KftConfigService("query_available_balance", "2GCB0AAA"));
        config.setBalanceChangeQuery(new KftpayConfig.KftConfigService("capital_account_balance_change_query",
                "2GCB0AAB", "/data/app-6010-payment/balanceChangeFile"));

        return config;
    }

    private Bill99Config loadBill99Config(Properties properties) throws IOException {
        Bill99Config config = new Bill99Config();
        AccountConfig accountConfig = new AccountConfig();
        accountConfig.setChannelCode(ChannelCode.BILL99.getCode());
        accountConfig.setChannelName(ChannelCode.BILL99.getDesc());
        accountConfig.setPrivateCertPwd(properties.getProperty("payment.channel.bill99.private.cert.password"));
        config.setAccountConfig(accountConfig);
        byte[] trustCert = FileUtils
                .readFileToByteArray(new File(properties.getProperty("payment.channel.bill99.trust.cert.path")));
        byte[] privateCert = FileUtils
                .readFileToByteArray(new File(properties.getProperty("payment.channel.bill99.private.cert.path")));
        config.setTrustCert(trustCert);
        config.setPrivateCert(privateCert);
        config.setMerchantId(properties.getProperty("payment.channel.bill99.merchant.id"));
        config.setAuthTerminalId(properties.getProperty("payment.channel.bill99.auth.terminal.id"));
        config.setOneKeyPayTerminalId(properties.getProperty("payment.channel.bill99.one.key.pay.terminal.id"));
        config.setIndieAuthUrl(properties.getProperty("payment.channel.bill99.indie.auth.url"));
        config.setIndieAuthVerifyUrl(properties.getProperty("payment.channel.bill99.indie.auth.verify.url"));
        config.setQuickPayUrl(properties.getProperty("payment.channel.bill99.quick.pay.url"));
        config.setQuickPayVerifyUrl(properties.getProperty("payment.channel.bill99.quick.pay.verify.url"));
        config.setOneKeyPayUrl(properties.getProperty("payment.channel.bill99.one.key.pay.url"));
        config.setPciQueryUrl(properties.getProperty("payment.channel.bill99.pci.query.url"));
        config.setPciDeleteUrl(properties.getProperty("payment.channel.bill99.pci.delete.url"));
        config.setOrderQueryUrl(properties.getProperty("payment.channel.bill99.order.query.url"));
        config.setCallbackUrl(properties.getProperty("payment.channel.bill99.callback.url"));

        config.setSslContext(config.initSSLContext());
        return config;
    }

    public BaseChannelConfig getConfig(String channelCode) {
        return CHANNEL_CONFIG_MAPPING.get(channelCode);
    }
}
