package x.y.z.bill.adapter;

import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;
import x.y.z.bill.adapter.channel.dto.request.*;
import x.y.z.bill.adapter.channel.dto.response.*;

/**
 * 请求渠道接口
 */
public interface ChannelAdapterFacade {
    /**
     * 鉴权申请
     * 
     * @param dto
     * @return
     */
    ResponseDTO<AuthResponseDTO> applyAuth(AuthRequestDTO dto);

    /**
     * 鉴权确认
     * 
     * @param dto
     * @return
     */
    ResponseDTO<AuthConfirmResponseDTO> confirmAuth(AuthConfirmRequestDTO dto);

    /**
     * 查询鉴权
     * 
     * @param dto
     * @return
     */
    ResponseDTO<QueryAuthInfoResponseDTO> queryAuthInfo(AuthRequestDTO dto);

    /**
     * 取消鉴权
     *
     * @param request
     * @return
     */
    ResponseDTO<TreatyInfoDTO> cancelAuthInfo(AuthRequestDTO request);

    /**
     * 申请快捷支付
     * 
     * @param dto
     * @return
     */
    ResponseDTO<QuickPayResponseDTO> applyQuickPay(QuickPayRequestDTO dto);

    /**
     * 快捷支付确认
     * 
     * @param dto
     * @return
     */
    ResponseDTO<QuickPayConfirmResponseDTO> confirmQuickPay(QuickPayConfirmRequestDTO dto);

    /**
     * 代付
     * 
     * @param requestDTO
     * @return
     */
    ResponseDTO<WithdrawResponseDTO> withdraw(WithdrawRequestDTO requestDTO);

    /**
     * 查询支付订单
     * 
     * @param dto
     * @return
     */
    ResponseDTO<PayQueryResponseDTO> queryPayOrder(PayQueryRequestDTO dto);

    /**
     * 异步下载对账单
     * 
     * @param dto
     * @return
     */
    ResponseDTO<Boolean> downloadRecon(ReconDownloadRequestDTO dto);

    /**
     * 创建网银支付URL
     * 
     * @param request
     * @return
     */
    ResponseDTO<EBankApplyPayResponseDTO> generatePayUrl(EBankApplyPayRequestDTO request);

    /**
     * 从第三方支付收银台返回到商户
     * 
     * @param request
     * @return
     */
    ResponseDTO<PayQueryResponseDTO> payCallback(CallbackRequestDTO request);

    /**
     * 后台异步通知
     * 
     * @param request
     * @return
     */
    ResponseDTO<PayQueryResponseDTO> payNotify(PayNotifyRequestDTO request);

    /**
     * 工行生成签名
     * 
     * @param dto
     * @return
     */
    ResponseDTO<OnlineSignResponseDTO> onlineSign(OnlineSignRequestDTO dto);

    /**
     * 代付查询
     * 
     * @param request
     * @param channelRecevOrder
     * @return
     */
    ResponseDTO<WithdrawQueryRespDTO> withdrawQuery(WithdrawRequestDTO request, String channelRecevOrder);

    /**
     * 批量代付查询
     *
     * @param request
     * @return
     */
    ResponseDTO<WithdrawQueryRespDTO> withdrawQuery(BatchWithdrawQueryRequestDTO request);
}
