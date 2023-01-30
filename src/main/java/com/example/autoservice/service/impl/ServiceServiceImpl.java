package com.example.autoservice.service.impl;

import com.example.autoservice.repository.ServiceRepository;
import com.example.autoservice.service.ServiceService;
import org.springframework.stereotype.Service;

@Service
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;

    public ServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public com.example.autoservice.model.Service save(
            com.example.autoservice.model.Service service) {
        return serviceRepository.save(service);
    }

    @Override
    public com.example.autoservice.model.Service get(Long id) {
        return serviceRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't get service with id: " + id));
    }
}

