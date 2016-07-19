package x.y.z.bill.command;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import io.alpha.core.model.BaseObject;

public class RegistForm extends BaseObject {

    private static final long serialVersionUID = 4738293626351299744L;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{5,15}$")
    private String username;
    @NotEmpty
    @Pattern(regexp = "^[1](?:3|4|5|7|8)[0-9]{9}$")
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
