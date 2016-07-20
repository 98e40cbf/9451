package x.y.z.bill.adapter.channel.bill99.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class GetDynNumContent {

    /**
     * 属性：定长15位的字母和数字字符 描述：由快钱公司分配给商户的15位唯一标识号。
     */
    @XStreamAlias("merchantId")
    private String merchantId;
    /**
     * 属性：最长32个字节（字母和数字字符） 描述：对于具备CRM管理能力的商户，可以在交易中使用客户号来代替卡号进行CNP交易请求。MAS系统将使用客户号来检索出事先登记对应的信用卡卡号，从而完成CNP交易。
     */
    @XStreamAlias("customerId")
    private String customerId;
    /**
     * 属性：最长32个字节（字母和数字字符） 描述：用于商户自定义标识一笔交易，如：使用订单编号作为外部检索参考号。除了撤销交易，商户端必须保证每笔交易请求的外部检索参考号的唯一性，
     * 否则，MAS系统将拒绝出现重复的外部检索参考号的交易请求。商户端可以使用外部检索参考号，通过MAS系统提供的查询交易流水功能来检索一笔交易流水记录。
     */
    @XStreamAlias("externalRefNumber")
    private String externalRefNumber;
    /**
     * 属性：最长12位、2位小数点的数字 描述：在退货交易时，表示要退还的金额。
     */
    @XStreamAlias("amount")
    private String amount;
    /**
     * 属性：最长19个字节（数字字符） 描述：信用卡的卡号，是CNP交易的交易要素之一。多数信用卡为16位数字，最后一位为校验位，最长为19位数字。
     */
    @XStreamAlias("bankId")
    private String bankId;
    /**
     * 属性：最长20字节数字字符 描述:用户手机号码。
     */
    @XStreamAlias("storablePan")
    private String storablePan;

    @XStreamAlias("token")
    private String token;
    @XStreamAlias("responseCode")
    private String responseCode;
    @XStreamAlias("responseTextMessage")
    private String responseTextMessage;

    public String getMerchantId() {
        return merchantId;
    }

    public GetDynNumContent setMerchantId(String merchantId) {
        this.merchantId = merchantId;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public GetDynNumContent setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getExternalRefNumber() {
        return externalRefNumber;
    }

    public GetDynNumContent setExternalRefNumber(String externalRefNumber) {
        this.externalRefNumber = externalRefNumber;
        return this;
    }

    public String getAmount() {
        return amount;
    }

    public GetDynNumContent setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public String getBankId() {
        return bankId;
    }

    public GetDynNumContent setBankId(String bankId) {
        this.bankId = bankId;
        return this;
    }

    public String getStorablePan() {
        return storablePan;
    }

    public GetDynNumContent setStorablePan(String storablePan) {
        this.storablePan = storablePan;
        return this;
    }

    public String getToken() {
        return token;
    }

    public GetDynNumContent setToken(String token) {
        this.token = token;
        return this;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public GetDynNumContent setResponseCode(String responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public String getResponseTextMessage() {
        return responseTextMessage;
    }

    public GetDynNumContent setResponseTextMessage(String responseTextMessage) {
        this.responseTextMessage = responseTextMessage;
        return this;
    }
}
