package x.y.z.bill.util.message;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import io.alpha.core.util.PropertiesLoader;
import io.alpha.logging.Logger;
import io.alpha.logging.LoggerFactory;
import io.alpha.util.JsonUtils;
import x.y.z.bill.constant.message.SmsConstants;
import x.y.z.bill.model.message.SmsParam;

/**
 * 阿里大鱼
 */
public final class SmsTaoBaoClientUtils {
    private static Logger logger = LoggerFactory.getLogger(SmsTaoBaoClientUtils.class);

    private SmsTaoBaoClientUtils() {

    }

    private static String SMS_TAOBAO_URL = PropertiesLoader.getProperty(SmsConstants.PROPS_SMS_TAOBAO_URL);
    private static String SMS_TAOBAO_APPKEY = PropertiesLoader.getProperty(SmsConstants.PROPS_SMS_TAOBAO_APPKEY);
    private static String SMS_TAOBAO_SECRET = PropertiesLoader.getProperty(SmsConstants.PROSP_SMS_TAOBAO_SECRET);
    private static String SMS_DEFAULT_TYPE = "normal";
    private static String SMS_TEMPLATE_CODE = "SMS_12465713";
    private static String SMS_FREE_SIGN = "刘锋";

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

}
