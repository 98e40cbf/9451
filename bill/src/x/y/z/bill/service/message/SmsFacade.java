package x.y.z.bill.service.message;

import static x.y.z.bill.builder.message.ResultBuilder.buildResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.alpha.core.dto.ResultDTO;
import io.alpha.logging.Logger;
import io.alpha.logging.LoggerFactory;
import io.alpha.util.ObjectId;
import io.alpha.util.RandomStringGenerator;
import io.alpha.util.StringUtils;
import io.alpha.validation.ValidationUtils;
import x.y.z.bill.builder.message.ResultBuilder;
import x.y.z.bill.builder.message.SmsRecordBuilder;
import x.y.z.bill.constant.message.ResultTypeEnum;
import x.y.z.bill.dto.message.SmsDTO;
import x.y.z.bill.model.message.SmsParam;
import x.y.z.bill.model.message.SmsRecord;
import x.y.z.bill.service.message.actor.SmsSendActor;

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
     * 
     * @param smsDTO
     * @return
     */
    public ResultDTO<String> sendCheckCode(SmsDTO smsDTO) {
        String identityId = ObjectId.getIdentityId();
        logger.info("{}-[发送短信验证码],处理开始:{}", identityId, smsDTO);

        try {
            ValidationUtils.validate(smsDTO);
        } catch (Exception t) {
            logger.error("{}-[发送短信验证码],处理失败,无效参数:{}", identityId, smsDTO);
            return buildResult(ResultTypeEnum.PARAM_INVALID);
        }

        // 1，生成验证码
        String checkCode = RandomStringGenerator.randomNumeric(6);
        SmsParam smsParam = new SmsParam();
        smsParam.setName(checkCode);// 阿里大鱼目前个人暂时没有成功申请到${code}

        // 2，新增短信记录
        SmsRecord smsRecord = smsRecordService.insert(SmsRecordBuilder.build(smsDTO, smsParam));
        if (smsRecord == null || StringUtils.isBlank(smsRecord.getSmsParam())) {
            logger.error("{}-[发送短信验证码],处理失败:{}", identityId, smsDTO);
            return ResultBuilder.buildResult(ResultTypeEnum.FAILED);
        }

        // 3，发送短信
        SmsSendActor.sendSms(smsRecord);
        logger.info("{}-[发送短信验证码],处理完毕", identityId);
        return ResultBuilder.buildResult(ResultTypeEnum.SUCCESS);

    }
}
