package x.y.z.bill.adapter.channel.kftpay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import x.y.z.bill.adapter.channel.dto.request.AuthConfirmRequestDTO;
import x.y.z.bill.adapter.channel.dto.request.AuthRequestDTO;
import x.y.z.bill.adapter.channel.dto.request.QuickPayConfirmRequestDTO;
import x.y.z.bill.adapter.channel.dto.request.WithdrawRequestDTO;
import x.y.z.bill.adapter.channel.kftpay.dto.KftpayRefundTicketStatus;

import com.lycheepay.gateway.client.dto.PayToBankAccountDTO;
import com.lycheepay.gateway.client.dto.QueryTradeRecordResultDTO;
import com.lycheepay.gateway.client.dto.TradeRecord;
import com.lycheepay.gateway.client.dto.gbp.TreatyApplyDTO;
import com.lycheepay.gateway.client.dto.gbp.TreatyCollectDTO;
import com.lycheepay.gateway.client.dto.gbp.TreatyConfirmDTO;

import io.alpha.util.CollectionUtil;
import io.alpha.util.DecimalUtil;

public class KftBuilder {
    public static TreatyApplyDTO buildTreatyApplyDTO(AuthRequestDTO request, KftpayConfig config) {
        final TreatyApplyDTO dto = new TreatyApplyDTO();
        dto.setMerchantId(config.getMerchantId());
        dto.setOrderNo(request.getChannelSendOrder());
        dto.setTreatyType(KftpayConstant.TREATY_TYPE.val());// 协议类型11：借记卡扣款

        Date dAfter = DateUtils.addYears(new Date(), 100);
        dto.setStartDate(DateFormatUtils.format(dAfter, "yyyyMMdd"));
        dto.setEndDate(DateFormatUtils.format(dAfter, "yyyyMMdd"));
        dto.setHolderName(request.getTreatyInfo().getRealName());
        dto.setBankType(request.getTreatyInfo().getChannelBankCode());
        dto.setBankCardType(KftpayConstant.BANKCARD_TYPE.val());// 借记卡
        dto.setBankCardNo(request.getTreatyInfo().getBankCardNo());
        dto.setMobileNo(request.getTreatyInfo().getMobile());
        dto.setCertificateType(KftpayConstant.CERTIFICATE_TYPE.val());// 身份证
        dto.setCertificateNo(request.getTreatyInfo().getIdCardNo());

        dto.setVersion(config.getVersion());
        return dto;
    }

    public static TreatyConfirmDTO builderConfirmTreatyApplyDTO(AuthConfirmRequestDTO request, KftpayConfig config) {
        final TreatyConfirmDTO dto = new TreatyConfirmDTO();
        dto.setMerchantId(config.getMerchantId());
        dto.setOrderNo(request.getChannelSendOrder());
        dto.setSmsSeq(request.getChannelToken());
        dto.setAuthCode(request.getSecurityCode());
        dto.setBankCardNo(request.getTreatyInfo().getBankCardNo());
        dto.setHolderName(request.getTreatyInfo().getRealName());

        dto.setVersion(config.getVersion());
        return dto;
    }

    public static PayToBankAccountDTO builderPayToBankAccountDTO(WithdrawRequestDTO requestDTO, KftpayConfig config) {
        final PayToBankAccountDTO payToBankAccountDTO = new PayToBankAccountDTO();
        payToBankAccountDTO.setAmount(DecimalUtil
                .formatWithoutScale(DecimalUtil.multiply(requestDTO.getAmount(), new BigDecimal(100))).toString());
        payToBankAccountDTO.setCurrency(KftpayConstant.CURRENCY.val());
        payToBankAccountDTO.setCustBankAccountNo(requestDTO.getTreatyInfo().getBankCardNo());
        payToBankAccountDTO.setCustBankNo(requestDTO.getTreatyInfo().getChannelBankCode());
        payToBankAccountDTO.setCustCertificationType(KftpayConstant.CERTIFICATE_TYPE.val());
        payToBankAccountDTO.setCustID(requestDTO.getTreatyInfo().getIdCardNo());
        payToBankAccountDTO.setCustName(requestDTO.getTreatyInfo().getRealName());
        payToBankAccountDTO.setCustPhone(requestDTO.getTreatyInfo().getMobile());
        payToBankAccountDTO.setMerchantId(config.getMerchantId());
        payToBankAccountDTO.setOrderNo(requestDTO.getChannelSendOrder());
        payToBankAccountDTO.setProductNo(config.getWithdrawService().getProductNo());
        payToBankAccountDTO.setService(config.getWithdrawService().getServiceName());
        payToBankAccountDTO.setTradeName("提现");
        payToBankAccountDTO.setTradeTime(new Date());
        payToBankAccountDTO.setVersion(config.getVersion());
        return payToBankAccountDTO;
    }

    public static TreatyCollectDTO builderTreatyCollectDTO(QuickPayConfirmRequestDTO requestDTO, KftpayConfig config) {
        TreatyCollectDTO dto = new TreatyCollectDTO();
        dto.setMerchantId(config.getMerchantId());
        dto.setOrderNo(requestDTO.getChannelSendOrder());
        dto.setTreatyNo(requestDTO.getTreatyInfo().getIdentityCode());
        dto.setCurrency(KftpayConstant.CURRENCY.val());
        dto.setAmount(DecimalUtil.formatWithoutScale(DecimalUtil.multiply(requestDTO.getAmount(), new BigDecimal(100)))
                .toString());
        dto.setBankType(requestDTO.getTreatyInfo().getChannelBankCode());
        dto.setBankCardNo(requestDTO.getTreatyInfo().getBankCardNo());
        dto.setHolderName(requestDTO.getTreatyInfo().getRealName());
        dto.setProductNo(config.getRechargeService().getProductNo());
        dto.setService(config.getRechargeService().getServiceName());
        dto.setVersion(config.getVersion());
        return dto;
    }

    public static boolean checkChannelNoData(QueryTradeRecordResultDTO result) {
        if (StringUtils.isNotBlank(result.getErrorCode())) {
            if ("CUST_KFTBANK_00000012".equals(result.getErrorCode())
                    || "CUST_KFTBANK_00000039".equals(result.getErrorCode())
                    || "CUST_KFTBANK_00000041".equals(result.getErrorCode())
                    || "CUST_KFTBANK_00000051".equals(result.getErrorCode())
                    || "CUST_KFTBANK_00000069".equals(result.getErrorCode())) {
                // 渠道无数据
                return true;
            }
        }
        return false;
    }

    public static boolean checkSuccess(QueryTradeRecordResultDTO result, String channelSendOrder) {
        List<TradeRecord> tradeList = result.getDetails();
        if (CollectionUtil.isNotEmpty(tradeList)) {
            TradeRecord tradeRecord = tradeList.get(0);
            if (tradeRecord != null) {
                if (channelSendOrder.equals(tradeRecord.getOrderNo())) {
                    if (String.valueOf(KftpayTradeStatus.SUCCESS.getCode()).equals(tradeRecord.getStatus())) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public static boolean checkFail(QueryTradeRecordResultDTO result, String channelSendOrder) {
        List<TradeRecord> tradeList = result.getDetails();
        if (CollectionUtil.isNotEmpty(tradeList)) {
            TradeRecord tradeRecord = tradeList.get(0);
            if (tradeRecord != null) {
                if (channelSendOrder.equals(tradeRecord.getOrderNo())) {
                    if (String.valueOf(KftpayTradeStatus.FAIL.getCode()).equals(tradeRecord.getStatus())) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public static boolean checkRefund(QueryTradeRecordResultDTO result, String channelSendOrder) {
        List<TradeRecord> tradeList = result.getDetails();
        if (CollectionUtil.isNotEmpty(tradeList)) {
            TradeRecord tradeRecord = tradeList.get(0);
            if (tradeRecord != null) {
                if (channelSendOrder.equals(tradeRecord.getOrderNo())) {
                    if (String.valueOf(KftpayRefundTicketStatus.PROSESSED.getCode())
                            .equals(tradeRecord.getDishonorStatus())) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

}
