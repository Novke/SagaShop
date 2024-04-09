package rs.saga.obuka.sagashop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private PayPalAccount payPalAccount;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ShoppingCart> shoppingCarts;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "role_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Role> roles = new ArrayList<>();

    public void setPayPalAccount(PayPalAccount payPalAccount) {
        this.payPalAccount = payPalAccount;
        payPalAccount.setUser(this);
    }

    public void removePayPalAccount() {
        if (this.payPalAccount != null) {
            this.payPalAccount.setUser(null);
            this.payPalAccount = null;
        }
    }

}
