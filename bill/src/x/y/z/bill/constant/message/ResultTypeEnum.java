package x.y.z.bill.constant.message;

/**
 * 操作结果类型.
 */
public enum ResultTypeEnum {
    /** 0 - 不确定的 */
    UNSURE((byte) 0, "UNSURE", "不确定的"),
    /** 1 - 成功 */
    SUCCESS((byte) 1, "SUCCESS", "成功"),
    /** 2 - 失败 */
    FAILED((byte) 2, "FAILED", "失败"),
    /** 3 - 失败 */
    PARAM_INVALID((byte) 3, "PARAM_INVALID", "无效的参数"),
    /** 4 - 超时 */
    OVERTIME((byte) 4, "OVERTIME", "超时");

    private byte status;
    private String code;
    private String desc;

    private ResultTypeEnum(byte status, String code, String desc) {
        this.status = status;
        this.code = code;
        this.desc = desc;
    }

    public byte getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static boolean isSuccess(int status){
        return status == SUCCESS.status;
    }
}
