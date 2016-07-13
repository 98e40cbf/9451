package x.y.z.bill.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.alpha.core.model.BaseObject;

public class ModifyPasswordDTO extends BaseObject {
    private static final long serialVersionUID = -3846435889147858668L;
    @NotNull
    @Min(1L)
    private Long userId;
    private String oldPassword;
    @NotBlank
    private String newPassword;

    public ModifyPasswordDTO() {
    }

    public ModifyPasswordDTO(final Long userId, final String oldPassword, final String newPassword) {
        this.userId = userId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(final String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }
}