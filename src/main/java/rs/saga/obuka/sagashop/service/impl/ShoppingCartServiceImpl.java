package rs.saga.obuka.sagashop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.saga.obuka.sagashop.dao.ProductDAO;
import rs.saga.obuka.sagashop.dao.ShoppingCartDAO;
import rs.saga.obuka.sagashop.dao.UserDAO;
import rs.saga.obuka.sagashop.domain.*;
import rs.saga.obuka.sagashop.exception.BudgetExceededException;
import rs.saga.obuka.sagashop.exception.DAOException;
import rs.saga.obuka.sagashop.exception.ErrorCode;
import rs.saga.obuka.sagashop.exception.ServiceException;
import rs.saga.obuka.sagashop.service.ShoppingCartService;

import java.math.BigDecimal;

import static rs.saga.obuka.sagashop.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final UserDAO userDAO;
    private final ProductDAO productDAO;
    private final ShoppingCartDAO shoppingCartDAO;

    @Override
    public ShoppingCart initializeShoppingCart(Long userId) throws ServiceException, DAOException {
        User user = userDAO.findOne(userId);
        if (user == null) throw new ServiceException(ErrorCode.ERR_GEN_002);

        ShoppingCart cart = new ShoppingCart("New cart" , Status.NEW, new BigDecimal(0), user, null);

        return shoppingCartDAO.save(cart);
    }

    @Override
    public ShoppingCart addItem(Long cartId, Long productId, Integer quantity) throws ServiceException, DAOException {

        ShoppingCart cart = shoppingCartDAO.findOne(cartId);
        if (cart == null) throw new ServiceException(ErrorCode.ERR_CART_001);

        Product product = productDAO.findOne(productId);
        if (product == null) throw new ServiceException(ErrorCode.ERR_GEN_002);

        if (quantity <= 0 || quantity > product.getQuantity()) throw new ServiceException(ERR_CART_003);

        Item item = new Item(quantity, product, cart);
        cart.addItem(item);
        cart.setPrice(cart.getPrice().add(product.getPrice().multiply(new BigDecimal(quantity))));
        cart.setStatus(Status.ACTIVE);

        //cuva item preko kaskade
        return shoppingCartDAO.save(cart);
    }

    @Override
    public ShoppingCart removeItem(Long cartId, Long itemId) throws ServiceException, DAOException {

        ShoppingCart cart = shoppingCartDAO.findOne(cartId);
        if (cart == null) throw new ServiceException(ErrorCode.ERR_CART_001);

        Item item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId)).findFirst().orElse(null);
        if (item == null) throw new ServiceException(ErrorCode.ERR_GEN_002);

        cart.getItems().remove(item);
        cart.setPrice(cart.getPrice().subtract(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity()))));
        if (cart.getItems().isEmpty()) {
            cart.setStatus(Status.INACTIVE); //NIJE NAGLASENO ALI MI IMA SMISLA
        }

        //cuva item preko kaskade
        return shoppingCartDAO.save(cart);
    }

    @Override
    public void checkout(Long cartId) throws ServiceException, DAOException, BudgetExceededException {

            // VALIDACIJA
            ShoppingCart cart = shoppingCartDAO.findOne(cartId);
            if (cart == null) throw new ServiceException(ErrorCode.ERR_CART_001);

            if (cart.getItems().isEmpty()
            || cart.getStatus() != Status.ACTIVE) throw new ServiceException(ERR_CART_002);

            for (Item item : cart.getItems()) {
                Product product = item.getProduct();
                if (item.getQuantity()>product.getQuantity()) throw new ServiceException(ERR_CART_003);
            }

            if (cart.getPrice().compareTo(cart.getUser().getPayPalAccount().getBudget()) > 0){
                throw new BudgetExceededException();
            }

            // PROSLO SVE USPESNO

            // skida proizvode sa stanja
            for (Item item : cart.getItems()) {
                Product product = item.getProduct();
                product.setQuantity(product.getQuantity() - item.getQuantity());
                productDAO.save(product);
            }

            // skida novac sa racuna
            cart.getUser().getPayPalAccount()
                    .setBudget(cart.getUser().getPayPalAccount().getBudget()
                            .subtract(cart.getPrice()));

            //postavlja se status na COMPLETED
            cart.setStatus(Status.COMPLETED);

            shoppingCartDAO.save(cart);
    }

    @Override
    public void closeShoppingCart(Long cartId) throws ServiceException, DAOException {

        ShoppingCart cart = shoppingCartDAO.findOne(cartId);
        if (cart == null) throw new ServiceException(ErrorCode.ERR_CART_001);

        cart.setStatus(Status.INACTIVE);
        //TODO jel ima validacija ako je u nekom stanju da onda ne sme ?
    }

}
