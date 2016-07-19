package x.y.z.bill.service.message.handler;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import io.alpha.core.dto.ResultDTO;
import io.alpha.core.util.PropertiesLoader;
import io.alpha.logging.Logger;
import io.alpha.logging.LoggerFactory;
import io.alpha.util.ObjectId;
import org.springframework.stereotype.Service;
import x.y.z.bill.builder.message.ResultBuilder;
import x.y.z.bill.constant.message.ResultTypeEnum;
import x.y.z.bill.constant.message.SmsConstants;
import x.y.z.bill.model.message.SmsRecord;

/**
 * 阿里大鱼短信发送
 */
@Service("taoBaoSmsHandler")
public class TaoBaoSmsHandler extends BaseSmsHandler {
    private static final Logger logger = LoggerFactory.getLogger(TaoBaoSmsHandler.class);

    private static String SMS_URL = PropertiesLoader.getProperty(SmsConstants.PROPS_SMS_TAOBAO_URL);
    private static String SMS_APPKEY = PropertiesLoader.getProperty(SmsConstants.PROPS_SMS_TAOBAO_APPKEY);
    private static String SMS_SECRET = PropertiesLoader.getProperty(SmsConstants.PROSP_SMS_TAOBAO_SECRET);
    private static String SMS_FREESIGN = PropertiesLoader.getProperty(SmsConstants.PROSP_SMS_TAOBAO_FREESIGN);
    private static String SMS_DEFAULT_TYPE = "normal";

    public ResultDTO<String> clientSendSms(SmsRecord smsRecord) {
        String identityId = ObjectId.getIdentityId();
        logger.info("{}-[阿里大鱼]短信发送:{}", identityId, smsRecord);

        TaobaoClient client = new DefaultTaobaoClient(SMS_URL, SMS_APPKEY, SMS_SECRET);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setSmsType(SMS_DEFAULT_TYPE);
        req.setSmsFreeSignName(SMS_FREESIGN);
        req.setSmsParamString(smsRecord.getSmsParam());
        req.setRecNum(smsRecord.getReceiveMobiles());
        req.setSmsTemplateCode(smsRecord.getSmsTemplateCode());

        try {
            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
            logger.info("短信发送结果:{}", rsp.getBody());
            return ResultBuilder.buildResult(ResultTypeEnum.SUCCESS);
        } catch (Exception t) {
            logger.error("短信发送异常:{}", t);
            return ResultBuilder.buildResult(ResultTypeEnum.FAILED);
        }
    }
}
