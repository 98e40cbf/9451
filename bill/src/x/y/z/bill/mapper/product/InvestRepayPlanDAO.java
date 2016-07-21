package x.y.z.bill.mapper.product;

import java.util.List;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.product.InvestRepayPlan;

@Repository
public interface InvestRepayPlanDAO {
    int insert(InvestRepayPlan record);

    InvestRepayPlan selectByPrimaryKey(Long id);

    /**
     * 批量插入
     * 
     * @param list
     * @return
     */
    int addInvestRepayBatch(List<InvestRepayPlan> list);

}