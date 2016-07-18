package x.y.z.bill.dto.message;

import java.io.Serializable;

/**
 * 短信参数
 */
public class SmsParam implements Serializable {
    private static final long serialVersionUID = -1034145945084463241L;
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
