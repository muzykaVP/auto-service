package com.example.autoservice.controller;

import com.example.autoservice.dto.mapper.RequestDtoMapper;
import com.example.autoservice.dto.mapper.ResponseDtoMapper;
import com.example.autoservice.dto.request.ServiceRequestDto;
import com.example.autoservice.dto.response.ServiceResponseDto;
import com.example.autoservice.model.Service;
import com.example.autoservice.service.ServiceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class ServiceController {
    private final ServiceService serviceService;
    private final RequestDtoMapper<ServiceRequestDto, Service> requestDtoMapper;
    private final ResponseDtoMapper<Service, ServiceResponseDto> responseDtoMapper;

    public ServiceController(ServiceService serviceService,
                             RequestDtoMapper<ServiceRequestDto, Service> requestDtoMapper,
                             ResponseDtoMapper<Service, ServiceResponseDto> responseDtoMapper) {
        this.serviceService = serviceService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
    }

    @PostMapping
    @Operation(description = "creates a new service with given parameters and saves it db")
    public ServiceResponseDto add(@RequestBody ServiceRequestDto serviceRequestDto) {
        Service service = requestDtoMapper.mapToModel(serviceRequestDto);
        return responseDtoMapper.mapToDto(serviceService.save(service));
    }

    @PutMapping("/{id}")
    @Operation(description = "sets all service fields by its id"
            + " with field parameters and returns updated service")
    public ServiceResponseDto update(@PathVariable Long id,
                                     @RequestBody ServiceRequestDto serviceRequestDto) {
        Service service = requestDtoMapper.mapToModel(serviceRequestDto);
        service.setId(id);
        return responseDtoMapper.mapToDto(serviceService.save(service));
    }

    @PutMapping("/{id}/status")
    @Operation(description = "sets service status by its id"
            + " with given parameter and returns updated service")
    public ServiceResponseDto updateStatus(@PathVariable Long id,
                                           @RequestParam String status) {
        Service service = serviceService.get(id);
        service.setStatus(Service.Status.valueOf(status));
        return responseDtoMapper.mapToDto(serviceService.save(service));
    }
}
