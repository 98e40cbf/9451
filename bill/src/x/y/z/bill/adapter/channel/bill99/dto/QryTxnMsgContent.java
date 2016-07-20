package x.y.z.bill.adapter.channel.bill99.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class QryTxnMsgContent {

    /**
     * 属性：最长32个字节（字母和数字字符）
     * 描述：用于商户自定义标识一笔交易，如：使用订单编号作为外部检索参考号。除了撤销交易，商户端必须保证每笔交易请求的外部检索参考号的唯一性，否则，MAS系统将拒绝出现重复的外部检索参考号的交易请求
     * 。商户端可以使用外部检索参考号，通过MAS系统提供的查询交易流水功能来检索一笔交易流水记录。
     */
    @XStreamAlias("externalRefNumber")
    private String externalRefNumber;
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
    @XStreamAlias("responseCode")
    private String responseCode;
    @XStreamAlias("responseTextMessage")
    private String responseTextMessage;

    public void setExternalRefNumber(String externalRefNumber) {
        this.externalRefNumber = externalRefNumber;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }
}
