package rs.saga.obuka.sagashop.service;

import rs.saga.obuka.sagashop.domain.Product;
import rs.saga.obuka.sagashop.dto.product.CreateProductCmd;
import rs.saga.obuka.sagashop.dto.product.ProductInfo;
import rs.saga.obuka.sagashop.dto.product.ProductResult;
import rs.saga.obuka.sagashop.dto.product.UpdateProductCmd;
import rs.saga.obuka.sagashop.exception.ServiceException;

import java.util.List;

public interface ProductService {

    Product save(CreateProductCmd cmd) throws ServiceException;

    List<ProductResult> findAll();

    ProductInfo findById(Long id);

    void update(UpdateProductCmd productDTO) throws ServiceException;

    void delete(Long id) throws ServiceException;

    List<ProductResult> findProducts(String name, Integer quantity, Double price, String category);
}
