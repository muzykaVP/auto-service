package com.example.autoservice.dto.request;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceRequestDto {
    private Long orderId;
    private Long masterId;
    private BigDecimal price;
    private String status;
}
