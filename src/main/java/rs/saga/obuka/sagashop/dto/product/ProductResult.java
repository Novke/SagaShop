package rs.saga.obuka.sagashop.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.saga.obuka.sagashop.dto.category.CategoryInfo;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResult {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    @JsonIgnore
    private List<CategoryInfo> categories;
}
