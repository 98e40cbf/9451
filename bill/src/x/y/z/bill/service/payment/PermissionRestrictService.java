package x.y.z.bill.service.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import x.y.z.bill.mapper.payment.PermissionRestrictDAO;
import x.y.z.bill.model.payment.PermissionRestrict;
import io.alpha.security.util.EncryptionUtils;
import io.alpha.service.BaseService;
import io.alpha.tx.annotation.TransMark;

/**
 * Created by yuanxw on 2016/7/12.
 */
@Service
@TransMark
public class PermissionRestrictService extends BaseService {

    @Autowired
    private PermissionRestrictDAO permissionRestrictDAO;

    public PermissionRestrict restrict(String businessCode, String bankCardNo) {
        try {
            bankCardNo = EncryptionUtils.encryptByAES(bankCardNo);
        } catch (Exception e) {
            throw new SecurityException(e);
        }
        return permissionRestrictDAO.queryPermissionRestrictByBankCardNo(businessCode, bankCardNo);
    }

    public PermissionRestrict restrict(String businessCode, Long userId) {
        return permissionRestrictDAO.queryPermissionRestrictByUser(businessCode, userId);
    }
}
