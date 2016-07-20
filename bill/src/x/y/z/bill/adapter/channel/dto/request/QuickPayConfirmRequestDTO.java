package x.y.z.bill.adapter.channel.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;

public class QuickPayConfirmRequestDTO extends ChannelBaseDTO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @NotNull(message = "平台订单号不能为空")
    private String channelSendOrder;
    @NotNull(message = "鉴权信息不能为空")
    private TreatyInfoDTO treatyInfo;
    private BigDecimal amount;
    private String securityCode;
    private String channelToken;
    @NotNull(message = "客户端IP不能为空")
    private String clientIp;

    public String getChannelSendOrder() {
        return channelSendOrder;
    }

    public QuickPayConfirmRequestDTO setChannelSendOrder(String channelSendOrder) {
        this.channelSendOrder = channelSendOrder;
        return this;
    }

    public TreatyInfoDTO getTreatyInfo() {
        return treatyInfo;
    }

    public QuickPayConfirmRequestDTO setTreatyInfo(TreatyInfoDTO treatyInfo) {
        this.treatyInfo = treatyInfo;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public QuickPayConfirmRequestDTO setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public QuickPayConfirmRequestDTO setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
        return this;
    }

    public String getChannelToken() {
        return channelToken;
    }

    public QuickPayConfirmRequestDTO setChannelToken(String channelToken) {
        this.channelToken = channelToken;
        return this;
    }

    public String getClientIp() {
        return clientIp;
    }

    public QuickPayConfirmRequestDTO setClientIp(String clientIp) {
        this.clientIp = clientIp;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QuickPayConfirmRequestDTO{");
        sb.append("channelSendOrder='").append(channelSendOrder).append('\'');
        sb.append(", treatyInfo=").append(treatyInfo);
        sb.append(", amount=").append(amount);
        sb.append(", securityCode='").append(securityCode).append('\'');
        sb.append(", channelToken='").append(channelToken).append('\'');
        sb.append(", clientIp='").append(clientIp).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
