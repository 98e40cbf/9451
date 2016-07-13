package x.y.z.bill.controller.product;

import io.alpha.core.dto.PageResultDTO;
import io.alpha.web.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import x.y.z.bill.constant.ProductUrl;
import x.y.z.bill.constant.ProductViews;
import x.y.z.bill.dto.product.ProductDTO;
import x.y.z.bill.service.product.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;

	@RequestMapping(value = ProductUrl.PRODUCT_LISTING, method = RequestMethod.GET)
	public String listing(final ModelMap model) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setPageNum(0);
		productDTO.setPageSize(10);
		PageResultDTO<ProductDTO> resultDTO = productService
				.selectProductList(productDTO);
		model.put("productList", resultDTO);

		model.addAttribute("pageNum", productDTO.getPageNum() + 1);
		int totalPage = resultDTO.getTotalRow() % productDTO.getPageSize() == 0 ? resultDTO
				.getTotalRow() / productDTO.getPageSize()
				: resultDTO.getTotalRow() / productDTO.getPageSize() + 1;
		model.addAttribute("totalPage", totalPage);
		return ProductViews.LISTING_VIEW;
	}

	@RequestMapping(value = ProductUrl.PRODUCT_LIST, method = RequestMethod.GET)
	@ResponseBody
	public PageResultDTO<ProductDTO> productList(
			@PathVariable final int pageNum, @PathVariable final int pageSize) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setPageNum(pageNum);
		productDTO.setPageSize(pageSize);
		PageResultDTO<ProductDTO> resultDTO = productService
				.selectProductList(productDTO);
		return resultDTO;
	}

}
