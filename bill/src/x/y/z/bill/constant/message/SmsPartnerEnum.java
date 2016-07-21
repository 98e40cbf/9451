package x.y.z.bill.constant.message;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import x.y.z.bill.service.message.handler.TaoBaoSmsHandler;

/**
 * 短信渠道枚举
 */
public enum SmsPartnerEnum {
    TAOBAO((byte) 1, TaoBaoSmsHandler.class, "taoBaoSmsHandler", "阿里大鱼");

    private static final ConcurrentMap<Byte, SmsPartnerEnum> typeMaps = new ConcurrentHashMap<>();

    static {
        for (SmsPartnerEnum smsPartnerEnum : values()) {
            typeMaps.put(smsPartnerEnum.type, smsPartnerEnum);
        }
    }

    private final Class<?> paramClazz;
    private final String handlerBeanId;
    private byte type;
    private String desc;

    SmsPartnerEnum(byte type, final Class<?> paramClazz, final String handlerBeanId, String desc) {
        this.type = type;
        this.paramClazz = paramClazz;
        this.handlerBeanId = handlerBeanId;
        this.desc = desc;
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
