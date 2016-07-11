package x.y.z.bill.mapper.payment;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.payment.RechageJournal;

@Repository
public interface RechageJournalDAO {
    int insert(RechageJournal record);

    int insertSelective(RechageJournal record);

    RechageJournal selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RechageJournal record);

    int updateByPrimaryKey(RechageJournal record);
}