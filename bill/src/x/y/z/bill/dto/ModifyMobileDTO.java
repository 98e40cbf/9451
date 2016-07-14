package x.y.z.bill.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.alpha.core.model.BaseObject;

public class ModifyMobileDTO extends BaseObject {
    private static final long serialVersionUID = 5389301809034142618L;
    @NotNull
    @Min(1L)
    private Long userId;
    @NotNull
    @Pattern(regexp = "\\d+")
    private String oldMobile;
    @NotNull
    @Pattern(regexp = "\\d+")
    private String newMobile;

    public ModifyMobileDTO() {
    }

    public ModifyMobileDTO(final Long userId, final String oldMobile, final String newMobile) {
        this.userId = userId;
        this.oldMobile = oldMobile;
        this.newMobile = newMobile;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public String getOldMobile() {
        return oldMobile;
    }

    public void setOldMobile(final String oldMobile) {
        this.oldMobile = oldMobile;
    }

    public String getNewMobile() {
        return newMobile;
    }

    public void setNewMobile(final String newMobile) {
        this.newMobile = newMobile;
    }

}