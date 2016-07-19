package x.y.z.bill.builder.message;

import x.y.z.bill.constant.message.SmsPartnerEnum;
import x.y.z.bill.constant.message.SmsStatusEnum;
import x.y.z.bill.model.message.SmsSendRecord;

import java.util.Date;

public final class SmsSendRecordBuilder {
    private SmsSendRecordBuilder() {

    }

    public static SmsSendRecord build(SmsPartnerEnum smsPartnerEnum, long smsId) {
        SmsSendRecord smsSendRecord = new SmsSendRecord();
        smsSendRecord.setPartnerType(smsPartnerEnum.getType());
        smsSendRecord.setSmsStatus(SmsStatusEnum.INIT.getStatus());
        smsSendRecord.setSmsId(smsId);
        smsSendRecord.setCreateTime(new Date());
        return smsSendRecord;
    }

}
