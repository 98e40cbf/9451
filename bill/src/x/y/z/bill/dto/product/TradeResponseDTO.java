package x.y.z.bill.dto.product;

import io.alpha.core.model.BaseObject;

/**
 * 交易返回结果DTO
 * 
 * @author YY
 *
 */
public class TradeResponseDTO extends BaseObject {

	private static final long serialVersionUID = 1L;

	private Integer status;
	private String errorCode;
	private String errorDetails;
	private Long data;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}

	public Long getData() {
		return data;
	}

	public void setData(Long data) {
		this.data = data;
	}

}
