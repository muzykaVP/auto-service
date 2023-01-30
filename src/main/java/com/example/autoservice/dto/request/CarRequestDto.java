package com.example.autoservice.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CarRequestDto {
    private String brand;
    private String model;
    private Integer manufactureYear;
    private String number;
    private Long ownerId;
}
