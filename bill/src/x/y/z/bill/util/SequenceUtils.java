package x.y.z.bill.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class SequenceUtils {
    /**
     * 用于存放递增序列号，序列区间[000001-999999].
     */
    private static final ArrayBlockingQueue<String> SEQ_QUEUE = new ArrayBlockingQueue<String>(10000);
    /**
     * 自增数
     */
    private static final AtomicInteger COUNTER = new AtomicInteger(1);
    /**
     * [0-9] 随机数
     */
    private static final char[] NUMBERIC = new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
    /**
     * 日期格式[yyMMddHHmmss]
     */
    private static final String DATE_PATTERN = "yyMMddHHmmss";
    private static final ThreadLocal<SimpleDateFormat> locals = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATE_PATTERN);
        };
    };
    /**
     * 随机数长度
     */
    private static final int RANDOM_LENGTH = 6;
    /**
     * 步长数据生成器
     */
    private static final Generator GENERATOR = new Generator();
    private static final ExecutorService executors = Executors.newSingleThreadExecutor();

    static {
        // 生成随机数
        executors.execute(GENERATOR);
    }

    private SequenceUtils() {
        throw new UnsupportedOperationException();
    }

    private static String generator(final int len, final char[] chars) {
        char[] randomChars = new char[len];
        try {
            SecureRandom wheel = SecureRandom.getInstance("SHA1PRNG");
            for (int i = 0; i < len; i++) {
                int nextChar = wheel.nextInt(chars.length);
                randomChars[i] = chars[nextChar];
            }
        } catch (NoSuchAlgorithmException e) {
            // ignore
        }
        return new String(randomChars);
    }

    /**
     * 获取序列号，格式为(四位系统编号[1000] + 业务编号[0000] + yyMMddHHmmss + 6位随机数 + 6位数字,共32位长度)
     */
    public static String getSequence(String systemCode, String businessCode) {
        String currentTime = locals.get().format(new Date());
        StringBuilder buff = new StringBuilder(30);
        buff.append(systemCode);
        buff.append(businessCode);
        buff.append(currentTime);
        buff.append(generator(RANDOM_LENGTH, NUMBERIC));// 6位随机数
        try {
            buff.append(SEQ_QUEUE.poll(500L, TimeUnit.MILLISECONDS));
        } catch (Exception e) {
            // ignore
        }
        return buff.toString();
    }

    /**
     * 随机数生成器
     */
    private static final class Generator implements Runnable {

        @Override
        public void run() {
            for (;;) {
                int currentIdx = COUNTER.getAndIncrement();
                if (currentIdx > 999999) {
                    currentIdx = 1;
                    COUNTER.set(2);
                }

                String index = paddingZero(Integer.toString(currentIdx), 6);
                try {
                    SEQ_QUEUE.put(index);
                } catch (InterruptedException e) {
                }
            }
        }

        /**
         * 前置补零操作
         *
         * @param str
         * @param length
         * @return
         * @author yuanxiangwu
         */
        private String paddingZero(final String str, final int length) {
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < length - str.length(); i++) {
                builder.append("0");
            }

            builder.append(str);
            return builder.toString();
        }
    }
}
