package x.y.z.bill.mapper.payment;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import x.y.z.bill.model.payment.PermissionRestrict;

@Repository
public interface PermissionRestrictDAO {

    PermissionRestrict queryPermissionRestrictByBankCardNo(@Param("businessCode") String businessCode,
            @Param("bankCardNo") String bankCardNo);

    PermissionRestrict queryPermissionRestrictByUser(@Param("businessCode") String businessCode,
            @Param("userId") Long userId);
}