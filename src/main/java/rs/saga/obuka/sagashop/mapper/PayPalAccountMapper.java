package rs.saga.obuka.sagashop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import rs.saga.obuka.sagashop.domain.PayPalAccount;
import rs.saga.obuka.sagashop.dto.paypal.CreatePayPalAccountCmd;
import rs.saga.obuka.sagashop.dto.paypal.PayPalAccountInfo;
import rs.saga.obuka.sagashop.dto.paypal.PayPalAccountResult;

import java.util.List;

@Mapper
public interface PayPalAccountMapper {

    PayPalAccountMapper INSTANCE = Mappers.getMapper(PayPalAccountMapper.class);

    @Mapping(target = "id", ignore = true)
    PayPalAccount createPayPalAccountCmdToPayPalAccount(CreatePayPalAccountCmd cmd);

    List<PayPalAccountResult> listPayPalAccountPayPalResult(List<PayPalAccount> payPalAccounts);

    PayPalAccountInfo payPalAccountToPayPalAccountInfo(PayPalAccount payPalAccount);

    void updatePayPalAccountInfoToPayPalAccount(@MappingTarget PayPalAccount payPalAccount, PayPalAccountInfo payPalAccountInfo);

}
