package x.y.z.bill.service.message;

import io.alpha.core.dto.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import x.y.z.bill.builder.message.SmsSendRecordBuilder;
import x.y.z.bill.constant.message.ResultTypeEnum;
import x.y.z.bill.constant.message.SmsPartnerEnum;
import x.y.z.bill.mapper.message.SmsSendRecordDAO;
import x.y.z.bill.model.message.SmsSendRecord;
import x.y.z.bill.util.ExceptionUtil;
import io.alpha.service.BaseService;
import io.alpha.tx.annotation.TransMark;

@Service
@TransMark
public class SmsSendRecordService extends BaseService {
    @Autowired
    private SmsSendRecordDAO smsSendRecordDAO;

    public SmsSendRecord getBySmsId(long smsId) {
        return smsSendRecordDAO.getBySmsId(smsId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public SmsSendRecord insert(SmsPartnerEnum smsPartnerEnum, long smsId) {
        SmsSendRecord smsSendRecord = SmsSendRecordBuilder.build(smsPartnerEnum, smsId);
        try {
            int result = smsSendRecordDAO.insert(smsSendRecord);
            if (result == 1) {
                return smsSendRecord;
            }
        } catch (Exception e) {
            if (ExceptionUtil.isDuplicateKey(e)) {
                return getBySmsId(smsId);
            }
            throw e;
        }
        return null;
    }

    public void update(long id, ResultDTO resultDTO){
        SmsSendRecord smsSendRecord = new SmsSendRecord();
        smsSendRecord.setId(id);
        if(ResultTypeEnum.isSuccess(resultDTO.getStatus())){
            smsSendRecord.setSmsStatus(ResultTypeEnum.SUCCESS.getStatus());
        }else{
            smsSendRecord.setSmsStatus(ResultTypeEnum.FAILED.getStatus());
            smsSendRecord.setErrorCode(resultDTO.getResultCode());
        }
        smsSendRecordDAO.updateByPrimaryKey(smsSendRecord);
    }

}
