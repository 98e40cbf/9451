package x.y.z.bill.adapter.channel.dto.response;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;

public class OnlineSignResponseDTO extends ChannelBaseDTO {
    private static final long serialVersionUID = 1L;
    // 在线签约提交地址
    private String url;
    // 接口名称
    private String interfaceName;
    // 接口版本
    private String interfaceVersion;
    // 交易数据（BASE64编码）
    private String tranData;
    // 银企互连请求签名数据(使用NC必输)
    private String certData;

    public OnlineSignResponseDTO() {
        super();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getInterfaceVersion() {
        return interfaceVersion;
    }

    public void setInterfaceVersion(String interfaceVersion) {
        this.interfaceVersion = interfaceVersion;
    }

    public String getTranData() {
        return tranData;
    }

    public void setTranData(String tranData) {
        this.tranData = tranData;
    }

    public String getCertData() {
        return certData;
    }

    public void setCertData(String certData) {
        this.certData = certData;
    }

}
