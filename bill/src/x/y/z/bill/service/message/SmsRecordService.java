package x.y.z.bill.service.message;

import io.alpha.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import x.y.z.bill.constant.message.SmsPriority;
import x.y.z.bill.constant.message.SmsStatusEnum;
import x.y.z.bill.constant.message.SmsTypeEnum;
import x.y.z.bill.mapper.message.SmsRecordDAO;
import x.y.z.bill.model.message.SmsRecord;
import x.y.z.bill.util.ExceptionUtil;

import java.util.Date;

@Service
public class SmsRecordService extends BaseService {
    @Autowired
    private SmsRecordDAO smsRecordDAO;

    /**
     * 根据业务号查询短信记录信息
     *
     * @param txnId
     * @return
     */
    public SmsRecord getByTxnId(String txnId) {
        return smsRecordDAO.getByTxnId(txnId);
    }

    /**
     * 新增短信记录
     *
     * @param txnId
     * @param smsTypeEnum
     * @param mobiles
     * @param smsParam
     * @return
     */
    public SmsRecord insert(final String txnId, final SmsTypeEnum smsTypeEnum, final String mobiles,
            final String smsParam) {
        SmsRecord smsRecord = new SmsRecord();
        smsRecord.setTxnId(txnId);
        smsRecord.setSmsType(smsTypeEnum.getType());
        smsRecord.setSmsStatus(SmsStatusEnum.SUCCESS.getStatus());
        smsRecord.setReceiveMobiles(mobiles);
        smsRecord.setSmsParam(smsParam);
        smsRecord.setSmsTemplateCode(smsTypeEnum.getTemplateCode());
        smsRecord.setPriority(SmsPriority.INESSENTIAL);
        smsRecord.setCreateTime(new Date());
        try {
            smsRecordDAO.insert(smsRecord);
        } catch (Exception e) {
            if (ExceptionUtil.isDuplicateKey(e)) {
                return getByTxnId(txnId);
            }
            throw e;
        }
        return smsRecord;
    }
}
