package x.y.z.bill.model.account;

import java.math.BigDecimal;
import java.util.Date;

import io.alpha.core.model.BaseObject;

public class BookKeeping extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private BigDecimal recharge;

    private BigDecimal invest;

    private BigDecimal withdraw;

    private Date lastUpdate;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public BigDecimal getRecharge() {
        return recharge;
    }

    public void setRecharge(final BigDecimal recharge) {
        this.recharge = recharge;
    }

    public BigDecimal getInvest() {
        return invest;
    }

    public void setInvest(final BigDecimal invest) {
        this.invest = invest;
    }

    public BigDecimal getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(final BigDecimal withdraw) {
        this.withdraw = withdraw;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(final Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}