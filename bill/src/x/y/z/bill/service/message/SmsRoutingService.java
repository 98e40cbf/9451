package x.y.z.bill.service.message;

import io.alpha.logging.Logger;
import io.alpha.logging.LoggerFactory;
import io.alpha.service.BaseService;
import org.springframework.stereotype.Service;
import x.y.z.bill.constant.message.SmsPartnerEnum;

@Service
public class SmsRoutingService extends BaseService {
    private static Logger logger = LoggerFactory.getLogger(SmsRoutingService.class);

    /**
     * 获取第三方渠道信息
     * @return
     */
    public SmsPartnerEnum getSmsPartner() {
        SmsPartnerEnum smsPartnerEnum = SmsPartnerEnum.TAOBAO;
        logger.info("[获取第三方渠道信息]，处理结果:{}", smsPartnerEnum);
        return smsPartnerEnum;
    }
}
