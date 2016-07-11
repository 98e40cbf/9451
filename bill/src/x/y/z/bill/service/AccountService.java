package x.y.z.bill.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.alpha.security.util.EncryptionUtils;
import io.alpha.service.BaseService;
import x.y.z.bill.command.LoginForm;
import x.y.z.bill.command.RealnameForm;
import x.y.z.bill.command.RegistForm;
import x.y.z.bill.constant.BizType;
import x.y.z.bill.constant.IdCardType;
import x.y.z.bill.model.account.User;
import x.y.z.bill.service.account.CapitalService;
import x.y.z.bill.service.account.UserService;
import x.y.z.bill.util.ExceptionUtil;

@Service
public class AccountService extends BaseService {

    @Autowired
    private UserService userService;
    @Autowired
    private CapitalService capitalService;

    public boolean regist(final RegistForm registForm) {
        try {
            userService.create(registForm.getUsername(), registForm.getMobile(), registForm.getPassword());
        } catch (Exception e) {
            logger.catching(e);
            return false;
        }
        return true;
    }

    public boolean login(final LoginForm loginForm) {
        String loginId = loginForm.getLoginId();
        User user;
        if (loginId.matches("\\d+")) {
            user = userService.queryByMobile(loginId);
        } else {
            user = userService.queryByName(loginId);
        }
        if (user != null) {
            return EncryptionUtils.verifyPassword(loginForm.getPassword(), user.getLoginPwd());
        }
        return false;
    }

    public boolean realname(final long userId, final RealnameForm realnameForm) {
        try {
            userService.realNameAuth(userId, realnameForm.getRealName(), realnameForm.getIdCardNo(),
                    IdCardType.IDENTITY_CARD);
        } catch (Exception e) {
            logger.catching(e);
            return false;
        }
        return true;
    }

    public boolean add(final Long userId, final BigDecimal amount, final String txnId, final String memo,
            final BizType bizType) {
        try {
            capitalService.add(userId, amount, txnId, memo, bizType);
        } catch (Exception e) {
            logger.catching(e);
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
            logger.catching(e);
            if (ExceptionUtil.isDuplicateKey(e)) {
                return true;
            }
            return false;
        }
        return true;
    }

    public boolean unfreeze(final Long userId, final String origTxnId, final String memo, final BizType bizType,
            final boolean bizStatus) {
        try {
            capitalService.unfreeze(userId, origTxnId, memo, bizType, bizStatus);
        } catch (Exception e) {
            logger.catching(e);
            if (ExceptionUtil.isDuplicateKey(e)) {
                return true;
            }
            return false;
        }
        return true;
    }

}
