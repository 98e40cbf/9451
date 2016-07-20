package x.y.z.bill.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.alpha.service.BaseService;
import io.alpha.tx.annotation.TransMark;
import x.y.z.bill.builder.message.SmsRecordBuilder;
import x.y.z.bill.constant.message.SmsTypeEnum;
import x.y.z.bill.mapper.message.SmsRecordDAO;
import x.y.z.bill.model.message.SmsParam;
import x.y.z.bill.model.message.SmsRecord;
import x.y.z.bill.util.ExceptionUtil;

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
     *
     * @param txnId
     * @param smsTypeEnum
     * @param mobiles
     * @param smsParam
     * @return
     */
    public SmsRecord insert(final String txnId, final SmsTypeEnum smsTypeEnum, final String mobiles,
            final SmsParam smsParam) {
        SmsRecord smsRecord = SmsRecordBuilder.build(txnId, smsTypeEnum, mobiles, smsParam);
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
