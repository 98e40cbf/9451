package x.y.z.bill.util;

import java.math.BigDecimal;

import io.alpha.logging.Logger;
import io.alpha.logging.LoggerFactory;

public class ArithmeticUtils {

    private static final Logger logger = LoggerFactory.getLogger(ArithmeticUtils.class);

    /***
     * 一次性计算利息，flag=1按天计算
     * 
     * @param principal
     * @param deadline
     * @param annualRate
     * @param flag
     * @return
     * @author #{yuanjunwen}
     */
    public static double arithmeticInterest(final double principal, final int deadline, final double annualRate,
            final int flag) {
        BigDecimal interest = new BigDecimal(0.00);
        if (flag == 1) {
            interest = new BigDecimal(principal * annualRate * deadline * 0.01 / 360);
        } else {
            interest = new BigDecimal(principal * annualRate * deadline * 0.01 / 12);
        }
        logger.debug("本金：" + principal + " 持有期限：" + deadline + " 年利率：" + annualRate + " 是否按天计算（1按天）：" + flag + " 利息："
                + interest);
        return interest.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static void main(String[] args) {
        System.out.println(arithmeticInterest(5400, 56, 4.5, 1));
    }

}
