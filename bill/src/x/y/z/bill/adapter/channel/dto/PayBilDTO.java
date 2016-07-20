package x.y.z.bill.adapter.channel.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import x.y.z.bill.adapter.constant.BillStatus;
import x.y.z.bill.adapter.constant.ReconBusinessType;
import io.alpha.core.model.BaseObject;

public class PayBilDTO extends BaseObject {

    private static final long serialVersionUID = 1L;
    private int recordCount;
    private ReconBusinessType bsType;
    private String channelCode;
    private String accountCode;
    private List<Row> rowList = new ArrayList<Row>();

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public ReconBusinessType getBsType() {
        return bsType;
    }

    public void setBsType(ReconBusinessType bsType) {
        this.bsType = bsType;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public PayBilDTO setRecordCount(int recordCount) {
        this.recordCount = recordCount;
        return this;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public PayBilDTO setChannelCode(String channelCode) {
        this.channelCode = channelCode;
        return this;
    }

    public List<Row> getRowList() {
        return rowList;
    }

    public PayBilDTO setRowList(List<Row> rowList) {
        this.rowList = rowList;
        return this;
    }

    public static class Row extends BaseObject {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        // 交易对方银行
        private String bankName;
        // 到账金额
        private BigDecimal realAmount;
        // 支付金额
        private BigDecimal payAmount;
        // 手续费
        private BigDecimal feeAmount;
        // 交易开始时间
        private Date payTime;
        // 商家订单号
        private String channelSendOrder;
        // 渠道订单号
        private String channelReceiveOrder;
        // 交易状态:成功、失败、进行中、撤销、冲正、挂起、通知、等待、转发、确认、调整
        private BillStatus billStatus;
        // 交易状态描述
        private String payResultDesc;
        // 业务类型
        private ReconBusinessType bisCode;

        public ReconBusinessType getBisCode() {
            return bisCode;
        }

        public void setBisCode(ReconBusinessType bisCode) {
            this.bisCode = bisCode;
        }

        public String getBankName() {
            return bankName;
        }

        public Row setBankName(String bankName) {
            this.bankName = bankName;
            return this;
        }

        public BigDecimal getRealAmount() {
            return realAmount;
        }

        public Row setRealAmount(BigDecimal realAmount) {
            this.realAmount = realAmount;
            return this;
        }

        public BigDecimal getPayAmount() {
            return payAmount;
        }

        public Row setPayAmount(BigDecimal payAmount) {
            this.payAmount = payAmount;
            return this;
        }

        public BigDecimal getFeeAmount() {
            return feeAmount;
        }

        public Row setFeeAmount(BigDecimal feeAmount) {
            this.feeAmount = feeAmount;
            return this;
        }

        public Date getPayTime() {
            return payTime;
        }

        public Row setPayTime(Date payTime) {
            this.payTime = payTime;
            return this;
        }

        public String getChannelSendOrder() {
            return channelSendOrder;
        }

        public Row setChannelSendOrder(String channelSendOrder) {
            this.channelSendOrder = channelSendOrder;
            return this;
        }

        public String getChannelReceiveOrder() {
            return channelReceiveOrder;
        }

        public Row setChannelReceiveOrder(String channelReceiveOrder) {
            this.channelReceiveOrder = channelReceiveOrder;
            return this;
        }

        public BillStatus getBillStatus() {
            return billStatus;
        }

        public Row setBillStatus(BillStatus billStatus) {
            this.billStatus = billStatus;
            return this;
        }

        public String getPayResultDesc() {
            return payResultDesc;
        }

        public Row setPayResultDesc(String payResultDesc) {
            this.payResultDesc = payResultDesc;
            return this;
        }

    }

}
