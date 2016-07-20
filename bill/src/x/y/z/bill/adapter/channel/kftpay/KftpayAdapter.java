package x.y.z.bill.adapter.channel.kftpay;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.y.z.bill.adapter.channel.Adapter;
import x.y.z.bill.adapter.channel.GenericAdapter;
import x.y.z.bill.adapter.channel.dto.ChannelBaseDTO;
import x.y.z.bill.adapter.channel.dto.PayBilDTO;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;
import x.y.z.bill.adapter.channel.dto.WithdrawStatus;
import x.y.z.bill.adapter.channel.dto.request.*;
import x.y.z.bill.adapter.channel.dto.response.*;
import x.y.z.bill.adapter.channel.kftpay.dto.ReconResponseDTO;
import x.y.z.bill.adapter.config.ConfigManager;
import x.y.z.bill.adapter.constant.BillStatus;
import x.y.z.bill.adapter.constant.ChannelCode;
import x.y.z.bill.adapter.constant.ReconBusinessType;
import x.y.z.bill.adapter.util.BigDecimalDeserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.lycheepay.gateway.client.dto.*;
import com.lycheepay.gateway.client.dto.gbp.*;

@Service(value = "adapter_100004")
public class KftpayAdapter extends GenericAdapter implements Adapter {

    @Autowired
    private ConfigManager configManager;

    /**
     * 获取渠道编码
     *
     * @return
     */
    @Override
    public String getChannelCode() {
        return ChannelCode.KFTPAY.getCode();
    }

    /**
     * 渠道名称
     *
     * @return
     */
    @Override
    public String getChannelName() {
        return ChannelCode.KFTPAY.getDesc();
    }

    /**
     * 获取渠道配置
     *
     * @param dto
     * @return
     */
    @Override
    public KftpayConfig getConfig(ChannelBaseDTO dto) {
        return (KftpayConfig) configManager.getConfig(dto.getChannelCode());
    }

    /**
     * 鉴权申请
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseDTO<AuthResponseDTO> applyAuth(AuthRequestDTO dto) {
        KftpayConfig config = getConfig(dto);
        TreatyApplyResultDTO result = null;
        try {
            final TreatyApplyDTO treatyApplyDTO = KftBuilder.buildTreatyApplyDTO(dto, config);
            treatyApplyDTO.setService(config.getAuthTreatyService().getServiceName());
            logger.info("订单号[{}]快付通协议收款申请请求信息为:{}", dto.getTxnId(), treatyApplyDTO);
            result = config.getGbpService().treatyCollectApply(treatyApplyDTO);
            logger.info("订单号[{}]快付通协议收款申请返回信息为:{}", dto.getTxnId(), result);
        } catch (Exception e) {
            logger.error(String.format("订单号[%s]快付通协议收款申请出现异常:", dto.getTxnId()), e);
        }

        if (result == null) {
            return ResponseDTO.buildException("NET_ERROR", "与服务器通信出错");
        }
        AuthResponseDTO data = new AuthResponseDTO();
        data.setChannelSendOrder(dto.getChannelSendOrder());
        data.setTreatyInfo(dto.getTreatyInfo());
        data.setTxnId(dto.getTxnId());
        if (KftpayTradeStatus.SUCCESS.getCode() == result.getStatus()) {
            logger.info("订单号[{}]快付通协议收款申请返回成功", dto.getTxnId());
            data.setChannelToken(result.getSmsSeq());
            return ResponseDTO.buildSuccess(data, result.getErrorCode(), result.getFailureDetails());
        } else {
            logger.info("订单号[{}]快付通协议收款申请返回失败[{} , {}]", dto.getTxnId(), result.getErrorCode(),
                    result.getFailureDetails());
            return ResponseDTO.buildFail(data, result.getErrorCode(), result.getFailureDetails());
        }
    }

    /**
     * 鉴权确认
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseDTO<AuthConfirmResponseDTO> confirmAuth(AuthConfirmRequestDTO dto) {
        KftpayConfig config = getConfig(dto);
        TreatyConfirmResultDTO result = null;
        try {
            final TreatyConfirmDTO treatyConfirmDTO = KftBuilder.builderConfirmTreatyApplyDTO(dto, config);
            treatyConfirmDTO.setService(config.getConfirmAuthTreatyService().getServiceName());
            logger.info("订单号[{}]快付通确认协议收款申请请求信息为:{}", dto.getTxnId(), treatyConfirmDTO);
            result = config.getGbpService().confirmTreatyCollectApply(treatyConfirmDTO);
            logger.info("订单号[{}]快付通确认协议收款申请返回信息为:{}", dto.getTxnId(), result);
        } catch (Exception e) {
            logger.error(String.format("订单号[%s]快付通确认协议收款申请出现异常:", dto.getTxnId()), e);
        }
        if (result == null) {
            return ResponseDTO.buildException("NET_ERROR", "与服务器通信出错");
        }

        AuthConfirmResponseDTO data = new AuthConfirmResponseDTO(dto);

        if (KftpayTradeStatus.SUCCESS.getCode() == result.getStatus()) {
            data.getTreatyInfo().setIdentityCode(result.getTreatyId());
            logger.info("订单号[{}]快付通协议收款确认成功", dto.getTxnId());
            return ResponseDTO.buildSuccess(data, result.getErrorCode(), result.getFailureDetails());
        } else {
            logger.info("订单号[{}]快付通协议收款确认失败[{} , {}]", dto.getTxnId(), result.getErrorCode(),
                    result.getFailureDetails());
            return ResponseDTO.buildFail(data, result.getErrorCode(), result.getFailureDetails());
        }
    }

    /**
     * 查询鉴权
     *
     * @param request
     * @return
     */
    @Override
    public ResponseDTO<QueryAuthInfoResponseDTO> queryAuthInfo(AuthRequestDTO request) {
        if (StringUtils.isBlank(request.getTreatyInfo().getIdentityCode())) {
            // 快付通渠道暂不支持协议号为空的查询
            return ResponseDTO.buildException("REQUEST_ERROR", "协议号不允许为空.");
        }
        KftpayConfig config = getConfig(request);
        SearchTreatyResultDTO result = null;
        try {
            final SearchTreatyDTO dto = new SearchTreatyDTO();
            dto.setMerchantId(config.getMerchantId());
            dto.setOrderNo(request.getChannelSendOrder());// 协议查询的订单号 与协议代扣申请的订单编号一致
            dto.setTreatyNo(request.getTreatyInfo().getIdentityCode());
            dto.setService(config.getQueryTreatyService().getServiceName());
            dto.setVersion(config.getVersion());
            logger.info("订单号[{}]快付通查询协议收款信息请求信息为:{}", request.getTxnId(), dto);
            result = config.getGbpService().queryTreatyInfo(dto);
            logger.info("订单号[{}]快付通查询协议收款信息返回信息为:{}", request.getTxnId(), result);
            if (result == null) {
                QueryAuthInfoResponseDTO data = new QueryAuthInfoResponseDTO(request.getTreatyInfo(), true);
                return ResponseDTO.build(ResponseDTO.Status.UNCERTAINTY, data, "null", "返回数据为空");
            } else if (TreatyStatus.VALID.getCode() == result.getStatus()) {
                QueryAuthInfoResponseDTO data = new QueryAuthInfoResponseDTO(request.getTreatyInfo(), true);
                return ResponseDTO.buildSuccess(data, result.getErrorCode(), result.getFailureDetails());
            } else {
                QueryAuthInfoResponseDTO data = new QueryAuthInfoResponseDTO(request.getTreatyInfo(), false);
                return ResponseDTO.buildSuccess(data, result.getErrorCode(), result.getFailureDetails());
            }
        } catch (Exception e) {
            logger.error(String.format("订单号[%s]快付通查询协议收款信息出现异常:", request.getTxnId()), e);
            return ResponseDTO.buildException("NET_ERROR", "与服务器通信出错");
        }
    }

    /**
     * 取消鉴权
     *
     * @param request
     * @return
     */
    @Override
    public ResponseDTO<TreatyInfoDTO> cancelAuthInfo(AuthRequestDTO request) {
        if (StringUtils.isBlank(request.getTreatyInfo().getIdentityCode())) {
            return ResponseDTO.buildException("REQUEST_ERROR", "协议号不允许为空.");
        }
        KftpayConfig config = getConfig(request);
        CancelTreatyResultDTO result = null;
        try {
            final CancelTreatyDTO dto = new CancelTreatyDTO();
            dto.setMerchantId(config.getMerchantId());
            dto.setOrderNo(request.getChannelSendOrder());// 协议查询的订单号 与协议代扣申请的订单编号一致
            dto.setTreatyNo(request.getTreatyInfo().getIdentityCode());
            dto.setService(config.getCancelTreatyService().getServiceName());
            dto.setVersion(config.getVersion());
            logger.info("订单号[{}]快付通取消协议收款请求信息为:{}", request.getTxnId(), dto);
            result = config.getGbpService().cancelTreatyInfo(dto);
            logger.info("订单号[{}]快付通取消协议收款返回信息为:{}", request.getTxnId(), result);
            if (result != null) {
                if (KftpayTradeStatus.SUCCESS.getCode() == result.getStatus()) {
                    logger.info("[{}][快付通] -> [解绑协议收款] 用户[{}] 结果[成功]", request.getTxnId(),
                            request.getTreatyInfo().getIdentityCode());
                    return ResponseDTO.buildSuccess(request.getTreatyInfo(), result.getErrorCode(),
                            result.getFailureDetails());
                } else if (KftpayTradeStatus.FAIL.getCode() == result.getStatus()) {
                    logger.info("[{}][快付通] -> [解绑协议收款] 用户[{}] 结果[失败]", request.getTxnId(),
                            request.getTreatyInfo().getIdentityCode());
                    return ResponseDTO.buildFail(request.getTreatyInfo(), result.getErrorCode(),
                            result.getFailureDetails());
                } else {
                    return ResponseDTO.build(ResponseDTO.Status.UNCERTAINTY, request.getTreatyInfo(),
                            result.getErrorCode(), result.getFailureDetails());
                }
            } else {
                return ResponseDTO.build(ResponseDTO.Status.UNCERTAINTY, request.getTreatyInfo(), "null", "返回数据为空");
            }
        } catch (Exception e) {
            logger.error(String.format("订单号[%s]快付通取消协议收款出现异常:", request.getTxnId()), e);
            return ResponseDTO.buildException("NET_ERROR", "与服务器通信出错");
        }
    }

    /**
     * 快捷支付确认
     *
     * @param request
     * @return
     */
    @Override
    public ResponseDTO<QuickPayConfirmResponseDTO> confirmQuickPay(QuickPayConfirmRequestDTO request) {
        QuickPayConfirmResponseDTO data = new QuickPayConfirmResponseDTO();
        data.setChannelSendOrder(request.getChannelSendOrder());
        data.setTreatyInfo(request.getTreatyInfo());

        KftpayConfig config = getConfig(request);
        if (BigDecimal.ZERO.compareTo(request.getAmount()) >= 0) {
            return ResponseDTO.buildFail(data, "REQUEST_ERROR", "请求金额必须大于0");
        }
        final TreatyCollectDTO treatyCollectDTO = KftBuilder.builderTreatyCollectDTO(request, config);
        logger.info("订单号[{}]快付通协议收款请求信息为:{}", request.getTxnId(), treatyCollectDTO);
        TreatyCollectResultDTO result = null;
        try {
            result = config.getGbpService().treatyCollect(treatyCollectDTO);
            logger.info("订单号[{}]快付通协议收款返回信息为:{}", request.getTxnId(), result);
            if (result != null) {
                if (KftpayTradeStatus.SUCCESS.getCode() == result.getStatus()) {
                    return ResponseDTO.buildSuccess(data, result.getErrorCode(), result.getFailureDetails());
                } else if (KftpayTradeStatus.FAIL.getCode() == result.getStatus()) {
                    return ResponseDTO.buildFail(data, result.getErrorCode(), result.getFailureDetails());
                }
            }
            return ResponseDTO.build(ResponseDTO.Status.UNCERTAINTY, data, null, null);
        } catch (Exception e) {
            logger.error("订单号[{}]处理快付通协议收款出错.", request.getTxnId(), e);
            return ResponseDTO.buildException("NET_ERROR", "与服务器通信出错");
        } finally {
            logger.info("订单号[{}]处理快付通协议收款返回结果为:{}", request.getTxnId(), result);
        }
    }

    /**
     * 代付
     *
     * @param requestDTO
     * @return
     */
    @Override
    public ResponseDTO<WithdrawResponseDTO> withdraw(WithdrawRequestDTO requestDTO) {
        WithdrawResponseDTO data = new WithdrawResponseDTO();
        data.setTreatyInfo(requestDTO.getTreatyInfo());
        KftpayConfig config = getConfig(requestDTO);
        try {
            final PayToBankAccountDTO payToBankAccountDTO = KftBuilder.builderPayToBankAccountDTO(requestDTO, config);
            logger.info("订单号[{}]快付通提现请求信息为:{}", requestDTO.getTxnId(), payToBankAccountDTO);
            TradeResultDTO result = config.getKftService().payToBankAccount(payToBankAccountDTO);
            logger.info("订单号[{}]快付通提现返回信息为:{}", requestDTO.getTxnId(), result);
            if (result != null) {
                if (KftpayTradeStatus.SUCCESS.getCode() == result.getStatus()) {
                    logger.info("订单号[{}]快付通提现成功.", requestDTO.getTxnId());
                    return ResponseDTO.buildSuccess(data, result.getErrorCode(), result.getFailureDetails());
                } else if (KftpayTradeStatus.FAIL.getCode() == result.getStatus()) {
                    logger.info("订单号[{}]快付通提现失败[{}:{}].", requestDTO.getTxnId(), result.getErrorCode(),
                            result.getFailureDetails());
                    return ResponseDTO.buildFail(data, result.getErrorCode(), result.getFailureDetails());
                } else {
                    logger.info("订单号[{}]快付通提现未知[{}:{}].", requestDTO.getTxnId(), result.getErrorCode(),
                            result.getFailureDetails());
                    return ResponseDTO.build(ResponseDTO.Status.UNCERTAINTY, data, result.getErrorCode(),
                            result.getFailureDetails());
                }

            }
            return ResponseDTO.build(ResponseDTO.Status.UNCERTAINTY, data, "NET_ERROR", "解析返回结果为空");
        } catch (Exception e) {
            logger.error(String.format("订单号[%s]快付通提现出现异常:", requestDTO.getTxnId()), e);
            return ResponseDTO.build(ResponseDTO.Status.UNCERTAINTY, data, "NET_ERROR", e.getMessage());
        }
    }

    /**
     * 查询支付订单
     *
     * @param request
     * @return
     */
    @Override
    public ResponseDTO<PayQueryResponseDTO> queryPayOrder(PayQueryRequestDTO request) {
        KftpayConfig config = getConfig(request);
        PayQueryResponseDTO data = new PayQueryResponseDTO();
        data.setTreatyInfo(request.getTreatyInfo());
        data.setChannelSendOrder(request.getChannelSendOrder());
        try {
            final QueryTradeRecordDTO dto = new QueryTradeRecordDTO();
            dto.setMerchantId(config.getMerchantId());
            dto.setProductNo(config.getTradeQueryService().getProductNo());
            dto.setService(config.getTradeQueryService().getServiceName());
            dto.setVersion(config.getVersion());
            String orgTradeProductNo = config.getRechargeService().getProductNo();
            dto.addCriteria(dto.new Criteria(orgTradeProductNo, "", request.getChannelSendOrder()));

            logger.info("订单号[{}]快付通交易查询请求信息为:{}", request.getTxnId(), dto);

            QueryTradeRecordResultDTO result = config.getKftService().queryTradeRecord(dto);

            logger.info("订单号[{}]快付通交易查询返回信息为:{}", request.getTxnId(), result);
            if (KftBuilder.checkChannelNoData(result)) {

                logger.info("订单号[{}]快付通交易查询结果：渠道无数据[{}:{}]", request.getTxnId(), result.getErrorCode(),
                        result.getFailureDetails());
                return ResponseDTO.buildFail(data, result.getErrorCode(), result.getFailureDetails());

            } else if (KftBuilder.checkSuccess(result, request.getChannelSendOrder())) {

                logger.info("订单号[{}]快付通交易查询结果：支付成功[{}]", request.getTxnId(), result.getDetails());
                return ResponseDTO.buildSuccess(data, result.getErrorCode(), result.getFailureDetails());

            } else if (KftBuilder.checkFail(result, request.getChannelSendOrder())
                    || KftBuilder.checkRefund(result, request.getChannelSendOrder())) {
                logger.info("订单号[{}]快付通交易查询结果：支付失败[{}]", request.getTxnId(), result.getDetails());
                return ResponseDTO.buildFail(data, result.getErrorCode(), result.getFailureDetails());

            } else {

                logger.info("订单号[{}]快付通交易查询结果未知[{}]", request.getTxnId(), result);
                return ResponseDTO.build(ResponseDTO.Status.UNCERTAINTY, data, result.getErrorCode(),
                        result.getFailureDetails());

            }

        } catch (Exception e) {
            logger.error("订单号[{}]快付通交易查询出现异常:", request.getTxnId(), e);
            return ResponseDTO.buildException(data, "NET_ERROR", e.getMessage());
        }
    }

    /**
     * 单笔代付查询
     *
     * @param request
     * @param channelRecevOrder
     * @return
     */
    @Override
    public ResponseDTO<WithdrawQueryRespDTO> withdrawQuery(WithdrawRequestDTO request, String channelRecevOrder) {
        KftpayConfig config = getConfig(request);

        WithdrawQueryRespDTO.WithdrawElement dataResult = new WithdrawQueryRespDTO.WithdrawElement();
        dataResult.setChannelOrder(channelRecevOrder);

        WithdrawQueryRespDTO data = new WithdrawQueryRespDTO();
        data.setChannelCode(request.getChannelCode());
        data.setPackageId(request.getChannelSendOrder());
        try {
            final QueryTradeRecordDTO dto = new QueryTradeRecordDTO();
            dto.setMerchantId(config.getMerchantId());
            dto.setProductNo(config.getTradeQueryService().getProductNo());
            dto.setService(config.getTradeQueryService().getServiceName());
            dto.setVersion(config.getVersion());
            String orgTradeProductNo = config.getWithdrawService().getProductNo();
            dto.addCriteria(dto.new Criteria(orgTradeProductNo, "", request.getChannelSendOrder()));

            logger.info("订单号[{}]快付通交易查询请求信息为:{}", request.getTxnId(), dto);

            QueryTradeRecordResultDTO result = config.getKftService().queryTradeRecord(dto);

            logger.info("订单号[{}]快付通交易查询返回信息为:{}", request.getTxnId(), result);

            if (KftBuilder.checkChannelNoData(result)) {

                data.setChannelNoData(true);
                logger.info("订单号[{}]快付通交易查询结果：渠道无数据[{}:{}]", request.getTxnId(), result.getErrorCode(),
                        result.getFailureDetails());
                return ResponseDTO.buildFail(data, result.getErrorCode(), result.getFailureDetails());

            } else if (KftBuilder.checkSuccess(result, request.getChannelSendOrder())) {

                dataResult.setStatus(WithdrawStatus.SUC);
                logger.info("订单号[{}]快付通交易查询结果：支付成功[{}]", request.getTxnId(), result.getDetails());
                return ResponseDTO.buildSuccess(data, result.getErrorCode(), result.getFailureDetails());

            } else if (KftBuilder.checkFail(result, request.getChannelSendOrder())) {

                dataResult.setStatus(WithdrawStatus.FAIL);
                logger.info("订单号[{}]快付通交易查询结果：支付失败[{}]", request.getTxnId(), result.getDetails());
                return ResponseDTO.buildFail(data, result.getErrorCode(), result.getFailureDetails());

            } else if (KftBuilder.checkRefund(result, request.getChannelSendOrder())) {

                dataResult.setStatus(WithdrawStatus.RET_TICKET);
                logger.info("订单号[{}]快付通交易查询结果：退票[{}]", request.getTxnId(), result.getDetails());
                return ResponseDTO.buildFail(data, result.getErrorCode(), result.getFailureDetails());

            } else {
                dataResult.setStatus(WithdrawStatus.HOLDING);
                logger.info("订单号[{}]快付通交易查询结果未知[{}]", request.getTxnId(), result);
                return ResponseDTO.build(ResponseDTO.Status.UNCERTAINTY, data, result.getErrorCode(),
                        result.getFailureDetails());

            }

        } catch (Exception e) {
            logger.error("订单号[{}]快付通交易查询出现异常:", request.getTxnId(), e);
            return ResponseDTO.buildException(data, "NET_ERROR", e.getMessage());
        } finally {
            data.getWithdrawElementList().add(dataResult);
        }
    }

    /**
     * 异步下载对账单
     * 
     * @param dto
     * @return
     * @author {高磊} 2016年4月6日 下午5:22:02
     */
    @Override
    public ResponseDTO<Boolean> downloadRecon(ReconDownloadRequestDTO dto) {
        KftpayConfig config = getConfig(dto);
        // 1.组装请求参数
        QueryReconResultRequestDTO requestDTO = new QueryReconResultRequestDTO();
        requestDTO.setMerchantId(config.getMerchantId());
        requestDTO.setProductNo(config.getReconQueryService().getProductNo());
        requestDTO.setService(config.getReconQueryService().getServiceName());
        requestDTO.setVersion(config.getVersion());
        String orgTradeProductNo = "";
        if (ReconBusinessType.RECHARGE == dto.getBsType()) {
            orgTradeProductNo = config.getRechargeService().getProductNo();
        } else if (ReconBusinessType.WITHDRAW == dto.getBsType()) {
            orgTradeProductNo = config.getWithdrawService().getProductNo();
        }
        // 对账结果查询条件 格式由originalProductNo ,startDate(YYYYMMDD),endDate(YYYYMMDD)组成，一组条件内以逗号“，”分隔，多组条件间以竖线“|”分隔；
        String queryCriteria = orgTradeProductNo + "," + DateFormatUtils.format(dto.getBillDate(), "yyyyMMdd") + ","
                + DateFormatUtils.format(dto.getBillDate(), "yyyyMMdd");
        requestDTO.setQueryCriteria(queryCriteria);

        File file = new File(
                config.getReconQueryService().getDownloadDirectory() + System.currentTimeMillis() + ".json");
        requestDTO.setDownloadFile(file);
        // 2.发送请求
        QueryBatchFileResultDTO response = null;
        logger.info("订单号[{}]快付通对账查询请求信息为:{}", dto.getTxnId(), requestDTO);
        try {
            response = config.getKftService().queryReconResult(requestDTO);
            logger.info("订单号[{}]快付通对账查询返回信息为:{}", dto.getTxnId(), response);
        } catch (Exception e) {
            logger.error("订单号[{}]快付通对账查询出现异常:", dto.getTxnId(), e);
            return ResponseDTO.buildFail(false, "REQUEST_ERROR", "请求出现异常");
        }
        // 3.解析返回的对象
        PayBilDTO payBillDTO = dealReconResult(response, dto.getBsType());
        payBillDTO.setChannelCode(dto.getChannelCode());
        // payBillDTO.setAccountCode(dto.getAccountCode());
        payBillDTO.setBsType(dto.getBsType());
        // 4.持久化文件
        try {
            // reconService.saveReconFile(payBillDTO, dto.getBillDate());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseDTO.buildFail(false, "SAVETOFILE_ERROR", e.getMessage());
        }
        return ResponseDTO.buildSuccess(true, "RECON_SUCCESS", "对账成功");
    }

    /**
     *
     * @param result
     * @param bsType
     * @return
     */
    private PayBilDTO dealReconResult(QueryBatchFileResultDTO result, ReconBusinessType bsType) {
        PayBilDTO response = new PayBilDTO();
        try {
            if (result != null && StringUtils.isNotBlank(result.getTotalCount())
                    && Integer.parseInt(result.getTotalCount()) > 0) {
                List<String> fileList = FileUtils.readLines(result.getDownloadFile());
                for (String reconLine : fileList) {
                    ReconResponseDTO reconResponseDTO = JSON.parseObject(reconLine, ReconResponseDTO.class,
                            BigDecimalDeserializer.customerParserConfigInstance, JSON.DEFAULT_PARSER_FEATURE,
                            new Feature[0]);
                    /**
                     * {"amount":"300,000","checkDate":"20160428","currency":"CNY","errorCode":"","failureDetails":"",
                     * "kftTradeTime":"2016-04-27 22:01:39",
                     * "orderNo":"844505160427220139194688002687","payeeBankAccountNo":"","payerBankAccountNo":
                     * "******6471","productNo":"2ACB0BXN","status":"1"}
                     */
                    PayBilDTO.Row row = new PayBilDTO.Row();
                    if (reconResponseDTO != null) {
                        row.setBankName("");
                        // 1-成功，2-失败，3-处理中
                        if (KftpayTradeStatus.SUCCESS.getCode() == reconResponseDTO.getStatus()) {
                            row.setBillStatus(BillStatus.OK);
                        } else if (KftpayTradeStatus.FAIL.getCode() == reconResponseDTO.getStatus()) {
                            row.setBillStatus(BillStatus.FAIL);
                        } else if (KftpayTradeStatus.IN_PROCESS.getCode() == reconResponseDTO.getStatus()) {
                            row.setBillStatus(BillStatus.DOING);
                        }
                        row.setChannelReceiveOrder("");
                        row.setChannelSendOrder(reconResponseDTO.getOrderNo());
                        row.setFeeAmount(BigDecimal.ZERO);
                        row.setPayAmount(reconResponseDTO.getAmount());
                        row.setPayResultDesc(reconResponseDTO.getFailureDetails());
                        row.setPayTime(reconResponseDTO.getKftTradeTime());
                        row.setRealAmount(reconResponseDTO.getAmount());
                        if (bsType == ReconBusinessType.RECHARGE) {
                            row.setBisCode(ReconBusinessType.QUICK_PAY);
                        } else {
                            row.setBisCode(ReconBusinessType.WITHDRAW);
                        }
                    }
                    response.getRowList().add(row);
                }
            }
        } catch (Exception e) {
            logger.error("[快付通]解析对账单出现异常:", e);
        }
        return response;
    }

}
