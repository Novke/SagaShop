package rs.saga.obuka.sagashop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.saga.obuka.sagashop.domain.Category;
import rs.saga.obuka.sagashop.dto.category.CreateCategoryCmd;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductCmd {

    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String description;
    private List<CreateCategoryCmd> categories;

//    public CreateProductCmd(String name, BigDecimal price, String description, Integer quantity) {
//        this.name = name;
//        this.price = price;
//        this.description = description;
//        this.quantity = quantity;
//    }
}
