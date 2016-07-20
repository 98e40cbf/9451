package x.y.z.bill.adapter.channel.dto.response;

import java.math.BigDecimal;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;

public class PayQueryResponseDTO extends ChannelBaseDTO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String channelSendOrder;
    private String channelRecvOrder;
    private TreatyInfoDTO treatyInfo;
    private BigDecimal amount;

    public PayQueryResponseDTO() {
        super();
    }

    public String getChannelSendOrder() {
        return channelSendOrder;
    }

    public PayQueryResponseDTO setChannelSendOrder(String channelSendOrder) {
        this.channelSendOrder = channelSendOrder;
        return this;
    }

    public String getChannelRecvOrder() {
        return channelRecvOrder;
    }

    public PayQueryResponseDTO setChannelRecvOrder(String channelRecvOrder) {
        this.channelRecvOrder = channelRecvOrder;
        return this;
    }

    public TreatyInfoDTO getTreatyInfo() {
        return treatyInfo;
    }

    public PayQueryResponseDTO setTreatyInfo(TreatyInfoDTO treatyInfo) {
        this.treatyInfo = treatyInfo;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PayQueryResponseDTO setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

}
