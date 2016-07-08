package x.y.z.bill.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.alpha.service.BaseService;
import io.alpha.tx.annotation.TransMark;
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

    public void create() {
        User user = new User();
        userDAO.insert(user);
        capitalService.createAccount(user.getId());
    }

    public User queryByName(final String username) {
        return userDAO.selectByUsername(username);
    }

    public User queryByMobile(final String mobile) {
        return userDAO.selectByMobile(mobile);
    }

    public void realNameAuth() {
        UserExtra userExtra = new UserExtra();
        userExtraDAO.insert(userExtra);
    }

    public void resetPwd() {
    }

}
