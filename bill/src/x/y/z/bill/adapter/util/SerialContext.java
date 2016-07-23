package x.y.z.bill.adapter.util;

/**
 *
 */
public class SerialContext {

    private static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<String>() {
        protected String initialValue() {
            return null;
        }
    };

    public static final String get() {
        return THREAD_LOCAL.get();
    }

    public static final String set(final String txnId) {
        THREAD_LOCAL.set(txnId);
        return THREAD_LOCAL.get();
    }

}
