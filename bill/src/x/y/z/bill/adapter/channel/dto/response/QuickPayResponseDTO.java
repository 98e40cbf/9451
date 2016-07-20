package x.y.z.bill.adapter.channel.dto.response;

import java.math.BigDecimal;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;

public class QuickPayResponseDTO extends ChannelBaseDTO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String platformOrder;
    private String channelSendOrder;
    private TreatyInfoDTO treatyInfo;
    private BigDecimal amount;
    private String token;

    public TreatyInfoDTO getTreatyInfo() {
        return treatyInfo;
    }

    public QuickPayResponseDTO setTreatyInfo(TreatyInfoDTO treatyInfo) {
        this.treatyInfo = treatyInfo;
        return this;
    }

    public String getToken() {
        return token;
    }

    public QuickPayResponseDTO setToken(String token) {
        this.token = token;
        return this;
    }

    public String getPlatformOrder() {
        return platformOrder;
    }

    public QuickPayResponseDTO setPlatformOrder(String platformOrder) {
        this.platformOrder = platformOrder;
        return this;
    }

    public String getChannelSendOrder() {
        return channelSendOrder;
    }

    public QuickPayResponseDTO setChannelSendOrder(String channelSendOrder) {
        this.channelSendOrder = channelSendOrder;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public QuickPayResponseDTO setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
}
