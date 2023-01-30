package com.example.autoservice.service;

import com.example.autoservice.model.Order;
import com.example.autoservice.model.Owner;
import java.util.List;

public interface OwnerService {
    Owner save(Owner owner);

    Owner get(Long id);

    List<Order> getOrders(Long ownerId);
}
