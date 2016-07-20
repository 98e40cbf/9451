package x.y.z.bill.adapter.channel.dto;

/**
 * Created by cyh on 2016/4/13.
 */
public enum WithdrawStatus {
    SUC("suc", "代付成功"), FAIL("fail", "代付失败"), HOLDING("holding", "处理中"), TO_BANK("to_bank",
            "已提交至银行"), RET_TICKET("ret_ticket", "退票");
    private String val;
    private String desc;

    WithdrawStatus(String val, String desc) {
        this.val = val;
        this.desc = desc;
    }

    public String val() {
        return val;
    }

    public String desc() {
        return desc;
    }
}