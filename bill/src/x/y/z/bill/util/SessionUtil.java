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
        return currentUser(session) != UserSession.NULL;
    }

    public static UserSession currentUser(final HttpSession session) {
        UserSession currentUser = (UserSession) session.getAttribute(CURRENT_LOGIN_USER);
        if (currentUser != null) {
            return currentUser;
        }
        return UserSession.NULL;

    }

}
