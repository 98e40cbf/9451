package x.y.z.bill.command;

import io.alpha.core.model.BaseObject;

public class LoginForm extends BaseObject {

    private static final long serialVersionUID = -7803948403031092965L;
    private String loginId;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(final String loginId) {
        this.loginId = loginId;
    }

}
