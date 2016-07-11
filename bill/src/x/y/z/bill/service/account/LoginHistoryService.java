package x.y.z.bill.service.account;

import java.util.Date;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import io.alpha.akka.Actors;
import io.alpha.core.util.ApplicationContextHolder;
import x.y.z.bill.mapper.account.LoginHistoryDAO;
import x.y.z.bill.model.account.LoginHistory;

public class LoginHistoryService extends UntypedActor {

    private static final ActorRef R = Actors.actorOf(0, Props.create(LoginHistoryService.class), "login:history-actor");

    public static void checkIn(final long userId, final long loginIp, final String browser) {
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setUserId(userId);
        loginHistory.setLoginIp(loginIp);
        loginHistory.setBrowser(browser);
        loginHistory.setLoginTime(new Date());
        loginHistory.setDevice((byte) 1);
        R.tell(loginHistory, ActorRef.noSender());
    }

    private void checkIn0(final LoginHistory loginHistory) {
        ApplicationContextHolder.getBean(LoginHistoryDAO.class).insert(loginHistory);
    }

    @Override
    public void onReceive(final Object arg0) throws Exception {
        if (arg0 instanceof LoginHistory) {
            checkIn0((LoginHistory) arg0);
        } else {
            unhandled(arg0);
        }
    }

}
