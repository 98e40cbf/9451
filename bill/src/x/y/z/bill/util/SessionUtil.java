package x.y.z.bill.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import x.y.z.bill.dto.UserSession;

public final class SessionUtil {

    // TODO alpha?
    public static final String CURRENT_LOGIN_USER_NAME = "CURRENT_LOGIN_USER_NAME";
    public static final String CURRENT_LOGIN_USER = "CURRENT_LOGIN_USER";

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
        UserSession currentUser = (UserSession) session.getAttribute(CURRENT_LOGIN_USER);
        if (currentUser != null) {
            return currentUser;
        }
        return UserSession.NULL;

    }

    public static void invalidate(final HttpSession session) {
        // Enumeration<String> attributeNames = session.getAttributeNames();
        // List<String> names = new ArrayList<>();
        // while (attributeNames.hasMoreElements()) {
        // names.add(attributeNames.nextElement());
        // }
        // names.forEach(session::removeAttribute);
        session.removeAttribute(CURRENT_LOGIN_USER);
        session.invalidate();
    }

    public static void setSMSVerificationCode(final HttpSession session, final String key, final String mobile,
            final String verificationCode) {
        session.setAttribute(key, String.join("_", mobile, verificationCode));
    }

    public static boolean verifySMSVerificationCode(final HttpSession session, final String key, final String mobile,
            final String verificationCode) {
        String savedCode = (String) session.getAttribute(key);
        if (savedCode != null) {
            int idx = savedCode.indexOf("_");
            if (idx > 0) {
                String code = savedCode.substring(idx + 1);
                if (code.equals(verificationCode)) {
                    if (savedCode.substring(0, idx).equals(mobile)) {
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

}
