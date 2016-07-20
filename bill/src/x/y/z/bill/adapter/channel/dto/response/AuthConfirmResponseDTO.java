package x.y.z.bill.adapter.channel.dto.response;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;
import x.y.z.bill.adapter.channel.dto.request.AuthConfirmRequestDTO;

public class AuthConfirmResponseDTO extends ChannelBaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String channelSendOrder;
    private TreatyInfoDTO treatyInfo;

    public AuthConfirmResponseDTO() {
        super();
    }

    public AuthConfirmResponseDTO(AuthConfirmRequestDTO request) {
        this.channelSendOrder = request.getChannelSendOrder();
        this.treatyInfo = request.getTreatyInfo();
    }

    public TreatyInfoDTO getTreatyInfo() {
        return treatyInfo;
    }

    public AuthConfirmResponseDTO setTreatyInfo(TreatyInfoDTO treatyInfo) {
        this.treatyInfo = treatyInfo;
        return this;
    }

    public String getChannelSendOrder() {
        return channelSendOrder;
    }

    public AuthConfirmResponseDTO setChannelSendOrder(String channelSendOrder) {
        this.channelSendOrder = channelSendOrder;
        return this;
    }
}
