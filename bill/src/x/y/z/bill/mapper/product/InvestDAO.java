package x.y.z.bill.mapper.product;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.product.Invest;

@Repository
public interface InvestDAO {
    Long insert(Invest record);

    Invest selectByPrimaryKey(Long id);

}