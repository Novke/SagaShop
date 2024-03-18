package rs.saga.obuka.sagashop.dto.paypal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePayPalAccountCmd {

    private String accountNumber;
    private BigDecimal budget;
    private String language;
    private LocalDate expiresOn;
    private String city;
    private String country;
    private String postalCode;
    private String street;
    private Long userId;

    public CreatePayPalAccountCmd(String accountNumber, BigDecimal budget, Long userId, LocalDate expiresOn, String language) {
        this.accountNumber = accountNumber;
        this.budget = budget;
        this.userId = userId;
        this.expiresOn = expiresOn;
        this.language = language;
    }
}
