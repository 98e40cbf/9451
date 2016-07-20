package x.y.z.bill.adapter.channel.dto;

import javax.validation.constraints.NotNull;

import io.alpha.core.model.BaseObject;

public class TreatyInfoDTO extends BaseObject {

    private static final long serialVersionUID = 1L;
    private String identityCode;
    @NotNull(message = "证件号码不能为空")
    private String idCardNo;
    @NotNull(message = "真实姓名不能为空")
    private String realName;
    @NotNull(message = "渠道对应银行编码不能为空")
    private String channelBankCode;
    @NotNull(message = "银行卡号不能为空")
    private String bankCardNo;
    @NotNull(message = "手机号码不能为空")
    private String mobile;

    public TreatyInfoDTO() {
    }

    public TreatyInfoDTO(String identityCode, String idCardNo, String realName, String channelBankCode,
            String bankCardNo, String mobile) {
        this.identityCode = identityCode;
        this.idCardNo = idCardNo;
        this.realName = realName;
        this.channelBankCode = channelBankCode;
        this.bankCardNo = bankCardNo;
        this.mobile = mobile;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public TreatyInfoDTO setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
        return this;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public TreatyInfoDTO setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public TreatyInfoDTO setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getChannelBankCode() {
        return channelBankCode;
    }

    public TreatyInfoDTO setChannelBankCode(String channelBankCode) {
        this.channelBankCode = channelBankCode;
        return this;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public TreatyInfoDTO setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public TreatyInfoDTO setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }
}
