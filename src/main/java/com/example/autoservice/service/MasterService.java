package com.example.autoservice.service;

import com.example.autoservice.model.Master;
import com.example.autoservice.model.Order;
import java.math.BigDecimal;
import java.util.List;

public interface MasterService {
    Master save(Master master);

    Master get(Long id);

    List<Order> getOrders(Long masterId);

    BigDecimal calculateSalary(Long id);
}
