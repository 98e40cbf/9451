package x.y.z.bill.adapter.channel.dto.response;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;
import x.y.z.bill.adapter.channel.dto.request.AuthRequestDTO;

public class AuthResponseDTO extends ChannelBaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String channelSendOrder;
    private String channelRecvOrder;
    private String channelToken;
    private TreatyInfoDTO treatyInfo;
    private boolean existAuthTreaty;
    /**
     * 是否已经存在鉴权信息
     */
    private Boolean existAuthenticate;

    public AuthResponseDTO() {
    }

    public AuthResponseDTO(AuthRequestDTO request) {
        this.channelSendOrder = request.getChannelSendOrder();
        this.channelRecvOrder = request.getChannelSendOrder();
        this.treatyInfo = request.getTreatyInfo();
    }

    public String getChannelRecvOrder() {
        return channelRecvOrder;
    }

    public AuthResponseDTO setChannelRecvOrder(String channelRecvOrder) {
        this.channelRecvOrder = channelRecvOrder;
        return this;
    }

    public String getChannelToken() {
        return channelToken;
    }

    public AuthResponseDTO setChannelToken(String channelToken) {
        this.channelToken = channelToken;
        return this;
    }

    public TreatyInfoDTO getTreatyInfo() {
        return treatyInfo;
    }

    public AuthResponseDTO setTreatyInfo(TreatyInfoDTO treatyInfo) {
        this.treatyInfo = treatyInfo;
        return this;
    }

    public Boolean getExistAuthenticate() {
        return existAuthenticate;
    }

    public AuthResponseDTO setExistAuthenticate(Boolean existAuthenticate) {
        this.existAuthenticate = existAuthenticate;
        return this;
    }

    public String getChannelSendOrder() {
        return channelSendOrder;
    }

    public AuthResponseDTO setChannelSendOrder(String channelSendOrder) {
        this.channelSendOrder = channelSendOrder;
        return this;
    }

    public Boolean getExistAuthTreaty() {
        return existAuthTreaty;
    }

    public AuthResponseDTO setExistAuthTreaty(Boolean existAuthTreaty) {
        this.existAuthTreaty = existAuthTreaty;
        return this;
    }

}
