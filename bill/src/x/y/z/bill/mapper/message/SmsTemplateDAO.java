package x.y.z.bill.mapper.message;

import org.springframework.stereotype.Repository;
import x.y.z.bill.model.message.SmsTemplate;

@Repository
public interface SmsTemplateDAO {
    int insert(SmsTemplate record);

    SmsTemplate selectByPrimaryKey(Long id);

    int updateByPrimaryKey(SmsTemplate record);

    SmsTemplate getByTemplateId(String templateId);

    SmsTemplate getByTemplateCode(String templateCode);
}