package x.y.z.bill.dto.message;

import io.alpha.core.model.BaseObject;
import x.y.z.bill.constant.message.SmsPartnerEnum;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SmsSendRecordDTO extends BaseObject {
    private static final long serialVersionUID = -1034145945084463241L;

    @NotNull
    @Min(1L)
    private Long smsId;
    @NotNull
    private SmsPartnerEnum smsPartnerEnum;

    public Long getSmsId() {
        return smsId;
    }

    public void setSmsId(Long smsId) {
        this.smsId = smsId;
    }

    public SmsPartnerEnum getSmsPartnerEnum() {
        return smsPartnerEnum;
    }

    public void setSmsPartnerEnum(SmsPartnerEnum smsPartnerEnum) {
        this.smsPartnerEnum = smsPartnerEnum;
    }
}
