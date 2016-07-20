package x.y.z.bill.adapter.channel.kftpay;

public enum TreatyStatus {

    VALID(1, "有效"), INVALID(2, "无效"), FREEZE(3, "冻结");

    private int code;
    private String desc;

    TreatyStatus(final int code, final String desc) {
        this.desc = desc;
        this.code = code;
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
