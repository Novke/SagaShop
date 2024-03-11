package rs.saga.obuka.sagashop.dto.paypal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayPalAccountResult {

    private String accountNumber;
    private BigDecimal budget;
    private LocalDate expiresOn;
    private String country;

}
