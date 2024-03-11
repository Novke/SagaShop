package rs.saga.obuka.sagashop.dto.paypal;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdatePayPalAccountCmd {

    private String accountNumber;
    private BigDecimal budget;
    private String language;
    private LocalDate expiresOn;
    private String city;
    private String country;
}
