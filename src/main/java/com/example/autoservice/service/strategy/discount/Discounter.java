package com.example.autoservice.service.strategy.discount;

import java.math.BigDecimal;

public interface Discounter {
    BigDecimal applyDiscount(BigDecimal amount);

    static Discounter serviceDiscounter(Integer count) {
        return amount -> amount.subtract(amount.multiply(BigDecimal.valueOf(count * 0.01)));
    }

    static Discounter productDiscounter(Integer count) {
        return amount -> amount.subtract(amount.multiply(BigDecimal.valueOf(count * 0.02)));
    }
}
