package com.example.autoservice.service;

import com.example.autoservice.model.Service;

public interface ServiceService {
    Service save(Service service);

    Service get(Long id);
}
