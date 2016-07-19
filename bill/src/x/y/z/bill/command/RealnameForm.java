package x.y.z.bill.command;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import io.alpha.core.model.BaseObject;

public class RealnameForm extends BaseObject {

    private static final long serialVersionUID = 5539652850230952027L;
    @NotEmpty
    private String realName;
    @NotEmpty
    @Pattern(regexp = "^\\d{17}(?:\\d|x|X)$")
    private String idCardNo;

    public String getRealName() {
        return realName;
    }

    public void setRealName(final String realName) {
        this.realName = realName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(final String idCardNo) {
        this.idCardNo = idCardNo;
    }

}
