package x.y.z.bill.mapper.payment;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import x.y.z.bill.model.payment.BankCardInfo;

@Repository
public interface BankCardInfoDAO {

    int countActivatedBankCardInfoByUser(@Param("bankCardNo") String bankCardNo, @Param("userId") Long userId);

    BankCardInfo insertSelective(BankCardInfo record);

    BankCardInfo queryByTxnId(String txnId);

    void updateBankCardInfoFailed(@Param("txnId") String txnId, @Param("version") Integer version);

    void updateBankCardInfoHanding(@Param("txnId") String txnId, @Param("version") Integer version);

    void updateBankCardInfoSucceed(@Param("txnId") String txnId, @Param("version") Integer version);

    BankCardInfo queryById(Long id);

    BankCardInfo queryUserBankCardBySucceed(Long userId);

}