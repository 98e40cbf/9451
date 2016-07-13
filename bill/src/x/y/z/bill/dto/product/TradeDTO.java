package x.y.z.bill.dto.product;

import java.math.BigDecimal;

import io.alpha.core.model.BaseObject;

public class TradeDTO extends BaseObject {

	private static final long serialVersionUID = 1L;

	private Long productId;

	private Long userId;

	private BigDecimal investAmount;
	
	private Integer platform;

	private String clientVersion;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
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
		this.clientVersion = clientVersion;
	}

}
