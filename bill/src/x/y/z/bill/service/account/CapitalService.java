package x.y.z.bill.service.account;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.alpha.service.BaseService;
import io.alpha.tx.annotation.TransMark;
import io.alpha.util.DecimalUtil;
import x.y.z.bill.constant.BizType;
import x.y.z.bill.constant.Direction;
import x.y.z.bill.mapper.account.CapitalAccountDAO;
import x.y.z.bill.mapper.account.CapitalJournalDAO;
import x.y.z.bill.model.account.CapitalAccount;
import x.y.z.bill.model.account.CapitalJournal;

@Service
@TransMark
public class CapitalService extends BaseService {

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
        for (;;) {
            CapitalAccount account = capitalAccountDAO.selectByPrimaryKey(userId);
            if (account == null) {
                throw new RuntimeException("资金账户不存在.");
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
                return;
            }
        }
    }

    public void freeze(final Long userId, final BigDecimal amount, final String txnId, final String memo,
            final BizType bizType) {
        for (;;) {
            CapitalAccount account = capitalAccountDAO.selectByPrimaryKey(userId);
            if (account == null) {
                throw new RuntimeException("资金账户不存在.");
            }
            if (DecimalUtil.lt(account.getBalance(), amount)) {
                throw new RuntimeException("余额不足.");
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
        for (;;) {
            CapitalAccount account = capitalAccountDAO.selectByPrimaryKey(userId);
            if (account == null) {
                throw new RuntimeException("资金账户不存在.");
            }
            CapitalJournal origJournal = capitalJournalDAO.selectByTxnIdAndType(origTxnId, BizType.preType(bizType));
            if (origJournal == null) {
                throw new RuntimeException("原冻结申请流水不存在.");
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
                return;
            }
        }
    }

}
