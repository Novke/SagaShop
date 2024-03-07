package rs.saga.obuka.sagashop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart extends BaseEntity<Long>{

//    @Column(nullable = false)
//    @NotNull
//    private String name;

    //sta je status ?? enum ?

    //price bigdecimal

    //userid long FK ? ili je dovoljno sto ga cuva u kao entitet

    //User
    //List<Item> items;

}
