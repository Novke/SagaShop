package rs.saga.obuka.sagashop.builder;

import rs.saga.obuka.sagashop.domain.Product;

import java.math.BigDecimal;

public class ProductBuilder {

    public static Product product(){
        return Product.builder()
                .name("Product")
                .price(new BigDecimal(100))
                .quantity(10)
                .build();
    }

}
