package rs.saga.obuka.sagashop.dto.product;

import rs.saga.obuka.sagashop.domain.CategoryProduct;

import java.math.BigDecimal;
import java.util.List;

public class ProductResult {

    private String name;
    private BigDecimal price;
    private List<CategoryProduct> categoryProducts;
}
