package x.y.z.bill.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.lambdaworks.redis.RedisConnection;

import io.alpha.core.util.ApplicationContextHolder;
import io.alpha.util.DateUtils;
import x.y.z.bill.dto.UserSession;

public final class SessionUtil {

    // TODO alpha?
    public static final String CURRENT_LOGIN_USER_NAME = "CURRENT_LOGIN_USER_NAME";
    public static final String CURRENT_LOGIN_USER = "CURRENT_LOGIN_USER";

    public static final String REGIST_VERIFICATION_CODE_KEY = "REGIST_verification_code_key";

    private static final AtomicReference<RedisConnection<String, String>> CONNECTION = new AtomicReference<>();

    @SuppressWarnings("unchecked")
    private static RedisConnection<String, String> get() {
        for (;;) {
            RedisConnection<String, String> connection = CONNECTION.get();
            if (connection != null) {
                return connection;
            }
            connection = ApplicationContextHolder.getBean("billRedisConnection", RedisConnection.class);
            if (CONNECTION.compareAndSet(null, connection)) {
                return connection;
            }
        }
    }

    public static void setAttribute(final HttpServletRequest request, final String key, final Object value) {
        setAttribute(request.getSession(true), key, value);
    }

    public static void setAttribute(final HttpSession session, final String key, final Object value) {
        session.setAttribute(key, value);
    }

    public static boolean hasLogin(final HttpSession session) {
        return UserSession.NULL.equals(currentUser(session)) == false;
    }

    public static UserSession currentUser(final HttpSession session) {
        Object object = session.getAttribute(CURRENT_LOGIN_USER);
        if (object instanceof UserSession) {
            return (UserSession) object;
        }
        return UserSession.NULL;

    }

    public static void invalidate(final HttpSession session) {
        Enumeration<String> attributeNames = session.getAttributeNames();
        List<String> names = new ArrayList<>();
        while (attributeNames.hasMoreElements()) {
            names.add(attributeNames.nextElement());
        }
        names.forEach(session::removeAttribute);
        session.invalidate();
    }

    public static void setSMSVerificationCode(final HttpSession session, final String key, final String mobile,
            final String verificationCode) {
        setAttribute(session, key,
                String.join("_", Long.toString(System.currentTimeMillis()), verificationCode, mobile));
    }

    public static boolean verifySMSVerificationCode(final HttpSession session, final String key, final String mobile,
            final String verificationCode) {
        String savedCode = (String) session.getAttribute(key);
        if (savedCode != null) {
            int idx = savedCode.indexOf("_");
            if (idx > 0) {
                long timestamp = Long.parseLong(savedCode.substring(0, idx));
                if (System.currentTimeMillis() - timestamp > DateUtils.MILLIS_PER_MINUTE * 10) {
                    session.removeAttribute(key);
                    return false;
                }
                int lastIdx = savedCode.lastIndexOf("_");
                String code = savedCode.substring(idx + 1, lastIdx);
                if (code.equals(verificationCode)) {
                    if (savedCode.substring(lastIdx + 1).equals(mobile)) {
                        session.removeAttribute(key);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void setSMSVerificationCode(final HttpSession session, final String key,
            final String verificationCode) {
        UserSession currentUser = currentUser(session);
        if (!UserSession.NULL.equals(currentUser)) {
            setSMSVerificationCode(session, key, currentUser.getMobile(), verificationCode);
        }
    }

    public static boolean verifySMSVerificationCode(final HttpSession session, final String key,
            final String verificationCode) {
        UserSession currentUser = currentUser(session);
        if (UserSession.NULL.equals(currentUser)) {
            return false;
        }
        return verifySMSVerificationCode(session, key, currentUser.getMobile(), verificationCode);
    }

    public static boolean checkVerificationLimit(final String key, final int limit) {
        RedisConnection<String, String> connection = get();
        long count = connection.incr(key);
        if (count == 1) {
            connection.expireat(key, lastTimeOfDay());
            return true;
        }
        if (count <= limit) {
            return true;
        }
        return false;
    }

    private static Date lastTimeOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

}
