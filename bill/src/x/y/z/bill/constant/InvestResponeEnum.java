package x.y.z.bill.constant;

public enum InvestResponeEnum {

	/**
	 * 系统异常
	 */
	SYSTEM_FAILED((byte) 0, "INVEST_ERROR_0000", "投资失败系统异常！"),
	/**
	 * 投资成功
	 */
	SUCCESS((byte) 1, "INVEST_SUCCESS_0001", "投资成功");
	private byte status;
	private String resultCode;
	private String resultDetails;

	private InvestResponeEnum(final byte status, final String resultCode,
			final String resultDetails) {
		this.status = status;
		this.resultCode = resultCode;
		this.resultDetails = resultDetails;
	}

	public byte getStatus() {
		return status;
	}

	public String getResultCode() {
		return resultCode;
	}

	public String getResultDetails() {
		return resultDetails;
	}

}
