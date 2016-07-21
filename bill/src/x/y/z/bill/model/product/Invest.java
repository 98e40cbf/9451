package x.y.z.bill.model.product;

import java.math.BigDecimal;
import java.util.Date;

import io.alpha.core.model.BaseObject;

public class Invest extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Long id;

    private BigDecimal investAmount;

    private Long userId;

    private String userName;

    private Long productId;

    private Date investDate;

    private BigDecimal hasPI;

    private Integer deadline;

    private Integer hasDeadline;

    private BigDecimal recivedPrincipal;

    private BigDecimal recievedInterest;

    private BigDecimal hasPrincipal;

    private BigDecimal hasInterest;

    private Integer investStatus;

    private String orderNo;

    private Integer platform;

    private String clientVersion;

    private String productName;

    private Integer productType;

    private Integer repaymentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(BigDecimal investAmount) {
        this.investAmount = investAmount;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Date getInvestDate() {
        return investDate;
    }

    public void setInvestDate(Date investDate) {
        this.investDate = investDate;
    }

    public BigDecimal getHasPI() {
        return hasPI;
    }

    public void setHasPI(BigDecimal hasPI) {
        this.hasPI = hasPI;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    public Integer getHasDeadline() {
        return hasDeadline;
    }

    public void setHasDeadline(Integer hasDeadline) {
        this.hasDeadline = hasDeadline;
    }

    public BigDecimal getRecivedPrincipal() {
        return recivedPrincipal;
    }

    public void setRecivedPrincipal(BigDecimal recivedPrincipal) {
        this.recivedPrincipal = recivedPrincipal;
    }

    public BigDecimal getRecievedInterest() {
        return recievedInterest;
    }

    public void setRecievedInterest(BigDecimal recievedInterest) {
        this.recievedInterest = recievedInterest;
    }

    public BigDecimal getHasPrincipal() {
        return hasPrincipal;
    }

    public void setHasPrincipal(BigDecimal hasPrincipal) {
        this.hasPrincipal = hasPrincipal;
    }

    public BigDecimal getHasInterest() {
        return hasInterest;
    }

    public void setHasInterest(BigDecimal hasInterest) {
        this.hasInterest = hasInterest;
    }

    public Integer getInvestStatus() {
        return investStatus;
    }

    public void setInvestStatus(Integer investStatus) {
        this.investStatus = investStatus;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion == null ? null : clientVersion.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(Integer repaymentType) {
        this.repaymentType = repaymentType;
    }
}