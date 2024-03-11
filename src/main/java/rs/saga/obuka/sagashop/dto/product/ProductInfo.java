package rs.saga.obuka.sagashop.dto.product;

import rs.saga.obuka.sagashop.domain.CategoryProduct;

import java.math.BigDecimal;
import java.util.List;

public class ProductInfo {

    private String name;
    private BigDecimal price;
    private String description;
    private List<CategoryProduct> categoryProducts;

}
