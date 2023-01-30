package com.example.autoservice.dto.mapper.impl;

import com.example.autoservice.dto.mapper.RequestDtoMapper;
import com.example.autoservice.dto.mapper.ResponseDtoMapper;
import com.example.autoservice.dto.request.ServiceRequestDto;
import com.example.autoservice.dto.response.ServiceResponseDto;
import com.example.autoservice.model.Service;
import com.example.autoservice.service.MasterService;
import com.example.autoservice.service.OrderService;
import org.springframework.stereotype.Component;

@Component
public class ServiceDtoMapper implements
        RequestDtoMapper<ServiceRequestDto, Service>,
        ResponseDtoMapper<Service, ServiceResponseDto> {
    private final MasterService masterService;
    private final OrderService orderService;

    public ServiceDtoMapper(MasterService masterService, OrderService orderService) {
        this.masterService = masterService;
        this.orderService = orderService;
    }

    @Override
    public Service mapToModel(ServiceRequestDto dto) {
        Service service = new Service();
        service.setStatus(Service.Status.valueOf(dto.getStatus()));
        service.setMaster(masterService.get(dto.getMasterId()));
        service.setOrder(orderService.get(dto.getOrderId()));
        service.setPrice(dto.getPrice());
        return service;
    }

    @Override
    public ServiceResponseDto mapToDto(Service object) {
        ServiceResponseDto responseDto = new ServiceResponseDto();
        responseDto.setId(object.getId());
        responseDto.setPrice(object.getPrice());
        responseDto.setMasterId(object.getMaster().getId());
        responseDto.setOrderId(object.getOrder().getId());
        responseDto.setStatus(object.getStatus().name());
        return responseDto;
    }
}
