package x.y.z.bill.mapper.account;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import x.y.z.bill.model.account.CapitalJournal;

@Repository
public interface CapitalJournalDAO {

    int insert(CapitalJournal record);

    CapitalJournal selectByUserIdBizTypeAndTxnId(@Param("userId") long userId, @Param("bizType") byte bizType,
            @Param("txnId") String txnId);

    List<CapitalJournal> selectByUserIdBizType(@Param("userId") Long userId, @Param("bizType") byte bizType,
            RowBounds rowBounds);
}