package rs.saga.obuka.sagashop.dto.product;

import rs.saga.obuka.sagashop.domain.CategoryProduct;

import java.math.BigDecimal;
import java.util.List;

public class UpdateProductCmd {

    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String description;
    private List<CategoryProduct> categoryProducts;
}
