package x.y.z.bill.constant;

/**
 * 短信状态类型
 */
public enum SmsStatusEnum {
    /** 0-失败 */
    FAILED((byte) 0, "失败"),
    /** 1-成功 */
    SUCCESS((byte) 1, "成功"),
    /** 2-超时 */
    OVERTIME((byte) 2, "超时");

    private byte status;
    private String desc;

    private SmsStatusEnum(final byte status, final String desc) {
        this.status = status;
        this.desc = desc;
    }

    public byte getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

}
