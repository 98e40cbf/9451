package x.y.z.bill.mapper.message;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.message.SmsSendRecord;

@Repository
public interface SmsSendRecordDAO {
    int insert(SmsSendRecord record);

    SmsSendRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKey(SmsSendRecord record);

    SmsSendRecord getBySmsId(long smsId);
}