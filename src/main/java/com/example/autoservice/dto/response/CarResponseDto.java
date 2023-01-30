package com.example.autoservice.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarResponseDto {
    private Long id;
    private String brand;
    private String model;
    private Integer manufactureYear;
    private String number;
    private Long ownerId;
}
