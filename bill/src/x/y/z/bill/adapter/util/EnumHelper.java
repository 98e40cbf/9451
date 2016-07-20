package x.y.z.bill.adapter.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnumHelper {
    private static final Logger logger = LoggerFactory.getLogger(EnumHelper.class);

    /**
     * 方法说明：<br> 将对应值转换为相关枚举
     * 
     * @param clazz 枚举类
     * @param val 枚举值
     */
    public static <T extends Enum<T>> T convert(Class<T> clazz, String val) {
        if (null == clazz) {
            throw new RuntimeException("必须指定枚举类");
        }
        if (StringUtils.isEmpty(val)) {
            return null;
        }
        try {
            return Enum.valueOf(clazz, val);
        } catch (Exception e) {
            logger.error(String.format("枚举[%s]无此值: [%s]", clazz.getName(), val), e);
            throw new RuntimeException(String.format("无[%s]枚举值", val));
        }
    }

}
