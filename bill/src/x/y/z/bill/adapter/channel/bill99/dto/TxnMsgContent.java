package x.y.z.bill.adapter.channel.bill99.dto;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class TxnMsgContent {

    /**
     * 属性：3位定长字母和数字字符 描述：标识当前消息对应的交互状态。
     */
    @XStreamAlias("interactiveStatus")
    private String interactiveStatus = "TR1";
    /**
     * 属性：3位定长字母字符 描述：标识当前消息对应的交易类型
     */
    @XStreamAlias("txnType")
    private String txnType = "PUR";
    /**
     * 属性：定长15位的字母和数字字符 描述：由快钱公司分配给商户的15位唯一标识号。
     */
    @XStreamAlias("merchantId")
    private String merchantId;
    /**
     * 属性：定长8位的字母和数字字符 描述：由快钱公司分配给商户的8位唯一标识号，一个商户可以申请多个终端编号。撤销交易中，原交易和撤销交易必须在同一个终端号下发生。
     */
    @XStreamAlias("terminalId")
    private String terminalId;
    /**
     * 属性：最长128字节的字母和数字字符
     * 描述：如果商户tr1报文传送tr3Url参数，那么将覆盖商户原先在系统中指定的地址，tr3使用覆盖后地址回调。如果商户tr1报文不传tr3Url参数，tr3仍将使用原先系统中指定的地址回调。
     * 注意：系统不支持同一商户同一交易时传送不同的Tr3回调地址。
     */
    @XStreamAlias("tr3Url")
    private String tr3Url;
    /**
     * 属性：14位定长的数字字符，格式为yyyyMMddHHmmss，如： 2007年12月19日13点01分01秒表达为20071219130101 描述：商户发送交易请求时的系统时间。
     */
    @XStreamAlias("entryTime")
    private String entryTime;
    /**
     * 属性：10位定长的数字字符 描述：对于没有通过PCI认证的商户销售系统，在任何时候都不能在数据库中保存信用卡卡号。否则，发生纠纷时商户将处于不利地位。因此，对于商户的事后的交易匹配需要，MAS系统返回的缩略卡号供商户保存。
     * 缩略卡号为信用卡卡号的前6位和后4位。
     */
    @XStreamAlias("storableCardNo")
    private String storableCardNo;
    /**
     * 属性：最长12位、2位小数点的数字 描述：在退货交易时，表示要退还的金额。
     */
    @XStreamAlias("amount")
    private String amount;
    /**
     * 属性：最长32个字节（字母和数字字符）
     * 描述：用于商户自定义标识一笔交易，如：使用订单编号作为外部检索参考号。除了撤销交易，商户端必须保证每笔交易请求的外部检索参考号的唯一性，否则，MAS系统将拒绝出现重复的外部检索参考号的交易请求
     * 。商户端可以使用外部检索参考号，通过MAS系统提供的查询交易流水功能来检索一笔交易流水记录。
     */
    @XStreamAlias("externalRefNumber")
    private String externalRefNumber;
    /**
     * 属性：最长32个字节（字母和数字字符） 描述：对于具备CRM管理能力的商户，可以在交易中使用客户号来代替卡号进行CNP交易请求。MAS系统将使用客户号来检索出事先登记对应的信用卡卡号，从而完成CNP交易。
     */
    @XStreamAlias("customerId")
    private String customerId;
    /**
     * 属性：最长3字节的字母和数字字符 描述:一些需要接口在交易前做处理的交易。
     */
    @XStreamAlias("spFlag")
    private String spFlag = "QPay02";

    @XStreamAlias("extMap")
    private List<ExtData> datas;
    @XStreamAlias("transTime")
    private String transTime;
    @XStreamAlias("refNumber")
    private String refNumber;
    @XStreamAlias("voidFlag")
    private String voidFlag;
    @XStreamAlias("responseCode")
    private String responseCode;
    @XStreamAlias("responseTextMessage")
    private String responseTextMessage;
    @XStreamAlias("cardOrg")
    private String cardOrg;
    @XStreamAlias("issuer")
    private String issuer;
    @XStreamAlias("authorizationCode")
    private String authorizationCode;
    @XStreamAlias("txnStatus")
    private String txnStatus;

    public String getTxnStatus() {
        return txnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        this.txnStatus = txnStatus;
    }

    public String getVoidFlag() {
        return voidFlag;
    }

    public void setVoidFlag(String voidFlag) {
        this.voidFlag = voidFlag;
    }

    public void addDatas(String key, String value) {
        if (this.datas == null) {
            this.datas = new ArrayList<ExtData>();
        }
        this.datas.add(new ExtData(key, value));
    }

    public List<ExtData> getDatas() {
        return datas;
    }

    public void setDatas(List<ExtData> datas) {
        this.datas = datas;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
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

    public String getCardOrg() {
        return cardOrg;
    }

    public void setCardOrg(String cardOrg) {
        this.cardOrg = cardOrg;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getInteractiveStatus() {
        return interactiveStatus;
    }

    public void setInteractiveStatus(String interactiveStatus) {
        this.interactiveStatus = interactiveStatus;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTr3Url() {
        return tr3Url;
    }

    public void setTr3Url(String tr3Url) {
        this.tr3Url = tr3Url;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getStorableCardNo() {
        return storableCardNo;
    }

    public void setStorableCardNo(String storableCardNo) {
        this.storableCardNo = storableCardNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExternalRefNumber() {
        return externalRefNumber;
    }

    public void setExternalRefNumber(String externalRefNumber) {
        this.externalRefNumber = externalRefNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSpFlag() {
        return spFlag;
    }

    public void setSpFlag(String spFlag) {
        this.spFlag = spFlag;
    }

    @XStreamAlias("extDate")
    public static class ExtData {
        @XStreamAlias("key")
        private String key;
        @XStreamAlias("value")
        private String value;

        public ExtData(String key, String value) {
            super();
            this.key = key;
            this.value = value;
        }
    }
}
