package x.y.z.bill.service.account;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import x.y.z.bill.mapper.account.MobileChangeHistoryDAO;
import x.y.z.bill.model.account.MobileChangeHistory;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import io.alpha.akka.Actors;
import io.alpha.core.config.ProcessorHelper;
import io.alpha.core.util.ApplicationContextHolder;
import io.alpha.security.util.EncryptionUtils;

class MobileChangeHistoryService extends UntypedActor {

    private static final ActorRef R = Actors.actorOf(ProcessorHelper.triple(),
            Props.create(MobileChangeHistoryService.class), "mobile:change-history-actor");

    private static final AtomicReference<MobileChangeHistoryDAO> DAO = new AtomicReference<>();

    static void record(final Long userId, final String oldMobile, final String newMobile) {
        MobileChangeHistory history = new MobileChangeHistory();
        history.setUserId(userId);
        history.setCreateTime(new Date());
        try {
            history.setSource(EncryptionUtils.encryptByAES(oldMobile));
            history.setTarget(EncryptionUtils.encryptByAES(newMobile));
            R.tell(history, ActorRef.noSender());
        } catch (Exception e) {
        }
    }

    @Override
    public void onReceive(final Object arg0) throws Exception {
        if (arg0 instanceof MobileChangeHistory) {
            get().insert((MobileChangeHistory) arg0);
        } else {
            unhandled(arg0);
        }
    }

    private MobileChangeHistoryDAO get() {
        for (;;) {
            MobileChangeHistoryDAO dao = DAO.get();
            if (dao != null) {
                return dao;
            }
            dao = ApplicationContextHolder.getBean(MobileChangeHistoryDAO.class);
            if (DAO.compareAndSet(null, dao)) {
                return dao;
            }
        }
    }

}
