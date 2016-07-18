package x.y.z.bill.model.account;

import java.util.Date;

import io.alpha.core.model.BaseObject;

public class UserExtra extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private String realName;

    private String idCardNo;

    private Byte idCardType;

    private Date createTime = new Date();

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(final String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(final String idCardNo) {
        this.idCardNo = idCardNo == null ? null : idCardNo.trim();
    }

    public Byte getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(final Byte idCardType) {
        this.idCardType = idCardType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(final Date createTime) {
        this.createTime = createTime;
    }
}