package x.y.z.bill.mapper.account;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.account.CapitalAccount;

@Repository
public interface CapitalAccountDAO {
    int insert(CapitalAccount record);

    CapitalAccount selectByUserId(Long userId);

    int updateByPrimaryKey(CapitalAccount record);
}