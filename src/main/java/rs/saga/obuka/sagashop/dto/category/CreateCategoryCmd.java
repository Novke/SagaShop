package rs.saga.obuka.sagashop.dto.category;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.saga.obuka.sagashop.dto.product.CreateProductCmd;

/**
 * @author: Ana Dedović
 * Date: 28.06.2021.
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryCmd implements Serializable {

    private String name;
    private String description;
    private List<CreateProductCmd> products;

}
