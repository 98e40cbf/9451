package x.y.z.bill.adapter.channel.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;

public class WithdrawRequestDTO extends ChannelBaseDTO {
    /**
     * 
     */
    private static final long serialVersionUID = -1L;
    @NotNull(message = "平台订单号不能为空")
    private String channelSendOrder;
    @NotNull(message = "鉴权信息不能为空")
    private TreatyInfoDTO treatyInfo;
    @DecimalMin(value = "0.01", message = "提现金额必须大于0.01")
    private BigDecimal amount;
    private String branchName;
    private BigDecimal fee;
    private String remark;

    public TreatyInfoDTO getTreatyInfo() {
        return treatyInfo;
    }

    public WithdrawRequestDTO setTreatyInfo(TreatyInfoDTO treatyInfo) {
        this.treatyInfo = treatyInfo;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public WithdrawRequestDTO setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getBranchName() {
        return branchName;
    }

    public WithdrawRequestDTO setBranchName(String branchName) {
        this.branchName = branchName;
        return this;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public WithdrawRequestDTO setFee(BigDecimal fee) {
        this.fee = fee;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public WithdrawRequestDTO setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getChannelSendOrder() {
        return channelSendOrder;
    }

    public WithdrawRequestDTO setChannelSendOrder(String channelSendOrder) {
        this.channelSendOrder = channelSendOrder;
        return this;
    }
}
