package x.y.z.bill.adapter.channel.dto.response;

import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;
import x.y.z.bill.adapter.channel.dto.request.AuthRequestDTO;
import io.alpha.core.model.BaseObject;

public class QueryAuthInfoResponseDTO extends BaseObject {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private TreatyInfoDTO treatyInfo;
    // 鉴权成功时，返回true
    private Boolean authenticate;

    public QueryAuthInfoResponseDTO() {
    }

    public QueryAuthInfoResponseDTO(AuthRequestDTO request) {
        this.treatyInfo = request.getTreatyInfo();
    }

    public QueryAuthInfoResponseDTO(TreatyInfoDTO treatyInfo, Boolean authenticate) {
        this.treatyInfo = treatyInfo;
        this.authenticate = authenticate;
    }

    public QueryAuthInfoResponseDTO(TreatyInfoDTO treatyInfo) {
        this.treatyInfo = treatyInfo;
    }

    public TreatyInfoDTO getTreatyInfo() {
        return treatyInfo;
    }

    public QueryAuthInfoResponseDTO setTreatyInfo(TreatyInfoDTO treatyInfo) {
        this.treatyInfo = treatyInfo;
        return this;
    }

    public Boolean isAuthenticate() {
        return authenticate;
    }

    public QueryAuthInfoResponseDTO setAuthenticate(Boolean authenticate) {
        this.authenticate = authenticate;
        return this;
    }
}
