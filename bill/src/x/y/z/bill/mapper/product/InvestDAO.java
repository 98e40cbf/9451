package x.y.z.bill.mapper.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import x.y.z.bill.model.product.Invest;

@Repository
public interface InvestDAO {
    Long insert(Invest record);

    Invest selectByPrimaryKey(Long id);

    int updateInvestStatus(@Param("investId") Long investId, @Param("investStatus") Integer investStatus);

    int updateSoldInvestStatus(@Param("productId") Long productId, @Param("investStatus") Integer investStatus);

    Integer selectInvestStatus(Long investId);

    List<Invest> selectInvests(@Param("productId") Long productId, @Param("investStatus") Integer investStatus);
}