/*******************************************************************************
 * Create on 2016年1月14日 上午10:56:26
 * Copyright (c) 2014 深圳市小牛电子商务有限公司版权所有. 粤ICP备13089339号
 * 注意：本内容仅限于深圳市小牛电子商务有限公司内部传阅，禁止外泄以及用于其他商业目的!
 ******************************************************************************/
package x.y.z.bill.adapter.channel.dto.request;

import javax.validation.constraints.NotNull;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;

/**
 *
 */
public class AuthRequestDTO extends ChannelBaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @NotNull(message = "平台订单号不能为空")
    private String channelSendOrder;
    @NotNull(message = "鉴权信息不能为空")
    private TreatyInfoDTO treatyInfo;

    public AuthRequestDTO() {
        super();
    }

    public AuthRequestDTO(TreatyInfoDTO treatyInfo, String channelSendOrder) {
        super();
        this.treatyInfo = treatyInfo;
        this.channelSendOrder = channelSendOrder;
    }

    public String getChannelSendOrder() {
        return channelSendOrder;
    }

    public void setChannelSendOrder(String channelSendOrder) {
        this.channelSendOrder = channelSendOrder;
    }

    public TreatyInfoDTO getTreatyInfo() {
        return treatyInfo;
    }

    public AuthRequestDTO setTreatyInfo(TreatyInfoDTO treatyInfo) {
        this.treatyInfo = treatyInfo;
        return this;
    }

}
