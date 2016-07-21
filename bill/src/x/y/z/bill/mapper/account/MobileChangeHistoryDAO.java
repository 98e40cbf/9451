package x.y.z.bill.mapper.account;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.account.MobileChangeHistory;

@Repository
public interface MobileChangeHistoryDAO {
    int insert(MobileChangeHistory record);

    MobileChangeHistory selectByPrimaryKey(Long id);

}