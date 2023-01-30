package com.example.autoservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderResponseDto {
    private Long id;
    private Long carId;
    private String description;
    private LocalDateTime orderDate;
    private List<Long> serviceIds;
    private List<Long> productIds;
    private String status;
    private BigDecimal finalPrice;
    private LocalDateTime completionDate;
}
