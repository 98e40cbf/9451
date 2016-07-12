package x.y.z.bill.service.payment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.y.z.bill.mapper.payment.BankCardBinDAO;
import x.y.z.bill.model.payment.BankCardBin;
import io.alpha.service.BaseService;
import io.alpha.tx.annotation.TransMark;

/**
 * Created by yuanxw on 2016/5/12.
 */
@Service
@TransMark
public class BankCardBinService extends BaseService {

    @Autowired
    private BankCardBinDAO bankCardBinDAO;

    public static final Map<String, BankCardBin> CARD_BIN_MAPPING = new HashMap<>();

    @PostConstruct
    private void loadBankCardBinConfig() {
        List<BankCardBin> configs = bankCardBinDAO.queryEnabledCardBinConfigs();
        logger.info("------ 系统初始化加载卡BIN数据 [{}] 条 ------ ", configs.size());
        for (BankCardBin bin : configs) {
            CARD_BIN_MAPPING.put(bin.getIdentifier(), bin);
        }
    }

    /**
     * 卡BIN识别
     *
     * @param bankCardNo
     * @return
     */
    public BankCardBin recognition(String bankCardNo) {
        for (int index = Math.min(10, bankCardNo.length()); index > 2; index--) {
            String shortCardNo = bankCardNo.substring(0, index);
            BankCardBin cardBin = CARD_BIN_MAPPING.get(shortCardNo);
            if (cardBin != null) {
                return cardBin;
            }
        }
        return null;
    }

    /**
     * 判断是否为信用卡
     *
     * @param bankCardNo
     * @return
     */
    public boolean isCreditCard(String bankCardNo) {
        BankCardBin bin = recognition(bankCardNo);
        return bin != null && bin.getType() != null && bin.getType().intValue() == 0;
    }

}
