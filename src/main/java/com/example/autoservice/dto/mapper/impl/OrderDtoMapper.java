package com.example.autoservice.dto.mapper.impl;

import com.example.autoservice.dto.mapper.RequestDtoMapper;
import com.example.autoservice.dto.mapper.ResponseDtoMapper;
import com.example.autoservice.dto.request.OrderRequestDto;
import com.example.autoservice.dto.response.OrderResponseDto;
import com.example.autoservice.model.Order;
import com.example.autoservice.model.Product;
import com.example.autoservice.model.Service;
import com.example.autoservice.service.CarService;
import com.example.autoservice.service.ProductService;
import com.example.autoservice.service.ServiceService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoMapper implements
        RequestDtoMapper<OrderRequestDto, Order>,
        ResponseDtoMapper<Order, OrderResponseDto> {
    private final CarService carService;
    private final ProductService productService;
    private final ServiceService serviceService;

    public OrderDtoMapper(CarService carService, ProductService productService,
                          ServiceService serviceService) {
        this.carService = carService;
        this.productService = productService;
        this.serviceService = serviceService;
    }

    @Override
    public Order mapToModel(OrderRequestDto dto) {
        Order order = new Order();
        order.setDescription(dto.getDescription());
        order.setStatus(Order.Status.valueOf(dto.getStatus()));
        order.setCar(carService.get(dto.getCarId()));
        order.setOrderDate(dto.getOrderDate());
        List<Product> products = dto.getProductIds().stream()
                .map(productService::get)
                .toList();
        order.setProducts(products);
        List<Service> services = dto.getServiceIds().stream()
                .map(serviceService::get)
                .toList();
        order.setServices(services);
        return order;
    }

    @Override
    public OrderResponseDto mapToDto(Order order) {
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setId(order.getId());
        responseDto.setOrderDate(order.getOrderDate());
        responseDto.setDescription(order.getDescription());
        responseDto.setCompletionDate(order.getCompletionDate());
        responseDto.setFinalPrice(order.getFinalPrice());
        responseDto.setCarId(order.getCar().getId());
        responseDto.setStatus(order.getStatus().name());
        List<Long> productIds = order.getProducts().stream()
                .map(Product::getId)
                .toList();
        responseDto.setProductIds(productIds);
        List<Long> serviceIds = order.getServices().stream()
                .map(Service::getId)
                .toList();
        responseDto.setServiceIds(serviceIds);
        return responseDto;
    }
}
