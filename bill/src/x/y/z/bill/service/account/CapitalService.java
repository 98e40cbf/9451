package x.y.z.bill.service.account;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.alpha.service.BaseService;
import io.alpha.tx.annotation.TransMark;
import x.y.z.bill.mapper.account.CapitalAccountDAO;
import x.y.z.bill.model.account.CapitalAccount;

@Service
@TransMark
public class CapitalService extends BaseService {

    @Autowired
    private CapitalAccountDAO capitalAccountDAO;

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

    public void add() {
    }

    public void freeze() {
    }

    public void unfreeze() {
    }

}
