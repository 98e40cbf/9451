package x.y.z.bill.service.message;

import io.alpha.core.dto.ResultDTO;
import io.alpha.logging.Logger;
import io.alpha.logging.LoggerFactory;
import io.alpha.util.ObjectId;
import io.alpha.util.RandomStringGenerator;
import io.alpha.util.StringUtils;
import io.alpha.validation.ValidationUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import x.y.z.bill.builder.message.ResultBuilder;
import x.y.z.bill.constant.message.ResultTypeEnum;
import x.y.z.bill.constant.message.SmsPartnerEnum;
import x.y.z.bill.constant.message.SmsTypeEnum;
import x.y.z.bill.dto.message.SmsDTO;
import x.y.z.bill.model.message.SmsRecord;
import x.y.z.bill.service.message.handler.BaseSmsHandler;

/**
 * 短信发送相关接口
 */
@Service
public class SmsFacade implements ApplicationContextAware {
    private static Logger logger = LoggerFactory.getLogger(SmsFacade.class);
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Autowired
    private SmsRecordService smsRecordService;
    @Autowired
    private SmsRoutingService smsRoutingService;

    /**
     * 发送短信验证码
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
            return ResultBuilder.buildResult(ResultTypeEnum.PARAM_INVALID);
        }

        // 1，生成验证码
        String checkCode = RandomStringGenerator.randomNumeric(6);
        String txnId = smsDTO.getTxnId();
        SmsTypeEnum smsTypeEnum = smsDTO.getSmsTypeEnum();
        String receiveMobiles = smsDTO.getMobiles();

        // 2，新增短信记录
        SmsRecord smsRecord = smsRecordService.insert(txnId, smsTypeEnum, receiveMobiles, checkCode);
        if (smsRecord != null && StringUtils.isNotBlank(smsRecord.getSmsParam())) {
            // 3，获取短信路由
            SmsPartnerEnum smsPartnerEnum = smsRoutingService.getSmsPartner();
            String beanId = smsPartnerEnum.getHandlerBeanId();
            BaseSmsHandler baseSmsHandler = applicationContext.getBean(beanId, BaseSmsHandler.class);

            // 4，渠道发送短信
            ResultDTO<String> result = baseSmsHandler.sendSms(smsPartnerEnum, smsRecord);
            logger.info("{}-[发送短信验证码],处理结果:{}", identityId, result);
            return result;
        }

        logger.error("{}-[发送短信验证码],处理失败:{}", identityId, smsDTO);
        return ResultBuilder.buildResult(ResultTypeEnum.FAILED);
    }
}
