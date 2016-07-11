package x.y.z.bill.command;

import org.hibernate.validator.constraints.NotEmpty;

import io.alpha.core.model.BaseObject;

public class LoginForm extends BaseObject {

    private static final long serialVersionUID = -7803948403031092965L;
    @NotEmpty
    private String loginId;
    @NotEmpty
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
