package x.y.z.bill.adapter.channel.bill99.dto;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class PCIQueryContent {

    @XStreamAlias("merchantId")
    private String merchantId;
    @XStreamAlias("customerId")
    private String customerId;
    @XStreamAlias("cardType")
    private String cardType = "0002";
    @XStreamAlias("pciInfos")
    private List<PCIInfo> pciInfos = new ArrayList<PCIInfo>();
    @XStreamAlias("responseCode")
    private String responseCode;
    @XStreamAlias("responseTextMessage")
    private String responseTextMessage;

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public List<PCIInfo> getPciInfos() {
        return pciInfos;
    }

    public void setPciInfos(List<PCIInfo> pciInfos) {
        this.pciInfos = pciInfos;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseTextMessage() {
        return responseTextMessage;
    }

    public void setResponseTextMessage(String responseTextMessage) {
        this.responseTextMessage = responseTextMessage;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

}
