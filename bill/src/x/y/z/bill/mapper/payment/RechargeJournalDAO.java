package x.y.z.bill.mapper.payment;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import x.y.z.bill.model.payment.RechargeJournal;

@Repository
public interface RechargeJournalDAO {
    int insertSelective(RechargeJournal record);

    RechargeJournal queryByTxnId(@Param("txnId") String txnId);

    int updateRechargeJournalFailed(@Param("txnId") String txnId, @Param("replyCode") String replyCode,
            @Param("replyMessage") String replyMessage, @Param("recvTime") Date recvTime,
            @Param("version") Integer version);

    int updateRechargeJournalHanding(@Param("txnId") String txnId, @Param("sendTime") Date sendTime,
            @Param("version") Integer version);

    int updateRechargeJournalSucceed(@Param("txnId") String txnId, @Param("replyCode") String replyCode,
            @Param("replyMessage") String replyMessage, @Param("recvTime") Date recvTime,
            @Param("version") Integer version);

    int countRechargeJournal(@Param("userId") Long userId, @Param("status") int status);

    List<RechargeJournal> queryRechargeJournal(@Param("userId") Long userId, @Param("status") int status,
            @Param("offset") int offset, @Param("rows") int rows);

    RechargeJournal queryUserFirstRechargeJournal(Long userId);
}