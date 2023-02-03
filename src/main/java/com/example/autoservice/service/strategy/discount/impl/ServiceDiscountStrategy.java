package com.example.autoservice.service.strategy.discount.impl;

import com.example.autoservice.model.Order;
import com.example.autoservice.model.Owner;
import com.example.autoservice.model.Service;
import com.example.autoservice.service.strategy.discount.DiscountStrategy;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class ServiceDiscountStrategy implements DiscountStrategy {
    private static final Double DISCOUNT_PERCENTAGE = 0.02;

    @Override
    public BigDecimal applyDiscount(Owner owner, Order order) {
        long completedOrdersCount = owner.getOrders().stream()
                .filter(order1 -> order1.getStatus() == Order.Status.SUCCESSFULLY_COMPLETED
                        || order1.getStatus() == Order.Status.UNSUCCESSFULLY_COMPLETED).count();
        BigDecimal servicesSum = order.getServices().stream()
                .filter(service -> service.getStatus() == Service.Status.UNPAID)
                .map(Service::getPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        return servicesSum
                .subtract(servicesSum
                        .multiply(BigDecimal.valueOf(completedOrdersCount * DISCOUNT_PERCENTAGE)));
    }
}
