/*******************************************************************************
 * Created on 2016年4月21日 下午5:33:52
 * Copyright (c) 深圳市小牛在线互联网信息咨询有限公司版权所有. 粤ICP备13089339号
 * 注意：本内容仅限于深圳市小牛在线互联网信息咨询有限公司内部传阅，禁止外泄以及用于其他商业目的!
 ******************************************************************************/
package x.y.z.bill.adapter.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @since 1.0.0
 * @version $Id$
 * @author {高磊} 2016年4月21日 下午5:33:52
 */
public enum BillStatus {

    OK("00", "成功"), FAIL("01", "失败"), DOING("02", "进行中"), CANCEL("03", "撤销"), REVERSAL("04", "冲正"), PENDING("05",
            "挂起"), NOTICE("06", "通知"), WAIT("07", "等待"), FORWARD("08", "转发"), CONFIRM("09", "确认"), REVISE("10", "调整");

    private final static Map<String, BillStatus> map = new HashMap<>();

    static {
        for (BillStatus billStatus : BillStatus.values()) {
            map.put(billStatus.status(), billStatus);
            map.put(billStatus.desc(), billStatus);
        }
    }

    private String status;
    private String desc;

    private BillStatus(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static BillStatus valOf(String statusOrDesc) {
        return map.get(statusOrDesc);
    }

    public String status() {
        return this.status;
    }

    public String desc() {
        return this.desc;
    }

}
