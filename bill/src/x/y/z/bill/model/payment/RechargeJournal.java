package x.y.z.bill.model.payment;

import java.math.BigDecimal;
import java.util.Date;

import io.alpha.core.model.BaseObject;

public class RechargeJournal extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String txnId;

    private String channelCode;

    private String channelName;

    private String channelSendOrder;

    private String channelRecvOrder;

    private BigDecimal amount;

    private BigDecimal cost;

    private Integer status;

    private Long userId;

    private String userName;

    private String businessCode;

    private String clientIp;

    private BigDecimal actulAmount;

    private String remark;

    private String replyCode;

    private String replyMessage;

    private Date transTimeout;

    private Date sendTime;

    private Date recvTime;

    private Date toAccountTime;

    private Date createTime;

    private Date lastModifyTime;

    private Integer version;

    private String origin;

    private Long bankCardId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId == null ? null : txnId.trim();
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public String getChannelSendOrder() {
        return channelSendOrder;
    }

    public void setChannelSendOrder(String channelSendOrder) {
        this.channelSendOrder = channelSendOrder == null ? null : channelSendOrder.trim();
    }

    public String getChannelRecvOrder() {
        return channelRecvOrder;
    }

    public void setChannelRecvOrder(String channelRecvOrder) {
        this.channelRecvOrder = channelRecvOrder == null ? null : channelRecvOrder.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode == null ? null : businessCode.trim();
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp == null ? null : clientIp.trim();
    }

    public BigDecimal getActulAmount() {
        return actulAmount;
    }

    public void setActulAmount(BigDecimal actulAmount) {
        this.actulAmount = actulAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getReplyCode() {
        return replyCode;
    }

    public void setReplyCode(String replyCode) {
        this.replyCode = replyCode == null ? null : replyCode.trim();
    }

    public String getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(String replyMessage) {
        this.replyMessage = replyMessage == null ? null : replyMessage.trim();
    }

    public Date getTransTimeout() {
        return transTimeout;
    }

    public void setTransTimeout(Date transTimeout) {
        this.transTimeout = transTimeout;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getRecvTime() {
        return recvTime;
    }

    public void setRecvTime(Date recvTime) {
        this.recvTime = recvTime;
    }

    public Date getToAccountTime() {
        return toAccountTime;
    }

    public void setToAccountTime(Date toAccountTime) {
        this.toAccountTime = toAccountTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public Long getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(Long bankCardId) {
        this.bankCardId = bankCardId;
    }
}