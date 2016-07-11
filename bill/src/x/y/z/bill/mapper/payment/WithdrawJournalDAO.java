package x.y.z.bill.mapper.payment;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.payment.WithdrawJournal;

@Repository
public interface WithdrawJournalDAO {
    int insert(WithdrawJournal record);

    int insertSelective(WithdrawJournal record);

    WithdrawJournal selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WithdrawJournal record);

    int updateByPrimaryKey(WithdrawJournal record);
}