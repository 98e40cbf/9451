package x.y.z.bill.mapper.payment;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.payment.PermissionRestrict;

@Repository
public interface PermissionRestrictDAO {
    int insert(PermissionRestrict record);

    int insertSelective(PermissionRestrict record);

    PermissionRestrict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PermissionRestrict record);

    int updateByPrimaryKey(PermissionRestrict record);
}