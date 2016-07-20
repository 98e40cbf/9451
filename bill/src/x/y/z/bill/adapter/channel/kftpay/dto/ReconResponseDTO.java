package x.y.z.bill.adapter.channel.kftpay.dto;

import java.math.BigDecimal;
import java.util.Date;

import io.alpha.core.model.BaseObject;

public class ReconResponseDTO extends BaseObject {

    private static final long serialVersionUID = 1L;
    private BigDecimal amount;
    private String checkDate;
    private String currency;
    private String errorCode;
    private String failureDetails;
    private Date kftTradeTime;
    private String orderNo;
    private String payeeBankAccountNo;
    private String payerBankAccountNo;
    private String productNo;
    private int status;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getFailureDetails() {
        return failureDetails;
    }

    public void setFailureDetails(String failureDetails) {
        this.failureDetails = failureDetails;
    }

    public Date getKftTradeTime() {
        return kftTradeTime;
    }

    public void setKftTradeTime(Date kftTradeTime) {
        this.kftTradeTime = kftTradeTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayeeBankAccountNo() {
        return payeeBankAccountNo;
    }

    public void setPayeeBankAccountNo(String payeeBankAccountNo) {
        this.payeeBankAccountNo = payeeBankAccountNo;
    }

    public String getPayerBankAccountNo() {
        return payerBankAccountNo;
    }

    public void setPayerBankAccountNo(String payerBankAccountNo) {
        this.payerBankAccountNo = payerBankAccountNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
