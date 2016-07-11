package x.y.z.bill.mapper.payment;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.payment.BankCardBin;

@Repository
public interface BankCardBinDAO {
    int insert(BankCardBin record);

    int insertSelective(BankCardBin record);

    BankCardBin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BankCardBin record);

    int updateByPrimaryKey(BankCardBin record);
}