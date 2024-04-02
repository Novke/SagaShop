package rs.saga.obuka.sagashop.service;

import rs.saga.obuka.sagashop.domain.ShoppingCart;
import rs.saga.obuka.sagashop.exception.BudgetExceededException;
import rs.saga.obuka.sagashop.exception.DAOException;
import rs.saga.obuka.sagashop.exception.ServiceException;

public interface ShoppingCartService {

    public ShoppingCart initializeShoppingCart(Long userId) throws ServiceException, DAOException;

    public ShoppingCart addItem(Long cartId, Long itemId, Integer quantity) throws ServiceException, DAOException;

    public ShoppingCart removeItem(Long cartId, Long itemId) throws ServiceException, DAOException;

    public void checkout(Long cartId) throws ServiceException, DAOException, BudgetExceededException;

    public void closeShoppingCart(Long cartId) throws ServiceException, DAOException;

}
