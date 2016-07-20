package x.y.z.bill.adapter.constant;

import java.util.HashMap;
import java.util.Map;

public enum ChannelCode {

    XIAONIU("100000", "小牛支付"), TENPAY("100001", "财付通"), BILL99("100002", "快钱"), YEEPAY("100003", "易宝"), KFTPAY("100004",
            "快付通"), ICBC("100005", "工行银企"), SZFS("100006", "深圳金融结算中心"), OFFLINE("999999", "线下渠道"), WEIXIN("100007",
                    "微信"), BESTPAY("100008", "翼支付");
    private static Map<String, ChannelCode> map = new HashMap<String, ChannelCode>() {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        {
            for (ChannelCode channelCode : ChannelCode.values()) {
                this.put(channelCode.getCode(), channelCode);
            }
        }
    };
    private String code;
    private String desc;

    ChannelCode(final String code, final String desc) {
        this.desc = desc;
        this.code = code;
    }

    public static ChannelCode valueType(String code) {
        return map.get(code);
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "[" + code + ":" + desc + "]";
    }
}
