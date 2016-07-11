package x.y.z.bill.service.account;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.alpha.security.util.EncryptionUtils;
import io.alpha.service.BaseService;
import io.alpha.tx.annotation.TransMark;
import io.alpha.util.SequenceHelper;
import x.y.z.bill.mapper.account.UserDAO;
import x.y.z.bill.mapper.account.UserExtraDAO;
import x.y.z.bill.model.account.User;
import x.y.z.bill.model.account.UserExtra;

@Service
@TransMark
public class UserService extends BaseService {

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

    public void resetPwd() {
    }

}
