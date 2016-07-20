package x.y.z.bill.adapter.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 银行卡账户类型
 */
public enum AccountType {
    /**
     * 默认(个人账户)
     */
    DEFAULT(1, "个人网银"),
    /**
     * 个人账户
     */
    PERSONAL(1, "个人网银"),
    /**
     * 企业账户
     */
    CORPORATE(2, "企业网银");

    private static Map<Integer, AccountType> map = new HashMap<>();

    static {
        for (AccountType accountType : AccountType.values()) {
            map.put(accountType.getValue(), accountType);
        }
    }

    private int value = 1;
    private String desc = "";

    private AccountType(final int value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static AccountType valueType(int value) {
        return map.get(value);
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "[value:" + value + " desc:" + desc + "]";
    }
}
