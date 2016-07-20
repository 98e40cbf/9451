package x.y.z.bill.service.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.y.z.bill.mapper.payment.BankCardInfoDAO;
import x.y.z.bill.model.payment.BankCardInfo;
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
        return bankCardInfoDAO.countActivatedBankCardInfoByUser(bankCardNo, userId) > 0 ? true : false;
    }

    public BankCardInfo save(BankCardInfo bankCardInfo) {
        return bankCardInfoDAO.insertSelective(bankCardInfo);

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
        return bankCardInfoDAO.queryByTxnId(txnId);
    }

    public BankCardInfo queryUserBankCardBySucceed(Long userId) {
        return bankCardInfoDAO.queryUserBankCardBySucceed(userId);
    }

    public void updateBankCardInfoSucceed(String txnId) {
        BankCardInfo bankCardInfo = bankCardInfoDAO.queryByTxnId(txnId);
        bankCardInfoDAO.updateBankCardInfoSucceed(txnId, bankCardInfo.getVersion());
    }

    public BankCardInfo queryById(Long id) {
        return bankCardInfoDAO.queryById(id);
    }
}
