package x.y.z.bill.adapter.channel.dto.request;

import java.math.BigDecimal;
import java.util.Map;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;

public class PayNotifyRequestDTO extends ChannelBaseDTO {
    private Map<String, String> originalParams;
    private String originalData;

    private String merchantCode;
    private String channelSendOrder;
    private String channelRecvOrder;
    private BigDecimal amount; // 交易金额
    private String tradeState;

    public PayNotifyRequestDTO() {
        super();
    }

    public Map<String, String> getOriginalParams() {
        return originalParams;
    }

    public PayNotifyRequestDTO setOriginalParams(Map<String, String> originalParams) {
        this.originalParams = originalParams;
        return this;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public PayNotifyRequestDTO setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
        return this;
    }

    public String getChannelSendOrder() {
        return channelSendOrder;
    }

    public PayNotifyRequestDTO setChannelSendOrder(String channelSendOrder) {
        this.channelSendOrder = channelSendOrder;
        return this;
    }

    public String getChannelRecvOrder() {
        return channelRecvOrder;
    }

    public PayNotifyRequestDTO setChannelRecvOrder(String channelRecvOrder) {
        this.channelRecvOrder = channelRecvOrder;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PayNotifyRequestDTO setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getTradeState() {
        return tradeState;
    }

    public PayNotifyRequestDTO setTradeState(String tradeState) {
        this.tradeState = tradeState;
        return this;
    }

    public String getOriginalData() {
        return originalData;
    }

    public PayNotifyRequestDTO setOriginalData(String originalData) {
        this.originalData = originalData;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PayNotifyRequestDTO{");
        sb.append("originalParams=").append(originalParams);
        sb.append(", originalData='").append(originalData).append('\'');
        sb.append(", merchantCode='").append(merchantCode).append('\'');
        sb.append(", channelSendOrder='").append(channelSendOrder).append('\'');
        sb.append(", channelRecvOrder='").append(channelRecvOrder).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", tradeState='").append(tradeState).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
