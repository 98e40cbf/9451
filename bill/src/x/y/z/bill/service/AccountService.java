package x.y.z.bill.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.alpha.security.util.EncryptionUtils;
import io.alpha.service.BaseService;
import io.alpha.util.NetworkUtils;
import x.y.z.bill.command.LoginForm;
import x.y.z.bill.command.RealnameForm;
import x.y.z.bill.command.RegistForm;
import x.y.z.bill.constant.BizType;
import x.y.z.bill.constant.IdCardType;
import x.y.z.bill.model.account.User;
import x.y.z.bill.service.account.CapitalService;
import x.y.z.bill.service.account.LoginHistoryService;
import x.y.z.bill.service.account.UserService;
import x.y.z.bill.util.ExceptionUtil;
import x.y.z.bill.util.SensitiveWords;

@Service
public class AccountService extends BaseService {

    @Autowired
    private UserService userService;
    @Autowired
    private CapitalService capitalService;

    public boolean regist(final RegistForm registForm) {
        if (SensitiveWords.exists(registForm.getUsername())) {
            return false;
        }
        try {
            userService.create(registForm.getUsername(), registForm.getMobile(), registForm.getPassword());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public long login(final LoginForm loginForm, final String loginIp, final String browser) throws Exception {
        String loginId = loginForm.getLoginId();
        User user;
        if (loginId.matches("\\d+")) {
            user = userService.queryByMobile(EncryptionUtils.encryptByAES(loginId));
        } else {
            user = userService.queryByName(loginId);
        }
        if (user != null) {
            if (EncryptionUtils.verifyPassword(loginForm.getPassword(), user.getLoginPwd())) {
                LoginHistoryService.checkIn(user.getId(), convert(loginIp), browser);
                return user.getId();
            }
        }
        return -1L;
    }

    private long convert(final String reqIp) {
        if (NetworkUtils.isValidIPv4(reqIp)) {
            String[] ip = reqIp.split("\\.");
            return Long.parseLong(String.format("%03d%03d%03d%03d", Integer.parseInt(ip[0]), Integer.parseInt(ip[1]),
                    Integer.parseInt(ip[2]), Integer.parseInt(ip[3])));
        }
        return 0L;
    }

    public boolean realname(final long userId, final RealnameForm realnameForm) {
        try {
            userService.realNameAuth(userId, realnameForm.getRealName(), realnameForm.getIdCardNo(),
                    IdCardType.IDENTITY_CARD);
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
            final boolean bizStatus) {
        try {
            capitalService.unfreeze(userId, origTxnId, memo, bizType, bizStatus);
        } catch (Exception e) {
            if (ExceptionUtil.isDuplicateKey(e)) {
                return true;
            }
            return false;
        }
        return true;
    }

    public boolean updatePassword(final Long userId, final String oldPassword, final String newPassword) {
        try {
            return userService.updateLoginPassword(userId, oldPassword, newPassword) == 1;
        } catch (Exception e) {
        }
        return false;
    }

}
