package x.y.z.bill.dto;

import io.alpha.core.model.BaseObject;
import org.hibernate.validator.constraints.Length;
import x.y.z.bill.constant.SmsTypeEnum;

import javax.validation.constraints.NotNull;

public class SmsDTO extends BaseObject {
    private static final long serialVersionUID = -1034145945084463241L;
    @Length(min = 1, max = 40)
    private String txnId;
    @NotNull
    private SmsTypeEnum smsTypeEnum;
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
