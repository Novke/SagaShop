package rs.saga.obuka.sagashop.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item extends BaseEntity<Long>{

    @NotNull
    Integer quantity;

    @ManyToOne
    @NotNull
    Product product;

    @ManyToOne
    @NotNull
    ShoppingCart shoppingCart;
}
