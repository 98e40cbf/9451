package x.y.z.bill.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import x.y.z.bill.constant.ProductUrl;
import x.y.z.bill.constant.ProductViews;
import x.y.z.bill.dto.product.ProductDTO;
import x.y.z.bill.service.product.ProductService;
import io.alpha.web.controller.BaseController;

@Controller
@RequestMapping("/product")
public class ProductDetailController extends BaseController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = ProductUrl.PRODUCT_DETAIL, method = RequestMethod.GET)
    public String detail(final ModelMap model, @PathVariable final long productId) {
        ProductDTO productDTO = productService.selectProductDetail(productId);
        model.put("product", productDTO);
        return ProductViews.DETAIL_VIEW;
    }

}
