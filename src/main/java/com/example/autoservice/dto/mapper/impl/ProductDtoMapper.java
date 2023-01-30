package com.example.autoservice.dto.mapper.impl;

import com.example.autoservice.dto.mapper.RequestDtoMapper;
import com.example.autoservice.dto.mapper.ResponseDtoMapper;
import com.example.autoservice.dto.request.ProductRequestDto;
import com.example.autoservice.dto.response.ProductResponseDto;
import com.example.autoservice.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper implements
        RequestDtoMapper<ProductRequestDto, Product>,
        ResponseDtoMapper<Product, ProductResponseDto> {
    @Override
    public Product mapToModel(ProductRequestDto dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return product;
    }

    @Override
    public ProductResponseDto mapToDto(Product object) {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setId(object.getId());
        responseDto.setName(object.getName());
        responseDto.setPrice(object.getPrice());
        return responseDto;
    }
}
