package x.y.z.bill.constant.message;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 短信业务类型
 */
public enum SmsBizTypeEnum {
    /** 1-文本短信 */
    TEXT((byte) 1, "文本短信"),
    /** 2-文本转语音 */
    VOICE((byte) 2, "文本转语音");

    private static final ConcurrentMap<Byte, SmsBizTypeEnum> typeMaps = new ConcurrentHashMap<>();
    static {
        for (SmsBizTypeEnum smsBizTypeEnum : values()) {
            typeMaps.put(smsBizTypeEnum.type, smsBizTypeEnum);
        }
    }

    private final byte type;
    private final String desc;

    private SmsBizTypeEnum(byte type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static SmsBizTypeEnum getEnumByType(byte type) {
        return typeMaps.get(type);
    }

    public byte getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
