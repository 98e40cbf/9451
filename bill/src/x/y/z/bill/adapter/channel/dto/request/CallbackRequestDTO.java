package x.y.z.bill.adapter.channel.dto.request;

import java.math.BigDecimal;
import java.util.Map;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;

public class CallbackRequestDTO extends ChannelBaseDTO {

    private Map<String, String> originalParams;

    private String merchantCode;
    @NotNull(message = "平台订单号不能为空")
    private String channelSendOrder;
    private String channelRecvOrder;
    @DecimalMin(value = "0.01", message = "交易金额必须大于0.01")
    private BigDecimal amount; // 交易金额
    private String tradeState;

    public CallbackRequestDTO() {
        super();
    }

    public CallbackRequestDTO(Map<String, String> originalParams) {
        super();
        this.originalParams = originalParams;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public CallbackRequestDTO setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
        return this;
    }

    public String getChannelSendOrder() {
        return channelSendOrder;
    }

    public CallbackRequestDTO setChannelSendOrder(String channelSendOrder) {
        this.channelSendOrder = channelSendOrder;
        return this;
    }

    public String getChannelRecvOrder() {
        return channelRecvOrder;
    }

    public CallbackRequestDTO setChannelRecvOrder(String channelRecvOrder) {
        this.channelRecvOrder = channelRecvOrder;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CallbackRequestDTO setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getTradeState() {
        return tradeState;
    }

    public CallbackRequestDTO setTradeState(String tradeState) {
        this.tradeState = tradeState;
        return this;
    }

    public Map<String, String> getOriginalParams() {
        return originalParams;
    }

    public CallbackRequestDTO setOriginalParams(Map<String, String> originalParams) {
        this.originalParams = originalParams;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CallbackRequestDTO{");
        sb.append("originalParams=").append(originalParams);
        sb.append(", merchantCode='").append(merchantCode).append('\'');
        sb.append(", channelSendOrder='").append(channelSendOrder).append('\'');
        sb.append(", channelRecvOrder='").append(channelRecvOrder).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", tradeState='").append(tradeState).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
