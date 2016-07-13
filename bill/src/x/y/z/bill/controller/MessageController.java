package x.y.z.bill.controller;

import io.alpha.util.RandomStringGenerator;
import io.alpha.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import x.y.z.bill.command.RegistForm;
import x.y.z.bill.constant.SmsTypeEnum;
import x.y.z.bill.constant.Views;
import x.y.z.bill.service.message.SmsRecordService;

import javax.validation.Valid;

@Controller
@RequestMapping("/message")
public class MessageController  extends BaseController {
    @Autowired
    private SmsRecordService smsRecordService;

    @GetMapping("/regist")
    public String registForm() {
        return Views.INDEX_MESSAGE_VIEW;
    }

    @PostMapping("/regist")
    public String register(@Valid final RegistForm registForm, final BindingResult result) {
        if (result.hasErrors()) {
            return Views.INDEX_MESSAGE_VIEW;
        }

        String mobile = registForm.getMobile();
        String checkCode = RandomStringGenerator.randomNumeric(6);
        String txnId =RandomStringGenerator.randomAlphabetic(32);
        SmsTypeEnum smsTypeEnum = SmsTypeEnum.REGISTER;
        smsRecordService.insert(txnId,smsTypeEnum, mobile, checkCode);
        return "redirect:/";
    }
}
