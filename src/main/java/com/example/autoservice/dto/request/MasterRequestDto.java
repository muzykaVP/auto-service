package com.example.autoservice.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MasterRequestDto {
    private String name;
    private List<Long> orderIds;
}
