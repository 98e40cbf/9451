package x.y.z.bill.service.payment;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.y.z.bill.adapter.ChannelAdapterFacade;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;
import x.y.z.bill.adapter.channel.dto.request.AuthConfirmRequestDTO;
import x.y.z.bill.adapter.channel.dto.request.AuthRequestDTO;
import x.y.z.bill.adapter.channel.dto.response.AuthConfirmResponseDTO;
import x.y.z.bill.adapter.channel.dto.response.AuthResponseDTO;
import x.y.z.bill.adapter.channel.dto.response.ResponseDTO;
import x.y.z.bill.adapter.constant.ChannelCode;
import x.y.z.bill.command.ApplyQuickPayAgreementFrom;
import x.y.z.bill.command.ConfirmFrom;
import x.y.z.bill.constant.BusinessCode;
import x.y.z.bill.constant.SystemCode;
import x.y.z.bill.mapper.payment.ChannelQuickAgreementDAO;
import x.y.z.bill.model.payment.BankCardBin;
import x.y.z.bill.model.payment.BankCardInfo;
import x.y.z.bill.model.payment.ChannelQuickAgreement;
import x.y.z.bill.model.payment.PermissionRestrict;
import x.y.z.bill.util.SequenceUtils;
import io.alpha.service.BaseService;
import io.alpha.tx.annotation.TransMark;

/**
 * Created by yuanxw on 2016/7/12.
 */
@Service
@TransMark
public class QuickAgreementService extends BaseService {
    @Autowired
    private ChannelQuickAgreementDAO channelQuickAgreementDAO;
    @Autowired
    private BankCardBinService bankCardBinService;
    @Autowired
    private BankCodeDictService bankCodeDictService;
    @Autowired
    private BankCardInfoService bankCardInfoService;
    @Autowired
    private PermissionRestrictService permissionRestrictService;
    @Autowired
    private ChannelAdapterFacade channelAdapterFacade;

    /**
     * 申请开通快捷支付协议
     *
     * @param from
     * @param userId
     * @param userName
     * @param clientIp
     * @return
     */
    public ResponseDTO<AuthResponseDTO> applyQuickPayAgreement(ApplyQuickPayAgreementFrom from, Long userId,
            String userName, String clientIp) {
        String txnId = SequenceUtils.getSequence(SystemCode.PAYMENT, BusinessCode.PAYMENT_QUICK_PAY_AGREEMENT);
        logger.info("[{}] [支付系统] - [申请快捷支付] - [用户:{}]", txnId, userName);

        // 核验银行卡是否存在限制
        PermissionRestrict cardRestrict = permissionRestrictService.restrict(BusinessCode.PAYMENT_QUICK_PAY_AGREEMENT,
                from.getBankCardNo());
        if (cardRestrict != null) {
            return ResponseDTO.buildFail(null, "", cardRestrict.getTips());
        }
        PermissionRestrict userRestrict = permissionRestrictService.restrict(BusinessCode.PAYMENT_QUICK_PAY_AGREEMENT,
                userId);
        if (userRestrict != null) {
            return ResponseDTO.buildFail(null, "", cardRestrict.getTips());
        }

        // 核验卡BIN信息
        BankCardBin bin = bankCardBinService.recognition(from.getBankCardNo());
        if (bin == null) {
            return ResponseDTO.buildFail(null, "", "用户银行卡不能识别");
        }
        if (bin.getType() != null && bin.getType().intValue() == 1) {
            return ResponseDTO.buildFail(null, "", "暂不支持信用卡");
        }

        // 验证银行卡是否bei被他人使用
        if (bankCardInfoService.isOtherPeopleUseCard(from.getBankCardNo(), userId)) {
            return ResponseDTO.buildFail(null, "", "该卡已被他人使用");
        }
        // 判断该用户是否存在处理中的记录
        if (existQuickAgreementByHanding(userId)) {
            return ResponseDTO.buildFail(null, "", "用户快捷支付申请处理中，请等待");
        }

        String channelSendOrder = SequenceUtils.getSequence(SystemCode.PAYMENT,
                BusinessCode.PAYMENT_QUICK_PAY_AGREEMENT);

        BankCardInfo bankCardInfo = new BankCardInfo();
        bankCardInfo.setTxnId(txnId);
        bankCardInfo.setUserId(userId);
        bankCardInfo.setUserName(userName);
        bankCardInfo.setCardType(1);// 借记卡
        bankCardInfo.setBankCode(bin.getBankCode());
        bankCardInfo.setBankName(bankCodeDictService.mappingBankName(bin.getBankCode()));
        bankCardInfo.setBankCardNo(from.getBankCardNo());
        bankCardInfo.setIdCardNo(from.getIdCardNo());
        bankCardInfo.setRealName(from.getRealName());
        bankCardInfo.setMobile(from.getMobile());
        bankCardInfo.setStatus(-1);// 初始化状态
        bankCardInfo.setOrigin(from.getOrigin());
        bankCardInfo.setCreateTime(new Date());
        bankCardInfo.setLastModifyTime(new Date());
        bankCardInfo.setClientIp(clientIp);

        bankCardInfo = bankCardInfoService.save(bankCardInfo);

        // 保存一条快捷支付申请记录
        ChannelQuickAgreement channelQuickAgreement = new ChannelQuickAgreement();
        channelQuickAgreement.setTxnId(txnId);
        channelQuickAgreement.setChannelCode(ChannelCode.BILL99.getCode());
        channelQuickAgreement.setChannelName(ChannelCode.BILL99.getDesc());
        channelQuickAgreement.setChannelSendOrder(channelSendOrder);
        channelQuickAgreement.setBusinessType(1);// 独立鉴权
        channelQuickAgreement.setUserId(userId);
        channelQuickAgreement.setUserName(userName);
        channelQuickAgreement.setAmount(BigDecimal.ZERO);
        channelQuickAgreement.setStatus(-1);// 初始化状态
        channelQuickAgreement.setOrigin(from.getOrigin());
        channelQuickAgreement.setCreateTime(new Date());
        channelQuickAgreement.setLastModifyTime(new Date());
        channelQuickAgreement.setIdentityCode(String.valueOf(userId));
        channelQuickAgreement.setClientIp(clientIp);
        channelQuickAgreement.setBankCardId(bankCardInfo.getId());

        channelQuickAgreementDAO.insertSelective(channelQuickAgreement);

        TreatyInfoDTO treaty = new TreatyInfoDTO();
        treaty.setIdentityCode(channelQuickAgreement.getIdentityCode());
        treaty.setBankCardNo(bankCardInfo.getBankCardNo());
        treaty.setChannelBankCode(bankCardInfo.getBankCode());
        treaty.setIdCardNo(bankCardInfo.getIdCardNo());
        treaty.setMobile(bankCardInfo.getMobile());
        treaty.setRealName(bankCardInfo.getRealName());
        AuthRequestDTO dto = new AuthRequestDTO();
        dto.setTxnId(txnId);
        dto.setTreatyInfo(treaty);
        dto.setChannelSendOrder(channelSendOrder);
        dto.setChannelCode(ChannelCode.BILL99.getCode());
        ResponseDTO<AuthResponseDTO> response = channelAdapterFacade.applyAuth(dto);
        if (ResponseDTO.Status.FAILED.equals(response.getStatus())) {
            updateChannelQuickAgreementFailed(txnId, response.getResponseCode(), response.getResponseMessage());
        }
        if (ResponseDTO.Status.SUCCESS.equals(response.getStatus())) {
            updateChannelQuickAgreementSucceed(txnId, response.getResponseCode(), response.getResponseMessage());
        }
        return response;
    }

    public void updateChannelQuickAgreementSucceed(String txnId, String responseCode, String responseMessage) {
        ChannelQuickAgreement channelQuickAgreement = channelQuickAgreementDAO.queryByTxnId(txnId);
        int result = channelQuickAgreementDAO.updateChannelQuickAgreementSucceed(txnId, responseCode, responseMessage,
                channelQuickAgreement.getVersion());
        if (result == 1) {
            bankCardInfoService.updateBankCardInfoSucceed(txnId);
            // TODO 添加用户实名信息
        }
    }

    /**
     * 更新渠道快捷协议处理失败
     *
     * @param txnId
     * @param responseCode
     * @param responseMessage
     */
    public void updateChannelQuickAgreementFailed(String txnId, String responseCode, String responseMessage) {
        ChannelQuickAgreement channelQuickAgreement = channelQuickAgreementDAO.queryByTxnId(txnId);
        int result = channelQuickAgreementDAO.updateChannelQuickAgreementFailed(txnId, responseCode, responseMessage,
                channelQuickAgreement.getVersion());
        if (result == 1) {
            bankCardInfoService.updateBankCardInfoFailed(txnId);
        }
    }

    private boolean existQuickAgreementByHanding(Long userId) {
        return channelQuickAgreementDAO.countQuickAgreementByUser(userId, 3) > 0 ? true : false;
    }

    public ResponseDTO<AuthConfirmResponseDTO> confirmQuickPayAgreement(String txnId, ConfirmFrom from) {
        ChannelQuickAgreement channelQuickAgreement = channelQuickAgreementDAO.queryByTxnId(txnId);
        if (channelQuickAgreement == null) {
            return ResponseDTO.buildFail(null, "", "需要确认的业务流水不存在");
        }
        // 如果已经成功，直接返回成功结果
        if (Integer.valueOf(1).equals(channelQuickAgreement.getStatus())) {
            return ResponseDTO.buildSuccess(null, "", "");
        }
        int cnt = channelQuickAgreementDAO.updateChannelQuickAgreementHanding(channelQuickAgreement.getTxnId(),
                channelQuickAgreement.getVersion());
        if (cnt == 1) {
            bankCardInfoService.updateBankCardInfoHanding(channelQuickAgreement.getTxnId());
            BankCardInfo bankCardInfo = bankCardInfoService.queryByTxnId(channelQuickAgreement.getTxnId());

            TreatyInfoDTO treaty = new TreatyInfoDTO();
            treaty.setIdentityCode(channelQuickAgreement.getIdentityCode());
            treaty.setBankCardNo(bankCardInfo.getBankCardNo());
            treaty.setChannelBankCode(bankCardInfo.getBankCode());
            treaty.setIdCardNo(bankCardInfo.getIdCardNo());
            treaty.setMobile(bankCardInfo.getMobile());
            treaty.setRealName(bankCardInfo.getRealName());
            AuthConfirmRequestDTO dto = new AuthConfirmRequestDTO();
            dto.setTxnId(txnId);
            dto.setTreatyInfo(treaty);
            dto.setChannelSendOrder(channelQuickAgreement.getChannelSendOrder());
            dto.setChannelCode(ChannelCode.BILL99.getCode());
            dto.setSecurityCode(from.getSecurityCode());
            ResponseDTO<AuthConfirmResponseDTO> response = channelAdapterFacade.confirmAuth(dto);
            if (ResponseDTO.Status.SUCCESS.equals(response.getStatus())) {
                updateChannelQuickAgreementSucceed(txnId, response.getResponseCode(), response.getResponseMessage());
            } else if (ResponseDTO.Status.FAILED.equals(response.getStatus())) {
                updateChannelQuickAgreementFailed(txnId, response.getResponseCode(), response.getResponseMessage());
            }
            return response;
        }
        return ResponseDTO.buildTrading(null, "", "协议确认结果已经被处理");
    }

    public ChannelQuickAgreement queryUserQuickAgreementBySucceed(Long userId) {
        return channelQuickAgreementDAO.queryUserQuickAgreementBySucceed(userId);
    }
}
