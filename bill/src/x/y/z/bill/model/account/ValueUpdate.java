package x.y.z.bill.model.account;

import io.alpha.core.model.BaseObject;

public class ValueUpdate extends BaseObject {

    private static final long serialVersionUID = -7727210070198518876L;
    private long id;
    private String oldValue;
    private String newValue;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(final String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(final String newValue) {
        this.newValue = newValue;
    }

}
