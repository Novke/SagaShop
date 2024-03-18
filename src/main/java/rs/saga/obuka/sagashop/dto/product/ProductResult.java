package rs.saga.obuka.sagashop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.saga.obuka.sagashop.domain.CategoryProduct;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResult {

    private Long id;
    private String name;
    private BigDecimal price;
    private List<CategoryProduct> categoryProducts;
}
