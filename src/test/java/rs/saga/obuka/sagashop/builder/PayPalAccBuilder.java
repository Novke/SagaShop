package rs.saga.obuka.sagashop.builder;

import rs.saga.obuka.sagashop.domain.PayPalAccount;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PayPalAccBuilder {

    public static PayPalAccount simplePayPal(){
        PayPalAccount payPalAccount = new PayPalAccount();
        payPalAccount.setBudget(new BigDecimal(10000));
        payPalAccount.setAccountNumber("NUMBER123");
        payPalAccount.setLanguage("ENG");
        payPalAccount.setExpiresOn(LocalDate.of(2028,11,10));
        return payPalAccount;
    }

}
