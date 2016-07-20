/*******************************************************************************
 * Create on 2015年5月26日 上午10:14:12
 * Copyright (c) 2014 深圳市小牛电子商务有限公司版权所有. 粤ICP备13089339号
 * 注意：本内容仅限于深圳市小牛电子商务有限公司内部传阅，禁止外泄以及用于其他商业目的!
 ******************************************************************************/
package x.y.z.bill.command;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.alpha.core.model.BaseObject;

/**
 *
 */
public class ApplyWithdrawFrom extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = 1;
    /**
     * 提现金额
     */
    @NotNull(message = "支付金额不能为空")
    @DecimalMin(value = "0.01", message = "支付金额必须大于0.01")
    @DecimalMax(value = "10000000.00", message = "提现金额不能大于10000000.00")
    private BigDecimal amount;
    /**
     * 来源
     */
    @NotNull(message = "来源不能为空")
    @Pattern(regexp = "PC|WeChat|Android|IOS", message = "来源超过限定范围[PC|WeChat|Android|IOS]")
    private String origin;

    public BigDecimal getAmount() {
        return amount;
    }

    public ApplyWithdrawFrom setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public ApplyWithdrawFrom setOrigin(String origin) {
        this.origin = origin;
        return this;
    }
}
