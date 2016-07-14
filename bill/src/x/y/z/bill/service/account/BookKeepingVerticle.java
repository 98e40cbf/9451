package x.y.z.bill.service.account;

import java.util.concurrent.atomic.AtomicReference;

import io.alpha.core.util.ApplicationContextHolder;
import io.alpha.util.FstUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import x.y.z.bill.mapper.account.BookKeepingDAO;

class BookKeepingVerticle extends AbstractVerticle {

    private static final AtomicReference<BookKeepingDAO> DAO = new AtomicReference<>();

    @Override
    public void start() throws Exception {
        EventBus eventBus = getVertx().eventBus();
        eventBus.consumer(CapitalService.BOOKKEEPING_ADDRESS, msg -> {
            Accounting accounting = FstUtils.deserialize((byte[]) msg.body());
            switch (accounting.getBizType()) {
                case RECHARGE:
                    break;
                case INVEST_UNFREEZE:
                    break;
                case WITHDRAW_UNFREEZE:
                    break;
                default:
                    break;
            }
        });
    }

    private BookKeepingDAO get() {
        for (;;) {
            BookKeepingDAO dao = DAO.get();
            if (dao != null) {
                return dao;
            }
            dao = ApplicationContextHolder.getBean(BookKeepingDAO.class);
            if (DAO.compareAndSet(null, dao)) {
                return dao;
            }
        }
    }

}
