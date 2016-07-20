package x.y.z.bill.mapper.payment;

import java.util.List;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.payment.BankCodeDict;

@Repository
public interface BankCodeDictDAO {

    List<BankCodeDict> queryAllBankCodeDictConfigs();
}