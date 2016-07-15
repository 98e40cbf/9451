package x.y.z.bill.util;

import java.util.Date;
import java.util.Random;

import com.itrus.util.DateUtils;

/**
 * ID生成器
 * 
 */
public class IDGenerator {
    /**
     * 产生投资订单ID
     * 规则: 当前时间(14位) + 用户ID(10位，不够前面补0) + 5位定长随机码
     * @param userId   用户ID
     * @return
     */
    public static String generalInvestOrderId(Long userId) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(DateUtils.getLongDateString(new Date()));
        buffer.append(getFixedTenLenthUserId(userId.toString()));
        buffer.append(getFixedFiveLenthNumber());
        return buffer.toString();
    }
    /**
     * 获取固定长度用户ID(10位,不够前面补0)
     * 
     * @param userId
     * @return
     *
     * @author qiujianfeng 2015年1月12日 下午2:51:07
     */
    private static String getFixedTenLenthUserId(String userId)
    {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 10 - userId.length(); i++) {
            // 不足5位前面补零
            buffer.append("0");
        }
        buffer.append(userId);
        return buffer.toString();
    }

    /**
     * 获取5位定长随机码
     * @return
     */
    private static String getFixedFiveLenthNumber() {
        StringBuffer buffer = new StringBuffer("");
        // 1-99999随机数
        String radomNumber = String.valueOf(new Random().nextInt(99998) + 1);
        for (int i = 0; i < 5 - radomNumber.length(); i++) {
            // 不足5位前面补零
            buffer.append("0");
        }
        buffer.append(radomNumber);
        return buffer.toString();

    }

}