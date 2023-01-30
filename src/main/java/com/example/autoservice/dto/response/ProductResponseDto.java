package com.example.autoservice.dto.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
}
