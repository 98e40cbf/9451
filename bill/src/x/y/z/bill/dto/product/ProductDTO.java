package x.y.z.bill.dto.product;

import io.alpha.core.model.BaseObject;

import java.math.BigDecimal;
import java.util.Date;

public class ProductDTO extends BaseObject {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String productName;

	private BigDecimal amount;

	private BigDecimal annualRate;

	private BigDecimal surplusAmount;

	private BigDecimal investTotalAmount;

	private BigDecimal investSchedule;

	private Integer deadline;

	private Integer status;

	private Integer repaymentType;

	private Integer productType;

	private Integer productArea;

	private String productNo;

	private Date valueDate;

	private Date auditDate;

	private Date createDate;

	private Date fullDate;

	private int pageNum;

	private int pageSize;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName == null ? null : productName.trim();
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAnnualRate() {
		return annualRate;
	}

	public void setAnnualRate(BigDecimal annualRate) {
		this.annualRate = annualRate;
	}

	public BigDecimal getSurplusAmount() {
		return surplusAmount;
	}

	public void setSurplusAmount(BigDecimal surplusAmount) {
		this.surplusAmount = surplusAmount;
	}

	public BigDecimal getInvestTotalAmount() {
		return investTotalAmount;
	}

	public void setInvestTotalAmount(BigDecimal investTotalAmount) {
		this.investTotalAmount = investTotalAmount;
	}

	public BigDecimal getInvestSchedule() {
		return investSchedule;
	}

	public void setInvestSchedule(BigDecimal investSchedule) {
		this.investSchedule = investSchedule;
	}

	public Integer getDeadline() {
		return deadline;
	}

	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(Integer repaymentType) {
		this.repaymentType = repaymentType;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public Integer getProductArea() {
		return productArea;
	}

	public void setProductArea(Integer productArea) {
		this.productArea = productArea;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo == null ? null : productNo.trim();
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getFullDate() {
		return fullDate;
	}

	public void setFullDate(Date fullDate) {
		this.fullDate = fullDate;
	}
}
