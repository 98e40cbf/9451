package x.y.z.bill.mapper.account;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.account.BookKeeping;

@Repository
public interface BookKeepingDAO {

    BookKeeping selectByPrimaryKey(Integer id);

    int amountToRecharge(BookKeeping record);

    int amountToInvest(BookKeeping record);

    int amountToWithdraw(BookKeeping record);

}