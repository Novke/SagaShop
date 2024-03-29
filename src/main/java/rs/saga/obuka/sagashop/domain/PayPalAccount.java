package rs.saga.obuka.sagashop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayPalAccount extends BaseEntity<Long>{


    @Column(nullable = false)
    @NotNull
    private String accountNumber;

    @Column(nullable = false)
    @NotNull
    private BigDecimal budget;

    @Column(nullable = false)
    @NotNull
    private String language;

    @Column(nullable = false)
    @NotNull
    private LocalDate expiresOn;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    private String city;
    private String country;
    private String postalCode;
    private String street;
}
