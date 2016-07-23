package x.y.z.bill.util.message;

import x.y.z.bill.constant.message.SmsConstants;
import x.y.z.bill.model.message.SmsParam;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.request.AlibabaAliqinFcTtsNumSinglecallRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.taobao.api.response.AlibabaAliqinFcTtsNumSinglecallResponse;

import io.alpha.core.util.PropertiesLoader;
import io.alpha.logging.Logger;
import io.alpha.logging.LoggerFactory;
import io.alpha.util.JsonUtils;

/**
 * 阿里大鱼
 */
public final class SmsTaoBaoClientUtils {
    private static Logger logger = LoggerFactory.getLogger(SmsTaoBaoClientUtils.class);

    private SmsTaoBaoClientUtils() {

    }

    private static String SMS_TAOBAO_URL = "http://gw.api.taobao.com/router/rest";
    private static String SMS_TAOBAO_APPKEY = "23410202";
    private static String SMS_TAOBAO_SECRET = "b38eb6336b3277003cee138bd75ed1ff";
    private static String SMS_TAOBAO_SHOWNUM = "051482043270";
    private static String SMS_DEFAULT_TYPE = "normal";
    private static String SMS_TEMPLATE_CODE = "SMS_12465713";
    private static String SMS_TEMPLATE_CODE_VOICE = "TTS_12520815";
    private static String SMS_FREE_SIGN = "刘锋";

    /**
     * 发送普通短信
     * @param mobile
     * @param param
     */
    public static void sendSms(final String mobile, final String param) {
        TaobaoClient client = new DefaultTaobaoClient(SMS_TAOBAO_URL, SMS_TAOBAO_APPKEY, SMS_TAOBAO_SECRET);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setSmsType(SMS_DEFAULT_TYPE);
        req.setSmsFreeSignName(SMS_FREE_SIGN);
        SmsParam smsParam = new SmsParam();
        smsParam.setName(param);
        req.setSmsParamString(JsonUtils.toJSON(smsParam));
        req.setRecNum(mobile);
        req.setSmsTemplateCode(SMS_TEMPLATE_CODE);
        try {
            logger.info("短信发送参数:{}", req.getSmsParam());
            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
            logger.info("短信发送结果:{}", rsp.getBody());
        } catch (Exception t) {
            logger.error("短信发送异常:{}", t);
        }
    }

    /**
     * 发送语言通信
     * @param mobile
     * @param param
     */
    public static void sendVoiceSms(final String mobile, final String param) {
        TaobaoClient client = new DefaultTaobaoClient(SMS_TAOBAO_URL, SMS_TAOBAO_APPKEY, SMS_TAOBAO_SECRET);
        AlibabaAliqinFcTtsNumSinglecallRequest req = new AlibabaAliqinFcTtsNumSinglecallRequest();

        req.setCalledNum(mobile);
        req.setCalledShowNum(SMS_TAOBAO_SHOWNUM);
        req.setTtsCode(SMS_TEMPLATE_CODE_VOICE);
        SmsParam smsParam = new SmsParam();
        smsParam.setName(param);
        req.setTtsParam(JsonUtils.toJSON(smsParam));

        try {
            logger.info("语音短信发送参数:{}", req.getTtsParam());
            AlibabaAliqinFcTtsNumSinglecallResponse rsp = client.execute(req);
            logger.info("语音短信发送结果:{}", rsp.getBody());
        } catch (Exception t) {
            logger.error("语音短信发送异常:{}", t);
        }
    }

    public static void main(String[] args) throws Exception {
        String mobile = "13537771017";
        String param = "123456";
        // sendVoiceSms(mobile, param);
        // sendSms(mobile, param);
        String SMS_URL = PropertiesLoader.getProperty(SmsConstants.PROPS_SMS_TAOBAO_URL);
        String SSMS_APPKEY = PropertiesLoader.getProperty(SmsConstants.PROPS_SMS_TAOBAO_APPKEY);
        System.out.print(SMS_URL);
        System.out.print(SSMS_APPKEY);
    }
}
