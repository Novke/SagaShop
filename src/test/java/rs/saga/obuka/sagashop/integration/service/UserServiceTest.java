package rs.saga.obuka.sagashop.integration.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rs.saga.obuka.sagashop.AbstractIntegrationTest;
import rs.saga.obuka.sagashop.dao.RoleDAO;
import rs.saga.obuka.sagashop.dao.UserDAO;
import rs.saga.obuka.sagashop.domain.PayPalAccount;
import rs.saga.obuka.sagashop.domain.Role;
import rs.saga.obuka.sagashop.domain.RoleName;
import rs.saga.obuka.sagashop.domain.User;
import rs.saga.obuka.sagashop.dto.paypal.PayPalAccountInfo;
import rs.saga.obuka.sagashop.dto.user.CreateUserCmd;
import rs.saga.obuka.sagashop.dto.user.UpdateUserCmd;
import rs.saga.obuka.sagashop.dto.user.UserInfo;
import rs.saga.obuka.sagashop.dto.user.UserResult;
import rs.saga.obuka.sagashop.exception.DAOException;
import rs.saga.obuka.sagashop.exception.ServiceException;
import rs.saga.obuka.sagashop.service.PayPalAccountService;
import rs.saga.obuka.sagashop.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserServiceTest extends AbstractIntegrationTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private PayPalAccountService payPalAccountService;

    @BeforeEach
    public void setUp(){
        transactionHandler.runInTransaction(()->{
            try {
                roleDAO.save(new Role(RoleName.USER));
                roleDAO.save(new Role(RoleName.ADMIN));
                return null;
            } catch (DAOException ex){
                throw new RuntimeException(ex);
            }
        });
    }
    @AfterEach
    public void tearDown(){
        userService.findAll().forEach(e -> {
            try {
                userService.delete(e.getId());
            } catch (ServiceException serviceException) {
                serviceException.printStackTrace();
            }
        });
        transactionHandler.runInTransaction(()->{
            try {
                roleDAO.deleteAll();
                return null;
            } catch (DAOException ex){
                throw new RuntimeException(ex);
            }
        });
    }

    @Test
    public void saveUser() throws ServiceException {
        CreateUserCmd cmd = new CreateUserCmd("user", "pass", "name", "lastname");
        User user = userService.save(cmd);
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(cmd.getUsername(), user.getUsername());
        Assertions.assertEquals(cmd.getPassword(), user.getPassword());
        Assertions.assertEquals(cmd.getName(), user.getName());
        Assertions.assertEquals(cmd.getSurname(), user.getSurname());
    }

    @Test
    public void updateUser() throws ServiceException {
        CreateUserCmd cmd = new CreateUserCmd("user", "pass", "name", "lastname");
        User user = userService.save(cmd);
        Assertions.assertNotNull(user.getId());

        UpdateUserCmd cmd2 = new UpdateUserCmd(user.getId(), "name2", "lastname2");
        userService.update(cmd2);

        UserInfo user2 = userService.findById(user.getId());
        Assertions.assertEquals("name2", user2.getUsername());
    }

    @Test
    public void deleteUser() throws ServiceException {
        CreateUserCmd cmd = new CreateUserCmd("user", "pass", "name", "lastname");
        User user = userService.save(cmd);
        Assertions.assertNotNull(user.getId());

        userService.delete(user.getId());

        UserInfo user2 = userService.findById(user.getId());
        Assertions.assertNull(user2);
    }

    @Test
    public void findOne() throws ServiceException {
        CreateUserCmd cmd = new CreateUserCmd("user", "pass", "name", "lastname");
        User user = userService.save(cmd);
        Assertions.assertNotNull(user.getId());

        UserInfo user2 = userService.findById(user.getId());
        Assertions.assertNotNull(user2);
        Assertions.assertEquals(user.getName(), user2.getName());
        Assertions.assertEquals(user.getSurname(), user2.getSurname());
        Assertions.assertEquals(user.getUsername(), user2.getUsername());
    }

    @Test
    public void findAll() throws ServiceException {
        CreateUserCmd cmd = new CreateUserCmd("user", "pass", "name", "lastname");
        userService.save(cmd);
        CreateUserCmd cmd2 = new CreateUserCmd("user2", "pass2", "name2", "lastname2");
        userService.save(cmd2);
        CreateUserCmd cmd3 = new CreateUserCmd("user3", "pass3", "name3", "lastname3");
        userService.save(cmd3);
        CreateUserCmd cmd4 = new CreateUserCmd("user4", "pass4", "name4", "lastname4");
        userService.save(cmd4);

        List<UserResult> users = userService.findAll();
        Assertions.assertEquals(4, users.size());
        Assertions.assertTrue(users.stream().anyMatch(u -> u.getUsername().equals("user")));
        Assertions.assertTrue(users.stream().anyMatch(u -> u.getUsername().equals("user2")));
        Assertions.assertTrue(users.stream().anyMatch(u -> u.getUsername().equals("user3")));
        Assertions.assertTrue(users.stream().anyMatch(u -> u.getUsername().equals("user4")));
    }

    @Test
    public void saveUserPaypalCascade() throws ServiceException, DAOException {
        PayPalAccount pp = PayPalAccount.builder()
                .expiresOn(LocalDate.of(2028, 12, 12))
                .accountNumber("1337")
                .budget(new BigDecimal("20000.00"))
                .language("ENG")
                .build();

        CreateUserCmd cmd = new CreateUserCmd("user", "pass", "name", "lastname");

        User createdUser = userService.save(cmd);
        createdUser = userService.linkPayPalAccount(createdUser.getId(), pp);

        Assertions.assertNotNull(createdUser.getId());
        Assertions.assertNotNull(createdUser.getPayPalAccount().getId());

        PayPalAccountInfo payPalAccountInfo = payPalAccountService.findById(createdUser.getPayPalAccount().getId());
        Assertions.assertNotNull(payPalAccountInfo);
        Assertions.assertEquals("1337", payPalAccountInfo.getAccountNumber());
    }

    @Test
    public void addRole() throws ServiceException, DAOException {
        CreateUserCmd cmd = new CreateUserCmd("user", "pass", "name", "lastname");

        User createdUser = userService.save(cmd);

        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());

        User editedUser = userService.addRole(createdUser.getId(), RoleName.ADMIN);

        assertEquals(2, editedUser.getRoles().size());
    }

}
