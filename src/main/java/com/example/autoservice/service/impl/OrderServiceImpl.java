package com.example.autoservice.service.impl;

import com.example.autoservice.model.Order;
import com.example.autoservice.model.Product;
import com.example.autoservice.repository.OrderRepository;
import com.example.autoservice.repository.ProductRepository;
import com.example.autoservice.service.OrderService;
import com.example.autoservice.service.strategy.discount.Discounter;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
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
                () -> new RuntimeException("Can't get order with id: " + id));
    }

    @Override
    public BigDecimal calculateOrderPrice(Long id) {
        Order order = get(id);
        List<com.example.autoservice.model.Service> services = order.getServices();
        List<Product> products = order.getProducts();
        BigDecimal servicesPrice = calculateServiceDiscount(services);
        BigDecimal productsPrice = calculateProductDiscount(products);
        order.setFinalPrice(servicesPrice.add(productsPrice));
        orderRepository.save(order);
        return order.getFinalPrice();
    }

    private BigDecimal calculateServiceDiscount(
            List<com.example.autoservice.model.Service> services) {
        BigDecimal servicesPriceSum = services.stream()
                .filter(service ->
                        service.getStatus() == com.example.autoservice.model.Service.Status.UNPAID)
                .map(com.example.autoservice.model.Service::getPrice)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return Discounter.serviceDiscounter(services.size()).applyDiscount(servicesPriceSum);
    }

    private BigDecimal calculateProductDiscount(List<Product> products) {
        BigDecimal servicesPriceSum = products.stream()
                .map(Product::getPrice)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return Discounter.productDiscounter(products.size()).applyDiscount(servicesPriceSum);
    }
}
