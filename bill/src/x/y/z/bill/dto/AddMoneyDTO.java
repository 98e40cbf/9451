package x.y.z.bill.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import x.y.z.bill.constant.BizType;
import io.alpha.core.model.BaseObject;

public class AddMoneyDTO extends BaseObject {
    private static final long serialVersionUID = 5029181108005392100L;
    @NotNull
    @Min(1L)
    private Long userId;
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;
    @Length(min = 1, max = 40)
    private String txnId;
    @NotNull
    private BizType bizType;
    @Length(max = 50)
    private String memo;

    public AddMoneyDTO() {
    }

    public AddMoneyDTO(final Long userId, final BigDecimal amount, final String txnId, final BizType bizType,
            final String memo) {
        this.userId = userId;
        this.amount = amount;
        this.txnId = txnId;
        this.bizType = bizType;
        this.memo = memo;
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
        this.txnId = txnId;
    }

    public BizType getBizType() {
        return bizType;
    }

    public void setBizType(final BizType bizType) {
        this.bizType = bizType;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(final String memo) {
        this.memo = memo;
    }
}