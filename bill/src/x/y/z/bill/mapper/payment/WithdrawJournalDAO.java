package x.y.z.bill.mapper.payment;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import x.y.z.bill.model.payment.WithdrawJournal;

@Repository
public interface WithdrawJournalDAO {

    int insertSelective(WithdrawJournal record);

    WithdrawJournal queryByTxnId(String txnId);

    int updateByUserConfirm(@Param("txnId") String txnId, @Param("version") Integer version);

    int countWithdrawJournal(@Param("userId") Long userId, @Param("status") int status);

    List<WithdrawJournal> queryWithdrawJournal(@Param("userId") Long userId, @Param("status") int status,
            @Param("offset") int offset, @Param("rows") int rows);
}