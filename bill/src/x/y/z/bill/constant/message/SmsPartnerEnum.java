package x.y.z.bill.constant.message;

import x.y.z.bill.service.message.handler.TaoBaoSmsHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 短信渠道枚举
 */
public enum SmsPartnerEnum {
    TAOBAO((byte) 1, TaoBaoSmsHandler.class, "taoBaoSmsHandler", "阿里大鱼");

    private byte type;
    private final Class<?> paramClazz;
    private final String handlerBeanId;
    private String desc;
    private static final ConcurrentMap<Byte, SmsPartnerEnum> typeMaps = new ConcurrentHashMap<>();

    SmsPartnerEnum(byte type, final Class<?> paramClazz, final String handlerBeanId, String desc) {
        this.type = type;
        this.paramClazz = paramClazz;
        this.handlerBeanId = handlerBeanId;
        this.desc = desc;
    }

    static {
        for (SmsPartnerEnum smsPartnerEnum : values()) {
            typeMaps.put(smsPartnerEnum.type, smsPartnerEnum);
        }
    }

    public static SmsPartnerEnum getByType(final byte type) {
        return typeMaps.get(type);

    }

    public byte getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getHandlerBeanId() {
        return handlerBeanId;
    }

    public Class<?> getParamClazz() {
        return paramClazz;
    }
}
