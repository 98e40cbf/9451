package x.y.z.bill.adapter.channel.dto.request;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.constant.ReconBusinessType;

public class ReconDownloadRequestDTO extends ChannelBaseDTO {

    private static final long serialVersionUID = 1L;
    @NotNull(message = "账单日期不能为空")
    @Past(message = "账单日期必须是过去时间")
    private Date billDate;
    @NotNull(message = "账单类型不能为空")
    private ReconBusinessType bsType;

    public ReconDownloadRequestDTO() {
        super();
    }

    public ReconBusinessType getBsType() {
        return bsType;
    }

    public void setBsType(ReconBusinessType bsType) {
        this.bsType = bsType;
    }

    public Date getBillDate() {
        return billDate;
    }

    public ReconDownloadRequestDTO setBillDate(Date billDate) {
        this.billDate = billDate;
        return this;
    }
}
