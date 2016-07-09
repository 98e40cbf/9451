package x.y.z.bill.mapper.account;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import x.y.z.bill.model.account.CapitalJournal;

@Repository
public interface CapitalJournalDAO {
    int insert(CapitalJournal record);

    CapitalJournal selectByPrimaryKey(Long id);

    CapitalJournal selectByTxnIdAndType(@Param("txnId") String txnId, @Param("bizType") byte bizType);

}