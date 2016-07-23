package x.y.z.bill.model.account;

import java.math.BigDecimal;
import java.util.Date;

import io.alpha.core.model.BaseObject;

public class CapitalAccount extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Byte acctType;

    private BigDecimal amount;

    private String digest;

    private Long version;

    private Date lastUpdate = new Date();

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

    public Byte getAcctType() {
        return acctType;
    }

    public void setAcctType(final Byte acctType) {
        this.acctType = acctType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(final String digest) {
        this.digest = digest == null ? null : digest.trim();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(final Long version) {
        this.version = version;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(final Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}