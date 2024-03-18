package rs.saga.obuka.sagashop.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.saga.obuka.sagashop.dao.PayPalAccountDAO;
import rs.saga.obuka.sagashop.dao.UserDAO;
import rs.saga.obuka.sagashop.domain.PayPalAccount;
import rs.saga.obuka.sagashop.domain.User;
import rs.saga.obuka.sagashop.dto.paypal.CreatePayPalAccountCmd;
import rs.saga.obuka.sagashop.dto.paypal.PayPalAccountInfo;
import rs.saga.obuka.sagashop.dto.paypal.PayPalAccountResult;
import rs.saga.obuka.sagashop.dto.paypal.UpdatePayPalAccountCmd;
import rs.saga.obuka.sagashop.exception.DAOException;
import rs.saga.obuka.sagashop.exception.ErrorCode;
import rs.saga.obuka.sagashop.exception.ServiceException;
import rs.saga.obuka.sagashop.mapper.PayPalAccountMapper;
import rs.saga.obuka.sagashop.service.PayPalAccountService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PayPalAccountServiceImpl implements PayPalAccountService {

    private final static Logger LOGGER = LoggerFactory.getLogger(PayPalAccountServiceImpl.class);

    private final PayPalAccountDAO payPalAccountDAO;
    private final UserDAO userDAO;


    @Override
    public PayPalAccount save(CreatePayPalAccountCmd cmd) throws ServiceException {
        User user = userDAO.findOne(cmd.getUserId());
        if (user == null) {
            throw new ServiceException(ErrorCode.ERR_GEN_002, "User not found!");
        }
        PayPalAccount payPalAccount = PayPalAccountMapper.INSTANCE.createPayPalAccountCmdToPayPalAccount(cmd);
        payPalAccount.setUser(user);

        try {
            payPalAccount = payPalAccountDAO.save(payPalAccount);
        } catch (Exception e) {
            LOGGER.error(null, e);
            throw new ServiceException(ErrorCode.ERR_GEN_001, "Saving of PayPal account failed!", e);
        }
        return payPalAccount;
    }

    @Override
    public List<PayPalAccountResult> findAll() {
        return PayPalAccountMapper.INSTANCE.listPayPalAccountPayPalResult(payPalAccountDAO.findAll());
    }

    @Override
    public PayPalAccountInfo findById(Long id) {
        return PayPalAccountMapper.INSTANCE.payPalAccountToPayPalAccountInfo(payPalAccountDAO.findOne(id));
    }

    @Override
    public void update(UpdatePayPalAccountCmd payPalAccountDTO) throws ServiceException {
        PayPalAccount payPalAccount;
        try {
            payPalAccount = payPalAccountDAO.findOne(payPalAccountDTO.getId());
            if (payPalAccount == null){
                throw new ServiceException(ErrorCode.ERR_GEN_002, "PayPal account not found!");
            }
            PayPalAccountMapper.INSTANCE.updatePayPalAccountInfoToPayPalAccount(payPalAccount, payPalAccountDTO);
            payPalAccountDAO.merge(payPalAccount);
        } catch (DAOException e) {
            LOGGER.error(null, e);
            throw new ServiceException(ErrorCode.ERR_GEN_001, "Update of PayPal account failed!", e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        PayPalAccount payPalAccount = payPalAccountDAO.findOne(id);
        if (payPalAccount != null) {
            try {
                payPalAccount.getUser().removePayPalAccount();
                payPalAccountDAO.delete(payPalAccount);
            } catch (Exception e) {
                LOGGER.error(null, e);
                throw new ServiceException(ErrorCode.ERR_GEN_001, "Deleting of PayPal account failed!", e);
            }
        } else {
            throw new ServiceException(ErrorCode.ERR_GEN_002, "PayPal account not found!");
        }
    }
}
