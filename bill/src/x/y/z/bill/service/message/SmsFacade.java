package x.y.z.bill.service.message;

import io.alpha.core.dto.ResultDTO;
import io.alpha.logging.Logger;
import io.alpha.logging.LoggerFactory;
import io.alpha.util.ObjectId;
import io.alpha.util.RandomStringGenerator;
import io.alpha.util.StringUtils;
import io.alpha.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import x.y.z.bill.builder.ResultBuilder;
import x.y.z.bill.constant.message.ResultTypeEnum;
import x.y.z.bill.constant.message.SmsTypeEnum;
import x.y.z.bill.dto.message.SmsDTO;
import x.y.z.bill.model.message.SmsRecord;

/**
 * 短信发送相关接口
 */
@Service
public class SmsFacade {
    private static Logger logger = LoggerFactory.getLogger(SmsFacade.class);
    @Autowired
    private SmsRecordService smsRecordService;

    /**
     * 发送短信验证码
     * @param smsDTO
     * @return
     */
    public ResultDTO<String> sendCheckCode(SmsDTO smsDTO) {
        String identityId = ObjectId.getIdentityId();
        try {
            ValidationUtils.validate(smsDTO);
        } catch (Exception t) {
            logger.error("{}-[发送短信验证码],处理失败,无效参数:{}", identityId, smsDTO);
            return ResultBuilder.buildResult(ResultTypeEnum.PARAM_INVALID);
        }

        String checkCode = RandomStringGenerator.randomNumeric(6);
        String txnId = smsDTO.getTxnId();
        SmsTypeEnum smsTypeEnum = smsDTO.getSmsTypeEnum();
        String receiveMobiles = smsDTO.getMobiles();

        SmsRecord smsRecord = smsRecordService.insert(txnId, smsTypeEnum, receiveMobiles, checkCode);
        if (smsRecord != null && StringUtils.isNotBlank(smsRecord.getSmsParam())) {
            if (logger.isDebugEnabled()) {
                logger.debug("{}-[发送短信验证码],处理成功", identityId);
            }
            return ResultBuilder.buildResult(ResultTypeEnum.SUCCESS, smsRecord.getSmsParam());
        }

        logger.error("{}-[发送短信验证码],处理失败:{}", identityId, smsDTO);
        return ResultBuilder.buildResult(ResultTypeEnum.FAILED);
    }
}
