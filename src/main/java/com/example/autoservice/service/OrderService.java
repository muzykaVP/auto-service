package com.example.autoservice.service;

import com.example.autoservice.model.Order;
import java.math.BigDecimal;

public interface OrderService {
    Order save(Order order);

    Order addProduct(Long orderId, Long productId);

    Order get(Long id);

    BigDecimal calculateOrderPrice(Long id);
}
