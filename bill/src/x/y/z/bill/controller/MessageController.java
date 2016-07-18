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
import x.y.z.bill.constant.message.SmsTypeEnum;
import x.y.z.bill.constant.Views;
import x.y.z.bill.dto.message.SmsDTO;
import x.y.z.bill.service.message.SmsFacade;
import x.y.z.bill.util.message.SmsTaoBaoClientUtils;

import javax.validation.Valid;

@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {
    @Autowired
    private SmsFacade smsFacade;

    @GetMapping("/regist")
    public String registForm() {
        return Views.INDEX_MESSAGE_VIEW;
    }

    @PostMapping("/regist")
    public String register(@Valid final RegistForm registForm, final BindingResult result) {
        if (result.hasErrors()) {
            return Views.INDEX_MESSAGE_VIEW;
        }

        SmsDTO smsDTO = new SmsDTO();
        smsDTO.setTxnId(RandomStringGenerator.randomAlphabetic(32));
        smsDTO.setMobiles(registForm.getMobile());
        smsDTO.setSmsTypeEnum(SmsTypeEnum.REGISTER);
        smsFacade.sendCheckCode(smsDTO);

        SmsTaoBaoClientUtils.sendSms(registForm.getMobile(), registForm.getUsername());
        return "redirect:/";
    }
}
