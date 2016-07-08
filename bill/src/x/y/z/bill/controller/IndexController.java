package x.y.z.bill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.alpha.web.controller.BaseController;
import x.y.z.bill.constant.Views;

@Controller
public class IndexController extends BaseController {

    @RequestMapping(value = { "/", METHOD_INDEX }, method = RequestMethod.GET)
    public String index() {
        return Views.INDEX_VIEW;
    }

}
