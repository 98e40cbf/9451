package x.y.z.bill.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.y.z.bill.builder.message.SmsRecordBuilder;
import x.y.z.bill.constant.message.SmsTypeEnum;
import x.y.z.bill.mapper.message.SmsRecordDAO;
import x.y.z.bill.model.message.SmsParam;
import x.y.z.bill.model.message.SmsRecord;
import x.y.z.bill.util.ExceptionUtil;
import io.alpha.service.BaseService;
import io.alpha.tx.annotation.TransMark;

@Service
@TransMark
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
     * @param smsRecord
     * @return
     */
    public SmsRecord insert(SmsRecord smsRecord) {
        String txnId = smsRecord.getTxnId();
        try {
            int result = smsRecordDAO.insert(smsRecord);
            if (result == 1) {
                return smsRecord;
            }
        } catch (Exception e) {
            if (ExceptionUtil.isDuplicateKey(e)) {
                return getByTxnId(txnId);
            }
            throw e;
        }
        return null;
    }
}
