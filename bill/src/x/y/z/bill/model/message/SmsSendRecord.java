package x.y.z.bill.model.message;

import java.util.Date;

import io.alpha.core.model.BaseObject;

public class SmsSendRecord extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long smsId;

    private Byte partnerType;

    private Byte smsStatus;

    private String errorCode;

    private String errorDetail;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSmsId() {
        return smsId;
    }

    public void setSmsId(Long smsId) {
        this.smsId = smsId;
    }

    public Byte getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(Byte partnerType) {
        this.partnerType = partnerType;
    }

    public Byte getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(Byte smsStatus) {
        this.smsStatus = smsStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode == null ? null : errorCode.trim();
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail == null ? null : errorDetail.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}