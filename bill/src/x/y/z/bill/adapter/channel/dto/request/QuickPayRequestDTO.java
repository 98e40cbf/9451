package x.y.z.bill.adapter.channel.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;

public class QuickPayRequestDTO extends ChannelBaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @NotNull(message = "平台订单号不能为空")
    private String channelSendOrder;
    @NotNull(message = "鉴权信息不能为空")
    private TreatyInfoDTO treatyInfo;
    @NotNull(message = "充值金额不能为空")
    @DecimalMin(value = "0.01", message = "充值金额必须不小于0.01")
    private BigDecimal amount;
    @NotNull(message = "客户端IP不能为空")
    private String clientIp;

    public QuickPayRequestDTO() {

    }

    public QuickPayRequestDTO(BigDecimal amount, TreatyInfoDTO treatyInfo) {
        this.amount = amount;
        this.treatyInfo = treatyInfo;
    }

    public TreatyInfoDTO getTreatyInfo() {
        return treatyInfo;
    }

    public QuickPayRequestDTO setTreatyInfo(TreatyInfoDTO treatyInfo) {
        this.treatyInfo = treatyInfo;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public QuickPayRequestDTO setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getChannelSendOrder() {
        return channelSendOrder;
    }

    public QuickPayRequestDTO setChannelSendOrder(String channelSendOrder) {
        this.channelSendOrder = channelSendOrder;
        return this;
    }

    public String getClientIp() {
        return clientIp;
    }

    public QuickPayRequestDTO setClientIp(String clientIp) {
        this.clientIp = clientIp;
        return this;
    }
}
