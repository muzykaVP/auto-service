package com.example.autoservice.service.impl;

import com.example.autoservice.model.Order;
import com.example.autoservice.model.Owner;
import com.example.autoservice.model.Product;
import com.example.autoservice.repository.OrderRepository;
import com.example.autoservice.repository.OwnerRepository;
import com.example.autoservice.repository.ProductRepository;
import com.example.autoservice.service.OrderService;
import com.example.autoservice.service.exception.NoSuchElementPresentException;
import com.example.autoservice.service.strategy.discount.DiscountStrategy;
import com.example.autoservice.service.strategy.discount.impl.ProductDiscountStrategy;
import com.example.autoservice.service.strategy.discount.impl.ServiceDiscountStrategy;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OwnerRepository ownerRepository;
    private DiscountStrategy discountStrategy;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductRepository productRepository,
                            OwnerRepository ownerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order addProduct(Long orderId, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("Can't add product with id: " + productId));
        Order order = get(orderId);
        order.getProducts().add(product);
        return orderRepository.save(order);
    }

    @Override
    public Order get(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new NoSuchElementPresentException("Can't get order with id: " + id));
    }

    @Override
    public BigDecimal calculateOrderPrice(Long id) {
        Order order = get(id);
        Owner owner = ownerRepository.findByOrderId(order.getId());
        BigDecimal servicesPrice = calculateServiceDiscount(owner, order);
        BigDecimal productsPrice = calculateProductDiscount(owner, order);
        order.setFinalPrice(servicesPrice.add(productsPrice));
        orderRepository.save(order);
        return order.getFinalPrice();
    }

    private BigDecimal calculateServiceDiscount(Owner owner, Order order) {
        discountStrategy = new ServiceDiscountStrategy();
        return discountStrategy.applyDiscount(owner, order);
    }

    private BigDecimal calculateProductDiscount(Owner owner, Order order) {
        discountStrategy = new ProductDiscountStrategy();
        return discountStrategy.applyDiscount(owner, order);
    }
}
