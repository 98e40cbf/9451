package x.y.z.bill.constant;

public enum BizType {

    /***/
    RECHARGE((byte) 1, "充值"),
    /***/
    INVEST_FREEZE((byte) 2, "投资冻结 "),
    /***/
    INVEST_UNFREEZE((byte) 3, "投资解冻"),
    /***/
    WITHDRAW_FREEZE((byte) 4, "提现冻结 "),
    /***/
    WITHDRAW_UNFREEZE((byte) 5, "提现解冻 ");

    private final byte type;
    private final String desc;

    BizType(final byte type, final String desc) {
        this.type = type;
        this.desc = desc;
    }

    public byte getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

}
