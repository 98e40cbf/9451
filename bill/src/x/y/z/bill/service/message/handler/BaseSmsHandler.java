package x.y.z.bill.service.message.handler;

import io.alpha.core.dto.ResultDTO;
import io.alpha.logging.Logger;
import io.alpha.logging.LoggerFactory;
import io.alpha.util.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import x.y.z.bill.builder.message.ResultBuilder;
import x.y.z.bill.builder.message.SmsSendRecordBuilder;
import x.y.z.bill.constant.message.ResultTypeEnum;
import x.y.z.bill.constant.message.SmsPartnerEnum;
import x.y.z.bill.model.message.SmsRecord;
import x.y.z.bill.model.message.SmsSendRecord;
import x.y.z.bill.service.message.SmsSendRecordService;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.l;

@Service
public abstract class BaseSmsHandler {
    private static Logger logger = LoggerFactory.getLogger(BaseSmsHandler.class);
    @Autowired
    private SmsSendRecordService smsSendRecordService;

    /**
     * 发送短信记录
     * @param smsRecord
     * @return
     */
    public ResultDTO<String> sendSms(SmsPartnerEnum smsPartnerEnum, SmsRecord smsRecord) {
        String identityId = ObjectId.getIdentityId();
        long smsId = smsRecord.getId();
        logger.info("{}-[发送短信记录]-{},处理开始");
        SmsSendRecord smsSendRecord = smsSendRecordService.insert(smsPartnerEnum, smsRecord.getId());
        if (smsSendRecord != null && smsSendRecord.getId() != null) {

        }

        logger.error("[发送短信记录]，处理失败");
        return ResultBuilder.buildResult(ResultTypeEnum.FAILED);
    }

    public abstract ResultDTO<String> clientSendSms(SmsRecord smsRecord);

}
