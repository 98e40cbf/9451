/*******************************************************************************
 * Create on 2015年5月26日 上午10:14:12
 * Copyright (c) 2014 深圳市小牛电子商务有限公司版权所有. 粤ICP备13089339号
 * 注意：本内容仅限于深圳市小牛电子商务有限公司内部传阅，禁止外泄以及用于其他商业目的!
 ******************************************************************************/
package x.y.z.bill.command;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import io.alpha.core.model.BaseObject;

/**
 *
 */
public class ConfirmFrom extends BaseObject {

    private static final long serialVersionUID = 1;

    /**
     * 验证码
     */
    @NotEmpty(message = "验证码不能为空")
    private String securityCode;
    /**
     * 来源
     */
    @NotNull(message = "来源不能为空")
    @Pattern(regexp = "PC|WeChat|Android|IOS", message = "来源超过限定范围[PC|WeChat|Android|IOS]")
    private String origin;

    public ConfirmFrom() {
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public ConfirmFrom setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public ConfirmFrom setOrigin(String origin) {
        this.origin = origin;
        return this;
    }
}
