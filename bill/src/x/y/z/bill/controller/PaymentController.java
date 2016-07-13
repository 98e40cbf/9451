package x.y.z.bill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import x.y.z.bill.model.payment.BankCardBin;
import x.y.z.bill.service.payment.BankCardBinService;
import io.alpha.web.controller.BaseController;

@Controller
@RequestMapping("/payment")
public class PaymentController extends BaseController {

    @Autowired
    private BankCardBinService bankCardBinService;

    @ResponseBody
    @GetMapping("/card/recognition/{bankCardNo}")
    public BankCardBin recognition(@PathVariable String bankCardNo) {
        return bankCardBinService.recognition(bankCardNo);
    }
}
