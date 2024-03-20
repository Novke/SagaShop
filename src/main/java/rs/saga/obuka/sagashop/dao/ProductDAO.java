package rs.saga.obuka.sagashop.dao;

import rs.saga.obuka.sagashop.domain.Product;
import rs.saga.obuka.sagashop.dto.product.ProductResult;

import java.util.List;

public interface ProductDAO extends AbstractDAO<Product, Long>{
    List<ProductResult> findByCriteria(String name, Integer quantity, Double price, String category);
}
