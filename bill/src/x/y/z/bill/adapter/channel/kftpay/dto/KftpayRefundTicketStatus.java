package x.y.z.bill.adapter.channel.kftpay.dto;

/**
 * 快付通退票状态 0未退票 1已退票
 */
public enum KftpayRefundTicketStatus {

    UN_PROSESSED(0, "未退票"), PROSESSED(1, "已退票");

    private int code;
    private String desc;

    KftpayRefundTicketStatus(final int code, final String desc) {
        this.desc = desc;
        this.code = code;
    }

    public static String getTradeStatusDesc(int code) {
        for (KftpayRefundTicketStatus c : KftpayRefundTicketStatus.values()) {
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
