package x.y.z.bill.mapper.account;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.account.User;
import x.y.z.bill.model.account.ValueUpdate;

@Repository
public interface UserDAO {
    int insert(User user);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKey(User user);

    User selectByUsername(String username);

    User selectByMobile(String mobile);

    int updateLoginPassword(ValueUpdate value);

    int updatePaymentPassword(ValueUpdate value);

    int updateMobile(ValueUpdate value);
}