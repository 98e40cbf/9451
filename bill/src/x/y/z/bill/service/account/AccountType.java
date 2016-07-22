package x.y.z.bill.service.account;

enum AccountType {

    /**/
    INVEST_MID_ACCT((byte) 1, "投资中间账户"),
    /**/
    USER_BALANCE_ACCT((byte) 51, "用户余额账户"),
    /**/
    USER_FROZEN_ACCT((byte) 52, "用户冻结金额账户");

    private final byte code;
    private final String desc;

    private AccountType(final byte code, final String desc) {
        this.code = code;
        this.desc = desc;
    }

    public byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
