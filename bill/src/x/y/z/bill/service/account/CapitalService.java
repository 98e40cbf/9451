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
import x.y.z.bill.mapper.account.BookKeepingDAO;
import x.y.z.bill.mapper.account.CapitalAccountDAO;
import x.y.z.bill.mapper.account.CapitalJournalDAO;
import x.y.z.bill.model.account.BookKeeping;
import x.y.z.bill.model.account.CapitalAccount;
import x.y.z.bill.model.account.CapitalJournal;

@IgnoreLog
@Service
@TransMark
class CapitalService extends BaseService {

    static final String BOOKKEEPING_ADDRESS = "bookkeeping.accounting.address";
    private static final long INVEST_MID_ACCT_ID = -60001L;
    private static final long UNKNOWN_ACCT_ID = -1L;
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
    @Autowired
    private BookKeepingDAO bookKeepingDAO;

    public void createAccountTo(final Long userId) {
        createBalanceAccount(userId);
        createFrozenAcct(userId);
        createBookKeepingTo(userId);
    }

    private CapitalAccount createBalanceAccount(final Long userId) {
        CapitalAccount balanceAcct = new CapitalAccount();
        balanceAcct.setUserId(userId);
        balanceAcct.setAcctType(AccountType.USER_BALANCE_ACCT.getCode());
        balanceAcct.setAmount(BigDecimal.ZERO);
        balanceAcct.setVersion(0L);
        balanceAcct.setDigest("n/a");
        capitalAccountDAO.insert(balanceAcct);
        return balanceAcct;
    }

    private CapitalAccount createFrozenAcct(final Long userId) {
        CapitalAccount frozenAcct = new CapitalAccount();
        frozenAcct.setUserId(userId);
        frozenAcct.setAcctType(AccountType.USER_FROZEN_ACCT.getCode());
        frozenAcct.setAmount(BigDecimal.ZERO);
        frozenAcct.setVersion(0L);
        frozenAcct.setDigest("n/a");
        capitalAccountDAO.insert(frozenAcct);
        return frozenAcct;
    }

    private void createBookKeepingTo(final Long userId) {
        BookKeeping bookKeeping = new BookKeeping();
        bookKeeping.setUserId(userId);
        bookKeeping.setInvest(BigDecimal.ZERO);
        bookKeeping.setProfit(BigDecimal.ZERO);
        bookKeeping.setRecharge(BigDecimal.ZERO);
        bookKeeping.setWithdraw(BigDecimal.ZERO);
        bookKeepingDAO.insert(bookKeeping);
    }

    public void add(final Long userId, final BigDecimal amount, final String txnId, final String memo,
            final BizType bizType) {
        CapitalHistoryService.record(userId, amount, txnId, bizType.getCode(), memo);
        for (;;) {
            CapitalAccount balanceAcct = getBalanceAccount(userId);
            Date current = new Date();
            balanceAcct.setAmount(DecimalUtil.add(balanceAcct.getAmount(), amount));
            balanceAcct.setDigest("n/a");
            balanceAcct.setLastUpdate(current);
            int count = capitalAccountDAO.updateByPrimaryKey(balanceAcct);
            if (count == 1) {
                CapitalJournal journal = new CapitalJournal();
                journal.setUserId(userId);
                journal.setBizType(bizType.getCode());
                journal.setTxnId(txnId);
                journal.setAmount(amount);
                journal.setBalance(balanceAcct.getAmount());
                journal.setDirection(Direction.INFLOW);
                journal.setCreateTime(current);
                journal.setDigest("n/a");
                journal.setMemo(memo);
                journal.setAcctFrom(UNKNOWN_ACCT_ID);
                journal.setAcctTo(balanceAcct.getId());
                capitalJournalDAO.insert(journal);
                vertx.eventBus().send(BOOKKEEPING_ADDRESS, FstUtils.serialize(new Accounting(userId, bizType, amount)));
                return;
            }
        }
    }

    private CapitalAccount getBalanceAccount(final Long userId) {
        CapitalAccount balanceAcct = capitalAccountDAO.selectByUserIdAcctType(userId,
                AccountType.USER_BALANCE_ACCT.getCode());
        if (balanceAcct == null) {
            throw new AccountNotFoundExcepiton("用户余额账户不存在:" + userId);
        }
        return balanceAcct;
    }

    public void freeze(final Long userId, final BigDecimal amount, final String txnId, final String memo,
            final BizType bizType) {
        CapitalHistoryService.record(userId, amount, txnId, bizType.getCode(), memo);
        for (;;) {
            CapitalAccount balanceAcct = getBalanceAccount(userId);
            if (DecimalUtil.lt(balanceAcct.getAmount(), amount)) {
                throw new BalanceNotEnoughException("余额不足:" + userId);
            }
            Date current = new Date();
            balanceAcct.setAmount(DecimalUtil.subtract(balanceAcct.getAmount(), amount));
            balanceAcct.setDigest("n/a");
            balanceAcct.setLastUpdate(current);
            CapitalAccount frozenAcct = getFrozenAccount(userId);
            frozenAcct.setAmount(DecimalUtil.add(frozenAcct.getAmount(), amount));
            frozenAcct.setDigest("n/a");
            frozenAcct.setLastUpdate(current);
            int balanceCount = capitalAccountDAO.updateByPrimaryKey(balanceAcct);
            int frozenCount = capitalAccountDAO.updateByPrimaryKey(frozenAcct);
            if (balanceCount == 1 && frozenCount == 1) {
                CapitalJournal journal = new CapitalJournal();
                journal.setUserId(userId);
                journal.setBizType(bizType.getCode());
                journal.setTxnId(txnId);
                journal.setAmount(amount);
                journal.setBalance(balanceAcct.getAmount());
                journal.setDirection(Direction.OUTFLOW);
                journal.setCreateTime(current);
                journal.setDigest("n/a");
                journal.setMemo(memo);
                journal.setAcctFrom(balanceAcct.getId());
                journal.setAcctTo(frozenAcct.getId());
                capitalJournalDAO.insert(journal);
                return;
            }
        }
    }

    private CapitalAccount getFrozenAccount(final Long userId) {
        CapitalAccount frozenAcct = capitalAccountDAO.selectByUserIdAcctType(userId,
                AccountType.USER_FROZEN_ACCT.getCode());
        if (frozenAcct == null) {
            throw new AccountNotFoundExcepiton("用户冻结金额账户不存在:" + userId);
        }
        return frozenAcct;
    }

    public void unfreeze(final Long userId, final String origTxnId, final String memo, final BizType bizType,
            final boolean bizStatus) {
        CapitalHistoryService.record(userId, BigDecimal.ZERO, origTxnId, bizType.getCode(), memo);
        for (;;) {
            CapitalAccount balanceAcct = getBalanceAccount(userId);
            CapitalAccount frozenAcct = getFrozenAccount(userId);
            CapitalJournal origJournal = getOriginalFreezeJournal(userId, origTxnId, bizType);
            Date current = new Date();
            BigDecimal amount = origJournal.getAmount();
            frozenAcct.setAmount(DecimalUtil.subtract(frozenAcct.getAmount(), amount));
            frozenAcct.setDigest("n/a");
            frozenAcct.setLastUpdate(current);
            balanceAcct.setLastUpdate(current);
            CapitalAccount investMidAcct = null;
            if (bizStatus) {
                investMidAcct = capitalAccountDAO.selectByUserIdAcctType(INVEST_MID_ACCT_ID,
                        AccountType.INVEST_MID_ACCT.getCode());
                investMidAcct.setAmount(DecimalUtil.add(investMidAcct.getAmount(), amount));
                investMidAcct.setDigest("n/a");
                investMidAcct.setLastUpdate(current);
            } else {
                balanceAcct.setAmount(DecimalUtil.add(balanceAcct.getAmount(), amount));
                balanceAcct.setDigest("n/a");
            }
            int frozenCount = capitalAccountDAO.updateByPrimaryKey(frozenAcct);
            int balanceCount = capitalAccountDAO.updateByPrimaryKey(balanceAcct);
            if (frozenCount == 1 && balanceCount == 1) {
                if (bizStatus) {
                    capitalAccountDAO.updateByPrimaryKey(investMidAcct);
                }
                handleUnfreezeJournal(userId, bizType, origTxnId, bizStatus, balanceAcct, frozenAcct, investMidAcct,
                        amount, current, memo);
                return;
            }
        }
    }

    private CapitalJournal getOriginalFreezeJournal(final Long userId, final String origTxnId, final BizType bizType) {
        CapitalJournal origJournal = capitalJournalDAO.selectByUserIdBizTypeAndTxnId(userId, BizType.preType(bizType),
                origTxnId);
        if (origJournal == null) {
            throw new CapitalJournalNotFoundException("冻结申请流水不存在:" + origTxnId);
        }
        return origJournal;
    }

    private void handleUnfreezeJournal(final Long userId, final BizType bizType, final String origTxnId,
            final boolean bizStatus, final CapitalAccount balanceAcct, final CapitalAccount frozenAcct,
            final CapitalAccount investMidAcct, final BigDecimal amount, final Date current, final String memo) {
        CapitalJournal journal = new CapitalJournal();
        journal.setUserId(userId);
        journal.setAmount(amount);
        journal.setBalance(balanceAcct.getAmount());
        journal.setTxnId(origTxnId);
        journal.setBizType(bizType.getCode());
        journal.setDirection(bizStatus ? Direction.OUTFLOW : Direction.INFLOW);
        journal.setCreateTime(current);
        journal.setDigest("n/a");
        journal.setAcctFrom(frozenAcct.getId());
        if (bizStatus) {
            if (BizType.INVEST_UNFREEZE == bizType) {
                journal.setAcctTo(investMidAcct.getId());
            } else {
                journal.setAcctTo(UNKNOWN_ACCT_ID);
            }
        } else {
            journal.setAcctTo(balanceAcct.getId());
        }
        journal.setMemo(memo);
        capitalJournalDAO.insert(journal);
        if (bizStatus) {
            vertx.eventBus().send(BOOKKEEPING_ADDRESS, FstUtils.serialize(new Accounting(userId, bizType, amount)));
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
