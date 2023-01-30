package com.example.autoservice.dto.mapper.impl;

import com.example.autoservice.dto.mapper.RequestDtoMapper;
import com.example.autoservice.dto.mapper.ResponseDtoMapper;
import com.example.autoservice.dto.request.CarRequestDto;
import com.example.autoservice.dto.response.CarResponseDto;
import com.example.autoservice.model.Car;
import com.example.autoservice.service.OwnerService;
import org.springframework.stereotype.Component;

@Component
public class CarDtoMapper implements
        ResponseDtoMapper<Car, CarResponseDto>,
        RequestDtoMapper<CarRequestDto, Car> {
    private final OwnerService ownerService;

    public CarDtoMapper(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Override
    public Car mapToModel(CarRequestDto dto) {
        Car car = new Car();
        car.setBrand(Car.Brand.valueOf(dto.getBrand()));
        car.setModel(dto.getModel());
        car.setOwner(ownerService.get(dto.getOwnerId()));
        car.setNumber(dto.getNumber());
        car.setManufactureYear(dto.getManufactureYear());
        return car;
    }

    @Override
    public CarResponseDto mapToDto(Car object) {
        CarResponseDto responseDto = new CarResponseDto();
        responseDto.setId(object.getId());
        responseDto.setBrand(object.getBrand().name());
        responseDto.setModel(object.getModel());
        responseDto.setNumber(object.getNumber());
        responseDto.setManufactureYear(object.getManufactureYear());
        responseDto.setOwnerId(object.getOwner().getId());
        return responseDto;
    }
}
