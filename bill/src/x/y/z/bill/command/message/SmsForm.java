package x.y.z.bill.command.message;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import io.alpha.core.model.BaseObject;

public class SmsForm extends BaseObject {
    private static final long serialVersionUID = 4738293626351299744L;
    @NotEmpty
    @Pattern(regexp = "^[1](?:3|4|5|7|8)[0-9]{9}$")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
