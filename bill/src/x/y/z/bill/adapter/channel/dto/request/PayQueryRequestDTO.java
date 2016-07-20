package x.y.z.bill.adapter.channel.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;
import x.y.z.bill.adapter.constant.BusinessCode;

public class PayQueryRequestDTO extends ChannelBaseDTO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @NotNull(message = "平台订单号不能为空")
    private String channelSendOrder;
    private String channelRecvOrder;
    private BusinessCode bisCode;
    private TreatyInfoDTO treatyInfo;
    private BigDecimal amount;

    public PayQueryRequestDTO() {
        super();
    }

    public PayQueryRequestDTO(BigDecimal amount, TreatyInfoDTO treatyInfo) {
        this.amount = amount;
        this.treatyInfo = treatyInfo;
    }

    public BusinessCode getBisCode() {
        return bisCode == null ? BusinessCode.QUICK_PAY : bisCode;
    }

    public void setBisCode(BusinessCode bisCode) {
        this.bisCode = bisCode;
    }

    public String getChannelSendOrder() {
        return channelSendOrder;
    }

    public PayQueryRequestDTO setChannelSendOrder(String channelSendOrder) {
        this.channelSendOrder = channelSendOrder;
        return this;
    }

    public TreatyInfoDTO getTreatyInfo() {
        return treatyInfo;
    }

    public PayQueryRequestDTO setTreatyInfo(TreatyInfoDTO treatyInfo) {
        this.treatyInfo = treatyInfo;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PayQueryRequestDTO setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getChannelRecvOrder() {
        return channelRecvOrder;
    }

    public PayQueryRequestDTO setChannelRecvOrder(String channelRecvOrder) {
        this.channelRecvOrder = channelRecvOrder;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PayQueryRequestDTO{");
        sb.append(", channelSendOrder='").append(channelSendOrder).append('\'');
        sb.append(", channelRecvOrder='").append(channelRecvOrder).append('\'');
        sb.append(", treatyInfo=").append(treatyInfo);
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }
}
