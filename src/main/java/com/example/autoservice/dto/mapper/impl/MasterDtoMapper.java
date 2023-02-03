package com.example.autoservice.dto.mapper.impl;

import com.example.autoservice.dto.mapper.RequestDtoMapper;
import com.example.autoservice.dto.mapper.ResponseDtoMapper;
import com.example.autoservice.dto.request.MasterRequestDto;
import com.example.autoservice.dto.response.MasterResponseDto;
import com.example.autoservice.model.Master;
import com.example.autoservice.model.Order;
import com.example.autoservice.service.OrderService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MasterDtoMapper implements
        ResponseDtoMapper<Master, MasterResponseDto>,
        RequestDtoMapper<MasterRequestDto, Master> {
    private final OrderService orderService;

    public MasterDtoMapper(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Master mapToModel(MasterRequestDto dto) {
        Master master = new Master();
        List<Order> orders = new ArrayList<>();
        dto.getOrderIds().forEach(id -> orders.add(orderService.get(id)));
        master.setOrders(orders);
        master.setName(dto.getName());
        return master;
    }

    @Override
    public MasterResponseDto mapToDto(Master object) {
        MasterResponseDto responseDto = new MasterResponseDto();
        List<Long> orderIds = object.getOrders().stream()
                .map(Order::getId)
                .toList();
        responseDto.setOrderIds(orderIds);
        responseDto.setName(object.getName());
        responseDto.setId(object.getId());
        return responseDto;
    }
}
