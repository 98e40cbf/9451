package x.y.z.bill.mapper.message;

import org.springframework.stereotype.Repository;
import x.y.z.bill.model.message.SmsRecord;

@Repository
public interface SmsRecordDAO {
    int insert(SmsRecord record);

    SmsRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKey(SmsRecord record);
}