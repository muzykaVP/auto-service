package com.example.autoservice.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MasterResponseDto {
    private Long id;
    private String name;
    private List<Long> orderIds;
}
