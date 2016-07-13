package x.y.z.bill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import x.y.z.bill.constant.Views;
import io.alpha.web.controller.BaseController;

@Controller
public class IndexController extends BaseController {

    @RequestMapping(value = { "/", METHOD_INDEX }, method = RequestMethod.GET)
    public String index() {
        return Views.INDEX_VIEW;
    }

    @RequestMapping(value = { "payment", "/payment/index" }, method = RequestMethod.GET)
    public String payment() {
        return Views.PAYMENT_INDEX_VIEW;
    }

}
