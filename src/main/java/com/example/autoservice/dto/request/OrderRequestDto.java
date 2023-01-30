package com.example.autoservice.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderRequestDto {
    private Long carId;
    private String description;
    private LocalDateTime orderDate = LocalDateTime.now();
    private List<Long> serviceIds;
    private List<Long> productIds;
    private String status;
}
