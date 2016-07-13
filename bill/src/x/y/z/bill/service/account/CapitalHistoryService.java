package x.y.z.bill.service.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.transaction.TransactionDefinition;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import io.alpha.akka.Actors;
import io.alpha.core.config.ProcessorHelper;
import io.alpha.core.util.ApplicationContextHolder;
import io.alpha.tx.TxHelper;
import x.y.z.bill.mapper.account.CapitalOperationHistoryDAO;
import x.y.z.bill.model.account.CapitalOperationHistory;

class CapitalHistoryService extends UntypedActor {

    private static final ActorRef R = Actors.actorOf(ProcessorHelper.triple(),
            Props.create(CapitalHistoryService.class), "capital:history-actor");

    private static final AtomicReference<CapitalOperationHistoryDAO> DAO = new AtomicReference<>();

    static void record(final Long userId, final BigDecimal amount, final String txnId, final byte bizType,
            final String memo) {
        CapitalOperationHistory history = new CapitalOperationHistory();
        history.setUserId(userId);
        history.setTxnId(txnId);
        history.setBizType(bizType);
        history.setAmount(amount);
        history.setMemo(memo);
        history.setCreateTime(new Date());
        R.tell(history, ActorRef.noSender());
    }

    @Override
    public void onReceive(final Object arg0) throws Exception {
        if (arg0 instanceof CapitalOperationHistory) {
            TxHelper.execute(TransactionDefinition.PROPAGATION_REQUIRES_NEW, () -> {
                get().insert((CapitalOperationHistory) arg0);
                return null;
            });
        } else {
            unhandled(arg0);
        }
    }

    private CapitalOperationHistoryDAO get() {
        for (;;) {
            CapitalOperationHistoryDAO dao = DAO.get();
            if (dao != null) {
                return dao;
            }
            dao = ApplicationContextHolder.getBean(CapitalOperationHistoryDAO.class);
            if (DAO.compareAndSet(null, dao)) {
                return dao;
            }
        }
    }

}
