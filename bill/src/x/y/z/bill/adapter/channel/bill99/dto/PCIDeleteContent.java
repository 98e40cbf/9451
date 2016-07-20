package x.y.z.bill.adapter.channel.bill99.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class PCIDeleteContent {

    @XStreamAlias("merchantId")
    private String merchantId;
    @XStreamAlias("customerId")
    private String customerId;
    @XStreamAlias("storablePan")
    private String storablePan;
    @XStreamAlias("pan")
    private String pan;
    @XStreamAlias("bankId")
    private String bankId;
    @XStreamAlias("responseCode")
    private String responseCode;
    @XStreamAlias("responseTextMessage")
    private String responseTextMessage;

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

    public String getStorablePan() {
        return storablePan;
    }

    public void setStorablePan(String storablePan) {
        this.storablePan = storablePan;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
}
