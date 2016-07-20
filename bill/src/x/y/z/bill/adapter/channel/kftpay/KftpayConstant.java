package x.y.z.bill.adapter.channel.kftpay;

public enum KftpayConstant {
    // 协议类型：借记卡扣款
    TREATY_TYPE("11", "协议类型：借记卡扣款"),
    // 银行卡类型：借记卡
    BANKCARD_TYPE("1", "银行卡类型：借记卡"),
    // 证件类型：身份证
    CERTIFICATE_TYPE("0", "证件类型：身份"),
    // 币种：人民币
    CURRENCY("CNY", " 币种：人民币");

    private String val;
    private String desc;

    private KftpayConstant(String val, String desc) {
        this.val = val;
        this.desc = desc;
    }

    public String val() {
        return val;
    }

    public String desc() {
        return desc;
    }
}
