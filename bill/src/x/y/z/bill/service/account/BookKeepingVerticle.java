package x.y.z.bill.service.account;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

import io.alpha.core.util.ApplicationContextHolder;
import io.alpha.logging.Logger;
import io.alpha.logging.LoggerFactory;
import io.alpha.tx.TxHelper;
import io.alpha.util.FstUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import x.y.z.bill.constant.BizType;
import x.y.z.bill.mapper.account.BookKeepingDAO;
import x.y.z.bill.model.account.BookKeeping;

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
                final Long userId = accounting.getUserId();
                switch (bizType) {
                    case RECHARGE:
                        handleRecharge(userId, amount);
                        break;
                    case INVEST_UNFREEZE:
                        handleInvest(userId, amount);
                        break;
                    case PROFITS:
                        handleProfit(userId, amount);
                        break;
                    case WITHDRAW_UNFREEZE:
                        handleWithdraw(userId, amount);
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            } catch (Throwable ignore) {
                logger.catching(ignore);
            }
        });
    }

    private void handleWithdraw(final Long userId, final BigDecimal amount) {
        BookKeeping company = new BookKeeping();
        company.setWithdraw(amount);
        company.setUserId(-1L);
        BookKeeping user = new BookKeeping();
        user.setWithdraw(amount);
        user.setUserId(userId);
        TxHelper.execute(() -> {
            get().amountToWithdraw(company);
            get().amountToWithdraw(user);
            return null;
        });
    }

    private void handleProfit(final Long userId, final BigDecimal amount) {
        BookKeeping company = new BookKeeping();
        company.setProfit(amount);
        company.setUserId(-1L);
        BookKeeping user = new BookKeeping();
        user.setProfit(amount);
        user.setUserId(userId);
        TxHelper.execute(() -> {
            get().amountToProfit(company);
            get().amountToProfit(user);
            return null;
        });
    }

    private void handleInvest(final Long userId, final BigDecimal amount) {
        BookKeeping company = new BookKeeping();
        company.setInvest(amount);
        company.setUserId(-1L);
        BookKeeping user = new BookKeeping();
        user.setInvest(amount);
        user.setUserId(userId);
        TxHelper.execute(() -> {
            get().amountToInvest(company);
            get().amountToInvest(user);
            return null;
        });
    }

    private void handleRecharge(final Long userId, final BigDecimal amount) {
        BookKeeping company = new BookKeeping();
        company.setRecharge(amount);
        company.setUserId(-1L);
        BookKeeping user = new BookKeeping();
        user.setRecharge(amount);
        user.setUserId(userId);
        TxHelper.execute(() -> {
            get().amountToRecharge(company);
            get().amountToRecharge(user);
            return null;
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
