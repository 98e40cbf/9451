package x.y.z.bill.service.payment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.y.z.bill.adapter.channel.dto.response.ResponseDTO;
import x.y.z.bill.adapter.constant.ChannelCode;
import x.y.z.bill.command.ApplyWithdrawFrom;
import x.y.z.bill.constant.BusinessCode;
import x.y.z.bill.constant.SystemCode;
import x.y.z.bill.mapper.payment.WithdrawJournalDAO;
import x.y.z.bill.model.payment.BankCardInfo;
import x.y.z.bill.model.payment.PermissionRestrict;
import x.y.z.bill.model.payment.WithdrawJournal;
import x.y.z.bill.util.SequenceUtils;
import io.alpha.core.dto.PageDTO;
import io.alpha.service.BaseService;
import io.alpha.tx.annotation.TransMark;
import io.alpha.util.DecimalUtil;

/**
 * Created by yuanxw on 2016/7/19.
 */
@Service
@TransMark
public class WithdrawJournalService extends BaseService {

    private static final BigDecimal WITHDRAW_COST = BigDecimal.ONE;
    @Autowired
    private WithdrawJournalDAO withdrawJournalDAO;
    @Autowired
    private BankCardInfoService bankCardInfoService;
    @Autowired
    private PermissionRestrictService permissionRestrictService;

    public ResponseDTO<String> applyWithdraw(ApplyWithdrawFrom from, Long userId, String userName, String clientIp) {
        PermissionRestrict userRestrict = permissionRestrictService.restrict(BusinessCode.PAYMENT_WITHDRAW, userId);
        if (userRestrict != null) {
            return ResponseDTO.buildFail(null, "", userRestrict.getTips());
        }

        BigDecimal actulAmount = DecimalUtil.subtract(from.getAmount(), WITHDRAW_COST);

        if (DecimalUtil.lt(actulAmount, BigDecimal.ZERO)) {
            return ResponseDTO.buildFail("", "", "提现金额不足以支付提现手续费");
        }

        BankCardInfo bankCardInfo = bankCardInfoService.queryUserBankCardBySucceed(userId);
        // 核验银行卡是否存在限制
        PermissionRestrict cardRestrict = permissionRestrictService.restrict(BusinessCode.PAYMENT_WITHDRAW,
                bankCardInfo.getBankCardNo());
        if (cardRestrict != null) {
            return ResponseDTO.buildFail(null, "", cardRestrict.getTips());
        }

        WithdrawJournal record = new WithdrawJournal();
        String txnId = SequenceUtils.getSequence(SystemCode.PAYMENT, BusinessCode.PAYMENT_WITHDRAW);
        String channelSendOrder = SequenceUtils.getSequence(SystemCode.PAYMENT, BusinessCode.PAYMENT_WITHDRAW);

        record.setTxnId(txnId);
        record.setChannelCode(ChannelCode.KFTPAY.getCode());
        record.setChannelName(ChannelCode.KFTPAY.getDesc());
        record.setChannelSendOrder(channelSendOrder);
        record.setBusinessCode(BusinessCode.PAYMENT_WITHDRAW);
        record.setUserId(userId);
        record.setUserName(userName);
        record.setAmount(from.getAmount());
        record.setActulAmount(actulAmount);
        record.setCost(WITHDRAW_COST);
        record.setStatus(-1);
        record.setAuditStatus(-1);
        record.setCreateTime(new Date());
        record.setLastModifyTime(new Date());
        record.setOrigin(from.getOrigin());
        record.setClientIp(clientIp);
        record.setBankCardId(bankCardInfo.getId());
        withdrawJournalDAO.insertSelective(record);
        // 发送验证码给用户

        return ResponseDTO.buildSuccess(txnId, "00", "提现申请成功,待用户确认");
    }

    public ResponseDTO<String> confirmWithdraw(String txnId) {
        WithdrawJournal record = withdrawJournalDAO.queryByTxnId(txnId);
        // TODO 先对用户进行扣款操作，扣款成功，将状态修改为用户已经确认状态
        int result = withdrawJournalDAO.updateByUserConfirm(txnId, record.getVersion());
        if (result == 1) {
            logger.info("[{}] - [支付系统] - [用户 {}] - [确认提现成功] - [{}]", txnId, record.getUserName(), result);
        } else {
            logger.info("[{}] - [支付系统] - [用户 {}] - [确认提现失败] - [{}]", txnId, record.getUserName(), result);
            throw new RuntimeException();
        }
        return ResponseDTO.buildSuccess(txnId, "", "提现申请成功，等待后台系统审核");
    }

    public PageDTO<WithdrawJournal, String> querySucceedWithdrawJournal(Long userId, int pageNum, int pageSize) {
        return queryWithdrawJournalByStatus(userId, 1, pageNum, pageSize);
    }

    public PageDTO<WithdrawJournal, String> queryWithdrawJournalByStatus(Long userId, int status, int pageNum,
            int pageSize) {
        PageDTO<WithdrawJournal, String> page = new PageDTO<>();
        int count = withdrawJournalDAO.countWithdrawJournal(userId, status);
        List<WithdrawJournal> data = withdrawJournalDAO.queryWithdrawJournal(userId, status, pageNum * pageSize,
                pageSize);
        page.setData(data);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setTotalRow(count);
        return page;
    }
}
