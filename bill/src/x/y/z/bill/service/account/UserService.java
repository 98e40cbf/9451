package x.y.z.bill.service.account;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.y.z.bill.exception.AccountNotFoundExcepiton;
import x.y.z.bill.mapper.account.UserDAO;
import x.y.z.bill.mapper.account.UserExtraDAO;
import x.y.z.bill.model.account.User;
import x.y.z.bill.model.account.UserExtra;
import x.y.z.bill.model.account.ValueUpdate;
import io.alpha.log.annotation.IgnoreLog;
import io.alpha.security.util.EncryptionUtils;
import io.alpha.service.BaseService;
import io.alpha.tx.annotation.TransMark;
import io.alpha.util.StringUtils;

@IgnoreLog
@Service
@TransMark
class UserService extends BaseService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserExtraDAO userExtraDAO;
    @Autowired
    private CapitalService capitalService;

    public void create(final String username, final String mobile, final String password) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setMobile(EncryptionUtils.encryptByAES(mobile));
        user.setLoginPwd(EncryptionUtils.encodePassword(password));
        user.setPaymentPwd("n/a");
        user.setEmail("n/a");
        user.setCreateTime(new Date());
        userDAO.insert(user);
        capitalService.createAccountTo(user.getId());
    }

    public void realNameAuth(final Long userId, final String realName, final String idCardNo, final byte idCardType)
            throws Exception {
        UserExtra userExtra = new UserExtra();
        userExtra.setUserId(userId);
        userExtra.setRealName(EncryptionUtils.encryptByAES(realName));
        userExtra.setIdCardNo(EncryptionUtils.encryptByAES(idCardNo));
        userExtra.setIdCardType(idCardType);
        userExtraDAO.insert(userExtra);
    }

    public User queryByName(final String username) {
        User user = userDAO.selectByUsername(username);
        checkUser(user, username);
        return user;
    }

    public User queryByMobile(final String mobile) {
        User user = userDAO.selectByMobile(mobile);
        checkUser(user, mobile);
        return user;
    }

    public int modifyLoginPassword(final Long userId, final String oldPassword, final String newPassword) {
        User user = userDAO.selectByPrimaryKey(userId);
        checkUser(user, userId);
        if (StringUtils.isNotBlank(oldPassword) && !EncryptionUtils.verifyPassword(oldPassword, user.getLoginPwd())) {
            return 0;
        }
        ValueUpdate value = new ValueUpdate();
        value.setId(userId);
        value.setOldValue(user.getLoginPwd());
        value.setNewValue(EncryptionUtils.encodePassword(newPassword.trim()));
        return userDAO.updateLoginPassword(value);
    }

    public int modifyPaymentPassword(final Long userId, final String oldPassword, final String newPassword) {
        User user = userDAO.selectByPrimaryKey(userId);
        checkUser(user, userId);
        if (StringUtils.isNotBlank(oldPassword) && !EncryptionUtils.verifyPassword(oldPassword, user.getPaymentPwd())) {
            return 0;
        }
        ValueUpdate value = new ValueUpdate();
        value.setId(userId);
        value.setOldValue(user.getPaymentPwd());
        value.setNewValue(EncryptionUtils.encodePassword(newPassword.trim()));
        return userDAO.updatePaymentPassword(value);
    }

    public int modifyMobile(final Long userId, final String oldMobile, final String newMobile) throws Exception {
        User user = userDAO.selectByPrimaryKey(userId);
        checkUser(user, userId);
        if (user.getMobile().equals(EncryptionUtils.encryptByAES(oldMobile))) {
            ValueUpdate value = new ValueUpdate();
            value.setId(userId);
            value.setOldValue(user.getMobile());
            value.setNewValue(EncryptionUtils.encryptByAES(newMobile));
            return userDAO.updateMobile(value);
        }
        return 0;
    }

    public User queryUser(final Long id) throws Exception {
        User user = userDAO.selectByPrimaryKey(id);
        checkUser(user, id);
        user.setMobile(EncryptionUtils.decryptByAES(user.getMobile()));
        return user;
    }

    public UserExtra queryExtra(final Long userId) throws Exception {
        UserExtra extra = userExtraDAO.selectByUserId(userId);
        if (extra != null) {
            String realName = extra.getRealName();
            if (realName != null) {
                extra.setRealName(EncryptionUtils.decryptByAES(realName));
            }
            String idCardNo = extra.getIdCardNo();
            if (idCardNo != null) {
                extra.setIdCardNo(EncryptionUtils.decryptByAES(idCardNo));
            }
        }
        return extra;
    }

    private void checkUser(final User user, final Object arg) {
        if (user == null) {
            throw new AccountNotFoundExcepiton("用户不存在:" + arg);
        }
    }

}
