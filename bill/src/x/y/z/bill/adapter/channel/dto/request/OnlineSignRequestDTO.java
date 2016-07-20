package x.y.z.bill.adapter.channel.dto.request;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;

public class OnlineSignRequestDTO extends ChannelBaseDTO {
    private TreatyInfoDTO treatyInfo;
    private String clientIp;
}
