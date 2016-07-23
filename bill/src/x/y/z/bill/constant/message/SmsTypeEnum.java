package x.y.z.bill.constant.message;

/**
 * 短信类型
 */
public enum SmsTypeEnum {
    /** 1-用户注册 */
    REGISTER((byte) 1, "sms_register", "sms_register_voice", "用户注册"),
    /** 2-找回密码 */
    PWD_RETRIEVE((byte) 2, "sms_pwd_retrieve", "sms_pwd_retrieve_voice", "找回密码"),
    /** 3-提现申请 */
    WITHDRAW((byte) 3, "sms_withdraw", "sms_withdraw_voice", "提现申请");

    private final byte type;
    private final String templateCode;
    private final String templateCodeVoice;
    private final String desc;

    private SmsTypeEnum(byte type, String templateCode, String templateCodeVoice, String desc) {
        this.type = type;
        this.templateCode = templateCode;
        this.templateCodeVoice = templateCodeVoice;
        this.desc = desc;
    }

    public byte getType() {
        return type;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public String getTemplateCodeVoice() {
        return templateCodeVoice;
    }

    public String getDesc() {
        return desc;
    }

}
