package x.y.z.bill.adapter.channel.dto.response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.WithdrawStatus;
import io.alpha.core.model.BaseObject;

public class WithdrawQueryRespDTO extends ChannelBaseDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 批量单笔序列号
     */
    List<WithdrawQueryRespDTO.WithdrawElement> withdrawElementList = new ArrayList<>(1);
    /**
     * 批量批次号
     */
    private String packageId;
    private Boolean channelNoData;

    public String getPackageId() {
        return packageId;
    }

    public WithdrawQueryRespDTO setPackageId(String packageId) {
        this.packageId = packageId;
        return this;
    }

    public List<WithdrawElement> getWithdrawElementList() {
        return withdrawElementList;
    }

    public WithdrawQueryRespDTO setWithdrawElementList(List<WithdrawElement> withdrawElementList) {
        this.withdrawElementList = withdrawElementList;
        return this;
    }

    public Boolean getChannelNoData() {
        return channelNoData;
    }

    public WithdrawQueryRespDTO setChannelNoData(Boolean channelNoData) {
        this.channelNoData = channelNoData;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WithdrawQueryRespDTO{");
        sb.append("packageId='").append(packageId).append('\'');
        sb.append(", channelNoData=").append(channelNoData);
        sb.append(", withdrawElementList=").append(withdrawElementList);
        sb.append('}');
        return sb.toString();
    }

    public static class WithdrawElement extends BaseObject {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        private WithdrawStatus status;

        private String serial;// 单笔序列号

        private String channelOrder; // 渠道订单号

        private String bankCardNo;// 收款方银行帐号

        private String bankType;// 银行类型

        private String realName;// 收款方真实姓名

        private BigDecimal payAmount;// 付款金额(以元为单位)

        private Date modifyTime;// 最后修改时间，格式：yyyy-MM-dd HH:mm:ss

        private String errCode;// 付款失败错误码

        private String errMsg; // 付款失败中文描述

        public WithdrawStatus getStatus() {
            return status;
        }

        public WithdrawElement setStatus(WithdrawStatus status) {
            this.status = status;
            return this;
        }

        public String getSerial() {
            return serial;
        }

        public WithdrawElement setSerial(String serial) {
            this.serial = serial;
            return this;
        }

        public String getBankCardNo() {
            return bankCardNo;
        }

        public WithdrawElement setBankCardNo(String bankCardNo) {
            this.bankCardNo = bankCardNo;
            return this;
        }

        public String getBankType() {
            return bankType;
        }

        public WithdrawElement setBankType(String bankType) {
            this.bankType = bankType;
            return this;
        }

        public String getRealName() {
            return realName;
        }

        public WithdrawElement setRealName(String realName) {
            this.realName = realName;
            return this;
        }

        public BigDecimal getPayAmount() {
            return payAmount;
        }

        public WithdrawElement setPayAmount(BigDecimal payAmount) {
            this.payAmount = payAmount;
            return this;
        }

        public Date getModifyTime() {
            return modifyTime;
        }

        public WithdrawElement setModifyTime(Date modifyTime) {
            this.modifyTime = modifyTime;
            return this;
        }

        public String getErrCode() {
            return errCode;
        }

        public WithdrawElement setErrCode(String errCode) {
            this.errCode = errCode;
            return this;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public WithdrawElement setErrMsg(String errMsg) {
            this.errMsg = errMsg;
            return this;
        }

        public String getChannelOrder() {
            return channelOrder;
        }

        public WithdrawElement setChannelOrder(String channelOrder) {
            this.channelOrder = channelOrder;
            return this;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("WithdrawElement{");
            sb.append("status=").append(status);
            sb.append(", serial='").append(serial).append('\'');
            sb.append(", channelOrder='").append(channelOrder).append('\'');
            sb.append(", bankCardNo='").append(bankCardNo).append('\'');
            sb.append(", bankType='").append(bankType).append('\'');
            sb.append(", realName='").append(realName).append('\'');
            sb.append(", payAmount=").append(payAmount);
            sb.append(", modifyTime=").append(modifyTime);
            sb.append(", errCode='").append(errCode).append('\'');
            sb.append(", errMsg='").append(errMsg).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
