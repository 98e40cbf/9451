package x.y.z.bill.service.product;

import io.alpha.core.dto.PageResultDTO;
import io.alpha.mybatis.util.CountHelper;
import io.alpha.service.BaseService;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.y.z.bill.dto.product.ProductDTO;
import x.y.z.bill.mapper.product.ProductDAO;
import x.y.z.bill.model.product.Product;

@Service
public class ProductService extends BaseService {

	@Autowired
	private ProductDAO productDAO;

	/**
	 * 产品列表
	 * 
	 * @param productDTO
	 * @return
	 */
	public PageResultDTO<ProductDTO> selectProductList(ProductDTO productDTO) {
		int offset = productDTO.getPageNum() * productDTO.getPageSize();
		RowBounds rowBounds = new RowBounds(offset, productDTO.getPageSize());
		List<Product> productList = productDAO.selectProductList(rowBounds);
		List<ProductDTO> productDTOList = biuldResultData(productList);
		PageResultDTO<ProductDTO> pageResultDTO = new PageResultDTO<ProductDTO>();
		pageResultDTO.setData(productDTOList);
		pageResultDTO.setTotalRow(CountHelper.getTotalRow());
		return pageResultDTO;
	}

	private List<ProductDTO> biuldResultData(List<Product> productList) {
		List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
		for (Product product : productList) {
			ProductDTO respProductDTO = new ProductDTO();
			respProductDTO.setId(product.getId());
			respProductDTO.setProductName(product.getProductName());
			respProductDTO.setAnnualRate(product.getAnnualRate());
			respProductDTO.setSurplusAmount(product.getSurplusAmount()
					.setScale(2));
			respProductDTO.setDeadline(product.getDeadline());
			respProductDTO.setInvestSchedule(product.getInvestSchedule()
					.setScale(2));
			respProductDTO.setStatus(product.getStatus());
			productDTOList.add(respProductDTO);
		}
		return productDTOList;
	}

	public ProductDTO selectProductDetail(Long productId) {
		Product product = productDAO.selectByPrimaryKey(productId);
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setProductName(product.getProductName());
		productDTO.setAnnualRate(product.getAnnualRate());
		productDTO.setSurplusAmount(product.getSurplusAmount().setScale(2));
		productDTO.setDeadline(product.getDeadline());
		productDTO.setInvestSchedule(product.getInvestSchedule().setScale(2));
		productDTO.setStatus(product.getStatus());
		return productDTO;
	}
}
