package x.y.z.bill.model.account;

import java.util.Date;

import io.alpha.core.model.BaseObject;

public class MobileChangeHistory extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private String source;

    private String target;

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

    public String getSource() {
        return source;
    }

    public void setSource(final String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(final String target) {
        this.target = target == null ? null : target.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(final Date createTime) {
        this.createTime = createTime;
    }
}