package x.y.z.bill.service.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.y.z.bill.mapper.payment.BankCardInfoDAO;
import x.y.z.bill.model.payment.BankCardInfo;
import io.alpha.security.util.EncryptionUtils;
import io.alpha.service.BaseService;
import io.alpha.tx.annotation.TransMark;

/**
 * Created by yuanxw on 2016/5/12.
 */
@Service
@TransMark
public class BankCardInfoService extends BaseService {

    @Autowired
    private BankCardInfoDAO bankCardInfoDAO;

    /**
     * 判断该卡是否存在其他人使用
     *
     * @param bankCardNo
     * @param userId
     * @return
     */
    public boolean isOtherPeopleUseCard(String bankCardNo, Long userId) {
        try {
            bankCardNo = EncryptionUtils.encryptByAES(bankCardNo);
        } catch (Exception e) {
            throw new SecurityException(e);
        }
        return bankCardInfoDAO.countActivatedBankCardInfoByUser(bankCardNo, userId) > 0 ? true : false;
    }

    public BankCardInfo save(BankCardInfo bankCardInfo) {
        bankCardInfo.encrypt();
        bankCardInfoDAO.insertSelective(bankCardInfo);
        bankCardInfo.decrypt();
        return bankCardInfo;
    }

    public void updateBankCardInfoFailed(String txnId) {
        BankCardInfo bankCardInfo = bankCardInfoDAO.queryByTxnId(txnId);
        bankCardInfoDAO.updateBankCardInfoFailed(txnId, bankCardInfo.getVersion());
    }

    public void updateBankCardInfoHanding(String txnId) {
        BankCardInfo bankCardInfo = bankCardInfoDAO.queryByTxnId(txnId);
        bankCardInfoDAO.updateBankCardInfoHanding(txnId, bankCardInfo.getVersion());
    }

    public BankCardInfo queryByTxnId(String txnId) {
        BankCardInfo bankCardInfo = bankCardInfoDAO.queryByTxnId(txnId);
        if (bankCardInfo != null) {
            bankCardInfo.decrypt();
        }
        return bankCardInfo;
    }

    public BankCardInfo queryUserBankCardBySucceed(Long userId) {
        BankCardInfo bankCardInfo = bankCardInfoDAO.queryUserBankCardBySucceed(userId);
        if (bankCardInfo != null) {
            bankCardInfo.decrypt();
        }
        return bankCardInfo;
    }

    public void updateBankCardInfoSucceed(String txnId) {
        BankCardInfo bankCardInfo = bankCardInfoDAO.queryByTxnId(txnId);
        bankCardInfoDAO.updateBankCardInfoSucceed(txnId, bankCardInfo.getVersion());
    }

    public BankCardInfo queryById(Long id) {
        BankCardInfo bankCardInfo = bankCardInfoDAO.queryById(id);
        if (bankCardInfo != null) {
            bankCardInfo.decrypt();
        }
        return bankCardInfo;
    }
}
