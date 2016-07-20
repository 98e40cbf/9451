package x.y.z.bill.service.payment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.y.z.bill.mapper.payment.BankCodeDictDAO;
import x.y.z.bill.model.payment.BankCodeDict;
import io.alpha.service.BaseService;
import io.alpha.tx.annotation.TransMark;

/**
 * Created by yuanxw on 2016/5/12.
 */
@Service
@TransMark
public class BankCodeDictService extends BaseService {

    public static final Map<String, String> BANK_CODE_DICT_MAPPING = new HashMap<>();
    @Autowired
    private BankCodeDictDAO bankCodeDictDAO;

    @PostConstruct
    private void loadBankCardBinConfig() {
        List<BankCodeDict> configs = bankCodeDictDAO.queryAllBankCodeDictConfigs();
        logger.info("------ 系统初始化加载银行字典数据 [{}] 条 ------ ", configs.size());
        for (BankCodeDict dict : configs) {
            BANK_CODE_DICT_MAPPING.put(dict.getBankCode(), dict.getBankName());
        }
    }

    /**
     * 获取银行卡字典中名称
     * 
     * @param bankCode
     * @return
     */
    public String mappingBankName(String bankCode) {
        return BANK_CODE_DICT_MAPPING.get(bankCode);
    }

}
