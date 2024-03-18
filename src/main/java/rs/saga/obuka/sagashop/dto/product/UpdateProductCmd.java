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
public class UpdateProductCmd {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String description;
    private List<CategoryProduct> categoryProducts;

    public UpdateProductCmd(Long id, String name, BigDecimal price, String description, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }
}
