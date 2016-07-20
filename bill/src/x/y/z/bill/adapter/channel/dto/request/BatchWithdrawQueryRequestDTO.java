package x.y.z.bill.adapter.channel.dto.request;

import javax.validation.constraints.NotNull;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;

public class BatchWithdrawQueryRequestDTO extends ChannelBaseDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 批量批次号
     */
    @NotNull(message = "批量代付批次号不能为空")
    private String packageId;

    public BatchWithdrawQueryRequestDTO() {
    }

    public BatchWithdrawQueryRequestDTO(String txnId, String packageId) {
        this.txnId = txnId;
        this.packageId = packageId;
    }

    public String getPackageId() {
        return packageId;
    }

    public BatchWithdrawQueryRequestDTO setPackageId(String packageId) {
        this.packageId = packageId;
        return this;
    }
}
