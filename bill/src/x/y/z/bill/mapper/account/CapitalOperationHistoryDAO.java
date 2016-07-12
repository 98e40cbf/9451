package x.y.z.bill.mapper.account;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.account.CapitalOperationHistory;

@Repository
public interface CapitalOperationHistoryDAO {
    int insert(CapitalOperationHistory record);

    CapitalOperationHistory selectByPrimaryKey(Long id);

}