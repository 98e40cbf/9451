/*******************************************************************************
 * Create on 2016年1月14日 上午10:56:26
 * Copyright (c) 2014 深圳市小牛电子商务有限公司版权所有. 粤ICP备13089339号
 * 注意：本内容仅限于深圳市小牛电子商务有限公司内部传阅，禁止外泄以及用于其他商业目的!
 ******************************************************************************/
package x.y.z.bill.adapter.channel.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import io.alpha.core.model.BaseObject;

/**
 *
 */
public class ChannelBaseDTO extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @NotNull(message = "业务编号")
    @Length(message = "业务编号长度在10~50位", max = 50, min = 10)
    protected String txnId;
    @NotNull(message = "渠道编码不能为空")
    protected String channelCode;

    public ChannelBaseDTO() {
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

}
