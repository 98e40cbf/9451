package x.y.z.bill.mapper.product;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.product.ProductRepayPlan;

@Repository
public interface ProductRepayPlanDAO {
    int insert(ProductRepayPlan record);


    ProductRepayPlan selectByPrimaryKey(Long id);

}