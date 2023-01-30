package com.example.autoservice.controller;

import com.example.autoservice.dto.mapper.RequestDtoMapper;
import com.example.autoservice.dto.mapper.ResponseDtoMapper;
import com.example.autoservice.dto.request.OwnerRequestDto;
import com.example.autoservice.dto.response.OrderResponseDto;
import com.example.autoservice.dto.response.OwnerResponseDto;
import com.example.autoservice.model.Order;
import com.example.autoservice.model.Owner;
import com.example.autoservice.service.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owners")
public class OwnerController {
    private final OwnerService ownerService;
    private final RequestDtoMapper<OwnerRequestDto, Owner> requestDtoMapper;
    private final ResponseDtoMapper<Owner, OwnerResponseDto> responseDtoMapper;
    private final ResponseDtoMapper<Order, OrderResponseDto> orderResponseDtoMapper;

    public OwnerController(OwnerService ownerService,
                           RequestDtoMapper<OwnerRequestDto, Owner> requestDtoMapper,
                           ResponseDtoMapper<Owner, OwnerResponseDto> responseDtoMapper,
                           ResponseDtoMapper<Order, OrderResponseDto> orderResponseDtoMapper) {
        this.ownerService = ownerService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
        this.orderResponseDtoMapper = orderResponseDtoMapper;
    }

    @PostMapping
    @Operation(description = "creates a new owner with given parameters and saves it to db")
    public OwnerResponseDto add(@RequestBody OwnerRequestDto ownerRequestDto) {
        Owner owner = requestDtoMapper.mapToModel(ownerRequestDto);
        return responseDtoMapper.mapToDto(ownerService.save(owner));
    }

    @PutMapping("/{id}")
    @Operation(description = "sets all owner fields by its id"
            + " with field parameters and returns updated owner")
    public OwnerResponseDto update(@PathVariable Long id,
                                   @RequestBody OwnerRequestDto ownerRequestDto) {
        Owner owner = requestDtoMapper.mapToModel(ownerRequestDto);
        owner.setId(id);
        return responseDtoMapper.mapToDto(ownerService.save(owner));
    }

    @GetMapping("/{id}/orders")
    @Operation(description = "returns all owner orders by its id")
    public List<OrderResponseDto> getOrders(@PathVariable Long id) {
        return ownerService.getOrders(id).stream()
                .map(orderResponseDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
