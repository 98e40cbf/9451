/*******************************************************************************
 * Created on 2016年4月7日 下午5:35:19
 * Copyright (c) 深圳市小牛在线互联网信息咨询有限公司版权所有. 粤ICP备13089339号
 * 注意：本内容仅限于深圳市小牛在线互联网信息咨询有限公司内部传阅，禁止外泄以及用于其他商业目的!
 ******************************************************************************/
package x.y.z.bill.adapter.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import x.y.z.bill.adapter.ChannelAdapterFacade;
import x.y.z.bill.adapter.channel.Adapter;
import x.y.z.bill.adapter.channel.dto.TreatyInfoDTO;
import x.y.z.bill.adapter.channel.dto.request.*;
import x.y.z.bill.adapter.channel.dto.response.*;
import x.y.z.bill.adapter.config.BaseChannelConfig;
import x.y.z.bill.adapter.config.ConfigManager;
import x.y.z.bill.adapter.constant.CommonConstant;
import x.y.z.bill.adapter.util.RequestContext;
import io.alpha.exception.FastCheckedException;
import io.alpha.service.BaseService;
import io.alpha.validation.ValidationUtils;

/**
 * 请求渠道接口
 * 
 */
@Service(value = "channelAdapterFacade")
public class ChannelAdapterFacadeImpl extends BaseService implements ChannelAdapterFacade, ApplicationContextAware {

    @Autowired
    private ConfigManager configManager;
    private ApplicationContext context;

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDTO<AuthResponseDTO> applyAuth(AuthRequestDTO dto) {
        RequestContext.reset(dto);

        // 1.校验参数，校验签名
        try {
            ValidationUtils.validate(dto);
            ValidationUtils.validate(dto.getTreatyInfo());
        } catch (FastCheckedException e) {
            return ResponseDTO.buildFail(new AuthResponseDTO(dto), "PARAM_ERROR", e.getMessage());
        }

        Adapter adapter = this.getAdapter(dto.getChannelCode());
        if (adapter == null) {
            return ResponseDTO.buildFail(new AuthResponseDTO(dto), "CHANNEL_UNEXIST", "支付渠道不存在");
        }
        try {
            BaseChannelConfig config = adapter.getConfig(dto);
            if (config == null) {
                return ResponseDTO.buildFail(new AuthResponseDTO(dto), "CONFIG_ERROR", "找不到配置信息");
            }
        } catch (Exception e) {
            logger.error("订单号[{}]获取渠道配置信息出错.", dto.getTxnId(), e);
            return ResponseDTO.buildFail(new AuthResponseDTO(dto), "CONFIG_ERROR", "获取配置信息出错");
        }

        return adapter.applyAuth(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDTO<AuthConfirmResponseDTO> confirmAuth(AuthConfirmRequestDTO dto) {
        RequestContext.reset(dto);

        try {
            ValidationUtils.validate(dto);
            ValidationUtils.validate(dto.getTreatyInfo());
        } catch (FastCheckedException e) {
            return ResponseDTO.buildFail(new AuthConfirmResponseDTO(dto), "PARAM_ERROR", e.getMessage());
        }
        // 1.校验参数，校验签名
        Adapter adapter = this.getAdapter(dto.getChannelCode());
        if (adapter == null) {
            return ResponseDTO.buildFail(new AuthConfirmResponseDTO(dto), "CHANNEL_UNEXIST", "支付渠道不存在");
        }
        try {

            BaseChannelConfig config = adapter.getConfig(dto);
            if (config == null) {
                return ResponseDTO.buildFail(new AuthConfirmResponseDTO(dto), "CONFIG_ERROR", "找不到配置信息");
            }
        } catch (Exception e) {
            logger.error("订单号[{}]获取渠道配置信息出错.", dto.getTxnId(), e);
            return ResponseDTO.buildFail(new AuthConfirmResponseDTO(dto), "CONFIG_ERROR", "获取配置信息出错");
        }

        return adapter.confirmAuth(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDTO<QueryAuthInfoResponseDTO> queryAuthInfo(AuthRequestDTO dto) {
        RequestContext.reset(dto);

        try {
            ValidationUtils.validate(dto);
            ValidationUtils.validate(dto.getTreatyInfo());
        } catch (FastCheckedException e) {
            return ResponseDTO.buildFail(new QueryAuthInfoResponseDTO(dto), "PARAM_ERROR", e.getMessage());
        }
        // 1.校验参数，校验签名
        Adapter adapter = this.getAdapter(dto.getChannelCode());
        if (adapter == null) {
            return ResponseDTO.buildFail(new QueryAuthInfoResponseDTO(dto), "CHANNEL_UNEXIST", "支付渠道不存在");
        }
        try {
            BaseChannelConfig config = adapter.getConfig(dto);
            if (config == null) {
                return ResponseDTO.buildFail(new QueryAuthInfoResponseDTO(dto), "CONFIG_ERROR", "找不到配置信息");
            }
        } catch (Exception e) {
            logger.error("订单号[{}]获取渠道配置信息出错.", dto.getTxnId(), e);
            return ResponseDTO.buildFail(new QueryAuthInfoResponseDTO(dto), "CONFIG_ERROR", "获取配置信息出错");
        }

        return adapter.queryAuthInfo(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDTO<TreatyInfoDTO> cancelAuthInfo(AuthRequestDTO dto) {
        RequestContext.reset(dto);

        try {
            ValidationUtils.validate(dto);
            ValidationUtils.validate(dto.getTreatyInfo());
        } catch (FastCheckedException e) {
            return ResponseDTO.buildFail(dto.getTreatyInfo(), "PARAM_ERROR", e.getMessage());
        }
        // 1.校验参数，校验签名
        Adapter adapter = this.getAdapter(dto.getChannelCode());
        if (adapter == null) {
            return ResponseDTO.buildFail(dto.getTreatyInfo(), "CHANNEL_UNEXIST", "支付渠道不存在");
        }
        try {
            BaseChannelConfig config = adapter.getConfig(dto);
            if (config == null) {
                return ResponseDTO.buildFail(dto.getTreatyInfo(), "CONFIG_ERROR", "找不到配置信息");
            }
        } catch (Exception e) {
            logger.error("订单号[{}]获取渠道配置信息出错.", dto.getTxnId(), e);
            return ResponseDTO.buildFail(dto.getTreatyInfo(), "CONFIG_ERROR", "获取配置信息出错");
        }

        return adapter.cancelAuthInfo(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDTO<QuickPayResponseDTO> applyQuickPay(QuickPayRequestDTO dto) {
        RequestContext.reset(dto);
        try {
            ValidationUtils.validate(dto);
            ValidationUtils.validate(dto.getTreatyInfo());
        } catch (FastCheckedException e) {
            return ResponseDTO.buildFail(new QuickPayResponseDTO(), "PARAM_ERROR", e.getMessage());
        }
        // 1.校验参数，校验签名
        Adapter adapter = this.getAdapter(dto.getChannelCode());
        if (adapter == null) {
            return ResponseDTO.buildFail(new QuickPayResponseDTO(), "CHANNEL_UNEXIST", "支付渠道不存在");
        }
        try {
            BaseChannelConfig config = adapter.getConfig(dto);
            if (config == null) {
                return ResponseDTO.buildFail(new QuickPayResponseDTO(), "CONFIG_ERROR", "找不到配置信息");
            }
        } catch (Exception e) {
            logger.error("订单号[{}]获取渠道配置信息出错.", dto.getTxnId(), e);
            return ResponseDTO.buildFail(new QuickPayResponseDTO(), "CONFIG_ERROR", "获取配置信息出错");
        }

        return adapter.applyQuickPay(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDTO<QuickPayConfirmResponseDTO> confirmQuickPay(QuickPayConfirmRequestDTO dto) {
        RequestContext.reset(dto);
        try {
            ValidationUtils.validate(dto);
            ValidationUtils.validate(dto.getTreatyInfo());
        } catch (FastCheckedException e) {
            return ResponseDTO.buildFail(new QuickPayConfirmResponseDTO(), "PARAM_ERROR", e.getMessage());
        }
        // 1.校验参数，校验签名
        Adapter adapter = this.getAdapter(dto.getChannelCode());
        if (adapter == null) {
            return ResponseDTO.buildFail(new QuickPayConfirmResponseDTO(), "CHANNEL_UNEXIST", "支付渠道不存在");
        }
        try {
            BaseChannelConfig config = adapter.getConfig(dto);
            if (config == null) {
                return ResponseDTO.buildFail(new QuickPayConfirmResponseDTO(), "CONFIG_ERROR", "找不到配置信息");
            }
        } catch (Exception e) {
            logger.error("订单号[{}]获取渠道配置信息出错.", dto.getTxnId(), e);
            return ResponseDTO.buildFail(new QuickPayConfirmResponseDTO(), "CONFIG_ERROR", "获取配置信息出错");
        }

        return adapter.confirmQuickPay(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDTO<WithdrawResponseDTO> withdraw(WithdrawRequestDTO dto) {
        RequestContext.reset(dto);
        try {
            ValidationUtils.validate(dto);
            ValidationUtils.validate(dto.getTreatyInfo());
        } catch (FastCheckedException e) {
            return ResponseDTO.buildFail(new WithdrawResponseDTO(), "PARAM_ERROR", e.getMessage());
        }
        // 1.校验参数，校验签名
        Adapter adapter = this.getAdapter(dto.getChannelCode());
        if (adapter == null) {
            return ResponseDTO.buildFail(new WithdrawResponseDTO(), "CHANNEL_UNEXIST", "支付渠道不存在");
        }
        try {
            BaseChannelConfig config = adapter.getConfig(dto);
            if (config == null) {
                return ResponseDTO.buildFail(new WithdrawResponseDTO(), "CONFIG_ERROR", "找不到配置信息");
            }
        } catch (Exception e) {
            logger.error("订单号[{}]获取渠道配置信息出错.", dto.getTxnId(), e);
            return ResponseDTO.buildFail(new WithdrawResponseDTO(), "CONFIG_ERROR", "获取配置信息出错");
        }

        return adapter.withdraw(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDTO<PayQueryResponseDTO> queryPayOrder(PayQueryRequestDTO dto) {
        RequestContext.reset(dto);
        try {
            ValidationUtils.validate(dto);
        } catch (FastCheckedException e) {
            return ResponseDTO.buildFail(new PayQueryResponseDTO(), "PARAM_ERROR", e.getMessage());
        }
        // 1.校验参数，校验签名
        Adapter adapter = this.getAdapter(dto.getChannelCode());
        if (adapter == null) {
            return ResponseDTO.buildFail(new PayQueryResponseDTO(), "CHANNEL_UNEXIST", "支付渠道不存在");
        }
        try {
            BaseChannelConfig config = adapter.getConfig(dto);
            if (config == null) {
                return ResponseDTO.buildFail(new PayQueryResponseDTO(), "CONFIG_ERROR", "找不到配置信息");
            }
        } catch (Exception e) {
            logger.error("订单号[{}]获取渠道配置信息出错.", dto.getTxnId(), e);
            return ResponseDTO.buildFail(new PayQueryResponseDTO(), "CONFIG_ERROR", "获取配置信息出错");
        }

        return adapter.queryPayOrder(dto);
    }

    @Override
    public ResponseDTO<EBankApplyPayResponseDTO> generatePayUrl(EBankApplyPayRequestDTO dto) {
        RequestContext.reset(dto);
        try {
            ValidationUtils.validate(dto);
        } catch (FastCheckedException e) {
            return ResponseDTO.buildFail(new EBankApplyPayResponseDTO(dto), "PARAM_ERROR", e.getMessage());
        }
        // 1.校验参数，校验签名
        Adapter adapter = this.getAdapter(dto.getChannelCode());
        if (adapter == null) {
            return ResponseDTO.buildFail(new EBankApplyPayResponseDTO(dto), "CHANNEL_UNEXIST", "支付渠道不存在");
        }
        try {
            BaseChannelConfig config = adapter.getConfig(dto);
            if (config == null) {
                return ResponseDTO.buildFail(new EBankApplyPayResponseDTO(dto), "CONFIG_ERROR", "找不到配置信息");
            }
        } catch (Exception e) {
            logger.error("订单号[{}]获取渠道配置信息出错.", dto.getTxnId(), e);
            return ResponseDTO.buildFail(new EBankApplyPayResponseDTO(dto), "CONFIG_ERROR", "获取配置信息出错");
        }

        return adapter.generatePayUrl(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDTO<Boolean> downloadRecon(ReconDownloadRequestDTO dto) {
        RequestContext.reset(dto);
        try {
            ValidationUtils.validate(dto);
        } catch (FastCheckedException e) {
            return ResponseDTO.buildFail(false, "PARAM_ERROR", e.getMessage());
        }

        return ResponseDTO.buildSuccess(true, "", "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDTO<PayQueryResponseDTO> payCallback(CallbackRequestDTO dto) {
        RequestContext.reset(dto);
        try {
            ValidationUtils.validate(dto);
        } catch (FastCheckedException e) {
            return ResponseDTO.buildFail(new PayQueryResponseDTO(), "PARAM_ERROR", e.getMessage());
        }
        // 1.校验参数，校验签名
        Adapter adapter = this.getAdapter(dto.getChannelCode());
        if (adapter == null) {
            return ResponseDTO.buildFail(new PayQueryResponseDTO(), "CHANNEL_UNEXIST", "支付渠道不存在");
        }
        try {
            BaseChannelConfig config = adapter.getConfig(dto);
            if (config == null) {
                return ResponseDTO.buildFail(new PayQueryResponseDTO(), "CONFIG_ERROR", "找不到配置信息");
            }
        } catch (Exception e) {
            logger.error("订单号[{}]获取渠道配置信息出错.", dto.getTxnId(), e);
            return ResponseDTO.buildFail(new PayQueryResponseDTO(), "CONFIG_ERROR", "获取配置信息出错");
        }

        return adapter.payCallback(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDTO<PayQueryResponseDTO> payNotify(PayNotifyRequestDTO dto) {
        RequestContext.reset(dto);
        try {
            ValidationUtils.validate(dto);
        } catch (FastCheckedException e) {
            return ResponseDTO.buildFail(new PayQueryResponseDTO(), "PARAM_ERROR", e.getMessage());
        }
        // 1.校验参数，校验签名
        Adapter adapter = this.getAdapter(dto.getChannelCode());
        if (adapter == null) {
            return ResponseDTO.buildFail(new PayQueryResponseDTO(), "CHANNEL_UNEXIST", "支付渠道不存在");
        }
        try {
            BaseChannelConfig config = adapter.getConfig(dto);
            if (config == null) {
                return ResponseDTO.buildFail(new PayQueryResponseDTO(), "CONFIG_ERROR", "找不到配置信息");
            }
        } catch (Exception e) {
            logger.error("订单号[{}]获取渠道配置信息出错.", dto.getTxnId(), e);
            return ResponseDTO.buildFail(new PayQueryResponseDTO(), "CONFIG_ERROR", "获取配置信息出错");
        }

        return adapter.payNotify(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDTO<OnlineSignResponseDTO> onlineSign(OnlineSignRequestDTO dto) {
        RequestContext.reset(dto);
        try {
            ValidationUtils.validate(dto);
        } catch (FastCheckedException e) {
            return ResponseDTO.buildFail(new OnlineSignResponseDTO(), "PARAM_ERROR", e.getMessage());
        }
        // 1.校验参数，校验签名
        Adapter adapter = this.getAdapter(dto.getChannelCode());
        if (adapter == null) {
            return ResponseDTO.buildFail(new OnlineSignResponseDTO(), "CHANNEL_UNEXIST", "支付渠道不存在");
        }
        try {
            BaseChannelConfig config = adapter.getConfig(dto);
            if (config == null) {
                return ResponseDTO.buildFail(new OnlineSignResponseDTO(), "CONFIG_ERROR", "找不到配置信息");
            }
        } catch (Exception e) {
            logger.error("订单号[{}]获取渠道配置信息出错.", dto.getTxnId(), e);
            return ResponseDTO.buildFail(new OnlineSignResponseDTO(), "CONFIG_ERROR", "获取配置信息出错");
        }

        return adapter.onlineSign(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDTO<WithdrawQueryRespDTO> withdrawQuery(WithdrawRequestDTO dto, String channelRecevOrder) {
        RequestContext.reset(dto);
        try {
            ValidationUtils.validate(dto);
        } catch (FastCheckedException e) {
            return ResponseDTO.buildFail(new WithdrawQueryRespDTO(), "PARAM_ERROR", e.getMessage());
        }
        // 1.校验参数，校验签名
        Adapter adapter = this.getAdapter(dto.getChannelCode());
        if (adapter == null) {
            return ResponseDTO.buildFail(new WithdrawQueryRespDTO(), "CHANNEL_UNEXIST", "支付渠道不存在");
        }
        try {
            BaseChannelConfig config = adapter.getConfig(dto);
            if (config == null) {
                return ResponseDTO.buildFail(new WithdrawQueryRespDTO(), "CONFIG_ERROR", "找不到配置信息");
            }
        } catch (Exception e) {
            logger.error("订单号[{}]获取渠道配置信息出错.", dto.getTxnId(), e);
            return ResponseDTO.buildFail(new WithdrawQueryRespDTO(), "CONFIG_ERROR", "获取配置信息出错");
        }

        return adapter.withdrawQuery(dto, channelRecevOrder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseDTO<WithdrawQueryRespDTO> withdrawQuery(BatchWithdrawQueryRequestDTO dto) {
        RequestContext.reset(dto);
        try {
            ValidationUtils.validate(dto);
        } catch (FastCheckedException e) {
            return ResponseDTO.buildFail(new WithdrawQueryRespDTO(), "PARAM_ERROR", e.getMessage());
        }
        // 1.校验参数，校验签名
        Adapter adapter = this.getAdapter(dto.getChannelCode());
        if (adapter == null) {
            return ResponseDTO.buildFail(new WithdrawQueryRespDTO(), "CHANNEL_UNEXIST", "支付渠道不存在");
        }
        try {
            BaseChannelConfig config = adapter.getConfig(dto);
            if (config == null) {
                return ResponseDTO.buildFail(new WithdrawQueryRespDTO(), "CONFIG_ERROR", "找不到配置信息");
            }
        } catch (Exception e) {
            logger.error("订单号[{}]获取渠道配置信息出错.", dto.getTxnId(), e);
            return ResponseDTO.buildFail(new WithdrawQueryRespDTO(), "CONFIG_ERROR", "获取配置信息出错");
        }

        return adapter.withdrawQuery(dto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private Adapter getAdapter(String channelCode) {
        return this.context.getBean(String.format("%s_%s", CommonConstant.BEAN_SUFFIX, channelCode), Adapter.class);
    }

}
