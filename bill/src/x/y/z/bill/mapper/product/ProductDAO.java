package x.y.z.bill.mapper.product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import x.y.z.bill.model.product.Product;

@Repository
public interface ProductDAO {
    int insert(Product record);

    Product selectByPrimaryKey(Long id);

    List<Product> selectProductList(RowBounds rowBounds);

    /**
     * 投资锁产品信息
     * 
     * @param productId
     * @return
     */
    Product selectForUpdate(Long productId);

    /**
     * 投资修改产品信息
     * 
     * @param productId
     * @param surplusAmount
     * @param investTotalAmount
     * @param investSchedule
     * @return
     */
    int updateProduct(@Param("productId") Long productId, @Param("surplusAmount") BigDecimal surplusAmount,
            @Param("investTotalAmount") BigDecimal investTotalAmount, @Param("investSchedule") BigDecimal investSchedule);

    int updateSoldProduct(@Param("productId") Long productId, @Param("status") Integer status,
            @Param("valueDate") Date valueDate,@Param("fullDate") Date fullDate);

}