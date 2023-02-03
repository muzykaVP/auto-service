package com.example.autoservice.service.strategy.discount;

import com.example.autoservice.model.Order;
import com.example.autoservice.model.Owner;
import java.math.BigDecimal;

public interface DiscountStrategy {
    BigDecimal applyDiscount(Owner owner, Order order);
}
