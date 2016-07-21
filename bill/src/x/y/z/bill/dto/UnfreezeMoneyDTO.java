package x.y.z.bill.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import x.y.z.bill.constant.BizType;
import io.alpha.core.model.BaseObject;

public class UnfreezeMoneyDTO extends BaseObject {
    private static final long serialVersionUID = -1034145945084463241L;
    @NotNull
    @Min(1L)
    private Long userId;
    @Length(min = 1, max = 40)
    private String origTxnId;
    @NotNull
    private BizType bizType;
    private boolean bizStatus;
    @Length(max = 50)
    private String memo;

    public UnfreezeMoneyDTO() {
    }

    public UnfreezeMoneyDTO(final Long userId, final String origTxnId, final BizType bizType, final boolean bizStatus,
            final String memo) {
        this.userId = userId;
        this.origTxnId = origTxnId;
        this.bizType = bizType;
        this.bizStatus = bizStatus;
        this.memo = memo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public String getOrigTxnId() {
        return origTxnId;
    }

    public void setOrigTxnId(final String origTxnId) {
        this.origTxnId = origTxnId;
    }

    public BizType getBizType() {
        return bizType;
    }

    public void setBizType(final BizType bizType) {
        this.bizType = bizType;
    }

    public boolean isBizStatus() {
        return bizStatus;
    }

    public void setBizStatus(final boolean bizStatus) {
        this.bizStatus = bizStatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(final String memo) {
        this.memo = memo;
    }

}