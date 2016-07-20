/*******************************************************************************
 * Create on 2015年5月26日 上午10:14:12
 * Copyright (c) 2014 深圳市小牛电子商务有限公司版权所有. 粤ICP备13089339号
 * 注意：本内容仅限于深圳市小牛电子商务有限公司内部传阅，禁止外泄以及用于其他商业目的!
 ******************************************************************************/
package x.y.z.bill.command;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import io.alpha.core.model.BaseObject;

/**
 *
 */
public class ApplyQuickPayAgreementFrom extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = 1;
    /**
     * 用户编号
     */
    @NotBlank(message = "银行卡编号不能为空")
    @Length(max = 19, min = 16, message = "银行卡号长度[16-19]位")
    private String bankCardNo;
    /**
     * 用户身份证号
     */
    @NotBlank(message = "身份证号码不能为空")
    @Length(max = 18, message = "身份证号码长度不正确")
    private String idCardNo;
    /**
     * 用户身份证名称
     */
    @NotBlank(message = "用户身份证名称不能为空")
    private String realName;
    /**
     * 用户手机号
     */
    @NotBlank(message = "银行预留手机号码不能为空")
    @Length(max = 11, min = 11, message = "银行预留手机号码长度错误")
    private String mobile;
    /**
     * 来源
     */
    @NotNull(message = "来源不能为空")
    @Pattern(regexp = "PC|WeChat|Android|IOS", message = "来源超过限定范围[PC|WeChat|Android|IOS]")
    private String origin;

    public String getBankCardNo() {
        return bankCardNo;
    }

    public ApplyQuickPayAgreementFrom setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
        return this;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public ApplyQuickPayAgreementFrom setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public ApplyQuickPayAgreementFrom setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public ApplyQuickPayAgreementFrom setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public ApplyQuickPayAgreementFrom setOrigin(String origin) {
        this.origin = origin;
        return this;
    }
}
