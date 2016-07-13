package x.y.z.bill.controller.product;

import io.alpha.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import x.y.z.bill.constant.ProductUrl;
import x.y.z.bill.constant.ProductViews;
import x.y.z.bill.service.product.ProductService;

@Controller
public class InvestController extends BaseController {

	@Autowired
	private ProductService productService;

	@RequestMapping(value = ProductUrl.PRODUCT_BUY, method = RequestMethod.POST)
	public String invest(final ModelMap model) {
		
		return ProductViews.LISTING_VIEW;
	}

}
