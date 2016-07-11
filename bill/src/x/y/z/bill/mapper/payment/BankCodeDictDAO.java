package x.y.z.bill.mapper.payment;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.payment.BankCodeDict;

@Repository
public interface BankCodeDictDAO {
    int insert(BankCodeDict record);

    int insertSelective(BankCodeDict record);

    BankCodeDict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BankCodeDict record);

    int updateByPrimaryKey(BankCodeDict record);
}