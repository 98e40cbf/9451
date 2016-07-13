package x.y.z.bill.mapper.account;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.account.UserExtra;

@Repository
public interface UserExtraDAO {
    int insert(UserExtra record);

    UserExtra selectByPrimaryKey(Long id);

    int updateByPrimaryKey(UserExtra record);

    UserExtra selectByUserId(Long userId);
}