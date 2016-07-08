package x.y.z.bill.mapper.account;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.account.User;

@Repository
public interface UserDAO {
    int insert(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKey(User record);
}