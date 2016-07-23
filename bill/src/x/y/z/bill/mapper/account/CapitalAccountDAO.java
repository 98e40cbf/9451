package x.y.z.bill.mapper.account;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import x.y.z.bill.model.account.CapitalAccount;

@Repository
public interface CapitalAccountDAO {
    int insert(CapitalAccount record);

    CapitalAccount selectByUserIdAcctType(@Param("userId") Long userId, @Param("acctType") byte acctType);

    int updateByPrimaryKey(CapitalAccount record);
}