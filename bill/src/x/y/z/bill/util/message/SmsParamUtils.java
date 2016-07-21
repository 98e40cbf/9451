package x.y.z.bill.util.message;

import x.y.z.bill.model.message.SmsParam;
import x.y.z.bill.model.message.SmsRecord;
import io.alpha.util.JsonUtils;
import io.alpha.util.StringUtils;

public final class SmsParamUtils {
    private SmsParamUtils() {

    }

    public static String getName(SmsRecord smsRecord) {
        if (smsRecord != null && StringUtils.isNotBlank(smsRecord.getSmsParam())) {
            SmsParam smsParam = JsonUtils.parseJSON(smsRecord.getSmsParam(), SmsParam.class);
            if (smsParam != null) {
                return smsParam.getName();
            }
        }
        return null;
    }

    public static String getCode(SmsRecord smsRecord) {
        if (smsRecord != null && StringUtils.isNotBlank(smsRecord.getSmsParam())) {
            SmsParam smsParam = JsonUtils.parseJSON(smsRecord.getSmsParam(), SmsParam.class);
            if (smsParam != null) {
                return smsParam.getCode();
            }
        }
        return null;
    }
}
