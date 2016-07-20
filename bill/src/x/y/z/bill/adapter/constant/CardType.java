package x.y.z.bill.adapter.constant;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 银行卡类型
 */
public enum CardType {
    /**
     * 借记卡
     */
    DEBIT_CARD("D", "借记卡"),
    /**
     * 信用卡
     */
    CREDIT_CARD("C", "信用卡");

    private static final ConcurrentMap<String, CardType> maps = new ConcurrentHashMap<String, CardType>();

    static {
        for (CardType type : values()) {
            maps.put(type.valueOf(), type);
        }
    }

    private String code = "D";
    private String name = "";

    CardType(final String code, final String name) {
        this.code = code;
    }

    public static CardType get(final String code) {
        CardType cardType = maps.get(code);
        if (cardType == null) {
            throw new IllegalArgumentException("卡类型[code = " + code + "]不存在");
        }
        return cardType;
    }

    public String getName() {
        return name;
    }

    public String valueOf() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}
