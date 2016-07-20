package x.y.z.bill.adapter.constant;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public enum BusinessCode {

    NONE("00"), // 无业务编码
    RECHARGE("01"), // 网银充值业务
    PROXY_PAY("02"), // 代付业务（提现）
    BIND_CARD("03"), // 绑卡业务
    AUTH_CARD("04"), // 鉴权
    QUICK_PAY("05"), // 快捷支付
    ICBC_ONLINE_PAY("06"), // 工行签约批扣
    WEIXIN_PAY("07");// 微信支付

    private static final ConcurrentMap<String, BusinessCode> typeNames = new ConcurrentHashMap<String, BusinessCode>();

    static {
        for (BusinessCode code : values()) {
            typeNames.put(code.getCode(), code);
        }
    }

    private String code;

    BusinessCode(final String code) {
        this.code = code;
    }

    public static BusinessCode get(final String code) {
        return typeNames.get(code);
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return getCode();
    }
}
