package x.y.z.bill.service.account;

import java.math.BigDecimal;

import x.y.z.bill.constant.BizType;
import io.alpha.core.model.BaseObject;
import io.alpha.util.DecimalUtil;

class Accounting extends BaseObject {

    private static final long serialVersionUID = 4638129767959186548L;
    private final BizType bizType;
    private final BigDecimal amount;

    public Accounting(final BizType bizType, final BigDecimal amount) {
        this.bizType = bizType;
        this.amount = DecimalUtil.format(amount);
    }

    public BizType getBizType() {
        return bizType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
