package rs.saga.obuka.sagashop.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ShoppingCart extends BaseEntity<Long>{

    @Column(nullable = false)
    @NotNull
    private String name;

    @NotNull
    private Status status = Status.INACTIVE;

    @NotNull
    private BigDecimal price;

    @NotNull
    @ManyToOne(optional = false)
    private User user;

    @NotNull
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
    private List<Item> items;


}
