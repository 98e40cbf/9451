package x.y.z.bill.adapter.constant;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public enum ReconBusinessType {

    QUICK_PAY("qpay", "快捷支付"), NETBANK_PAY("netbankpay", "网银支付"), RECHARGE("recharge", "充值"), WITHDRAW("withdraw",
            "提现"), ALL("all", "所有交易");

    private static final ConcurrentMap<String, ReconBusinessType> typeNames = new ConcurrentHashMap<String, ReconBusinessType>();

    static {
        for (ReconBusinessType type : values()) {
            typeNames.put(type.getType(), type);
        }
    }

    private String type;
    private String name;

    ReconBusinessType(final String type, final String name) {
        this.name = name;
        this.type = type;
    }

    public static ReconBusinessType get(final String type) {
        return typeNames.get(type);
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ReconBusinessType{type:" + type + ",  name:" + name + "}";
    }

}
