package x.y.z.bill.builder.message;

import java.util.Date;

import x.y.z.bill.constant.message.SmsBizTypeEnum;
import x.y.z.bill.constant.message.SmsPriority;
import x.y.z.bill.constant.message.SmsStatusEnum;
import x.y.z.bill.constant.message.SmsTypeEnum;
import x.y.z.bill.dto.message.SmsDTO;
import x.y.z.bill.model.message.SmsParam;
import x.y.z.bill.model.message.SmsRecord;
import io.alpha.util.JsonUtils;

public final class SmsRecordBuilder {
    private SmsRecordBuilder() {

    }

    public static SmsRecord build(SmsDTO smsDTO, final SmsParam smsParam) {
        SmsRecord smsRecord = new SmsRecord();
        smsRecord.setTxnId(smsDTO.getTxnId());
        SmsTypeEnum smsTypeEnum = smsDTO.getSmsTypeEnum();
        smsRecord.setSmsType(smsTypeEnum.getType());
        smsRecord.setSmsStatus(SmsStatusEnum.SUCCESS.getStatus());
        smsRecord.setReceiveMobiles(smsDTO.getMobiles());
        if (smsParam != null) {
            smsRecord.setSmsParam(JsonUtils.toJSON(smsParam));
        }

        SmsBizTypeEnum smsBizTypeEnum = smsDTO.getSmsBizTypeEnum();
        switch (smsBizTypeEnum) {
            case VOICE:
                smsRecord.setSmsTemplateCode(smsTypeEnum.getTemplateCodeVoice());
                break;
            case TEXT:
            default:
                smsRecord.setSmsTemplateCode(smsTypeEnum.getTemplateCode());
                break;
        }
        smsRecord.setSmsBizType(smsBizTypeEnum.getType());
        smsRecord.setPriority(SmsPriority.INESSENTIAL);
        smsRecord.setCreateTime(new Date());
        return smsRecord;
    }
}
