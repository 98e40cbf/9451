package x.y.z.bill.service.message.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.y.z.bill.builder.message.ResultBuilder;
import x.y.z.bill.constant.message.ResultTypeEnum;
import x.y.z.bill.constant.message.SmsConstants;
import x.y.z.bill.model.message.SmsRecord;
import x.y.z.bill.service.message.SmsTemplateService;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.request.AlibabaAliqinFcTtsNumSinglecallRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.taobao.api.response.AlibabaAliqinFcTtsNumSinglecallResponse;

import io.alpha.core.dto.ResultDTO;
import io.alpha.core.util.PropertiesLoader;
import io.alpha.logging.Logger;
import io.alpha.logging.LoggerFactory;
import io.alpha.util.ObjectId;
import io.alpha.util.StringUtils;

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
    private static String SMS_SHOWNUM = PropertiesLoader.getProperty(SmsConstants.PROSP_SMS_TAOBAO_SHOWNUM);
    private static String SMS_DEFAULT_TYPE = "normal";
    @Autowired
    private SmsTemplateService smsTemplateService;

    public ResultDTO<String> clientSendSms(SmsRecord smsRecord) {
        String identityId = ObjectId.getIdentityId();
        logger.info("{}-[阿里大鱼]短信发送:{}", identityId, smsRecord);

        String templateCode = smsRecord.getSmsTemplateCode();
        String templateId = smsTemplateService.getTemplateIdByCode(templateCode);
        if (StringUtils.isBlank(templateId)) {
            logger.error("{}-[阿里大鱼]，短信发送失败:模板Id为空", identityId);
            return ResultBuilder.buildResult(ResultTypeEnum.FAILED);
        }

        TaobaoClient client = new DefaultTaobaoClient(SMS_URL, SMS_APPKEY, SMS_SECRET);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        /**
         * 短信类型，传入值请填写normal
         */
        req.setSmsType(SMS_DEFAULT_TYPE);
        /**
         * 短信签名，传入的短信签名必须是在阿里大鱼“管理中心-短信签名管理”中的可用签名。
         * 如“阿里大鱼”已在短信签名管理中通过审核，则可传入”阿里大鱼“（传参时去掉引号）作为短信签名。
         * 短信效果示例：【阿里大鱼】欢迎使用阿里大鱼服务。
         */
        req.setSmsFreeSignName(SMS_FREESIGN);
        /**
         * 短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致，
         * 多个变量之间以逗号隔开。示例：针对模板“验证码${code}，您正在进行${product}身份验证，打死不要告诉别人哦！”，
         * 传参时需传入{"code":"1234","product":"alidayu"}
         */
        req.setSmsParamString(smsRecord.getSmsParam());
        /**
         * 短信接收号码。 支持单个或多个手机号码，传入号码为11位手机号码，不能加0或+86。 群发短信需传入多个号码，
         * 以英文逗号分隔，一次调用最多传入200个号码。
         * 示例：18600000000,13911111111,13322222222
         */
        req.setRecNum(smsRecord.getReceiveMobiles());
        /**
         * 短信模板ID，传入的模板必须是在阿里大鱼“管理中心-短信模板管理”中的可用模板。示例：SMS_585014
         */
        req.setSmsTemplateCode(templateId);

        try {
            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
            logger.info("{}-[阿里大鱼]短信发送结果:{}", identityId, rsp.getBody());
            return ResultBuilder.buildResult(ResultTypeEnum.SUCCESS);
        } catch (Exception t) {
            logger.error("{}-[阿里大鱼]短信短信发送异常:{}", identityId, t);
            return ResultBuilder.buildResult(ResultTypeEnum.FAILED);
        }
    }

    /**
     * 处理文本转语音通知
     * @param smsRecord
     * @return
     */
    public ResultDTO<String> clientSendVoiceSms(SmsRecord smsRecord) {
        String identityId = ObjectId.getIdentityId();
        logger.info("{}-[阿里大鱼处理文本转语音通知]，入口参数:{}", identityId, smsRecord);

        String templateCode = smsRecord.getSmsTemplateCode();
        String templateId = smsTemplateService.getTemplateIdByCode(templateCode);
        if (StringUtils.isBlank(templateId)) {
            logger.error("{}-[阿里大鱼处理文本转语音通知]，处理失败:模板Id为空", identityId);
            return ResultBuilder.buildResult(ResultTypeEnum.FAILED);
        }

        System.out.println("SMS_URL=" + SMS_URL);
        System.out.println("SMS_APPKEY=" + SMS_APPKEY);
        System.out.println("SMS_SECRET=" + SMS_SECRET);
        TaobaoClient client = new DefaultTaobaoClient(SMS_URL, SMS_APPKEY, SMS_SECRET);
        AlibabaAliqinFcTtsNumSinglecallRequest req = new AlibabaAliqinFcTtsNumSinglecallRequest();
        /**
         * 被叫号码，支持国内手机号与固话号码,格式如下057188773344,13911112222,4001112222,95500
         */
        req.setCalledNum(smsRecord.getReceiveMobiles());
        /**
         * 被叫号显，传入的显示号码必须是阿里大于“管理中心-号码管理”中申请或购买的号码
         */
        req.setCalledShowNum(SMS_SHOWNUM);
        /**
         * TTS模板ID，传入的模板必须是在阿里大于“管理中心-语音TTS模板管理”中的可用模板
         */
        req.setTtsCode(templateId);
        /**
         * 文本转语音（TTS）模板变量，传参规则{"key"："value"}，
         * key的名字须和TTS模板中的变量名一致，多个变量之间以逗号隔开，
         * 示例：{"name":"xiaoming","code":"1234"}
         */
        req.setTtsParam(smsRecord.getSmsParam());

        try {
            AlibabaAliqinFcTtsNumSinglecallResponse rsp = client.execute(req);
            logger.info("{}-[阿里大鱼处理文本转语音通知]，处理结果:{}", identityId, rsp.getBody());
            return ResultBuilder.buildResult(ResultTypeEnum.SUCCESS);
        } catch (Exception t) {
            logger.error("{}-[阿里大鱼处理文本转语音通知]，处理异常:{}", identityId, t);
            return ResultBuilder.buildResult(ResultTypeEnum.FAILED);
        }
    }
}
