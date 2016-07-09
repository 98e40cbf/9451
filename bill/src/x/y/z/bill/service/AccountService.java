package x.y.z.bill.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.alpha.service.BaseService;
import x.y.z.bill.constant.BizType;
import x.y.z.bill.service.account.CapitalService;
import x.y.z.bill.service.account.UserService;
import x.y.z.bill.util.ExceptionUtil;

@Service
public class AccountService extends BaseService {

    @Autowired
    private UserService userService;
    @Autowired
    private CapitalService capitalService;

    public boolean regist(final String username, final String mobile, final String password) {
        try {
            userService.create(username, mobile, password);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean add(final Long userId, final BigDecimal amount, final String txnId, final String memo,
            final BizType bizType) {
        try {
            capitalService.add(userId, amount, txnId, memo, bizType);
        } catch (Exception e) {
            if (ExceptionUtil.isDuplicateKey(e)) {
                return true;
            }
            return false;
        }
        return true;
    }

    public boolean freeze(final Long userId, final BigDecimal amount, final String txnId, final String memo,
            final BizType bizType) {
        try {
            capitalService.freeze(userId, amount, txnId, memo, bizType);
        } catch (Exception e) {
            if (ExceptionUtil.isDuplicateKey(e)) {
                return true;
            }
            return false;
        }
        return true;
    }

    public boolean unfreeze(final Long userId, final String origTxnId, final String memo, final BizType bizType,
            final boolean status) {
        try {
            capitalService.unfreeze(userId, origTxnId, memo, bizType, status);
        } catch (Exception e) {
            if (ExceptionUtil.isDuplicateKey(e)) {
                return true;
            }
            return false;
        }
        return true;
    }

}
