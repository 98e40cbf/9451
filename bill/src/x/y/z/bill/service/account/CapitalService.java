package x.y.z.bill.service.account;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.alpha.core.config.ProcessorHelper;
import io.alpha.core.dto.PageResultDTO;
import io.alpha.log.annotation.IgnoreLog;
import io.alpha.mybatis.statement.RecordCountHelper;
import io.alpha.service.BaseService;
import io.alpha.tx.annotation.TransMark;
import io.alpha.util.DecimalUtil;
import io.alpha.util.FstUtils;
import io.alpha.vertx.util.VertxUtils;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import x.y.z.bill.constant.BizType;
import x.y.z.bill.constant.Direction;
import x.y.z.bill.exception.AccountNotFoundExcepiton;
import x.y.z.bill.exception.BalanceNotEnoughException;
import x.y.z.bill.exception.CapitalJournalNotFoundException;
import x.y.z.bill.mapper.account.CapitalAccountDAO;
import x.y.z.bill.mapper.account.CapitalJournalDAO;
import x.y.z.bill.model.account.CapitalAccount;
import x.y.z.bill.model.account.CapitalJournal;

@IgnoreLog
@Service
@TransMark
class CapitalService extends BaseService {

    static final String BOOKKEEPING_ADDRESS = "bookkeeping.accounting.address";
    private static final Vertx vertx;

    static {
        vertx = VertxUtils.get("BOOKKEEPING-ACCOUNTING-Vertx");
        vertx.deployVerticle(BookKeepingVerticle.class.getName(),
                new DeploymentOptions().setInstances(ProcessorHelper.triple()));
    }

    @Autowired
    private CapitalAccountDAO capitalAccountDAO;
    @Autowired
    private CapitalJournalDAO capitalJournalDAO;

    public void createAccount(final Long userId) {
        CapitalAccount account = new CapitalAccount();
        account.setUserId(userId);
        account.setBalance(BigDecimal.ZERO);
        account.setFrozen(BigDecimal.ZERO);
        account.setVersion(0L);
        account.setLastUpdate(new Date());
        account.setDigest("n/a");
        capitalAccountDAO.insert(account);
    }

    public void add(final Long userId, final BigDecimal amount, final String txnId, final String memo,
            final BizType bizType) {
        CapitalHistoryService.record(userId, amount, txnId, bizType.getCode(), memo);
        for (;;) {
            CapitalAccount account = capitalAccountDAO.selectByUserId(userId);
            if (account == null) {
                throw new AccountNotFoundExcepiton("资金账户不存在.");
            }
            account.setBalance(DecimalUtil.add(account.getBalance(), amount));
            account.setDigest("n/a");
            Date current = new Date();
            account.setLastUpdate(current);
            int count = capitalAccountDAO.updateByPrimaryKey(account);
            if (count == 1) {
                CapitalJournal journal = new CapitalJournal();
                journal.setUserId(userId);
                journal.setAmount(amount);
                journal.setBalance(account.getBalance());
                journal.setTxnId(txnId);
                journal.setBizType(bizType.getCode());
                journal.setDirection(Direction.INFLOW);
                journal.setCreateTime(current);
                journal.setDigest("n/a");
                journal.setMemo(memo);
                capitalJournalDAO.insert(journal);
                vertx.eventBus().send(BOOKKEEPING_ADDRESS, FstUtils.serialize(new Accounting(bizType, amount)));
                return;
            }
        }
    }

    public void freeze(final Long userId, final BigDecimal amount, final String txnId, final String memo,
            final BizType bizType) {
        CapitalHistoryService.record(userId, amount, txnId, bizType.getCode(), memo);
        for (;;) {
            CapitalAccount account = capitalAccountDAO.selectByUserId(userId);
            if (account == null) {
                throw new AccountNotFoundExcepiton("资金账户不存在.");
            }
            if (DecimalUtil.lt(account.getBalance(), amount)) {
                throw new BalanceNotEnoughException("余额不足.");
            }
            account.setBalance(DecimalUtil.subtract(account.getBalance(), amount));
            account.setFrozen(DecimalUtil.add(account.getFrozen(), amount));
            account.setDigest("n/a");
            Date current = new Date();
            account.setLastUpdate(current);
            int count = capitalAccountDAO.updateByPrimaryKey(account);
            if (count == 1) {
                CapitalJournal journal = new CapitalJournal();
                journal.setUserId(userId);
                journal.setAmount(amount);
                journal.setBalance(account.getBalance());
                journal.setTxnId(txnId);
                journal.setBizType(bizType.getCode());
                journal.setDirection(Direction.OUTFLOW);
                journal.setCreateTime(current);
                journal.setDigest("n/a");
                journal.setMemo(memo);
                capitalJournalDAO.insert(journal);
                return;
            }
        }
    }

    public void unfreeze(final Long userId, final String origTxnId, final String memo, final BizType bizType,
            final boolean bizStatus) {
        CapitalHistoryService.record(userId, DecimalUtil.format(-1L), origTxnId, bizType.getCode(), memo);
        for (;;) {
            CapitalAccount account = capitalAccountDAO.selectByUserId(userId);
            if (account == null) {
                throw new AccountNotFoundExcepiton("资金账户不存在.");
            }
            CapitalJournal origJournal = capitalJournalDAO.selectByUserIdTxnIdAndBizType(userId, origTxnId,
                    BizType.preType(bizType));
            if (origJournal == null) {
                throw new CapitalJournalNotFoundException("冻结申请流水不存在.");
            }
            BigDecimal amount = origJournal.getAmount();
            account.setFrozen(DecimalUtil.subtract(account.getFrozen(), amount));
            if (!bizStatus) {
                account.setBalance(DecimalUtil.add(account.getBalance(), amount));
            }
            account.setDigest("n/a");
            Date current = new Date();
            account.setLastUpdate(current);
            int count = capitalAccountDAO.updateByPrimaryKey(account);
            if (count == 1) {
                CapitalJournal journal = new CapitalJournal();
                journal.setUserId(userId);
                journal.setAmount(amount);
                journal.setBalance(account.getBalance());
                journal.setTxnId(origTxnId);
                journal.setBizType(bizType.getCode());
                journal.setDirection(bizStatus ? Direction.OUTFLOW : Direction.INFLOW);
                journal.setCreateTime(current);
                journal.setDigest("n/a");
                journal.setMemo(memo);
                capitalJournalDAO.insert(journal);
                if (bizStatus) {
                    vertx.eventBus().send(BOOKKEEPING_ADDRESS, FstUtils.serialize(new Accounting(bizType, amount)));
                }
                return;
            }
        }
    }

    public PageResultDTO<CapitalJournal> queryJournalByUserId(final Long userId, final byte bizType,
            final RowBounds rowBounds) {
        PageResultDTO<CapitalJournal> result = new PageResultDTO<>();
        result.setData(capitalJournalDAO.selectByUserIdBizType(userId, bizType, rowBounds));
        result.setTotalRow(RecordCountHelper.getCount());
        return result;
    }

}
