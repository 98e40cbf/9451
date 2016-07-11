package x.y.z.bill.mapper.account;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.account.LoginHistory;

@Repository
public interface LoginHistoryDAO {
    int insert(LoginHistory record);

    LoginHistory selectByPrimaryKey(Long id);
}