package x.y.z.bill.mapper.payment;

import org.springframework.stereotype.Repository;

import x.y.z.bill.model.payment.ChannelQuickAgreement;

@Repository
public interface ChannelQuickAgreementDAO {
    int insert(ChannelQuickAgreement record);

    int insertSelective(ChannelQuickAgreement record);

    ChannelQuickAgreement selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ChannelQuickAgreement record);

    int updateByPrimaryKey(ChannelQuickAgreement record);
}