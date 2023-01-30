package com.example.autoservice.dto.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ServiceResponseDto {
    private Long id;
    private Long orderId;
    private Long masterId;
    private BigDecimal price;
    private String status;
}
