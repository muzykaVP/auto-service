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
import java.util.stream.Collectors;
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
        List<Product> products = dto.getProductIds().stream()
                .map(productService::get)
                .collect(Collectors.toList());
        order.setProducts(products);
        List<Service> services = dto.getServiceIds().stream()
                .map(serviceService::get)
                .collect(Collectors.toList());
        order.setServices(services);
        return order;
    }

    @Override
    public OrderResponseDto mapToDto(Order object) {
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setId(object.getId());
        responseDto.setOrderDate(object.getOrderDate());
        responseDto.setDescription(object.getDescription());
        responseDto.setCompletionDate(object.getCompletionDate());
        responseDto.setFinalPrice(object.getFinalPrice());
        responseDto.setCarId(object.getCar().getId());
        List<Long> productIds = object.getProducts().stream()
                .map(Product::getId)
                .collect(Collectors.toList());
        responseDto.setProductIds(productIds);
        List<Long> serviceIds = object.getServices().stream()
                .map(Service::getId)
                .collect(Collectors.toList());
        responseDto.setServiceIds(serviceIds);
        return responseDto;
    }
}
