package x.y.z.bill.dto;

import io.alpha.core.model.BaseObject;

public final class UserSession extends BaseObject {

    private static final long serialVersionUID = -536789386612459567L;

    public static final UserSession NONE = new UserSession();

    private long id = -1L;
    private String name = "n/a";
    private String mobile = "n/a";

    public UserSession() {
    }

    public UserSession(final long id, final String name, final String mobile) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(final String mobile) {
        this.mobile = mobile;
    }
}
