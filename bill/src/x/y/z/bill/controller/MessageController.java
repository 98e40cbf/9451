package x.y.z.bill.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import x.y.z.bill.command.RegistForm;
import x.y.z.bill.command.message.SmsForm;
import x.y.z.bill.constant.Views;
import x.y.z.bill.constant.message.SmsBizTypeEnum;
import x.y.z.bill.constant.message.SmsTypeEnum;
import x.y.z.bill.dto.message.SmsDTO;
import x.y.z.bill.service.message.SmsFacade;
import x.y.z.bill.util.message.SmsTaoBaoClientUtils;
import io.alpha.util.RandomStringGenerator;
import io.alpha.web.controller.BaseController;

@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {
    @Autowired
    private SmsFacade smsFacade;

    @GetMapping("/index")
    public String index() {
        return Views.INDEX_MESSAGE_VIEW;
    }

    @PostMapping("/normal")
    public String normal(@Valid final SmsForm smsForm, final BindingResult result) {
        if (result.hasErrors()) {
            return Views.INDEX_MESSAGE_VIEW;
        }

        SmsDTO smsDTO = new SmsDTO();
        smsDTO.setTxnId(RandomStringGenerator.randomAlphabetic(32));
        smsDTO.setMobiles(smsForm.getMobile());
        smsDTO.setSmsTypeEnum(SmsTypeEnum.REGISTER);
        smsDTO.setSmsBizTypeEnum(SmsBizTypeEnum.TEXT);
        smsFacade.sendCheckCode(smsDTO);

        return "redirect:/message/index";
    }

    @PostMapping("/voice")
    public String voice(@Valid final SmsForm smsForm, final BindingResult result) {
        if (result.hasErrors()) {
            return Views.INDEX_MESSAGE_VIEW;
        }

        SmsDTO smsDTO = new SmsDTO();
        smsDTO.setTxnId(RandomStringGenerator.randomAlphabetic(32));
        smsDTO.setMobiles(smsForm.getMobile());
        smsDTO.setSmsTypeEnum(SmsTypeEnum.REGISTER);
        smsDTO.setSmsBizTypeEnum(SmsBizTypeEnum.VOICE);
        smsFacade.sendCheckCode(smsDTO);

        return "redirect:/message/index";
    }
}
