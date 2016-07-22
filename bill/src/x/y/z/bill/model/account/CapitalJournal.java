package x.y.z.bill.model.account;

import java.math.BigDecimal;
import java.util.Date;

import io.alpha.core.model.BaseObject;

public class CapitalJournal extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Byte bizType;

    private String txnId;

    private Long acctFrom;

    private Long acctTo;

    private BigDecimal amount;

    private BigDecimal balance;

    private Byte direction;

    private String memo;

    private String digest;

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

    public Byte getBizType() {
        return bizType;
    }

    public void setBizType(final Byte bizType) {
        this.bizType = bizType;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(final String txnId) {
        this.txnId = txnId == null ? null : txnId.trim();
    }

    public Long getAcctFrom() {
        return acctFrom;
    }

    public void setAcctFrom(final Long acctFrom) {
        this.acctFrom = acctFrom;
    }

    public Long getAcctTo() {
        return acctTo;
    }

    public void setAcctTo(final Long acctTo) {
        this.acctTo = acctTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(final BigDecimal balance) {
        this.balance = balance;
    }

    public Byte getDirection() {
        return direction;
    }

    public void setDirection(final Byte direction) {
        this.direction = direction;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(final String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(final String digest) {
        this.digest = digest == null ? null : digest.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(final Date createTime) {
        this.createTime = createTime;
    }
}