package rs.saga.obuka.sagashop.integration.service;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import rs.saga.obuka.sagashop.AbstractIntegrationTest;
import rs.saga.obuka.sagashop.dao.UserDAO;
import rs.saga.obuka.sagashop.domain.ShoppingCart;
import rs.saga.obuka.sagashop.domain.Status;
import rs.saga.obuka.sagashop.domain.User;
import rs.saga.obuka.sagashop.exception.DAOException;
import rs.saga.obuka.sagashop.exception.ServiceException;
import rs.saga.obuka.sagashop.service.ShoppingCartService;

import static org.junit.Assert.*;

public class ShoppingCartServiceTest extends AbstractIntegrationTest {

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserDAO userDAO;

    @AfterEach
    public void cleanUp() {
        userDAO.findAll().forEach(user -> {
            try {
                userDAO.delete(user);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testInitializeCart() throws DAOException, ServiceException {
        //TODO pada? userDAO je null?!
        User user = new User("user",
                "pass",
                "name","lastname",null,null);
        user = userDAO.save(user);

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

    @Test
    public void testAddItem(){
        assertThrows(ServiceException.class, () -> shoppingCartService.initializeShoppingCart(1L));
    }

}
