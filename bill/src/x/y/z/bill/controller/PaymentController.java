package x.y.z.bill.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import x.y.z.bill.adapter.channel.dto.response.*;
import x.y.z.bill.command.ApplyQuickPayAgreementFrom;
import x.y.z.bill.command.ApplyQuickPayFrom;
import x.y.z.bill.command.ApplyWithdrawFrom;
import x.y.z.bill.command.ConfirmFrom;
import x.y.z.bill.model.payment.BankCardBin;
import x.y.z.bill.model.payment.BankCardInfo;
import x.y.z.bill.model.payment.RechargeJournal;
import x.y.z.bill.model.payment.WithdrawJournal;
import x.y.z.bill.service.payment.*;
import io.alpha.core.dto.PageDTO;
import io.alpha.util.HttpUtils;
import io.alpha.web.controller.BaseController;

@Controller
@RequestMapping("/payment")
public class PaymentController extends BaseController {

    // TODO 要修改的地方
    private static final Long userId = 89757L;
    private static final String userName = "Robot";
    @Autowired
    private BankCardBinService bankCardBinService;
    @Autowired
    private BankCardInfoService bankCardInfoService;
    @Autowired
    private QuickAgreementService quickAgreementService;
    @Autowired
    private RechargeJournalService rechargeJournalService;
    @Autowired
    private WithdrawJournalService withdrawJournalService;

    @ResponseBody
    @GetMapping("/card/recognition/{bankCardNo}")
    public BankCardBin recognition(@PathVariable String bankCardNo) {
        return bankCardBinService.recognition(bankCardNo);
    }

    @ResponseBody
    @PostMapping("/card/apply/agreement")
    public ResponseDTO applyQuickPayAgreement(@Valid final ApplyQuickPayAgreementFrom from, final BindingResult result,
            HttpServletRequest request) {
        if (result.hasErrors()) {
            return ResponseDTO.buildFail(null, "", "参数校验异常");
        }
        try {
            ResponseDTO<AuthResponseDTO> response = quickAgreementService.applyQuickPayAgreement(from, userId, userName,
                    HttpUtils.getRemoteIpAddr(request));
            if (response != null && ResponseDTO.Status.SUCCESS.equals(response.getStatus())) {
                request.getSession().setAttribute("PAYMENT_QUICK_PAY_AGREEMENT_TXN_ID", response.getData().getTxnId());
            }
            return response;
        } catch (Exception e) {
            logger.error("用户申请快捷支付协议异常", e);
        }
        return ResponseDTO.buildFail(null, "", "系统异常");
    }

    @ResponseBody
    @PostMapping("/card/confirm/agreement")
    public ResponseDTO<AuthConfirmResponseDTO> confirmQuickPayAgreement(@Valid final ConfirmFrom from,
            final BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return ResponseDTO.buildFail(null, "", "参数校验异常");
        }
        String txnId = (String) request.getSession().getAttribute("PAYMENT_QUICK_PAY_AGREEMENT_TXN_ID");
        if (StringUtils.isEmpty(txnId)) {
            return ResponseDTO.buildFail(null, "", "验证流水不存在");
        }
        ResponseDTO<AuthConfirmResponseDTO> response = quickAgreementService.confirmQuickPayAgreement(txnId, from);
        return response;
    }

    @ResponseBody
    @PostMapping("/card/apply/quick/pay")
    public ResponseDTO<QuickPayResponseDTO> applyQuickPay(@Valid final ApplyQuickPayFrom from,
            final BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return ResponseDTO.buildFail(null, "", "参数校验异常");
        }
        ResponseDTO<QuickPayResponseDTO> response = rechargeJournalService.applyQuickPay(from, userId, userName,
                HttpUtils.getRemoteIpAddr(request));
        if (response != null && ResponseDTO.Status.SUCCESS.equals(response.getStatus())) {
            request.getSession().setAttribute("PAYMENT_QUICK_PAY_TXN_ID", response.getData().getTxnId());
        }
        return response;

    }

    @ResponseBody
    @PostMapping("/card/confirm/quick/pay")
    public ResponseDTO<QuickPayConfirmResponseDTO> confirmQuickPay(@Valid final ConfirmFrom from,
            final BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return ResponseDTO.buildFail(null, "", "参数校验异常");
        }
        String txnId = (String) request.getSession().getAttribute("PAYMENT_QUICK_PAY_TXN_ID");
        ResponseDTO<QuickPayConfirmResponseDTO> response = rechargeJournalService.confirmQuickPay(txnId, userId, from);
        return response;
    }

    @ResponseBody
    @PostMapping("/card/apply/withdraw")
    public ResponseDTO<WithdrawResponseDTO> applyWithdraw(@Valid final ApplyWithdrawFrom from,
            final BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return ResponseDTO.buildFail(null, "", "参数校验异常");
        }

        ResponseDTO<WithdrawResponseDTO> response = withdrawJournalService.applyWithdraw(from, userId, userName,
                HttpUtils.getRemoteIpAddr(request));
        if (response != null && ResponseDTO.Status.SUCCESS.equals(response.getStatus())) {
            String securityCode = RandomStringUtils.randomNumeric(6);
            request.getSession().setAttribute("PAYMENT_WITHDRAW_TXN_ID", response.getData().getTxnId());
            request.getSession().setAttribute("PAYMENT_WITHDRAW_SECURITY_CODE", securityCode);
        }
        return response;

    }

    @ResponseBody
    @PostMapping("/card/confirm/withdraw")
    public ResponseDTO<String> confirmWithdraw(@Valid final ConfirmFrom from, final BindingResult result,
            HttpServletRequest request) {
        if (result.hasErrors()) {
            return ResponseDTO.buildFail(null, "", "参数校验异常");
        }
        String txnId = (String) request.getSession().getAttribute("PAYMENT_WITHDRAW_TXN_ID");
        String securityCode = (String) request.getSession().getAttribute("PAYMENT_WITHDRAW_SECURITY_CODE");
        if (StringUtils.isNotEmpty(securityCode) && securityCode.equals(from.getSecurityCode())) {
            ResponseDTO<String> response = withdrawJournalService.confirmWithdraw(txnId);
            return response;
        }
        return ResponseDTO.buildFail(null, "", "参数校验异常");
    }

    @ResponseBody
    @GetMapping("/card/index")
    public BankCardInfo queryUserBankCard() {
        return bankCardInfoService.queryUserBankCardBySucceed(userId);
    }

    @ResponseBody
    @GetMapping("/journal/rechage/{pageNum}/{pageSize}")
    public PageDTO<RechargeJournal, String> queryRechageJournal(@PathVariable int pageNum, @PathVariable int pageSize) {
        return rechargeJournalService.querySucceedRechageJournal(userId, pageNum, pageSize);
    }

    @ResponseBody
    @GetMapping("/journal/withdraw/{pageNum}/{pageSize}")
    public PageDTO<WithdrawJournal, String> queryWithdrawJournal(@PathVariable int pageNum,
            @PathVariable int pageSize) {
        return withdrawJournalService.querySucceedWithdrawJournal(userId, pageNum, pageSize);
    }
}
