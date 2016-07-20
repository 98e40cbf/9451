package x.y.z.bill.adapter.channel.bill99;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import x.y.z.bill.adapter.channel.bill99.dto.*;
import x.y.z.bill.adapter.channel.dto.PayBilDTO;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;
import x.y.z.bill.adapter.channel.dto.request.*;
import x.y.z.bill.adapter.channel.dto.response.*;
import io.alpha.logging.Logger;
import io.alpha.logging.LoggerFactory;

public class Bill99Builder {
    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final ResponseDTO<AuthResponseDTO> buildAuthResp(AuthRequestDTO req, MasMessage message) {
        AuthResponseDTO data = new AuthResponseDTO(req);
        BeanUtils.copyProperties(req, data);

        if (message.getErrorMsgContent() != null) {
            return ResponseDTO.buildFail(data, message.getErrorMsgContent().getErrorCode(),
                    message.getErrorMsgContent().getErrorMessage());
        }
        IndAuthContent indAuthContent = message.getIndAuthContent();
        if (indAuthContent != null) {
            data.setExistAuthenticate(false);
            if (StringUtils.isNotBlank(indAuthContent.getResponseCode())) {
                if (Bill99Status.SUCCESS.equals(indAuthContent.getResponseCode())) {
                    data.setChannelToken(indAuthContent.getToken());
                    return ResponseDTO.buildSuccess(data, indAuthContent.getResponseCode(),
                            indAuthContent.getResponseTextMessage());
                } else {
                    return ResponseDTO.buildFail(data, indAuthContent.getResponseCode(),
                            indAuthContent.getResponseTextMessage());
                }
            }
            return ResponseDTO.buildFail(data, indAuthContent.getResponseCode(),
                    indAuthContent.getResponseTextMessage());
        }
        return ResponseDTO.buildFail(data, "RESPONSE_ERROR", "服务器返回数据错误");
    }

    public static ResponseDTO<AuthConfirmResponseDTO> buildResponse(AuthConfirmRequestDTO req, MasMessage message) {
        AuthConfirmResponseDTO data = new AuthConfirmResponseDTO(req);

        if (message.getErrorMsgContent() != null) {
            return ResponseDTO.buildFail(data, message.getErrorMsgContent().getErrorCode(),
                    message.getErrorMsgContent().getErrorMessage());
        }
        IndAuthDynVerifyContent verifyContent = message.getIndAuthDynVerifyContent();
        if (verifyContent != null) {
            if (StringUtils.isNotBlank(verifyContent.getResponseCode())) {
                if (Bill99Status.SUCCESS.equals(verifyContent.getResponseCode())) {
                    return ResponseDTO.buildSuccess(data, verifyContent.getResponseCode(),
                            verifyContent.getResponseTextMessage());
                } else {
                    return ResponseDTO.buildFail(data, verifyContent.getResponseCode(),
                            verifyContent.getResponseTextMessage());
                }
            }
            return ResponseDTO.buildFail(data, verifyContent.getResponseCode(), verifyContent.getResponseTextMessage());
        }
        return ResponseDTO.buildFail(data, "RESPONSE_ERROR", "服务器返回数据错误");
    }

    public static ResponseDTO<QueryAuthInfoResponseDTO> buildQueryAuthResp(AuthRequestDTO dto, MasMessage message) {
        QueryAuthInfoResponseDTO data = new QueryAuthInfoResponseDTO(dto.getTreatyInfo());
        data.setAuthenticate(false);
        PCIQueryContent pciQueryContent = message.getPciQueryContent();
        if (message.getErrorMsgContent() != null) {
            return ResponseDTO.buildFail(data, message.getErrorMsgContent().getErrorCode(),
                    message.getErrorMsgContent().getErrorMessage());
        }
        if (pciQueryContent != null) {
            if (StringUtils.isNotBlank(pciQueryContent.getResponseCode())) {
                if (Bill99Status.SUCCESS.equals(pciQueryContent.getResponseCode())) {
                    if (!CollectionUtils.isEmpty(message.getPciQueryContent().getPciInfos())) {
                        String storableCardNo = createStorableCardNo(dto.getTreatyInfo().getBankCardNo());
                        List<PCIInfo> pciInfos = message.getPciQueryContent().getPciInfos();
                        for (PCIInfo pciInfo : pciInfos) {
                            if (StringUtils.isNotBlank(storableCardNo)
                                    && storableCardNo.equals(pciInfo.getStorablePan())) {
                                data.setAuthenticate(true);
                                return ResponseDTO.buildSuccess(data, pciQueryContent.getResponseCode(),
                                        pciQueryContent.getResponseTextMessage());
                            }
                        }
                    }
                }
            }
            return ResponseDTO.buildFail(data, pciQueryContent.getResponseCode(),
                    pciQueryContent.getResponseTextMessage());
        }
        return ResponseDTO.buildFail(data, "RESPONSE_ERROR", "服务器返回数据错误");
    }

    public static ResponseDTO<QuickPayConfirmResponseDTO> buildQuickPayConfirmResp(QuickPayConfirmRequestDTO dto,
            MasMessage masMessage) {
        QuickPayConfirmResponseDTO data = new QuickPayConfirmResponseDTO();
        data.setTxnId(dto.getTxnId());
        data.setAmount(dto.getAmount());
        data.setChannelRecvOrder(data.getChannelRecvOrder());
        data.setChannelSendOrder(data.getChannelSendOrder());
        data.setPayStatus(data.getPayStatus());
        data.setTreatyInfo(dto.getTreatyInfo());
        data.setChannelCode(data.getChannelCode());
        if (masMessage.getErrorMsgContent() != null) {
            return ResponseDTO.buildFail(data, masMessage.getErrorMsgContent().getErrorCode(),
                    masMessage.getErrorMsgContent().getErrorMessage());
        }
        TxnMsgContent txnMsgContent = masMessage.getTxnMsgContent();
        if (txnMsgContent != null && StringUtils.isNotEmpty(txnMsgContent.getResponseCode())) {
            if (Bill99Status.SUCCESS.equals(txnMsgContent.getResponseCode())) {
                data.setAmount(new BigDecimal(txnMsgContent.getAmount()));
                return ResponseDTO.buildSuccess(data, txnMsgContent.getResponseCode(),
                        txnMsgContent.getResponseTextMessage());
            } else if (Bill99Status.UNCERTAINTY_68.equals(txnMsgContent.getResponseCode())
                    || Bill99Status.UNCERTAINTY_C0.equals(txnMsgContent.getResponseCode())) {
                return ResponseDTO.build(ResponseDTO.Status.UNCERTAINTY, data, txnMsgContent.getResponseCode(),
                        txnMsgContent.getResponseTextMessage());
            } else {
                return ResponseDTO.buildFail(data, txnMsgContent.getResponseCode(),
                        txnMsgContent.getResponseTextMessage());
            }
        }
        return ResponseDTO.buildFail(data, "RESPONSE_ERROR", "服务器返回数据错误");
    }

    public static ResponseDTO<QuickPayResponseDTO> buildQuickPayResp(QuickPayRequestDTO dto, MasMessage masMessage) {
        QuickPayResponseDTO data = new QuickPayResponseDTO();
        BeanUtils.copyProperties(dto, data);
        if (masMessage.getErrorMsgContent() != null) {
            return ResponseDTO.buildFail(data, masMessage.getErrorMsgContent().getErrorCode(),
                    masMessage.getErrorMsgContent().getErrorMessage());
        }
        GetDynNumContent getDynNumContent = masMessage.getGetDynNumContent();
        if (getDynNumContent != null) {
            if (StringUtils.isNotBlank(getDynNumContent.getResponseCode())) {
                if (Bill99Status.SUCCESS.equals(getDynNumContent.getResponseCode())
                        && StringUtils.isNotBlank(getDynNumContent.getToken())) {
                    data.setToken(getDynNumContent.getToken());
                    return ResponseDTO.buildSuccess(data, getDynNumContent.getResponseCode(),
                            getDynNumContent.getResponseTextMessage());
                }
            }
            return ResponseDTO.buildFail(data, getDynNumContent.getResponseCode(),
                    getDynNumContent.getResponseTextMessage());
        }
        return ResponseDTO.buildFail(data, "RESPONSE_ERROR", "服务器返回数据错误");
    }

    public static String createStorableCardNo(String bankCardNO) {
        if (StringUtils.isNotBlank(bankCardNO) && bankCardNO.length() >= 10) {
            return bankCardNO.substring(0, 6) + bankCardNO.substring(bankCardNO.length() - 4);
        }
        return null;
    }

    public static ResponseDTO<PayQueryResponseDTO> buildPayQueryResponse(PayQueryRequestDTO dto,
            MasMessage masMessage) {
        PayQueryResponseDTO data = new PayQueryResponseDTO();
        data.setChannelSendOrder(dto.getChannelSendOrder());
        data.setTreatyInfo(dto.getTreatyInfo());
        if (masMessage.getErrorMsgContent() != null) {
            return ResponseDTO.buildFail(data, masMessage.getErrorMsgContent().getErrorCode(),
                    masMessage.getErrorMsgContent().getErrorMessage());
        }

        TxnMsgContent txnMsgContent = masMessage.getTxnMsgContent();
        QryTxnMsgContent qryTxnMsgContent = masMessage.getQryTxnMsgContent();
        if (txnMsgContent != null && qryTxnMsgContent != null) {
            if (Bill99Status.SUCCESS.equals(txnMsgContent.getResponseCode())) {
                data.setAmount(new BigDecimal(txnMsgContent.getAmount()));
                if (Bill99Status.TXN_SUCCESS.equals(txnMsgContent.getTxnStatus())) {
                    return ResponseDTO.buildSuccess(data, txnMsgContent.getResponseCode(),
                            txnMsgContent.getResponseTextMessage());
                } else if (Bill99Status.TXN_FAILED.equals(txnMsgContent.getTxnStatus())
                        || Bill99Status.TXN_REVERSED.equals(txnMsgContent.getTxnStatus())) {
                    return ResponseDTO.buildFail(data, txnMsgContent.getResponseCode(),
                            txnMsgContent.getResponseTextMessage());
                } else {
                    return ResponseDTO.build(ResponseDTO.Status.UNCERTAINTY, data, txnMsgContent.getResponseCode(),
                            txnMsgContent.getResponseTextMessage());
                }
            } else if (Bill99Status.UNCERTAINTY_68.equals(txnMsgContent.getResponseCode())
                    || Bill99Status.UNCERTAINTY_C0.equals(txnMsgContent.getResponseCode())) {
                return ResponseDTO.build(ResponseDTO.Status.UNCERTAINTY, data, txnMsgContent.getResponseCode(),
                        txnMsgContent.getResponseTextMessage());
            } else if (StringUtils.isNotBlank(txnMsgContent.getResponseCode())) {
                return ResponseDTO.buildFail(data, txnMsgContent.getResponseCode(),
                        txnMsgContent.getResponseTextMessage());
            }
        }
        return ResponseDTO.buildFail(data, "RESPONSE_ERROR", "服务器返回数据错误");
    }

    public static void formatBillFileContent(PayBilDTO response, File billFile) {

    }

    private static void processRenBillData(final String line, PayBilDTO response) {

    }

    public static ResponseDTO<TreatyInfoDTO> buildCancelAuth(AuthRequestDTO requestDTO, MasMessage message) {
        if (message.getErrorMsgContent() != null) {
            return ResponseDTO.buildFail(requestDTO.getTreatyInfo(), message.getErrorMsgContent().getErrorCode(),
                    message.getErrorMsgContent().getErrorMessage());
        }

        PCIDeleteContent content = message.getPciDeleteContent();
        if (content != null) {
            if (Bill99Status.SUCCESS.equals(content.getResponseCode())) {
                return ResponseDTO.buildSuccess(requestDTO.getTreatyInfo(), content.getResponseCode(),
                        content.getResponseTextMessage());
            } else {
                return ResponseDTO.buildFail(requestDTO.getTreatyInfo(), content.getResponseCode(),
                        content.getResponseTextMessage());
            }
        }
        return ResponseDTO.buildFail(requestDTO.getTreatyInfo(), "RESPONSE_ERROR", "服务器返回数据错误");
    }
}
