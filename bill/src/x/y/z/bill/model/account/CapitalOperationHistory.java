package x.y.z.bill.model.account;

import io.alpha.core.model.BaseObject;

import java.math.BigDecimal;
import java.util.Date;

public class CapitalOperationHistory extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private BigDecimal amount;

    private String txnId;

    private Byte bizType;

    private String memo = "";

    private Date createTime;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(final String txnId) {
        this.txnId = txnId == null ? null : txnId.trim();
    }

    public Byte getBizType() {
        return bizType;
    }

    public void setBizType(final Byte bizType) {
        this.bizType = bizType;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(final String memo) {
        if (memo != null) {
            this.memo = memo.trim();
        }
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(final Date createTime) {
        this.createTime = createTime;
    }
}