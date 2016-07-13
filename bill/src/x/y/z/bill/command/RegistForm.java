package x.y.z.bill.command;

import io.alpha.core.model.BaseObject;

import org.hibernate.validator.constraints.NotEmpty;

public class RegistForm extends BaseObject {

    private static final long serialVersionUID = 4738293626351299744L;
    @NotEmpty
    private String username;
    @NotEmpty
    private String mobile;
    @NotEmpty
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(final String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

}
