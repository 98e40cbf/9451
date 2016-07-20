package x.y.z.bill.adapter.channel.dto;

public enum PayStatus {
    OK(0, "支付成功"), FAIL(1, "支付失败"), UNKNOWN(-1, "未知");
    private int status;
    private String desc;

    PayStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int status() {
        return status;
    }

    public String desc() {
        return desc;
    }
}
