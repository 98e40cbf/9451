package x.y.z.bill.adapter.channel.bill99.dto;

import x.y.z.bill.adapter.util.XMLSerializer;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("MasMessage")
public class MasMessage {

    @XStreamAsAttribute
    @XStreamAlias("xmlns")
    private String namespace = "http://www.99bill.com/mas_cnp_merchant_interface";

    @XStreamAlias("version")
    private String version = "1.0";// 版本号

    @XStreamAlias("PciQueryContent")
    private PCIQueryContent pciQueryContent = null;
    @XStreamAlias("indAuthContent")
    private IndAuthContent indAuthContent = null;
    @XStreamAlias("indAuthDynVerifyContent")
    private IndAuthDynVerifyContent indAuthDynVerifyContent = null;
    @XStreamAlias("TxnMsgContent")
    private TxnMsgContent txnMsgContent = null;
    @XStreamAlias("QryTxnMsgContent")
    private QryTxnMsgContent qryTxnMsgContent = null;
    @XStreamAlias("PciDeleteContent")
    private PCIDeleteContent pciDeleteContent = null;
    @XStreamAlias("ErrorMsgContent")
    private ErrorMsgContent errorMsgContent = null;
    @XStreamAlias("GetDynNumContent")
    private GetDynNumContent getDynNumContent = null;

    public MasMessage() {
    }

    public MasMessage(IndAuthContent indAuthContent) {
        this.indAuthContent = indAuthContent;
    }

    public MasMessage(PCIQueryContent pciQueryContent) {
        this.pciQueryContent = pciQueryContent;
    }

    public MasMessage(IndAuthDynVerifyContent indAuthDynVerifyContent) {
        this.indAuthDynVerifyContent = indAuthDynVerifyContent;
    }

    public MasMessage(PCIDeleteContent pciDeleteContent) {
        this.pciDeleteContent = pciDeleteContent;
    }

    public MasMessage(GetDynNumContent content) {
        this.getDynNumContent = content;
    }

    public MasMessage(TxnMsgContent content) {
        this.txnMsgContent = content;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public PCIQueryContent getPciQueryContent() {
        return pciQueryContent;
    }

    public void setPciQueryContent(PCIQueryContent pciQueryContent) {
        this.pciQueryContent = pciQueryContent;
    }

    public IndAuthContent getIndAuthContent() {
        return indAuthContent;
    }

    public void setIndAuthContent(IndAuthContent indAuthContent) {
        this.indAuthContent = indAuthContent;
    }

    public IndAuthDynVerifyContent getIndAuthDynVerifyContent() {
        return indAuthDynVerifyContent;
    }

    public void setIndAuthDynVerifyContent(IndAuthDynVerifyContent indAuthDynVerifyContent) {
        this.indAuthDynVerifyContent = indAuthDynVerifyContent;
    }

    public TxnMsgContent getTxnMsgContent() {
        return txnMsgContent;
    }

    public void setTxnMsgContent(TxnMsgContent txnMsgContent) {
        this.txnMsgContent = txnMsgContent;
    }

    public QryTxnMsgContent getQryTxnMsgContent() {
        return qryTxnMsgContent;
    }

    public void setQryTxnMsgContent(QryTxnMsgContent qryTxnMsgContent) {
        this.qryTxnMsgContent = qryTxnMsgContent;
    }

    public ErrorMsgContent getErrorMsgContent() {
        return errorMsgContent;
    }

    public void setErrorMsgContent(ErrorMsgContent errorMsgContent) {
        this.errorMsgContent = errorMsgContent;
    }

    public PCIDeleteContent getPciDeleteContent() {
        return pciDeleteContent;
    }

    public void setPciDeleteContent(PCIDeleteContent pciDeleteContent) {
        this.pciDeleteContent = pciDeleteContent;
    }

    public GetDynNumContent getGetDynNumContent() {
        return getDynNumContent;
    }

    public MasMessage setGetDynNumContent(GetDynNumContent getDynNumContent) {
        this.getDynNumContent = getDynNumContent;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return XMLSerializer.toXML(this);
    }
}
