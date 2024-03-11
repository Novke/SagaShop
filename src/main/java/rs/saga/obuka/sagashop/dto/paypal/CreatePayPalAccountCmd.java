package rs.saga.obuka.sagashop.dto.paypal;

import rs.saga.obuka.sagashop.domain.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreatePayPalAccountCmd {

    private String accountNumber;
    private BigDecimal budget;
    private String language;
    private LocalDate expiresOn;
    private String city;
    private String country;
    private String postalCode;
    private String street;
    private User user;
}
