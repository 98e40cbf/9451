package x.y.z.bill.adapter.util;

import java.math.BigDecimal;

import io.alpha.util.DecimalUtil;

public class AmountUtil {
    public static Long yuanToFen(BigDecimal amount) {
        return DecimalUtil.formatWithoutScale(DecimalUtil.multiply(amount, new BigDecimal("100"))).longValue();
    }

    public static BigDecimal fenToYuan(BigDecimal amount) {
        return DecimalUtil.divide(amount, new BigDecimal("100"));
    }

    public static BigDecimal fenToYuan(String amount) {
        return DecimalUtil.divide(new BigDecimal(amount), new BigDecimal("100"));
    }
}
