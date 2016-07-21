package x.y.z.bill.model.product;

import java.math.BigDecimal;
import java.util.Date;

import io.alpha.core.model.BaseObject;

public class ProductRepayPlan extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Date repayDate;

    private Date realRepayDate;

    private String repayPeriod;

    private BigDecimal hasPI;

    private BigDecimal stillPrincipal;

    private BigDecimal stillInterest;

    private Integer repayStatus;

    private Long productId;

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

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    public Date getRealRepayDate() {
        return realRepayDate;
    }

    public void setRealRepayDate(Date realRepayDate) {
        this.realRepayDate = realRepayDate;
    }

    public String getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(String repayPeriod) {
        this.repayPeriod = repayPeriod == null ? null : repayPeriod.trim();
    }

    public BigDecimal getHasPI() {
        return hasPI;
    }

    public void setHasPI(BigDecimal hasPI) {
        this.hasPI = hasPI;
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

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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