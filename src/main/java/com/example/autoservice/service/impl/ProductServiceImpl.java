package com.example.autoservice.service.impl;

import com.example.autoservice.model.Product;
import com.example.autoservice.repository.ProductRepository;
import com.example.autoservice.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product get(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't get product with id: " + id));
    }
}
