package x.y.z.bill.adapter.channel.kftpay;

/**
 * 快付通交易状态 0 未处理 1 交易成功 2 交易失败 3 交易处理中 4 异常成功 5 异常失败
 */
public enum KftpayTradeStatus {

    UN_PROCESSED(0, "未处理"), SUCCESS(1, "交易成功"), FAIL(2, "交易失败"), IN_PROCESS(3, "交易处理中"), EXCEPTION_SUCCESS(4,
            "异常成功"), EXCEPTION_FAIL(5, "异常失败");

    private int code;
    private String desc;

    KftpayTradeStatus(final int code, final String desc) {
        this.desc = desc;
        this.code = code;
    }

    public static String getTradeStatusDesc(int code) {
        for (KftpayTradeStatus c : KftpayTradeStatus.values()) {
            if (c.getCode() == code) {
                return c.desc;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(final String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "[code:+" + code + "  desc:" + desc + "]";
    }
}