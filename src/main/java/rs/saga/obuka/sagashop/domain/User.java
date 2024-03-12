package rs.saga.obuka.sagashop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity<Long>{

    @Column(nullable = false)
    @NotNull
    private String username;

    @Column(nullable = false)
    @NotNull
    private String password;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    @NotNull
    private String surname;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PayPalAccount> payPalAccounts;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<ShoppingCart> shoppingCarts;

}
