package x.y.z.bill.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import io.alpha.core.dto.PageResultDTO;
import io.alpha.util.DecimalUtil;
import io.alpha.util.HttpUtils;
import io.alpha.util.SequenceHelper;
import io.alpha.web.controller.BaseController;
import x.y.z.bill.command.LoginForm;
import x.y.z.bill.command.RealnameForm;
import x.y.z.bill.command.RegistForm;
import x.y.z.bill.constant.BizType;
import x.y.z.bill.constant.Views;
import x.y.z.bill.model.account.CapitalJournal;
import x.y.z.bill.service.account.AccountService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/regist")
    public String registForm() {
        return Views.INDEX_VIEW;
    }

    @PostMapping("/regist")
    public String register(@Valid final RegistForm registForm, final BindingResult result) {
        if (result.hasErrors()) {
            return Views.INDEX_VIEW;
        }
        accountService.regist(registForm);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@Valid final LoginForm loginForm, final HttpServletRequest request,
            @RequestHeader(HttpHeaders.USER_AGENT) final String userAgent, final BindingResult result)
            throws Exception {
        if (result.hasErrors()) {
            return Views.INDEX_VIEW;
        }
        long login = accountService.login(loginForm, HttpUtils.getRemoteIpAddr(request), userAgent);
        logger.info("登录：{}", login);
        return "redirect:/";
    }

    // TODO userId get from Session.
    @PostMapping("/realname")
    public String realname(final Long userId, @Valid final RealnameForm realnameForm, final BindingResult result) {
        if (result.hasErrors()) {
            return Views.INDEX_VIEW;
        }
        accountService.realname(userId, realnameForm);
        return "redirect:/";
    }

    @PostMapping("/recharge")
    public String recharge(final Long userId, final String amount) {
        accountService.add(userId, DecimalUtil.format(new BigDecimal(amount)), SequenceHelper.get(), "充值" + amount,
                BizType.RECHARGE);
        return "redirect:/";
    }

    @PostMapping("/buy")
    public String buy(final Long userId, final String amount) {
        accountService.freeze(userId, DecimalUtil.format(new BigDecimal(amount)), SequenceHelper.get(), "投资" + amount,
                BizType.INVEST_APPLY);
        return "redirect:/";
    }

    @PostMapping("/buy-complete")
    public String buyComplete(final Long userId, final String txnId) {
        accountService.unfreeze(userId, txnId, "投资完成 ", BizType.INVEST_UNFREEZE, true);
        return "redirect:/";
    }

    @PostMapping("/buy-fallback")
    public String buyFallback(final Long userId, final String txnId) {
        accountService.unfreeze(userId, txnId, "投资失败 ", BizType.INVEST_UNFREEZE, false);
        return "redirect:/";
    }

    @PostMapping("/update-password")
    public String updatePassword(final Long userId, final String oldPassword, final String newPassword) {
        boolean updated = accountService.updatePassword(userId, oldPassword, newPassword);
        logger.info("更新密码：{}", updated);
        return "redirect:/";
    }

    @GetMapping("/list")
    public String list(final Long userId) {
        PageResultDTO<CapitalJournal> list = accountService.loadAllCapitalJournal(userId);
        logger.info("所有资金流水：{}", list);
        list = accountService.loadRechargeCapitalJournal(userId);
        logger.info("所有充值流水：{}", list);
        list = accountService.loadWithdrawCapitalJournal(userId);
        logger.info("所有提现流水：{}", list);
        return "redirect:/";
    }
}
