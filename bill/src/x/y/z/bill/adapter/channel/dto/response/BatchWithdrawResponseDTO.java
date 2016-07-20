package x.y.z.bill.adapter.channel.dto.response;

import java.util.List;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.request.BatchWithdrawRequestDTO;

public class BatchWithdrawResponseDTO extends ChannelBaseDTO {

    private static final long serialVersionUID = 1L;
    /**
     * 批量单笔序列号
     */
    List<BatchWithdrawRequestDTO.WithdrawElement> withdrawElementList;
    /**
     * 批量批次号
     */
    private String packageId;

    public BatchWithdrawResponseDTO() {
    }

    public BatchWithdrawResponseDTO(BatchWithdrawRequestDTO request) {
        this.packageId = request.getPackageId();
        this.withdrawElementList = request.getWithdrawElementList();
    }

    public BatchWithdrawResponseDTO(String packageId,
            List<BatchWithdrawRequestDTO.WithdrawElement> withdrawElementList) {
        this.packageId = packageId;
        this.withdrawElementList = withdrawElementList;
    }

    public String getPackageId() {
        return packageId;
    }

    public BatchWithdrawResponseDTO setPackageId(String packageId) {
        this.packageId = packageId;
        return this;
    }

    public List<BatchWithdrawRequestDTO.WithdrawElement> getWithdrawElementList() {
        return withdrawElementList;
    }

    public BatchWithdrawResponseDTO setWithdrawElementList(
            List<BatchWithdrawRequestDTO.WithdrawElement> withdrawElementList) {
        this.withdrawElementList = withdrawElementList;
        return this;
    }
}
