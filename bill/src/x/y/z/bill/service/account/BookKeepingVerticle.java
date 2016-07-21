package x.y.z.bill.service.account;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

import x.y.z.bill.constant.BizType;
import x.y.z.bill.mapper.account.BookKeepingDAO;
import x.y.z.bill.model.account.BookKeeping;
import io.alpha.core.util.ApplicationContextHolder;
import io.alpha.logging.Logger;
import io.alpha.logging.LoggerFactory;
import io.alpha.util.FstUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class BookKeepingVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(BookKeepingVerticle.class);

    private static final AtomicReference<BookKeepingDAO> DAO = new AtomicReference<>();

    @Override
    public void start() throws Exception {
        EventBus eventBus = getVertx().eventBus();
        eventBus.consumer(CapitalService.BOOKKEEPING_ADDRESS, msg -> {
            try {
                final Accounting accounting = FstUtils.deserialize((byte[]) msg.body());
                final BigDecimal amount = accounting.getAmount();
                final BizType bizType = accounting.getBizType();
                final BookKeeping bookKeeping = new BookKeeping();
                bookKeeping.setId(1);
                if (BizType.RECHARGE == bizType) {
                    bookKeeping.setRecharge(amount);
                    get().amountToRecharge(bookKeeping);
                } else if (BizType.INVEST_UNFREEZE == bizType) {
                    bookKeeping.setInvest(amount);
                    get().amountToInvest(bookKeeping);
                } else if (BizType.WITHDRAW_UNFREEZE == bizType) {
                    bookKeeping.setWithdraw(amount);
                    get().amountToWithdraw(bookKeeping);
                }
            } catch (Throwable ignore) {
                logger.catching(ignore);
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
