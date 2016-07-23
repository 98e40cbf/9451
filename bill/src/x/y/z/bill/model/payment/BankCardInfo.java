package x.y.z.bill.model.payment;

import java.util.Date;

import io.alpha.core.model.BaseObject;
import io.alpha.security.util.EncryptionUtils;

public class BankCardInfo extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String txnId;

    private Long userId;

    private String userName;

    private Integer cardType;

    private String bankCode;

    private String bankName;

    private String bankCardNo;

    private String idCardNo;

    private String realName;

    private String mobile;

    private Integer status;

    private String origin;

    private String remark;

    private Date createTime;

    private Date lastModifyTime;

    private Integer version;

    private String clientIp;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getCardType() {
        return cardType;
    }

    public BankCardInfo setCardType(Integer cardType) {
        this.cardType = cardType;
        return this;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo == null ? null : bankCardNo.trim();
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo == null ? null : idCardNo.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getClientIp() {
        return clientIp;
    }

    public BankCardInfo setClientIp(String clientIp) {
        this.clientIp = clientIp;
        return this;
    }

    public void encrypt() {
        try {
            if (this.bankCardNo != null) {
                this.bankCardNo = EncryptionUtils.encryptByAES(bankCardNo);
            }
            if (this.idCardNo != null) {
                this.idCardNo = EncryptionUtils.encryptByAES(idCardNo);
            }
            if (this.realName != null) {
                this.realName = EncryptionUtils.encryptByAES(realName);
            }
            if (this.mobile != null) {
                this.mobile = EncryptionUtils.encryptByAES(mobile);
            }
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }

    public void decrypt() {
        try {
            if (this.bankCardNo != null) {
                this.bankCardNo = EncryptionUtils.decryptByAES(bankCardNo);
            }
            if (this.idCardNo != null) {
                this.idCardNo = EncryptionUtils.decryptByAES(idCardNo);
            }
            if (this.realName != null) {
                this.realName = EncryptionUtils.decryptByAES(realName);
            }
            if (this.mobile != null) {
                this.mobile = EncryptionUtils.decryptByAES(mobile);
            }
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }
}