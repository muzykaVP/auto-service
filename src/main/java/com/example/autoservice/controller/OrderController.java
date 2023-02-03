package com.example.autoservice.controller;

import com.example.autoservice.dto.mapper.RequestDtoMapper;
import com.example.autoservice.dto.mapper.ResponseDtoMapper;
import com.example.autoservice.dto.request.OrderRequestDto;
import com.example.autoservice.dto.response.OrderResponseDto;
import com.example.autoservice.model.Order;
import com.example.autoservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final RequestDtoMapper<OrderRequestDto, Order> requestDtoMapper;
    private final ResponseDtoMapper<Order, OrderResponseDto> responseDtoMapper;

    public OrderController(OrderService orderService,
                           RequestDtoMapper<OrderRequestDto, Order> requestDtoMapper,
                           ResponseDtoMapper<Order, OrderResponseDto> responseDtoMapper) {
        this.orderService = orderService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
    }

    @PostMapping
    @Operation(description = "creates a new order with given parameters and saves it to db")
    public OrderResponseDto add(@RequestBody OrderRequestDto orderRequestDto) {
        Order order = requestDtoMapper.mapToModel(orderRequestDto);
        return responseDtoMapper.mapToDto(orderService.save(order));
    }

    @PutMapping("/{id}/add-product")
    @Operation(description = "adds product id to product ids list of order by its id"
            + " and returns updated order")
    public OrderResponseDto addProduct(@PathVariable Long id,
                                       @RequestParam Long productId) {
        Order order = orderService.addProduct(id, productId);
        return responseDtoMapper.mapToDto(orderService.save(order));
    }

    @PutMapping("/{id}")
    @Operation(description = "sets all order fields by its id"
            + " with field parameters and returns updated order")
    public OrderResponseDto update(@PathVariable Long id,
                                   @RequestBody OrderRequestDto orderRequestDto) {
        Order order = requestDtoMapper.mapToModel(orderRequestDto);
        order.setId(id);
        return responseDtoMapper.mapToDto(orderService.save(order));
    }

    @PutMapping("/{id}/status")
    @Operation(description = "sets order status by its id"
            + " with given parameter and returns updated order")
    public OrderResponseDto updateStatus(@PathVariable Long id,
                                         @RequestParam String status) {
        Order order = orderService.get(id);
        order.setStatus(Order.Status.valueOf(status));
        return responseDtoMapper.mapToDto(orderService.save(order));
    }

    @GetMapping("/{id}/cost")
    @Operation(description = "calculates final price of order by its id")
    public BigDecimal calculateFinalPrice(@PathVariable Long id) {
        return orderService.calculateOrderPrice(id);
    }
}
