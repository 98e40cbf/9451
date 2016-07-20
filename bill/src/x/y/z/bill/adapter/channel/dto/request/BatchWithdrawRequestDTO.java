package x.y.z.bill.adapter.channel.dto.request;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;
import x.y.z.bill.adapter.constant.AccountType;
import io.alpha.core.model.BaseObject;

/**
 * Created by cyh on 2016/4/6.
 */
public class BatchWithdrawRequestDTO extends ChannelBaseDTO {

    private static final long serialVersionUID = 1L;
    @NotNull(message = "批量代付包不能为空")
    List<WithdrawElement> withdrawElementList;
    /**
     * 批量批次号
     */
    @NotNull(message = "批量代付批次号不能为空")
    private String packageId;

    public BatchWithdrawRequestDTO() {
        super();
    }

    public BatchWithdrawRequestDTO(String txnId, String packageId) {
        this.txnId = txnId;
        this.packageId = packageId;
    }

    public BatchWithdrawRequestDTO(String packageId, List<WithdrawElement> withdrawElementList) {
        this.packageId = packageId;
        this.withdrawElementList = withdrawElementList;
    }

    public String getPackageId() {
        return packageId;
    }

    public BatchWithdrawRequestDTO setPackageId(String packageId) {
        this.packageId = packageId;
        return this;
    }

    public List<WithdrawElement> getWithdrawElementList() {
        return withdrawElementList;
    }

    public BatchWithdrawRequestDTO setWithdrawElementList(List<WithdrawElement> withdrawElementList) {
        this.withdrawElementList = withdrawElementList;
        return this;
    }

    public static class WithdrawElement extends BaseObject {

        private static final long serialVersionUID = 1L;
        @NotNull(message = "鉴权信息不能为空")
        private TreatyInfoDTO treaty;
        @NotNull(message = "提现金额不能为空")
        @DecimalMin(value = "0.01", message = "提现金额必须不小于0.01")
        private BigDecimal amount;
        private BigDecimal fee;
        @NotNull(message = "平台订单号不能为空")
        private String channelSendOrder;
        private AccountType bankCardAccountType;
        /**
         * 开户地区(1～2位数字编码，不支持汉字，可以填写为0。见财付通提供的开户地区编码)
         */
        private String area = "0";//
        /**
         * 开户城市(1～4位数字编码，不支持汉字，可以填写为0。见财付通提供的开户城市编码)
         */
        private String city = "0";//
        /**
         * 支行名称（汉字，可以填写为全角空格）
         */
        private String branch = "";//
        /**
         * 付款说明
         */
        private String desc = "";//
        /**
         * 付款接收通知手机号
         */
        private String mobile = "";//
        /**
         * 批量单笔序列号
         */
        private String serialId;//

        public TreatyInfoDTO getTreaty() {
            return treaty;
        }

        public WithdrawElement setTreaty(TreatyInfoDTO treaty) {
            this.treaty = treaty;
            return this;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public WithdrawElement setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public BigDecimal getFee() {
            return fee;
        }

        public WithdrawElement setFee(BigDecimal fee) {
            this.fee = fee;
            return this;
        }

        public String getChannelSendOrder() {
            return channelSendOrder;
        }

        public WithdrawElement setChannelSendOrder(String channelSendOrder) {
            this.channelSendOrder = channelSendOrder;
            return this;
        }

        public AccountType getBankCardAccountType() {
            return bankCardAccountType;
        }

        public WithdrawElement setBankCardAccountType(AccountType bankCardAccountType) {
            this.bankCardAccountType = bankCardAccountType;
            return this;
        }

        public String getArea() {
            return area;
        }

        public WithdrawElement setArea(String area) {
            this.area = area;
            return this;
        }

        public String getCity() {
            return city;
        }

        public WithdrawElement setCity(String city) {
            this.city = city;
            return this;
        }

        public String getBranch() {
            return branch;
        }

        public WithdrawElement setBranch(String branch) {
            this.branch = branch;
            return this;
        }

        public String getDesc() {
            return desc;
        }

        public WithdrawElement setDesc(String desc) {
            this.desc = desc;
            return this;
        }

        public String getMobile() {
            return mobile;
        }

        public WithdrawElement setMobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public String getSerialId() {
            return serialId;
        }

        public WithdrawElement setSerialId(String serialId) {
            this.serialId = serialId;
            return this;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("WithdrawElement{");
            sb.append("treaty=").append(treaty);
            sb.append(", amount=").append(amount);
            sb.append(", fee=").append(fee);
            sb.append(", channelSendOrder='").append(channelSendOrder).append('\'');
            sb.append(", bankCardAccountType=").append(bankCardAccountType);
            sb.append(", area='").append(area).append('\'');
            sb.append(", city='").append(city).append('\'');
            sb.append(", branch='").append(branch).append('\'');
            sb.append(", desc='").append(desc).append('\'');
            sb.append(", mobile='").append(mobile).append('\'');
            sb.append(", serialId='").append(serialId).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
