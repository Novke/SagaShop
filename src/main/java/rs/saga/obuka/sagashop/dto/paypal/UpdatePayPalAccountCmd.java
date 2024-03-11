package rs.saga.obuka.sagashop.dto.paypal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePayPalAccountCmd {

    private Long id;
    private String accountNumber;
    private BigDecimal budget;
    private String language;
    private LocalDate expiresOn;
    private String city;
    private String country;
}
