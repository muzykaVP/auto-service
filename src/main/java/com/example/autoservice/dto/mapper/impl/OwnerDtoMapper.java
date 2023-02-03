package com.example.autoservice.dto.mapper.impl;

import com.example.autoservice.dto.mapper.RequestDtoMapper;
import com.example.autoservice.dto.mapper.ResponseDtoMapper;
import com.example.autoservice.dto.request.OwnerRequestDto;
import com.example.autoservice.dto.response.OwnerResponseDto;
import com.example.autoservice.model.Car;
import com.example.autoservice.model.Order;
import com.example.autoservice.model.Owner;
import com.example.autoservice.service.CarService;
import com.example.autoservice.service.OrderService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OwnerDtoMapper implements
        RequestDtoMapper<OwnerRequestDto, Owner>,
        ResponseDtoMapper<Owner, OwnerResponseDto> {
    private final CarService carService;
    private final OrderService orderService;

    public OwnerDtoMapper(CarService carService, OrderService orderService) {
        this.carService = carService;
        this.orderService = orderService;
    }

    @Override
    public Owner mapToModel(OwnerRequestDto dto) {
        Owner owner = new Owner();
        List<Car> cars = dto.getCarIds().stream()
                .map(carService::get)
                .toList();
        owner.setCars(cars);
        List<Order> orders = dto.getOrderIds().stream()
                .map(orderService::get)
                .toList();
        owner.setOrders(orders);
        return owner;
    }

    @Override
    public OwnerResponseDto mapToDto(Owner object) {
        OwnerResponseDto responseDto = new OwnerResponseDto();
        responseDto.setId(object.getId());
        List<Long> carIds = object.getCars().stream()
                .map(Car::getId)
                .toList();
        responseDto.setCarIds(carIds);
        List<Long> orderIds = object.getOrders().stream()
                .map(Order::getId)
                .toList();
        responseDto.setOrderIds(orderIds);
        return responseDto;
    }
}
