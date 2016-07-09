package x.y.z.bill.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.alpha.util.DecimalUtil;
import io.alpha.util.SequenceHelper;
import io.alpha.web.controller.BaseController;
import x.y.z.bill.command.UserRegistCommand;
import x.y.z.bill.constant.BizType;
import x.y.z.bill.constant.Views;
import x.y.z.bill.service.AccountService;

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
    public String register(@Valid final UserRegistCommand registCommand, final BindingResult result) {
        if (result.hasErrors()) {
            return Views.INDEX_VIEW;
        }
        accountService.regist(registCommand);
        return Views.INDEX_VIEW;
    }

    @PostMapping("/recharge")
    public String recharge() {
        accountService.add(1L, DecimalUtil.format(100), SequenceHelper.get(), "充值100", BizType.RECHARGE);
        return Views.INDEX_VIEW;
    }

    @PostMapping("/buy")
    public String buy() {
        accountService.freeze(1L, DecimalUtil.format(15), SequenceHelper.get(), "投资15", BizType.INVEST_APPLY);
        return Views.INDEX_VIEW;
    }

    @PostMapping("/buy-complete")
    public String buyComplete(final String txnId) {
        accountService.unfreeze(1L, txnId, "投资15完成 ", BizType.INVEST_UNFREEZE, true);
        return Views.INDEX_VIEW;
    }

    @PostMapping("/buy-fallback")
    public String buyFallback(final String txnId) {
        accountService.unfreeze(1L, txnId, "投资15失败 ", BizType.INVEST_UNFREEZE, false);
        return Views.INDEX_VIEW;
    }
}
