package x.y.z.bill.service.product;

import io.alpha.service.BaseService;
import io.alpha.util.DecimalUtil;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import x.y.z.bill.constant.InvestResponeEnum;
import x.y.z.bill.dto.product.TradeDTO;
import x.y.z.bill.dto.product.TradeResponseDTO;
import x.y.z.bill.mapper.product.InvestDAO;
import x.y.z.bill.mapper.product.ProductDAO;
import x.y.z.bill.model.product.Invest;
import x.y.z.bill.model.product.Product;
import x.y.z.bill.util.ArithmeticUtils;
import x.y.z.bill.util.IDGenerator;

@Service
public class InvestService extends BaseService {

    @Autowired
    private InvestDAO investDAO;
    @Autowired
    private ProductDAO productDAO;

    /**
     * 产品投资
     * 
     * @param tradeDTO
     * @return
     */
    public TradeResponseDTO invest(TradeDTO tradeDTO) {

        TradeResponseDTO tradeResponseDTO = new TradeResponseDTO();
        Product product = productDAO.selectByPrimaryKey(tradeDTO.getProductId());
        Invest invest = new Invest();
        invest.setInvestAmount(tradeDTO.getInvestAmount());
        invest.setRecivedPrincipal(tradeDTO.getInvestAmount());
        BigDecimal interest = DecimalUtil.format(ArithmeticUtils.arithmeticInterest(tradeDTO.getInvestAmount()
                .doubleValue(), product.getDeadline(), product.getAnnualRate().doubleValue(), 1));
        invest.setRecievedInterest(interest);
        invest.setProductId(tradeDTO.getProductId());
        invest.setProductName(product.getProductName());
        invest.setDeadline(product.getDeadline());
        invest.setProductType(product.getProductType());
        invest.setRepaymentType(product.getRepaymentType());
        invest.setClientVersion(tradeDTO.getClientVersion());
        invest.setPlatform(tradeDTO.getPlatform());
        invest.setInvestDate(new Date());
        String txnId = IDGenerator.generalInvestOrderId(tradeDTO.getUserId());
        invest.setOrderNo(txnId);
        Long investId = investDAO.insert(invest);

        // TODO 投资用户账户扣钱
        // 投资产品扣钱
        investProduct(tradeDTO);
        tradeResponseDTO.setStatus((int) InvestResponeEnum.SUCCESS.getStatus());
        tradeResponseDTO.setResultCode(InvestResponeEnum.SUCCESS.getResultCode());
        tradeResponseDTO.setResultDetails(InvestResponeEnum.SUCCESS.getResultDetails());
        return tradeResponseDTO;
    }

    @Transactional
    private void investProduct(TradeDTO tradeDTO) {
        Product product = productDAO.selectForUpdate(tradeDTO.getProductId());
        BigDecimal surplusAmount = product.getSurplusAmount().subtract(tradeDTO.getInvestAmount());// 产品剩余可投金额
        BigDecimal investTotalAmount = product.getInvestTotalAmount().add(tradeDTO.getInvestAmount());// 产品已投资总额
        BigDecimal investSchedule = investTotalAmount.divide(product.getAmount(), BigDecimal.ROUND_HALF_DOWN);// 投资进度
        productDAO.updateProduct(tradeDTO.getProductId(), surplusAmount, investTotalAmount, investSchedule);

    }

}
