package x.y.z.bill.model.product;

import io.alpha.core.model.BaseObject;

import java.math.BigDecimal;
import java.util.Date;

public class InvestRepayPlan extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long payId;

    private Long investId;

    private Long userId;

    private Long productId;

    private String repayPeriod;

    private Integer repayStatus;

    private Date repayDate;

    private String orderNo;

    private BigDecimal stillPrincipal;

    private BigDecimal stillInterest;

    private String productName;

    private Integer productType;

    private Integer repaymentType;

    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPayId() {
        return payId;
    }

    public void setPayId(Long payId) {
        this.payId = payId;
    }

    public Long getInvestId() {
        return investId;
    }

    public void setInvestId(Long investId) {
        this.investId = investId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(String repayPeriod) {
        this.repayPeriod = repayPeriod == null ? null : repayPeriod.trim();
    }

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public BigDecimal getStillPrincipal() {
        return stillPrincipal;
    }

    public void setStillPrincipal(BigDecimal stillPrincipal) {
        this.stillPrincipal = stillPrincipal;
    }

    public BigDecimal getStillInterest() {
        return stillInterest;
    }

    public void setStillInterest(BigDecimal stillInterest) {
        this.stillInterest = stillInterest;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}