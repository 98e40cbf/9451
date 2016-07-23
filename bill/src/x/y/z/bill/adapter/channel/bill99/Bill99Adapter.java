/*******************************************************************************
 * Create on 2016年1月14日 上午11:43:09
 * Copyright (c) 2014 深圳市小牛电子商务有限公司版权所有. 粤ICP备13089339号
 * 注意：本内容仅限于深圳市小牛电子商务有限公司内部传阅，禁止外泄以及用于其他商业目的!
 ******************************************************************************/
package x.y.z.bill.adapter.channel.bill99;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.y.z.bill.adapter.channel.GenericAdapter;
import x.y.z.bill.adapter.channel.bill99.dto.*;
import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;
import x.y.z.bill.adapter.channel.dto.request.*;
import x.y.z.bill.adapter.channel.dto.response.*;
import x.y.z.bill.adapter.config.ConfigManager;
import x.y.z.bill.adapter.constant.ChannelCode;
import x.y.z.bill.adapter.httpclient.HttpClientTools;
import x.y.z.bill.adapter.httpclient.HttpRequestDTO;
import x.y.z.bill.adapter.httpclient.HttpResult;
import x.y.z.bill.adapter.util.XMLSerializer;

@Service(value = "adapter_100002")
public class Bill99Adapter extends GenericAdapter implements Bill99Status {
    @Autowired
    private ConfigManager configManager;

    /**
     * 获取渠道编码
     *
     * @return
     */
    @Override
    public String getChannelCode() {
        return ChannelCode.BILL99.getCode();
    }

    /**
     * 渠道名称
     *
     * @return
     */
    @Override
    public String getChannelName() {
        return ChannelCode.BILL99.getDesc();
    }

    /**
     * 获取渠道配置
     *
     * @param dto
     * @return
     */
    @Override
    public Bill99Config getConfig(ChannelBaseDTO dto) {
        return (Bill99Config) configManager.getConfig(dto.getChannelCode());
    }

    /**
     * 鉴权申请
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseDTO<AuthResponseDTO> applyAuth(AuthRequestDTO dto) {
        Bill99Config config = getConfig(dto);

        IndAuthContent content = new IndAuthContent();
        content.setMerchantId(config.getMerchantId());
        content.setCustomerId(dto.getTreatyInfo().getIdentityCode());
        content.setTerminalId(config.getAuthTerminalId());
        content.setCardHolderId(dto.getTreatyInfo().getIdCardNo());
        content.setCardHolderName(dto.getTreatyInfo().getRealName());
        content.setBankId(dto.getTreatyInfo().getChannelBankCode());
        content.setPan(dto.getTreatyInfo().getBankCardNo());
        content.setPhoneNO(dto.getTreatyInfo().getMobile());
        content.setExternalRefNumber(dto.getChannelSendOrder());

        MasMessage message = new MasMessage(content);
        try {
            message = callBill99Server(dto.getTxnId(), message, config.getSslContext(), createAuthorization(config),
                    config.getIndieAuthUrl());
        } catch (Exception e) {
            logger.error("[{}] - [支付系统] - [快钱渠道] - 申请鉴权出错", dto.getTxnId(), e);
            return ResponseDTO.buildException("NET_ERROR", "与服务器通信出错");
        }
        return Bill99Builder.buildAuthResp(dto, message);
    }

    /**
     * 鉴权确认
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseDTO<AuthConfirmResponseDTO> confirmAuth(AuthConfirmRequestDTO dto) {
        Bill99Config config = getConfig(dto);

        IndAuthDynVerifyContent content = new IndAuthDynVerifyContent();

        content.setMerchantId(config.getMerchantId());
        content.setCustomerId(dto.getTreatyInfo().getIdentityCode());
        content.setExternalRefNumber(dto.getChannelSendOrder());
        content.setBankId(dto.getTreatyInfo().getChannelBankCode());
        content.setPan(dto.getTreatyInfo().getBankCardNo());
        content.setPhoneNO(dto.getTreatyInfo().getMobile());
        content.setValidCode(dto.getSecurityCode());
        content.setToken(dto.getChannelToken());

        MasMessage message = new MasMessage(content);
        try {
            message = callBill99Server(dto.getTxnId(), message, config.getSslContext(), createAuthorization(config),
                    config.getIndieAuthVerifyUrl());
        } catch (Exception e) {
            logger.error("[{}] - [支付系统] - [快钱渠道] - 确认鉴权异常", dto.getTxnId(), e);
            return ResponseDTO.buildException("NET_ERROR", "与服务器通信出错");
        }
        return Bill99Builder.buildResponse(dto, message);
    }

    /**
     * 查询鉴权
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseDTO<QueryAuthInfoResponseDTO> queryAuthInfo(AuthRequestDTO dto) {
        Bill99Config config = getConfig(dto);

        MasMessage message = new MasMessage();
        PCIQueryContent content = new PCIQueryContent();
        content.setMerchantId(config.getMerchantId());
        content.setCustomerId(dto.getTreatyInfo().getIdentityCode());
        message.setPciQueryContent(content);

        try {
            message = callBill99Server(dto.getTxnId(), message, config.getSslContext(), createAuthorization(config),
                    config.getPciQueryUrl());
        } catch (Exception e) {
            logger.error("[{}] - [支付系统] - [快钱渠道] - 订单查询异常", dto.getTxnId(), e);
            return ResponseDTO.buildException("NET_ERROR", "与服务器通信出错");
        }
        return Bill99Builder.buildQueryAuthResp(dto, message);
    }

    /**
     * 取消鉴权
     *
     * @param requestDTO
     * @return
     */
    @Override
    public ResponseDTO<TreatyInfoDTO> cancelAuthInfo(AuthRequestDTO requestDTO) {
        ResponseDTO<QueryAuthInfoResponseDTO> authData = this.queryAuthInfo(requestDTO);
        if (ResponseDTO.Status.SUCCESS.compareTo(authData.getStatus()) != 0) {
            return ResponseDTO.buildFail(requestDTO.getTreatyInfo(), authData.getResponseCode(),
                    authData.getResponseMessage());
        }
        if (authData.getData() == null || !authData.getData().isAuthenticate()) {
            logger.info("[{}] - [支付系统] - [快钱渠道] - 快钱取消协议返回成功，未找到该卡的鉴权信息.", requestDTO.getTxnId());
            return ResponseDTO.buildSuccess(requestDTO.getTreatyInfo(), authData.getResponseCode(),
                    authData.getResponseMessage());
        }

        Bill99Config config = getConfig(requestDTO);

        MasMessage message = new MasMessage();
        PCIDeleteContent content = new PCIDeleteContent();
        content.setMerchantId(config.getMerchantId());
        content.setCustomerId(requestDTO.getTreatyInfo().getIdentityCode());
        content.setStorablePan(Bill99Builder.createStorableCardNo(requestDTO.getTreatyInfo().getBankCardNo()));
        content.setBankId(requestDTO.getTreatyInfo().getChannelBankCode());
        content.setPan(requestDTO.getTreatyInfo().getBankCardNo());

        message.setPciDeleteContent(content);

        try {
            message = callBill99Server(requestDTO.getTxnId(), message, config.getSslContext(),
                    createAuthorization(config), config.getPciQueryUrl());
        } catch (Exception e) {
            logger.error("[{}] - [支付系统] - [快钱渠道] - 取消鉴权协议异常", requestDTO.getTxnId(), e);
            return ResponseDTO.buildException("NET_ERROR", "与服务器通信出错");
        }
        return Bill99Builder.buildCancelAuth(requestDTO, message);

    }

    /**
     * 申请快捷支付
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseDTO<QuickPayResponseDTO> applyQuickPay(QuickPayRequestDTO dto) {
        Bill99Config config = getConfig(dto);

        GetDynNumContent content = new GetDynNumContent();
        content.setMerchantId(config.getMerchantId());
        content.setCustomerId(dto.getTreatyInfo().getIdentityCode());
        content.setExternalRefNumber(dto.getChannelSendOrder());
        content.setStorablePan(Bill99Builder.createStorableCardNo(dto.getTreatyInfo().getBankCardNo()));
        content.setBankId(dto.getTreatyInfo().getChannelBankCode());
        content.setAmount(dto.getAmount().setScale(2).toString());
        MasMessage masMessage = new MasMessage(content);
        try {
            masMessage = callBill99Server(dto.getTxnId(), masMessage, config.getSslContext(),
                    createAuthorization(config), config.getQuickPayUrl());
        } catch (Exception e) {
            logger.error("[{}] - [支付系统] - [快钱渠道] - 快捷支付申请异常", dto.getTxnId(), e);
            return ResponseDTO.buildException("NET_ERROR", "与服务器通信出错");
        }
        return Bill99Builder.buildQuickPayResp(dto, masMessage);
    }

    /**
     * 快捷支付确认
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseDTO<QuickPayConfirmResponseDTO> confirmQuickPay(QuickPayConfirmRequestDTO dto) {
        Bill99Config config = getConfig(dto);

        TxnMsgContent content = new TxnMsgContent();
        content.setTerminalId(config.getOneKeyPayTerminalId());
        content.setTr3Url(config.getCallbackUrl());
        content.setEntryTime(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        content.setStorableCardNo(Bill99Builder.createStorableCardNo(dto.getTreatyInfo().getBankCardNo()));
        content.setAmount(dto.getAmount().setScale(2).toString());
        content.setExternalRefNumber(dto.getChannelSendOrder());
        content.setCustomerId(dto.getTreatyInfo().getIdentityCode());
        content.setMerchantId(config.getMerchantId());
        content.addDatas("phone", dto.getTreatyInfo().getMobile());
        content.addDatas("validCode", "");
        content.addDatas("savePciFlag", "0");
        content.addDatas("token", dto.getChannelToken());
        content.addDatas("payBatch", "2");

        MasMessage message = new MasMessage(content);

        try {
            message = callBill99Server(dto.getTxnId(), message, config.getSslContext(), createAuthorization(config),
                    config.getQuickPayVerifyUrl());
        } catch (Exception e) {
            logger.error("[{}] - [支付系统] - [快钱渠道] - 快捷支付确认异常", dto.getTxnId(), e);
            return ResponseDTO.buildException("NET_ERROR", "与服务器通信出错");
        }
        return Bill99Builder.buildQuickPayConfirmResp(dto, message);
    }

    /**
     * 查询支付订单
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseDTO<PayQueryResponseDTO> queryPayOrder(PayQueryRequestDTO dto) {
        Bill99Config config = getConfig(dto);

        MasMessage message = new MasMessage();
        QryTxnMsgContent content = new QryTxnMsgContent();

        content.setTerminalId(config.getOneKeyPayTerminalId());
        content.setExternalRefNumber(dto.getChannelSendOrder());
        content.setMerchantId(config.getMerchantId());
        message.setQryTxnMsgContent(content);

        try {
            message = callBill99Server(dto.getTxnId(), message, config.getSslContext(), createAuthorization(config),
                    config.getOrderQueryUrl());
        } catch (Exception e) {
            logger.error("[{}] - [支付系统] - [快钱渠道] - 查询支付订单异常", dto.getTxnId(), e);
            return ResponseDTO.buildException("NET_ERROR", "与服务器通信出错");
        }

        return Bill99Builder.buildPayQueryResponse(dto, message);
    }

    /**
     * 异步下载对账单
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseDTO<Boolean> downloadRecon(ReconDownloadRequestDTO dto) {
        return ResponseDTO.buildSuccess(true, "SUCCESS", "获取对账文件成功");
    }

    private MasMessage callBill99Server(String txnId, MasMessage message, SSLContext sslContext, String authorization,
            String url) throws Exception {
        Map<String, String> header = new HashMap<String, String>();
        header.put("Authorization", authorization);

        HttpRequestDTO httpRequest = new HttpRequestDTO();
        httpRequest.setHeader(header).setHttpMethod("post").setSslSocketFactory(new SSLSocketFactory(sslContext));
        httpRequest.setUrl(url);

        HttpResponse httpResponse = null;
        try {
            String sendData = XMLSerializer.toXML(message, false);
            logger.info("[{}] - [支付系统] - [发送快钱服务器] 数据:{}", txnId, sendData);
            HttpResult httpResult = HttpClientTools.sendRequest(sendData, httpRequest);
            logger.info("[{}] - [支付系统] - [接收快钱服务器] 数据:{}", txnId, httpResult);
            if (httpResult != null) {
                if (HttpStatus.SC_OK == httpResult.getResultCode()) {
                    message = XMLSerializer.parseXml(httpResult.getResponseBody(), MasMessage.class);
                }
            }
        } catch (Throwable e) {
            logger.error("[{}] - [支付系统] - [快钱渠道] - 访问快钱服务器异常", txnId, e);
            throw e;
        } finally {
            // 获得响应具体内容
            // 关闭输入流同时会将连接交回至连接处理
            HttpClientUtils.closeQuietly(httpResponse);
        }
        return message;
    }

    /**
     * @param config
     * @return
     */
    private String createAuthorization(Bill99Config config) {
        String authString = config.getMerchantId() + ":" + config.getAccountConfig().getPrivateCertPwd();
        return "Basic " + Base64.encodeBase64String(authString.getBytes());
    }
}
