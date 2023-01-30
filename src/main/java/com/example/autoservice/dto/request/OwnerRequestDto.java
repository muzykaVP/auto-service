package com.example.autoservice.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnerRequestDto {
    private List<Long> carIds;
    private List<Long> orderIds;
}
