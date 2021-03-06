package x.y.z.bill.dto.message;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import x.y.z.bill.constant.message.SmsBizTypeEnum;
import x.y.z.bill.constant.message.SmsTypeEnum;
import io.alpha.core.model.BaseObject;

public class SmsDTO extends BaseObject {
    private static final long serialVersionUID = -1034145945084463241L;
    @Length(min = 1, max = 40)
    private String txnId;
    @NotNull
    private SmsTypeEnum smsTypeEnum;
    @NotNull
    private SmsBizTypeEnum smsBizTypeEnum;
    @NotNull
    @Length(min = 1, max = 600)
    private String mobiles;

    private String smsParam;

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public SmsTypeEnum getSmsTypeEnum() {
        return smsTypeEnum;
    }

    public void setSmsTypeEnum(SmsTypeEnum smsTypeEnum) {
        this.smsTypeEnum = smsTypeEnum;
    }

    public SmsBizTypeEnum getSmsBizTypeEnum() {
        return smsBizTypeEnum;
    }

    public void setSmsBizTypeEnum(SmsBizTypeEnum smsBizTypeEnum) {
        this.smsBizTypeEnum = smsBizTypeEnum;
    }

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public String getSmsParam() {
        return smsParam;
    }

    public void setSmsParam(String smsParam) {
        this.smsParam = smsParam;
    }
}
