package x.y.z.bill.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.alpha.core.dto.PageResultDTO;
import io.alpha.log.annotation.IgnoreLog;
import io.alpha.mybatis.session.CountBounds;
import io.alpha.security.util.EncryptionUtils;
import io.alpha.service.BaseService;
import io.alpha.util.NetworkUtils;
import io.alpha.validation.ValidationUtils;
import x.y.z.bill.command.LoginForm;
import x.y.z.bill.command.RealnameForm;
import x.y.z.bill.command.RegistForm;
import x.y.z.bill.constant.BizType;
import x.y.z.bill.constant.IdCardType;
import x.y.z.bill.dto.AddMoneyDTO;
import x.y.z.bill.dto.FreezeMoneyDTO;
import x.y.z.bill.dto.ModifyMobileDTO;
import x.y.z.bill.dto.ModifyPasswordDTO;
import x.y.z.bill.dto.UnfreezeMoneyDTO;
import x.y.z.bill.dto.UserSession;
import x.y.z.bill.model.account.CapitalJournal;
import x.y.z.bill.model.account.User;
import x.y.z.bill.model.account.UserExtra;
import x.y.z.bill.util.ExceptionUtil;
import x.y.z.bill.util.SensitiveWords;

@IgnoreLog
@Service
public class AccountService extends BaseService {

    @Autowired
    private UserService userService;
    @Autowired
    private CapitalService capitalService;

    public boolean regist(final RegistForm registForm) {
        if (SensitiveWords.exists(registForm.getUsername())) {
            return false;
        }
        try {
            userService.create(registForm.getUsername(), registForm.getMobile(), registForm.getPassword());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public UserSession login(final LoginForm loginForm, final String loginIp, final String browser) throws Exception {
        String loginId = loginForm.getLoginId();
        try {
            User user;
            if (loginId.matches("\\d+")) {
                user = userService.queryByMobile(EncryptionUtils.encryptByAES(loginId));
            } else {
                user = userService.queryByName(loginId);
            }
            if (user != null) {
                if (EncryptionUtils.verifyPassword(loginForm.getPassword(), user.getLoginPwd())) {
                    LoginHistoryService.record(user.getId(), convert(loginIp), browser);
                    return new UserSession(user.getId(), user.getUsername(), user.getMobile());
                }
            }
        } catch (Exception e) {
            logger.catching(e);
        }
        return UserSession.NONE;
    }

    private long convert(final String reqIp) {
        if (NetworkUtils.isValidIPv4(reqIp)) {
            String[] ip = reqIp.split("\\.");
            return Long.parseLong(String.format("%03d%03d%03d%03d", Integer.parseInt(ip[0]), Integer.parseInt(ip[1]),
                    Integer.parseInt(ip[2]), Integer.parseInt(ip[3])));
        }
        return 0L;
    }

    public boolean realname(final long userId, final RealnameForm realnameForm) {
        try {
            userService.realNameAuth(userId, realnameForm.getRealName(), realnameForm.getIdCardNo(),
                    IdCardType.IDENTITY_CARD);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean add(final AddMoneyDTO addMoneyDTO) {
        try {
            ValidationUtils.validate(addMoneyDTO);
            capitalService.add(addMoneyDTO.getUserId(), addMoneyDTO.getAmount(), addMoneyDTO.getTxnId(),
                    addMoneyDTO.getMemo(), addMoneyDTO.getBizType());
        } catch (Exception e) {
            logger.catching(e);
            if (ExceptionUtil.isDuplicateKey(e)) {
                return true;
            }
            return false;
        }
        return true;
    }

    public boolean freeze(final FreezeMoneyDTO freeMoneyDTO) {
        try {
            ValidationUtils.validate(freeMoneyDTO);
            capitalService.freeze(freeMoneyDTO.getUserId(), freeMoneyDTO.getAmount(), freeMoneyDTO.getTxnId(),
                    freeMoneyDTO.getMemo(), freeMoneyDTO.getBizType());
        } catch (Exception e) {
            logger.catching(e);
            if (ExceptionUtil.isDuplicateKey(e)) {
                return true;
            }
            return false;
        }
        return true;
    }

    public boolean unfreeze(final UnfreezeMoneyDTO unfreezeMoneyDTO) {
        try {
            ValidationUtils.validate(unfreezeMoneyDTO);
            capitalService.unfreeze(unfreezeMoneyDTO.getUserId(), unfreezeMoneyDTO.getOrigTxnId(),
                    unfreezeMoneyDTO.getMemo(), unfreezeMoneyDTO.getBizType(), unfreezeMoneyDTO.isBizStatus());
        } catch (Exception e) {
            logger.catching(e);
            if (ExceptionUtil.isDuplicateKey(e)) {
                return true;
            }
            return false;
        }
        return true;
    }

    public boolean modifyLoginPassword(final ModifyPasswordDTO modifyPasswordDTO) {
        try {
            ValidationUtils.validate(modifyPasswordDTO);
            return userService.modifyLoginPassword(modifyPasswordDTO.getUserId(), modifyPasswordDTO.getOldPassword(),
                    modifyPasswordDTO.getNewPassword()) == 1;
        } catch (Exception e) {
            logger.catching(e);
        }
        return false;
    }

    public boolean modifyPaymentPassword(final ModifyPasswordDTO modifyPasswordDTO) {
        try {
            ValidationUtils.validate(modifyPasswordDTO);
            return userService.modifyPaymentPassword(modifyPasswordDTO.getUserId(), modifyPasswordDTO.getOldPassword(),
                    modifyPasswordDTO.getNewPassword()) == 1;
        } catch (Exception e) {
            logger.catching(e);
        }
        return false;
    }

    public PageResultDTO<CapitalJournal> loadAllCapitalJournal(final Long userId) {
        try {
            return capitalService.queryJournalByUserId(userId, (byte) 0, new CountBounds(0, 10));
        } catch (Exception e) {
            logger.catching(e);
            return new PageResultDTO<>();
        }
    }

    public PageResultDTO<CapitalJournal> loadRechargeCapitalJournal(final Long userId) {
        try {
            return capitalService.queryJournalByUserId(userId, BizType.RECHARGE.getCode(), new CountBounds(0, 10));
        } catch (Exception e) {
            logger.catching(e);
            return new PageResultDTO<>();
        }
    }

    public PageResultDTO<CapitalJournal> loadWithdrawCapitalJournal(final Long userId) {
        try {
            return capitalService.queryJournalByUserId(userId, BizType.WITHDRAW_UNFREEZE.getCode(),
                    new CountBounds(0, 10));
        } catch (Exception e) {
            logger.catching(e);
            return new PageResultDTO<>();
        }
    }

    public UserExtra queryExtra(final Long userId) {
        try {
            return userService.queryExtra(userId);
        } catch (Exception e) {
            logger.catching(e);
            return new UserExtra();
        }
    }

    public User queryUser(final Long id) {
        try {
            return userService.queryUser(id);
        } catch (Exception e) {
            logger.catching(e);
            return new User();
        }
    }

    public boolean modifyMobile(final ModifyMobileDTO modifyMobileDTO) {
        try {
            ValidationUtils.validate(modifyMobileDTO);
            boolean status = userService.modifyMobile(modifyMobileDTO.getUserId(), modifyMobileDTO.getOldMobile(),
                    modifyMobileDTO.getNewMobile()) == 1;
            if (status) {
                MobileChangeHistoryService.record(modifyMobileDTO.getUserId(), modifyMobileDTO.getOldMobile(),
                        modifyMobileDTO.getNewMobile());
            }
            return status;
        } catch (Exception e) {
            logger.catching(e);
            return false;
        }
    }

}
