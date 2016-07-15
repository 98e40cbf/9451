package x.y.z.bill.util;

import io.alpha.util.SequenceHelper;

/**
 * ID生成器
 */
public class IDGenerator {

    public static String generalInvestOrderId(final Long userId) {
        return SequenceHelper.get();
    }

}