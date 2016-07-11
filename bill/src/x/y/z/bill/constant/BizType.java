package x.y.z.bill.constant;

public enum BizType {

    /***/
    RECHARGE((byte) 1, "充值"),
    /***/
    INVEST_APPLY((byte) 2, "投资申请"),
    /***/
    INVEST_UNFREEZE((byte) 3, "投资解冻"),
    /***/
    PROFITS((byte) 4, "收益"),
    /***/
    WITHDRAW_APPLY((byte) 5, "提现申请"),
    /***/
    WITHDRAW_UNFREEZE((byte) 6, "提现解冻");

    private final byte code;
    private final String desc;

    BizType(final byte code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

    public byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static byte preType(final BizType bizType) {
        switch (bizType) {
            case INVEST_UNFREEZE:
                return INVEST_APPLY.getCode();
            case WITHDRAW_UNFREEZE:
                return WITHDRAW_APPLY.getCode();
            default:
                throw new IllegalArgumentException();
        }
    }

}
