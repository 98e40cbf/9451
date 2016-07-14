package x.y.z.bill.dto;

import io.alpha.core.model.BaseObject;

public final class UserSession extends BaseObject {

    private static final long serialVersionUID = 9120238102262445775L;

    public static final UserSession NULL = new UserSession();

    private long id = 0L;
    private String name = "";

    public UserSession() {
    }

    public UserSession(final long id, final String name) {
        this.id = id;
        this.name = name;
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
}
