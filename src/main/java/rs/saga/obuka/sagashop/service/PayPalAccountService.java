package rs.saga.obuka.sagashop.service;

import rs.saga.obuka.sagashop.domain.PayPalAccount;
import rs.saga.obuka.sagashop.dto.paypal.CreatePayPalAccountCmd;
import rs.saga.obuka.sagashop.dto.paypal.PayPalAccountInfo;
import rs.saga.obuka.sagashop.dto.paypal.PayPalAccountResult;
import rs.saga.obuka.sagashop.dto.paypal.UpdatePayPalAccountCmd;
import rs.saga.obuka.sagashop.exception.ServiceException;

import java.util.List;

public interface PayPalAccountService {

    PayPalAccount save(CreatePayPalAccountCmd cmd) throws ServiceException;

    List<PayPalAccountResult> findAll();

    PayPalAccountInfo findById(Long id);

    void update(UpdatePayPalAccountCmd payPalAccountDTO) throws ServiceException;

    void delete(Long id) throws ServiceException;
}
