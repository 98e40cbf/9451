package x.y.z.bill.service.message.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.alpha.core.dto.ResultDTO;
import io.alpha.logging.Logger;
import io.alpha.logging.LoggerFactory;
import io.alpha.util.ObjectId;
import x.y.z.bill.builder.message.ResultBuilder;
import x.y.z.bill.constant.message.ResultTypeEnum;
import x.y.z.bill.constant.message.SmsBizTypeEnum;
import x.y.z.bill.constant.message.SmsPartnerEnum;
import x.y.z.bill.model.message.SmsRecord;
import x.y.z.bill.model.message.SmsSendRecord;
import x.y.z.bill.service.message.SmsSendRecordService;

@Service
public abstract class BaseSmsHandler {
    private static Logger logger = LoggerFactory.getLogger(BaseSmsHandler.class);
    @Autowired
    private SmsSendRecordService smsSendRecordService;

    /**
     * 发送短信记录
     * 
     * @param smsRecord
     * @return
     */
    public ResultDTO<String> sendSms(SmsPartnerEnum smsPartnerEnum, SmsRecord smsRecord) {
        String identityId = ObjectId.getIdentityId();
        long smsId = smsRecord.getId();
        logger.info("{}-[发送短信记录]-{},处理开始", identityId, smsId);
        SmsSendRecord smsSendRecord = smsSendRecordService.insert(smsPartnerEnum, smsRecord.getId());
        ResultDTO<String> result = ResultBuilder.buildResult(ResultTypeEnum.FAILED);
        if (smsSendRecord == null && smsSendRecord.getId() == null) {
            logger.error("{}-[发送短信记录]-{}，短信发送记录新增失败:{}", identityId, smsId, result);
            return result;
        }

        long smsSendRecordId = smsSendRecord.getId();
        SmsBizTypeEnum smsBizTypeEnum = SmsBizTypeEnum.getEnumByType(smsRecord.getSmsBizType());
        switch (smsBizTypeEnum) {
            case VOICE:
                result = clientSendVoiceSms(smsRecord);
                break;
            case TEXT:
            default:
                result = clientSendSms(smsRecord);
                break;
        }

        smsSendRecordService.update(smsSendRecordId, result);
        logger.info("{}-[发送短信记录]-{}，处理结果:{}", identityId, smsId, result);
        return result;
    }

    public abstract ResultDTO<String> clientSendSms(SmsRecord smsRecord);

    public abstract ResultDTO<String> clientSendVoiceSms(SmsRecord smsRecord);

}
