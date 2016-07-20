package x.y.z.bill.adapter.channel.dto.response;

import java.math.BigDecimal;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.PayStatus;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;

public class QuickPayConfirmResponseDTO extends ChannelBaseDTO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String channelSendOrder;
    private String channelRecvOrder;
    private TreatyInfoDTO treatyInfo;
    private BigDecimal amount;
    private PayStatus payStatus;// 查询支付流水返回的状态

    public PayStatus getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(PayStatus payStatus) {
        this.payStatus = payStatus;
    }

    public String getChannelSendOrder() {
        return channelSendOrder;
    }

    public QuickPayConfirmResponseDTO setChannelSendOrder(String channelSendOrder) {
        this.channelSendOrder = channelSendOrder;
        return this;
    }

    public TreatyInfoDTO getTreatyInfo() {
        return treatyInfo;
    }

    public QuickPayConfirmResponseDTO setTreatyInfo(TreatyInfoDTO treatyInfo) {
        this.treatyInfo = treatyInfo;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public QuickPayConfirmResponseDTO setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getChannelRecvOrder() {
        return channelRecvOrder;
    }

    public QuickPayConfirmResponseDTO setChannelRecvOrder(String channelRecvOrder) {
        this.channelRecvOrder = channelRecvOrder;
        return this;
    }

}
