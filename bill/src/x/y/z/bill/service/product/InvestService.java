package x.y.z.bill.service.product;

import io.alpha.service.BaseService;
import io.alpha.tx.annotation.TransMark;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import x.y.z.bill.dto.product.TradeDTO;
import x.y.z.bill.dto.product.TradeResponseDTO;
import x.y.z.bill.mapper.product.InvestDAO;
import x.y.z.bill.mapper.product.ProductDAO;
import x.y.z.bill.model.product.Invest;
import x.y.z.bill.model.product.Product;

@Service
public class InvestService extends BaseService {

	@Autowired
	private InvestDAO investDAO;
	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private PlatformTransactionManager txManager;

	private TransactionStatus openDefaultTransaction() {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = txManager.getTransaction(def);
		return status;
	}

	public TradeResponseDTO invest(TradeDTO tradeDTO) {

		Invest invest = new Invest();
		invest.setProductId(tradeDTO.getProductId());
		invest.setProductName("");
		invest.setInvestAmount(tradeDTO.getInvestAmount());
		invest.setClientVersion(tradeDTO.getClientVersion());
		invest.setPlatform(tradeDTO.getPlatform());
		Long investId = investDAO.insert(invest);
		
		// TODO 投资用户账户扣钱

		return null;
	}

	@Transactional
	private TradeResponseDTO investProduct(TradeDTO tradeDTO) {
		Product product = productDAO.selectForUpdate(tradeDTO.getProductId());
		BigDecimal surplusAmount = product.getSurplusAmount().subtract(
				tradeDTO.getInvestAmount());// 产品剩余可投金额
		BigDecimal investTotalAmount = product.getInvestTotalAmount().add(
				tradeDTO.getInvestAmount());// 产品已投资总额
		BigDecimal investSchedule = investTotalAmount.divide(
				product.getAmount(), BigDecimal.ROUND_HALF_DOWN);// 投资进度
		productDAO.updateProduct(tradeDTO.getProductId(), surplusAmount,
				investTotalAmount, investSchedule);
		return null;
	}
}
