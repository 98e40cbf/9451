package x.y.z.bill.builder.message;

import java.util.Date;

import x.y.z.bill.constant.message.SmsPriority;
import x.y.z.bill.constant.message.SmsStatusEnum;
import x.y.z.bill.constant.message.SmsTypeEnum;
import x.y.z.bill.model.message.SmsParam;
import x.y.z.bill.model.message.SmsRecord;
import io.alpha.util.JsonUtils;

public final class SmsRecordBuilder {
    private SmsRecordBuilder() {

    }

    public static SmsRecord build(final String txnId, final SmsTypeEnum smsTypeEnum, final String mobiles,
            final SmsParam smsParam) {
        SmsRecord smsRecord = new SmsRecord();
        smsRecord.setTxnId(txnId);
        smsRecord.setSmsType(smsTypeEnum.getType());
        smsRecord.setSmsStatus(SmsStatusEnum.SUCCESS.getStatus());
        smsRecord.setReceiveMobiles(mobiles);
        if (smsParam != null) {
            smsRecord.setSmsParam(JsonUtils.toJSON(smsParam));
        }
        smsRecord.setSmsTemplateCode(smsTypeEnum.getTemplateCode());
        smsRecord.setPriority(SmsPriority.INESSENTIAL);
        smsRecord.setCreateTime(new Date());
        return smsRecord;
    }
}
