package x.y.z.bill.adapter.channel.dto.response;

import java.math.BigDecimal;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.WithdrawStatus;

public class WithdrawQueryResponseDTO extends ChannelBaseDTO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String channelSendOrder;
    private String channelRecevOrder;
    private BigDecimal amount;
    private boolean channelNoData;
    private WithdrawStatus status;

    public String getChannelSendOrder() {
        return channelSendOrder;
    }

    public WithdrawQueryResponseDTO setChannelSendOrder(String channelSendOrder) {
        this.channelSendOrder = channelSendOrder;
        return this;
    }

    public String getChannelRecevOrder() {
        return channelRecevOrder;
    }

    public WithdrawQueryResponseDTO setChannelRecevOrder(String channelRecevOrder) {
        this.channelRecevOrder = channelRecevOrder;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public WithdrawQueryResponseDTO setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public WithdrawStatus getStatus() {
        return status;
    }

    public WithdrawQueryResponseDTO setStatus(WithdrawStatus status) {
        this.status = status;
        return this;
    }

    public boolean isChannelNoData() {
        return channelNoData;
    }

    public WithdrawQueryResponseDTO setChannelNoData(boolean channelNoData) {
        this.channelNoData = channelNoData;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WithdrawQueryResponseDTO{");
        sb.append("channelSendOrder='").append(channelSendOrder).append('\'');
        sb.append(", channelRecevOrder='").append(channelRecevOrder).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", channelNoData=").append(channelNoData);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
