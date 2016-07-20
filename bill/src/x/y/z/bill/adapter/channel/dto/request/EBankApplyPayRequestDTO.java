package x.y.z.bill.adapter.channel.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;
import x.y.z.bill.adapter.constant.CardType;

public class EBankApplyPayRequestDTO extends ChannelBaseDTO {

    private static final long serialVersionUID = 8207506601616292625L;

    @NotNull(message = "鉴权信息不能为空")
    private TreatyInfoDTO treatyInfo;
    @NotNull(message = "平台订单号不能为空")
    private String channelSendOrder;
    /**
     * 卡类型
     */
    private CardType cardType = CardType.DEBIT_CARD;
    /**
     * 充值金额
     */
    @DecimalMin(value = "0.01", message = "充值金额必须大于0.01")
    private BigDecimal money;
    /**
     * 平台手续费
     */
    private BigDecimal fee = BigDecimal.ZERO;
    /**
     * 充值用户IP
     */
    @NotNull(message = "客户端IP不能为空")
    private String clientIp;
    /**
     * 充值描述
     */
    private String description;

    private String attachData;

    public CardType getCardType() {
        return cardType;
    }

    public EBankApplyPayRequestDTO setCardType(CardType cardType) {
        this.cardType = cardType;
        return this;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public EBankApplyPayRequestDTO setMoney(BigDecimal money) {
        this.money = money;
        return this;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public EBankApplyPayRequestDTO setFee(BigDecimal fee) {
        this.fee = fee;
        return this;
    }

    public String getClientIp() {
        return clientIp;
    }

    public EBankApplyPayRequestDTO setClientIp(String clientIp) {
        this.clientIp = clientIp;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EBankApplyPayRequestDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getChannelSendOrder() {
        return channelSendOrder;
    }

    public EBankApplyPayRequestDTO setChannelSendOrder(String channelSendOrder) {
        this.channelSendOrder = channelSendOrder;
        return this;
    }

    public TreatyInfoDTO getTreatyInfo() {
        return treatyInfo;
    }

    public EBankApplyPayRequestDTO setTreatyInfo(TreatyInfoDTO treatyInfo) {
        this.treatyInfo = treatyInfo;
        return this;
    }

    public String getAttachData() {
        return attachData;
    }

    public EBankApplyPayRequestDTO setAttachData(String attachData) {
        this.attachData = attachData;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EBankApplyPayRequestDTO{");
        sb.append("treatyInfo=").append(treatyInfo);
        sb.append(", channelSendOrder='").append(channelSendOrder).append('\'');
        sb.append(", cardType=").append(cardType);
        sb.append(", money=").append(money);
        sb.append(", fee=").append(fee);
        sb.append(", clientIp='").append(clientIp).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", attachData='").append(attachData).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
