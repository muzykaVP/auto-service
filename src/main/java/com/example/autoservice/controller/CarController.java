package com.example.autoservice.controller;

import com.example.autoservice.dto.mapper.RequestDtoMapper;
import com.example.autoservice.dto.mapper.ResponseDtoMapper;
import com.example.autoservice.dto.request.CarRequestDto;
import com.example.autoservice.dto.response.CarResponseDto;
import com.example.autoservice.model.Car;
import com.example.autoservice.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final RequestDtoMapper<CarRequestDto, Car> requestDtoMapper;
    private final ResponseDtoMapper<Car, CarResponseDto> responseDtoMapper;

    public CarController(CarService carService,
                         RequestDtoMapper<CarRequestDto, Car> requestDtoMapper,
                         ResponseDtoMapper<Car, CarResponseDto> responseDtoMapper) {
        this.carService = carService;
        this.requestDtoMapper = requestDtoMapper;
        this.responseDtoMapper = responseDtoMapper;
    }

    @PostMapping
    @Operation(description = "creates a new car with given parameters and saves it to db")
    public CarResponseDto add(@RequestBody CarRequestDto carRequestDto) {
        Car car = requestDtoMapper.mapToModel(carRequestDto);
        return responseDtoMapper.mapToDto(carService.save(car));
    }

    @PutMapping("/{id}")
    @Operation(description = "sets all car fields by its id"
            + " with field parameters and returns updated car")
    public CarResponseDto update(@PathVariable Long id,
                                 @RequestBody CarRequestDto carRequestDto) {
        Car car = requestDtoMapper.mapToModel(carRequestDto);
        car.setId(id);
        return responseDtoMapper.mapToDto(carService.save(car));
    }

}
