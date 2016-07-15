package x.y.z.bill.service.product;

import io.alpha.core.dto.PageResultDTO;
import io.alpha.mybatis.statement.RecordCountHelper;
import io.alpha.service.BaseService;
import io.alpha.util.DecimalUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.y.z.bill.dto.product.ProductDTO;
import x.y.z.bill.mapper.product.InvestDAO;
import x.y.z.bill.mapper.product.InvestRepayPlanDAO;
import x.y.z.bill.mapper.product.ProductDAO;
import x.y.z.bill.mapper.product.ProductRepayPlanDAO;
import x.y.z.bill.model.product.Invest;
import x.y.z.bill.model.product.InvestRepayPlan;
import x.y.z.bill.model.product.Product;
import x.y.z.bill.model.product.ProductRepayPlan;
import x.y.z.bill.util.ArithmeticUtils;

@Service
public class ProductService extends BaseService {

    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private InvestDAO investDAO;
    @Autowired
    private ProductRepayPlanDAO productRepayPlanDAO;
    @Autowired
    private InvestRepayPlanDAO investRepayPlanDAO;

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
        pageResultDTO.setTotalRow(RecordCountHelper.getCount());
        return pageResultDTO;
    }

    private List<ProductDTO> biuldResultData(List<Product> productList) {
        List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
        for (Product product : productList) {
            ProductDTO respProductDTO = new ProductDTO();
            respProductDTO.setId(product.getId());
            respProductDTO.setProductName(product.getProductName());
            respProductDTO.setAnnualRate(product.getAnnualRate());
            respProductDTO.setSurplusAmount(product.getSurplusAmount().setScale(2));
            respProductDTO.setDeadline(product.getDeadline());
            respProductDTO.setInvestSchedule(product.getInvestSchedule().setScale(2));
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

    public void productSoldOut(Long productId) {
        Product product = productDAO.selectByPrimaryKey(productId);
        if (product.getAmount().compareTo(product.getInvestTotalAmount()) != 0) {
            return;
        }
        ProductRepayPlan productRepayPlan = new ProductRepayPlan();
        Date valueDate = DateUtils.addDays(new Date(), 1);
        Date repayDate = DateUtils.addDays(valueDate, product.getDeadline());
        productRepayPlan.setRepayDate(repayDate);
        productRepayPlan.setRepayPeriod("1");
        productRepayPlan.setProductId(productId);
        productRepayPlan.setProductName(product.getProductName());
        productRepayPlan.setProductType(product.getProductType());
        productRepayPlan.setRepaymentType(product.getRepaymentType());
        productRepayPlan.setRepayStatus(3);
        productRepayPlan.setStillPrincipal(product.getAmount());
        BigDecimal stillInterest = DecimalUtil.format(ArithmeticUtils.arithmeticInterest(product.getAmount()
                .doubleValue(), product.getDeadline(), product.getAnnualRate().doubleValue(), 1));
        productRepayPlan.setStillInterest(stillInterest);
        productRepayPlanDAO.insert(productRepayPlan);

        List<Invest> invests = investDAO.selectInvests(productId, 2);
        List<InvestRepayPlan> list = new ArrayList<InvestRepayPlan>();
        for (Invest invest : invests) {
            InvestRepayPlan investRepayPlan = new InvestRepayPlan();
            investRepayPlan.setInvestId(invest.getId());
            investRepayPlan.setOrderNo(invest.getOrderNo());
            investRepayPlan.setProductId(productId);
            investRepayPlan.setProductName(invest.getProductName());
            investRepayPlan.setProductType(invest.getProductType());
            investRepayPlan.setRepayDate(repayDate);
            investRepayPlan.setRepayStatus(3);
            investRepayPlan.setStillPrincipal(invest.getRecivedPrincipal());
            investRepayPlan.setStillInterest(invest.getRecievedInterest());
            investRepayPlan.setUserId(invest.getUserId());
            list.add(investRepayPlan);
        }
        // TODO 调用账户解冻
        investRepayPlanDAO.addInvestRepayBatch(list);

        productDAO.updateSoldProduct(productId, 3, valueDate, new Date());

        investDAO.updateSoldInvestStatus(productId, 3);
    }
}
