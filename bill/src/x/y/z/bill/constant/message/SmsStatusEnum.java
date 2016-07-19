package x.y.z.bill.constant.message;

/**
 * 短信状态类型
 */
public enum SmsStatusEnum {
    /** 0-初始化 */
    INIT((byte) 0, "初始化"),
    /** 1-成功 */
    SUCCESS((byte) 1, "成功"),
    /** 2-失败 */
    FAILED((byte) 2, "失败"),
    /** 3-超时 */
    OVERTIME((byte) 3, "超时");

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
