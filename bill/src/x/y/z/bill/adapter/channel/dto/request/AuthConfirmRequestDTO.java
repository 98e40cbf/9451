package x.y.z.bill.adapter.channel.dto.request;

import javax.validation.constraints.NotNull;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;

/**
 * Created by cyh on 2016/3/17.
 */
public class AuthConfirmRequestDTO extends ChannelBaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @NotNull(message = "平台订单号不能为空")
    private String channelSendOrder;
    private String channelToken;
    private String securityCode;
    @NotNull(message = "鉴权信息不能为空")
    private TreatyInfoDTO treatyInfo;

    public AuthConfirmRequestDTO() {
        super();
    }

    public AuthConfirmRequestDTO(String securityCode, TreatyInfoDTO treatyInfo) {
        super();
        this.securityCode = securityCode;
        this.treatyInfo = treatyInfo;
    }

    public AuthConfirmRequestDTO(String channelToken, String securityCode, TreatyInfoDTO treatyInfo) {
        super();
        this.channelToken = channelToken;
        this.securityCode = securityCode;
        this.treatyInfo = treatyInfo;
    }

    public String getChannelToken() {
        return channelToken;
    }

    public AuthConfirmRequestDTO setChannelToken(String channelToken) {
        this.channelToken = channelToken;
        return this;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public AuthConfirmRequestDTO setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
        return this;
    }

    public TreatyInfoDTO getTreatyInfo() {
        return treatyInfo;
    }

    public AuthConfirmRequestDTO setTreatyInfo(TreatyInfoDTO treatyInfo) {
        this.treatyInfo = treatyInfo;
        return this;
    }

    public String getChannelSendOrder() {
        return channelSendOrder;
    }

    public AuthConfirmRequestDTO setChannelSendOrder(String channelSendOrder) {
        this.channelSendOrder = channelSendOrder;
        return this;
    }
}
