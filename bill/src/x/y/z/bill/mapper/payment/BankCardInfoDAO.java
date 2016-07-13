package x.y.z.bill.mapper.payment;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.payment.BankCardInfo;

@Repository
public interface BankCardInfoDAO {
    int insert(BankCardInfo record);

    int insertSelective(BankCardInfo record);

    BankCardInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BankCardInfo record);

    int updateByPrimaryKey(BankCardInfo record);
}