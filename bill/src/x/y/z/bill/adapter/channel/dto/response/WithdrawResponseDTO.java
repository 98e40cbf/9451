package x.y.z.bill.adapter.channel.dto.response;

import java.math.BigDecimal;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;

public class WithdrawResponseDTO extends ChannelBaseDTO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String channelRecvOrder;
    private TreatyInfoDTO treatyInfo;
    private String channelSendOrder;
    private BigDecimal amount;

    public String getChannelRecvOrder() {
        return channelRecvOrder;
    }

    public WithdrawResponseDTO setChannelRecvOrder(String channelRecvOrder) {
        this.channelRecvOrder = channelRecvOrder;
        return this;
    }

    public TreatyInfoDTO getTreatyInfo() {
        return treatyInfo;
    }

    public WithdrawResponseDTO setTreatyInfo(TreatyInfoDTO treatyInfo) {
        this.treatyInfo = treatyInfo;
        return this;
    }

    public String getChannelSendOrder() {
        return channelSendOrder;
    }

    public WithdrawResponseDTO setChannelSendOrder(String channelSendOrder) {
        this.channelSendOrder = channelSendOrder;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public WithdrawResponseDTO setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
}
