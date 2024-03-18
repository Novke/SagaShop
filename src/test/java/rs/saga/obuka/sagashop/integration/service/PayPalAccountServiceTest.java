package rs.saga.obuka.sagashop.integration.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rs.saga.obuka.sagashop.AbstractIntegrationTest;
import rs.saga.obuka.sagashop.domain.PayPalAccount;
import rs.saga.obuka.sagashop.domain.User;
import rs.saga.obuka.sagashop.dto.paypal.CreatePayPalAccountCmd;
import rs.saga.obuka.sagashop.dto.paypal.PayPalAccountInfo;
import rs.saga.obuka.sagashop.dto.paypal.UpdatePayPalAccountCmd;
import rs.saga.obuka.sagashop.dto.user.CreateUserCmd;
import rs.saga.obuka.sagashop.exception.ServiceException;
import rs.saga.obuka.sagashop.service.PayPalAccountService;
import rs.saga.obuka.sagashop.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PayPalAccountServiceTest extends AbstractIntegrationTest {

    @Autowired
    private PayPalAccountService payPalAccountService;
    @Autowired
    private UserService userService;
    private User user;

    @BeforeEach
    public void setUp() throws ServiceException {
        CreateUserCmd cmd = new CreateUserCmd("user", "pass", "name", "lastname");
        user = userService.save(cmd);
    }

    @AfterEach
    public void tearDown() {
        //brise sve preko kaskade
        userService.findAll().forEach(e -> {
            try {
                userService.delete(e.getId());
            } catch (ServiceException serviceException) {
                serviceException.printStackTrace();
            }
        });
    }

    @Test
    public void savePayPalAccount() throws ServiceException {

        CreatePayPalAccountCmd createPayPalAccountCmd = new CreatePayPalAccountCmd("1337", new BigDecimal(10000), user.getId(), LocalDate.of(2027, 5, 5), "SRB" );
        PayPalAccount account = payPalAccountService.save(createPayPalAccountCmd);

        Assertions.assertNotNull(account.getId());
        Assertions.assertEquals(createPayPalAccountCmd.getAccountNumber(), account.getAccountNumber());
        Assertions.assertEquals(createPayPalAccountCmd.getBudget(), account.getBudget());
        Assertions.assertEquals(user.getId(), account.getUser().getId());
        Assertions.assertEquals(createPayPalAccountCmd.getExpiresOn(), account.getExpiresOn());
        Assertions.assertEquals(createPayPalAccountCmd.getCountry(), account.getCountry());
        Assertions.assertEquals(createPayPalAccountCmd.getLanguage(), account.getLanguage());

    }

    @Test
    public void updatePayPalAccount() throws ServiceException {

        CreatePayPalAccountCmd createCmd = new CreatePayPalAccountCmd("1337", new BigDecimal(10000), user.getId(), LocalDate.of(2025, 10, 10), "SRB");
        PayPalAccount account = payPalAccountService.save(createCmd);

        UpdatePayPalAccountCmd updateCmd = new UpdatePayPalAccountCmd(
                account.getId(),
                "String",
                new BigDecimal("20000.00"),
                "ENG",
                LocalDate.of(2027, 5, 5),
                "Belgrade",
                "Serbia");
        payPalAccountService.update(updateCmd);

        PayPalAccountInfo updatedAccount = payPalAccountService.findById(account.getId());

        Assertions.assertEquals(updateCmd.getBudget(), updatedAccount.getBudget());
        Assertions.assertEquals(updateCmd.getLanguage(), updatedAccount.getLanguage());
        Assertions.assertEquals(updateCmd.getExpiresOn(), updatedAccount.getExpiresOn());
        Assertions.assertEquals(updateCmd.getCity(), updatedAccount.getCity());
        Assertions.assertEquals(updateCmd.getCountry(), updatedAccount.getCountry());
        Assertions.assertEquals(updateCmd.getExpiresOn(), updatedAccount.getExpiresOn());
        Assertions.assertEquals(updateCmd.getLanguage(), updatedAccount.getLanguage());
    }

    @Test
    public void deletePayPalAccount() throws ServiceException {
        CreatePayPalAccountCmd createCmd = new CreatePayPalAccountCmd("1337", new BigDecimal(10000), user.getId(), LocalDate.of(2023,11,11), "GER");
        PayPalAccount account = payPalAccountService.save(createCmd);
        Assertions.assertNotNull(account.getId());

        payPalAccountService.delete(account.getId());

        PayPalAccountInfo account2 = payPalAccountService.findById(account.getId());
        Assertions.assertNull(account2);
    }

    @Test
    public void findOne() throws ServiceException {
        CreatePayPalAccountCmd createCmd = new CreatePayPalAccountCmd("1337", new BigDecimal("10000.00"), user.getId(), LocalDate.of(2028,12,12), "RUS");
        PayPalAccount account = payPalAccountService.save(createCmd);
        Assertions.assertNotNull(account.getId());

        PayPalAccountInfo account2 = payPalAccountService.findById(account.getId());
        Assertions.assertNotNull(account2);
        Assertions.assertEquals(account.getAccountNumber(), account2.getAccountNumber());
        Assertions.assertEquals(account.getBudget(), account2.getBudget());
        Assertions.assertEquals(account.getLanguage(), account2.getLanguage());
        Assertions.assertEquals(account.getExpiresOn(), account2.getExpiresOn());
        Assertions.assertEquals(account.getCity(), account2.getCity());
        Assertions.assertEquals(account.getCountry(), account2.getCountry());
    }

    @Test
    public void findAll() throws ServiceException {
        CreatePayPalAccountCmd createCmd = new CreatePayPalAccountCmd("1337", new BigDecimal(10000), user.getId(), LocalDate.of(2028,12,12), "RUS");
        payPalAccountService.save(createCmd);

        CreateUserCmd createUserCmd = new CreateUserCmd("user2", "pass2", "name2", "lastname2");
        User user2 = userService.save(createUserCmd);

        CreatePayPalAccountCmd createCmd2 = new CreatePayPalAccountCmd("1338", new BigDecimal(10000), user2.getId(), LocalDate.of(2028,12,12), "RUS");
        payPalAccountService.save(createCmd2);

        Assertions.assertEquals(2, payPalAccountService.findAll().size());

    }
}
