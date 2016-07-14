package x.y.z.bill.service.account;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.alpha.log.annotation.IgnoreLog;
import io.alpha.security.util.EncryptionUtils;
import io.alpha.service.BaseService;
import io.alpha.tx.annotation.TransMark;
import io.alpha.util.SequenceHelper;
import io.alpha.util.StringUtils;
import x.y.z.bill.mapper.account.UserDAO;
import x.y.z.bill.mapper.account.UserExtraDAO;
import x.y.z.bill.model.account.User;
import x.y.z.bill.model.account.UserExtra;

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
        user.setEmail(SequenceHelper.get());
        user.setCreateTime(new Date());
        userDAO.insert(user);
        capitalService.createAccount(user.getId());
    }

    public User queryByName(final String username) {
        return userDAO.selectByUsername(username);
    }

    public User queryByMobile(final String mobile) {
        return userDAO.selectByMobile(mobile);
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

    public int modifyLoginPassword(final Long userId, final String oldPassword, final String newPassword) {
        User user = userDAO.selectByPrimaryKey(userId);
        if (StringUtils.isNotBlank(oldPassword) && !EncryptionUtils.verifyPassword(oldPassword, user.getLoginPwd())) {
            return 0;
        }
        user.setLoginPwd(EncryptionUtils.encodePassword(newPassword.trim()));
        return userDAO.updateLoginPassword(user);
    }

    public int modifyPaymentPassword(final Long userId, final String oldPassword, final String newPassword) {
        User user = userDAO.selectByPrimaryKey(userId);
        if (StringUtils.isNotBlank(oldPassword) && !EncryptionUtils.verifyPassword(oldPassword, user.getPaymentPwd())) {
            return 0;
        }
        user.setPaymentPwd(EncryptionUtils.encodePassword(newPassword.trim()));
        return userDAO.updatePaymentPassword(user);
    }

    public int modifyMobile(final Long userId, final String oldMobile, final String newMobile) throws Exception {
        User user = userDAO.selectByPrimaryKey(userId);
        if (user != null) {
            if (user.getMobile().equals(EncryptionUtils.encryptByAES(oldMobile))) {
                user.setMobile(EncryptionUtils.encryptByAES(newMobile));
                return userDAO.updateMobile(user);
            }
        }
        return 0;
    }

    public User queryUser(final Long id) throws Exception {
        User user = userDAO.selectByPrimaryKey(id);
        if (user != null) {
            String mobile = user.getMobile();
            if (mobile != null) {
                user.setMobile(EncryptionUtils.decryptByAES(mobile));
            }
        }
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

}
