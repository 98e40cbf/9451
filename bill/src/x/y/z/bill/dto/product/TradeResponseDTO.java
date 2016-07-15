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
	private String resultCode;
	private String resultDetails;
	private Long data;

	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultDetails() {
		return resultDetails;
	}

	public void setResultDetails(String resultDetails) {
		this.resultDetails = resultDetails;
	}

	public Long getData() {
		return data;
	}

	public void setData(Long data) {
		this.data = data;
	}

}
