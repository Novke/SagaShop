package rs.saga.obuka.sagashop.integration.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rs.saga.obuka.sagashop.AbstractIntegrationTest;
import rs.saga.obuka.sagashop.dao.UserDAO;
import rs.saga.obuka.sagashop.domain.ShoppingCart;
import rs.saga.obuka.sagashop.domain.Status;
import rs.saga.obuka.sagashop.domain.User;
import rs.saga.obuka.sagashop.exception.DAOException;
import rs.saga.obuka.sagashop.exception.ServiceException;
import rs.saga.obuka.sagashop.service.ShoppingCartService;
import rs.saga.obuka.sagashop.util.TransactionHandler;

import static org.junit.Assert.*;
import static rs.saga.obuka.sagashop.builder.UserBuilder.genericUser;

public class ShoppingCartServiceTest extends AbstractIntegrationTest {

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private TransactionHandler transactionHandler;


    @AfterEach
    public void cleanUp() {
        transactionHandler.runInTransaction(() -> {
            try {
                userDAO.deleteAll(userDAO.findAll());
            } catch (DAOException e) {
                throw new RuntimeException(e);
            }
            return null;
        });
    }

    @Test
    public void testInitializeCart() throws DAOException, ServiceException {
        User user = transactionHandler.runInTransaction(() -> {
            try {
                return userDAO.save(genericUser());
            } catch (DAOException e) {
                throw new RuntimeException(e);
            }
        });

        assertNotNull(user);
        assertNotNull(user.getId());

        ShoppingCart cart = shoppingCartService.initializeShoppingCart(user.getId());
        assertNotNull(cart);
        assertNotNull(cart.getId());
        assertEquals(Status.NEW, cart.getStatus());
        assertEquals(cart.getUser().getId(), user.getId());
    }

    @Test
    public void testInitializeCartFail() {
        Long id = 1L;
        assertNull(userDAO.findOne(id));
        assertThrows(ServiceException.class, () -> shoppingCartService.initializeShoppingCart(id));
    }


}
