/*******************************************************************************
 * Create on 2016年1月7日 上午9:50:38
 * Copyright (c) 2014 深圳市小牛电子商务有限公司版权所有. 粤ICP备13089339号
 * 注意：本内容仅限于深圳市小牛电子商务有限公司内部传阅，禁止外泄以及用于其他商业目的!
 ******************************************************************************/
package x.y.z.bill.adapter.channel;

import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;
import x.y.z.bill.adapter.channel.dto.request.*;
import x.y.z.bill.adapter.channel.dto.response.*;
import io.alpha.service.BaseService;

public abstract class GenericAdapter extends BaseService implements Adapter {

    @Override
    public ResponseDTO<OnlineSignResponseDTO> onlineSign(OnlineSignRequestDTO dto) {
        return this.buildDefaultResult();
    }

    /**
     * 鉴权申请
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseDTO<AuthResponseDTO> applyAuth(AuthRequestDTO dto) {
        return this.buildDefaultResult();
    }

    /**
     * 鉴权确认
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseDTO<AuthConfirmResponseDTO> confirmAuth(AuthConfirmRequestDTO dto) {
        return this.buildDefaultResult();
    }

    /**
     * 查询鉴权
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseDTO<QueryAuthInfoResponseDTO> queryAuthInfo(AuthRequestDTO dto) {
        return this.buildDefaultResult();
    }

    /**
     * 取消鉴权
     * 
     * @param request
     * @return
     */
    @Override
    public ResponseDTO<TreatyInfoDTO> cancelAuthInfo(AuthRequestDTO request) {
        return this.buildDefaultResult();
    }

    /**
     * 申请快捷支付
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseDTO<QuickPayResponseDTO> applyQuickPay(QuickPayRequestDTO dto) {
        return this.buildDefaultResult();
    }

    /**
     * 快捷支付确认
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseDTO<QuickPayConfirmResponseDTO> confirmQuickPay(QuickPayConfirmRequestDTO dto) {
        return this.buildDefaultResult();
    }

    /**
     * 查询支付订单
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseDTO<PayQueryResponseDTO> queryPayOrder(PayQueryRequestDTO dto) {
        return this.buildDefaultResult();
    }

    /**
     * 异步下载对账单
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseDTO<Boolean> downloadRecon(ReconDownloadRequestDTO dto) {
        return this.buildDefaultResult();
    }

    /**
     * 代付
     *
     * @param requestDTO
     * @return
     */
    // TODO 代付接口考虑和批量代付接口进行合并
    @Override
    public ResponseDTO<WithdrawResponseDTO> withdraw(WithdrawRequestDTO requestDTO) {
        return this.buildDefaultResult();
    }

    protected final <T> ResponseDTO<T> buildDefaultResult() {
        return ResponseDTO.buildException(null, "NO_IMPLEMENTATION_SERVICE", "no implementation service");
    }

    /**
     * 创建网银支付URL
     *
     * @param request
     * @return
     */
    @Override
    public ResponseDTO<EBankApplyPayResponseDTO> generatePayUrl(EBankApplyPayRequestDTO request) {
        return this.buildDefaultResult();
    }

    /**
     * 批量代付
     *
     * @param request
     * @return
     */
    @Override
    public ResponseDTO<BatchWithdrawResponseDTO> withdraw(BatchWithdrawRequestDTO request) {
        return this.buildDefaultResult();
    }

    /**
     * 批量代付查询
     * 
     * @param request
     * @return
     */
    @Override
    public ResponseDTO<WithdrawQueryRespDTO> withdrawQuery(BatchWithdrawQueryRequestDTO request) {
        return this.buildDefaultResult();
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
        return this.buildDefaultResult();
    }

    @Override
    public ResponseDTO<PayQueryResponseDTO> payCallback(CallbackRequestDTO request) {
        return this.buildDefaultResult();
    }

    @Override
    public ResponseDTO<PayQueryResponseDTO> payNotify(PayNotifyRequestDTO request) {
        return this.buildDefaultResult();
    }
}
