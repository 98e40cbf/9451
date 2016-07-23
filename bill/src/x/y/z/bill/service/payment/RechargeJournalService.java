package x.y.z.bill.service.payment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.y.z.bill.adapter.ChannelAdapterFacade;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;
import x.y.z.bill.adapter.channel.dto.request.QuickPayConfirmRequestDTO;
import x.y.z.bill.adapter.channel.dto.request.QuickPayRequestDTO;
import x.y.z.bill.adapter.channel.dto.response.QuickPayConfirmResponseDTO;
import x.y.z.bill.adapter.channel.dto.response.QuickPayResponseDTO;
import x.y.z.bill.adapter.channel.dto.response.ResponseDTO;
import x.y.z.bill.adapter.constant.ChannelCode;
import x.y.z.bill.adapter.util.SerialContext;
import x.y.z.bill.command.ApplyQuickPayFrom;
import x.y.z.bill.command.ConfirmFrom;
import x.y.z.bill.constant.BusinessCode;
import x.y.z.bill.constant.SystemCode;
import x.y.z.bill.mapper.payment.RechargeJournalDAO;
import x.y.z.bill.model.payment.BankCardInfo;
import x.y.z.bill.model.payment.ChannelQuickAgreement;
import x.y.z.bill.model.payment.PermissionRestrict;
import x.y.z.bill.model.payment.RechargeJournal;
import x.y.z.bill.util.SequenceUtils;
import io.alpha.core.dto.PageDTO;
import io.alpha.service.BaseService;
import io.alpha.tx.annotation.TransMark;

/**
 * Created by yuanxw on 2016/7/18.
 */
@Service
@TransMark
public class RechargeJournalService extends BaseService {

    @Autowired
    private QuickAgreementService quickAgreementService;
    @Autowired
    private BankCardInfoService bankCardInfoService;
    @Autowired
    private ChannelAdapterFacade channelAdapterFacade;
    @Autowired
    private RechargeJournalDAO rechargeJournalDAO;
    @Autowired
    private PermissionRestrictService permissionRestrictService;

    public ResponseDTO<QuickPayResponseDTO> applyQuickPay(ApplyQuickPayFrom from, Long userId, String userName,
            String clientIp) {


        PermissionRestrict userRestrict = permissionRestrictService.restrict(BusinessCode.PAYMENT_QUICK_PAY,
                userId);
        if (userRestrict != null) {
            return ResponseDTO.buildFail(null, "", userRestrict.getTips());
        }

        String txnId = SequenceUtils.getSequence(SystemCode.PAYMENT, BusinessCode.PAYMENT_QUICK_PAY);
        SerialContext.set(txnId);
        String channelSendOrder = SequenceUtils.getSequence(SystemCode.PAYMENT, BusinessCode.PAYMENT_QUICK_PAY);

        ChannelQuickAgreement quickAgreement = quickAgreementService.queryUserQuickAgreementBySucceed(userId);

        BankCardInfo bankCardInfo = bankCardInfoService.queryById(quickAgreement.getBankCardId());
        // 核验银行卡是否存在限制
        PermissionRestrict cardRestrict = permissionRestrictService.restrict(BusinessCode.PAYMENT_QUICK_PAY,
                bankCardInfo.getBankCardNo());
        if (cardRestrict != null) {
            return ResponseDTO.buildFail(null, "", cardRestrict.getTips());
        }

        RechargeJournal record = new RechargeJournal();
        record.setTxnId(txnId);
        record.setChannelCode(ChannelCode.BILL99.getCode());
        record.setChannelName(ChannelCode.BILL99.getDesc());
        record.setChannelSendOrder(channelSendOrder);
        record.setAmount(from.getAmount());
        record.setCost(BigDecimal.ZERO);
        record.setStatus(-1);// 入库初始化状态
        record.setUserId(userId);
        record.setUserName(userName);
        record.setBusinessCode(BusinessCode.PAYMENT_QUICK_PAY);
        record.setTransTimeout(DateUtils.addHours(new Date(), 1));
        record.setSendTime(new Date());
        record.setCreateTime(new Date());
        record.setLastModifyTime(new Date());
        // record.setVersion(0);
        record.setOrigin(from.getOrigin());
        record.setClientIp(clientIp);
        record.setBankCardId(bankCardInfo.getId());

        rechargeJournalDAO.insertSelective(record);

        QuickPayRequestDTO dto = new QuickPayRequestDTO();
        dto.setChannelCode(ChannelCode.BILL99.getCode());
        dto.setTxnId(txnId);
        dto.setChannelSendOrder(channelSendOrder);
        dto.setAmount(from.getAmount());
        dto.setClientIp(clientIp);

        TreatyInfoDTO treaty = new TreatyInfoDTO();
        treaty.setIdentityCode(quickAgreement.getIdentityCode());
        treaty.setBankCardNo(bankCardInfo.getBankCardNo());
        treaty.setChannelBankCode(bankCardInfo.getBankCode());
        treaty.setIdCardNo(bankCardInfo.getIdCardNo());
        treaty.setMobile(bankCardInfo.getMobile());
        treaty.setRealName(bankCardInfo.getRealName());
        dto.setTreatyInfo(treaty);

        ResponseDTO<QuickPayResponseDTO> response = channelAdapterFacade.applyQuickPay(dto);
        if (!ResponseDTO.Status.SUCCESS.equals(response.getStatus())) {
            updateRechargeJournalFailed(txnId, response.getResponseCode(), response.getResponseMessage());
        }
        return response;
    }

    private void updateRechargeJournalFailed(String txnId, String responseCode, String responseMessage) {
        RechargeJournal rechargeJournal = rechargeJournalDAO.queryByTxnId(txnId);
        rechargeJournalDAO.updateRechargeJournalFailed(txnId, responseCode, responseMessage, new Date(),
                rechargeJournal.getVersion());
    }

    public ResponseDTO<QuickPayConfirmResponseDTO> confirmQuickPay(String txnId, Long userId, ConfirmFrom from) {
        ChannelQuickAgreement quickAgreement = quickAgreementService.queryUserQuickAgreementBySucceed(userId);
        BankCardInfo bankCardInfo = bankCardInfoService.queryById(quickAgreement.getBankCardId());
        RechargeJournal rechargeJournal = queryBytxnId(txnId);

        QuickPayConfirmRequestDTO dto = new QuickPayConfirmRequestDTO();
        dto.setChannelCode(ChannelCode.BILL99.getCode());
        dto.setTxnId(txnId);
        dto.setChannelSendOrder(rechargeJournal.getChannelSendOrder());
        dto.setAmount(rechargeJournal.getAmount());
        dto.setClientIp(rechargeJournal.getClientIp());
        dto.setSecurityCode(from.getSecurityCode());

        TreatyInfoDTO treaty = new TreatyInfoDTO();
        treaty.setIdentityCode(quickAgreement.getIdentityCode());
        treaty.setBankCardNo(bankCardInfo.getBankCardNo());
        treaty.setChannelBankCode(bankCardInfo.getBankCode());
        treaty.setIdCardNo(bankCardInfo.getIdCardNo());
        treaty.setMobile(bankCardInfo.getMobile());
        treaty.setRealName(bankCardInfo.getRealName());
        dto.setTreatyInfo(treaty);
        int result = rechargeJournalDAO.updateRechargeJournalHanding(txnId, new Date(), rechargeJournal.getVersion());
        if (result == 1) {
            ResponseDTO<QuickPayConfirmResponseDTO> response = channelAdapterFacade.confirmQuickPay(dto);
            if (ResponseDTO.Status.SUCCESS.equals(response.getStatus())) {
                updateRechargeJournalSucceed(txnId, response.getResponseCode(), response.getResponseMessage());
            } else if (ResponseDTO.Status.FAILED.equals(response.getStatus())) {
                updateRechargeJournalSucceed(txnId, response.getResponseCode(), response.getResponseMessage());
            }
            return response;
        }
        return ResponseDTO.buildTrading(null, "", "");
    }

    private void updateRechargeJournalSucceed(String txnId, String responseCode, String responseMessage) {
        RechargeJournal rechargeJournal = rechargeJournalDAO.queryByTxnId(txnId);
        rechargeJournalDAO.updateRechargeJournalFailed(txnId, responseCode, responseMessage, new Date(),
                rechargeJournal.getVersion());
    }

    public void updateRehageJournalSucceed(String txnId, String responseCode, String responseMessage) {
        RechargeJournal rechargeJournal = rechargeJournalDAO.queryByTxnId(txnId);
        int result = rechargeJournalDAO.updateRechargeJournalSucceed(txnId, responseCode, responseMessage, new Date(),
                rechargeJournal.getVersion());
        if (result == 1) {
            // TODO 调用资金接口给用户加款
        }
    }

    private RechargeJournal queryBytxnId(String txnId) {
        return rechargeJournalDAO.queryByTxnId(txnId);
    }

    public PageDTO<RechargeJournal, String> querySucceedRechargeJournal(Long userId, int pageNum, int pageSize) {
        return queryRechargeJournalByStatus(userId, 1, pageNum, pageSize);
    }

    public PageDTO<RechargeJournal, String> queryHandingRechargeJournal(Long userId, int pageNum, int pageSize) {
        return queryRechargeJournalByStatus(userId, 3, pageNum, pageSize);
    }

    public PageDTO<RechargeJournal, String> queryRechargeJournalByStatus(Long userId, int status, int pageNum,
            int pageSize) {
        PageDTO<RechargeJournal, String> page = new PageDTO<>();
        int count = rechargeJournalDAO.countRechargeJournal(userId, status);
        List<RechargeJournal> data = rechargeJournalDAO.queryRechargeJournal(userId, status, pageNum * pageSize,
                pageSize);
        page.setData(data);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setTotalRow(count);
        return page;
    }

    public RechargeJournal queryUserFirstRechargeJournal(Long userId) {
        return rechargeJournalDAO.queryUserFirstRechargeJournal(userId);
    }
}
