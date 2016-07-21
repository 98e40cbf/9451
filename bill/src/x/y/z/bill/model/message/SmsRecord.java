package x.y.z.bill.model.message;

import java.util.Date;

import io.alpha.core.model.BaseObject;

public class SmsRecord extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String txnId;

    private Byte smsType;

    private Byte smsStatus;

    private String receiveMobiles;

    private String smsParam;

    private String smsTemplateCode;

    private Byte priority;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId == null ? null : txnId.trim();
    }

    public Byte getSmsType() {
        return smsType;
    }

    public void setSmsType(Byte smsType) {
        this.smsType = smsType;
    }

    public Byte getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(Byte smsStatus) {
        this.smsStatus = smsStatus;
    }

    public String getReceiveMobiles() {
        return receiveMobiles;
    }

    public void setReceiveMobiles(String receiveMobiles) {
        this.receiveMobiles = receiveMobiles == null ? null : receiveMobiles.trim();
    }

    public String getSmsParam() {
        return smsParam;
    }

    public void setSmsParam(String smsParam) {
        this.smsParam = smsParam == null ? null : smsParam.trim();
    }

    public String getSmsTemplateCode() {
        return smsTemplateCode;
    }

    public void setSmsTemplateCode(String smsTemplateCode) {
        this.smsTemplateCode = smsTemplateCode == null ? null : smsTemplateCode.trim();
    }

    public Byte getPriority() {
        return priority;
    }

    public void setPriority(Byte priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}