package x.y.z.bill.mapper.payment;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import x.y.z.bill.model.payment.ChannelQuickAgreement;

@Repository
public interface ChannelQuickAgreementDAO {
    int insertSelective(ChannelQuickAgreement record);

    int countQuickAgreementByUser(@Param("userId") Long userId, @Param("status") Integer status);

    ChannelQuickAgreement queryByTxnId(String txnId);

    int updateChannelQuickAgreementFailed(@Param("txnId") String txnId, @Param("replyCode") String replyCode,
                                          @Param("replyMessage") String replyMessage, @Param("version") Integer version);

    int updateChannelQuickAgreementHanding(@Param("txnId") String txnId, @Param("version") Integer version);

    int updateChannelQuickAgreementSucceed(@Param("txnId") String txnId, @Param("replyCode") String replyCode,
                                           @Param("replyMessage") String replyMessage, @Param("version") Integer version);

    ChannelQuickAgreement queryUserQuickAgreementBySucceed(Long userId);

    int updateApplyChannelQuickAgreementSucceed(@Param("txnId") String txnId, @Param("token") String token, @Param("replyCode") String replyCode,
                                                @Param("replyMessage") String replyMessage, @Param("version") Integer version);
}