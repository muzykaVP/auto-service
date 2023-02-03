package com.example.autoservice.controller;

import com.example.autoservice.dto.mapper.RequestDtoMapper;
import com.example.autoservice.dto.mapper.ResponseDtoMapper;
import com.example.autoservice.dto.request.MasterRequestDto;
import com.example.autoservice.dto.response.MasterResponseDto;
import com.example.autoservice.dto.response.OrderResponseDto;
import com.example.autoservice.model.Master;
import com.example.autoservice.model.Order;
import com.example.autoservice.service.MasterService;
import io.swagger.v3.oas.annotations.Operation;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/masters")
public class MasterController {
    private final MasterService masterService;
    private final ResponseDtoMapper<Master, MasterResponseDto> responseDtoMapper;
    private final ResponseDtoMapper<Order, OrderResponseDto> orderResponseDtoMapper;
    private final RequestDtoMapper<MasterRequestDto, Master> requestDtoMapper;

    public MasterController(MasterService masterService,
                            ResponseDtoMapper<Master, MasterResponseDto> responseDtoMapper,
                            ResponseDtoMapper<Order, OrderResponseDto> orderResponseDtoMapper,
                            RequestDtoMapper<MasterRequestDto, Master> requestDtoMapper) {
        this.masterService = masterService;
        this.responseDtoMapper = responseDtoMapper;
        this.orderResponseDtoMapper = orderResponseDtoMapper;
        this.requestDtoMapper = requestDtoMapper;
    }

    @PostMapping
    @Operation(description = "creates a new master with given parameters and saves it to db")
    public MasterResponseDto add(@RequestBody MasterRequestDto masterRequestDto) {
        Master master = requestDtoMapper.mapToModel(masterRequestDto);
        return responseDtoMapper.mapToDto(masterService.save(master));
    }

    @PutMapping("/{id}")
    @Operation(description = "sets all master fields by its id"
            + " with field parameters and returns updated master")
    public MasterResponseDto update(@PathVariable Long id,
                                    @RequestBody MasterRequestDto masterRequestDto) {
        Master master = requestDtoMapper.mapToModel(masterRequestDto);
        master.setId(id);
        return responseDtoMapper.mapToDto(master);
    }

    @GetMapping("/{id}/orders")
    @Operation(description = "returns all master orders by its id")
    public List<OrderResponseDto> getOrders(@PathVariable Long id) {
        return masterService.getOrders(id).stream()
                .map(orderResponseDtoMapper::mapToDto)
                .toList();
    }

    @GetMapping("/{id}/salary")
    @Operation(description = "calculates salary for master by id")
    public BigDecimal getSalary(@PathVariable Long id) {
        return masterService.calculateSalary(id);
    }
}
