package x.y.z.bill.service.account;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.alpha.service.BaseService;
import io.alpha.tx.annotation.TransMark;
import io.alpha.util.DecimalUtil;
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

    // TODO 重复请求只能成功一次
    public void add(final Long userId, final BigDecimal amount, final String txnId, final String memo,
            final byte bizType) {
        for (;;) {
            CapitalAccount account = capitalAccountDAO.selectByPrimaryKey(userId);
            if (account == null) {
                throw new RuntimeException();
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
                journal.setBizType(bizType);
                journal.setCreateTime(current);
                journal.setDigest("n/a");
                journal.setMemo(memo);
                capitalJournalDAO.insert(journal);
                return;
            }
        }
    }

    public void freeze(final Long userId, final BigDecimal amount, final String txnId, final String memo,
            final byte bizType) {
        for (;;) {
            CapitalAccount account = capitalAccountDAO.selectByPrimaryKey(userId);
            if (account == null) {
                throw new RuntimeException();
            }
            if (DecimalUtil.lt(account.getBalance(), amount)) {
                throw new RuntimeException();
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
                journal.setBizType(bizType);
                journal.setCreateTime(current);
                journal.setDigest("n/a");
                journal.setMemo(memo);
                capitalJournalDAO.insert(journal);
                return;
            }
        }
    }

    public void unfreeze(final Long userId, final String origTxnId, final String memo, final byte bizType,
            final boolean status) {
        for (;;) {
            CapitalAccount account = capitalAccountDAO.selectByPrimaryKey(userId);
            if (account == null) {
                throw new RuntimeException();
            }
            CapitalJournal origJournal = capitalJournalDAO.selectByTxnId(origTxnId);
            if (origJournal == null) {
                throw new RuntimeException();
            }
            BigDecimal amount = origJournal.getAmount();
            if (status) {
                account.setFrozen(DecimalUtil.subtract(account.getFrozen(), amount));
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
                    journal.setBizType(bizType);
                    journal.setCreateTime(current);
                    journal.setDigest("n/a");
                    journal.setMemo(memo);
                    capitalJournalDAO.insert(journal);
                    return;
                }
            } else {
                account.setBalance(DecimalUtil.add(account.getBalance(), amount));
                account.setFrozen(DecimalUtil.subtract(account.getFrozen(), amount));
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
                    journal.setBizType(bizType);
                    journal.setCreateTime(current);
                    journal.setDigest("n/a");
                    journal.setMemo(memo);
                    capitalJournalDAO.insert(journal);
                    return;
                }
            }
        }
    }

}