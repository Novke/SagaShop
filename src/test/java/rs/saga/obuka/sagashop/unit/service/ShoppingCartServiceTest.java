package rs.saga.obuka.sagashop.unit.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import rs.saga.obuka.sagashop.AbstractUnitServiceTest;
import rs.saga.obuka.sagashop.dao.ProductDAO;
import rs.saga.obuka.sagashop.dao.ShoppingCartDAO;
import rs.saga.obuka.sagashop.dao.UserDAO;
import rs.saga.obuka.sagashop.domain.*;
import rs.saga.obuka.sagashop.exception.BudgetExceededException;
import rs.saga.obuka.sagashop.exception.DAOException;
import rs.saga.obuka.sagashop.exception.ServiceException;
import rs.saga.obuka.sagashop.service.ShoppingCartService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;
import static rs.saga.obuka.sagashop.builder.ProductBuilder.product;
import static rs.saga.obuka.sagashop.builder.UserBuilder.userAna;

public class ShoppingCartServiceTest extends AbstractUnitServiceTest {

    @Autowired
    private ShoppingCartService shoppingCartService;
    @MockBean
    private UserDAO userDAO;
    @MockBean
    private ProductDAO productDAO;
    @MockBean
    private ShoppingCartDAO shoppingCartDAO;

    @Test
    public void testAddItem() throws DAOException, ServiceException {
        User user = userAna();
        user.setId(5L);

        Product product = product();
        product.setId(3L);

        ShoppingCart cart = new ShoppingCart("New cart" , Status.NEW, new BigDecimal(0), user, null);
        cart.setId(4L);

        when(productDAO.findOne(product().getId())).thenReturn(product);
        when(shoppingCartDAO.findOne(cart.getId())).thenReturn(cart);
        //TODO ne valja, productDAO vraca null
        ShoppingCart result = shoppingCartService.addItem(cart.getId(), product.getId(), 1);

        assertNotNull(result);
        assertEquals(Status.ACTIVE, result.getStatus());
        assertTrue(result.getItems().stream().anyMatch(i -> i.getProduct().getId().equals(product.getId())));
    }

    @Test
    public void testAddItemFailCart(){
        User user = userAna();
        user.setId(5L);

        Product product = product();
        product.setId(3L);

        ShoppingCart cart = new ShoppingCart("New cart" , Status.NEW, new BigDecimal(0), user, null);
        cart.setId(4L);

        when(shoppingCartDAO.findOne(cart.getId())).thenReturn(null);
        assertThrows(ServiceException.class, () -> shoppingCartService.addItem(cart.getId(), product.getId(), 1));
    }

    @Test
    public void testAddItemFailProduct(){
        User user = userAna();
        user.setId(5L);

        Product product = product();
        product.setId(3L);

        ShoppingCart cart = new ShoppingCart("New cart" , Status.NEW, new BigDecimal(0), user, null);
        cart.setId(4L);

        when(shoppingCartDAO.findOne(cart.getId())).thenReturn(cart);
        when(productDAO.findOne(product().getId())).thenReturn(null);
        assertThrows(ServiceException.class, () -> shoppingCartService.addItem(cart.getId(), product.getId(), 1));
    }

    @Test
    public void testAddItemFailQuantity(){
        User user = userAna();
        user.setId(5L);

        Product product = product();
        product.setId(3L);

        ShoppingCart cart = new ShoppingCart("New cart" , Status.NEW, new BigDecimal(0), user, null);
        cart.setId(4L);

        when(shoppingCartDAO.findOne(cart.getId())).thenReturn(cart);
        when(productDAO.findOne(product().getId())).thenReturn(product);
        assertThrows(ServiceException.class, () -> shoppingCartService.addItem(cart.getId(), product.getId(), 0));
        assertThrows(ServiceException.class, () -> shoppingCartService.addItem(cart.getId(), product.getId(), product.getQuantity()+1));
    }

    @Test
    public void testRemoveItem() throws DAOException, ServiceException {
        User user = userAna();
        user.setId(5L);

        Product product = product();
        product.setId(3L);

        ShoppingCart cart = new ShoppingCart("New cart" , Status.NEW, new BigDecimal(0), user, null);
        cart.setId(4L);

        Item item = new Item(1, product, cart);
        item.setId(7L);
        cart.addItem(item);

        when(shoppingCartDAO.findOne(cart.getId())).thenReturn(cart);
        when(shoppingCartDAO.save(cart)).thenReturn(cart);

        ShoppingCart result = shoppingCartService.removeItem(cart.getId(), item.getId());

        assertNotNull(result);
        assertFalse(result.getItems().stream().anyMatch(i -> i.getId().equals(item.getId())));
        if (result.getItems().isEmpty()) assertEquals(Status.INACTIVE, result.getStatus());
    }

    @Test
    public void testRemoveItemFailCart() throws DAOException, ServiceException {
        User user = userAna();
        user.setId(5L);

        Product product = product();
        product.setId(3L);

        ShoppingCart cart = new ShoppingCart("New cart" , Status.NEW, new BigDecimal(0), user, null);
        cart.setId(4L);

        Item item = new Item(1, product, cart);
        item.setId(7L);
        cart.addItem(item);

        when(shoppingCartDAO.findOne(cart.getId())).thenReturn(null);
        when(shoppingCartDAO.save(cart)).thenReturn(cart);

        assertThrows(ServiceException.class, () -> shoppingCartService.removeItem(cart.getId(), item.getId()));
    }

    @Test
    public void testRemoveItemFailItem() throws DAOException, ServiceException {
        User user = userAna();
        user.setId(5L);

        Product product = product();
        product.setId(3L);

        ShoppingCart cart = new ShoppingCart("New cart" , Status.NEW, new BigDecimal(0), user, null);
        cart.setId(4L);

        Item item = new Item(1, product, cart);
        item.setId(7L);
        cart.addItem(item);

        when(shoppingCartDAO.findOne(cart.getId())).thenReturn(null);
        when(shoppingCartDAO.save(cart)).thenReturn(cart);

        assertThrows(ServiceException.class, () -> shoppingCartService.removeItem(cart.getId(), 0L));
    }

    @Test
    public void testCheckout() throws DAOException, ServiceException, BudgetExceededException {
        User user = userAna();
        PayPalAccount acc = new PayPalAccount();
        acc.setBudget(new BigDecimal(1000000));
        user.setPayPalAccount(acc);
        user.setId(5L);
        ShoppingCart cart = new ShoppingCart("New cart" , Status.NEW, new BigDecimal(0), user, null);
        cart.setId(4L);
        cart.addItem(new Item(1, product(),cart));
        cart.setStatus(Status.ACTIVE);

        when(shoppingCartDAO.findOne(cart.getId())).thenReturn(cart);
        when(shoppingCartDAO.save(cart)).thenReturn(cart);

        assertDoesNotThrow(() -> shoppingCartService.checkout(cart.getId()));

        verify(shoppingCartDAO, times(1)).save(cart);
        assertEquals(Status.COMPLETED, cart.getStatus());
    }

    @Test
    public void testCeckoutFailCartNull(){

        ShoppingCart cart = new ShoppingCart("New cart" , Status.NEW, new BigDecimal(0), userAna(), null);
        cart.setId(4L);

        when(shoppingCartDAO.findOne(cart.getId())).thenReturn(null);

        assertThrows(ServiceException.class, () -> shoppingCartService.checkout(cart.getId()));
    }

    @Test
    public void testcheckoutFailCartInactive(){

        ShoppingCart cart = new ShoppingCart("New cart" , Status.INACTIVE, new BigDecimal(0), userAna(), Collections.emptyList());
        cart.setId(4L);

        when(shoppingCartDAO.findOne(cart.getId())).thenReturn(cart);

        assertThrows(ServiceException.class, () -> shoppingCartService.checkout(cart.getId()));
    }

    @Test
    public void testCheckoutFailProductQuantity(){
        ShoppingCart cart = new ShoppingCart("New cart" , Status.ACTIVE, new BigDecimal(0), userAna(), List.of(new Item(product().getQuantity()+1, product(), null)));
        cart.setId(4L);

//        cart.addItem();

        when(shoppingCartDAO.findOne(cart.getId())).thenReturn(cart);

        assertThrows(ServiceException.class, () -> shoppingCartService.checkout(cart.getId()));
    }

}
