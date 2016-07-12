package x.y.z.bill.service.account;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import io.alpha.akka.Actors;
import io.alpha.core.config.ProcessorHelper;
import io.alpha.core.util.ApplicationContextHolder;
import x.y.z.bill.mapper.account.LoginHistoryDAO;
import x.y.z.bill.model.account.LoginHistory;

class LoginHistoryService extends UntypedActor {

    private static final ActorRef R = Actors.actorOf(ProcessorHelper.triple(), Props.create(LoginHistoryService.class),
            "login:history-actor");

    private static final AtomicReference<LoginHistoryDAO> DAO = new AtomicReference<>();

    public static void checkIn(final long userId, final long loginIp, final String browser) {
        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setUserId(userId);
        loginHistory.setLoginIp(loginIp);
        loginHistory.setBrowser(browser);
        loginHistory.setLoginTime(new Date());
        loginHistory.setDevice((byte) 1);
        R.tell(loginHistory, ActorRef.noSender());
    }

    @Override
    public void onReceive(final Object arg0) throws Exception {
        if (arg0 instanceof LoginHistory) {
            get().insert((LoginHistory) arg0);
        } else {
            unhandled(arg0);
        }
    }

    private LoginHistoryDAO get() {
        for (;;) {
            LoginHistoryDAO dao = DAO.get();
            if (dao != null) {
                return dao;
            }
            dao = ApplicationContextHolder.getBean(LoginHistoryDAO.class);
            if (DAO.compareAndSet(null, dao)) {
                return dao;
            }
        }
    }

}
