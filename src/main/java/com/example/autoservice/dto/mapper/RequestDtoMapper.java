package com.example.autoservice.dto.mapper;

public interface RequestDtoMapper<D, T> {
    T mapToModel(D dto);
}
