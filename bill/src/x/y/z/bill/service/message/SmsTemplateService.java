package x.y.z.bill.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.y.z.bill.mapper.message.SmsTemplateDAO;
import x.y.z.bill.model.message.SmsTemplate;

@Service
public class SmsTemplateService {
    @Autowired
    private SmsTemplateDAO smsTemplateDAO;

    /**
     * 根据模板编号获取外部平台模板Id
     * 
     * @param templateCode
     * @return
     */
    public String getTemplateIdByCode(String templateCode) {
        SmsTemplate smsTemplate = getByCode(templateCode);
        if (smsTemplate != null) {
            return smsTemplate.getTemplateId();
        }
        return null;
    }

    public SmsTemplate getByCode(String templateCode) {
        return smsTemplateDAO.getByTemplateCode(templateCode);
    }
}
