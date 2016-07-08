package x.y.z.bill.mapper.account;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.account.CapitalJournal;

@Repository
public interface CapitalJournalDAO {
    int insert(CapitalJournal record);

    CapitalJournal selectByPrimaryKey(Long id);

}