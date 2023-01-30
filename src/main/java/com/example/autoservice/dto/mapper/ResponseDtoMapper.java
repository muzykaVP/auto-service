package com.example.autoservice.dto.mapper;

public interface ResponseDtoMapper<T, D> {
    D mapToDto(T object);
}
