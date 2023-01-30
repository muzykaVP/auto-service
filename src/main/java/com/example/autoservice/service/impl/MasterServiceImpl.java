package com.example.autoservice.service.impl;

import com.example.autoservice.model.Master;
import com.example.autoservice.model.Order;
import com.example.autoservice.repository.MasterRepository;
import com.example.autoservice.service.MasterService;
import com.example.autoservice.service.ServiceService;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MasterServiceImpl implements MasterService {
    private static final BigDecimal SALARY_RATE = BigDecimal.valueOf(0.4);
    private final MasterRepository masterRepository;
    private final ServiceService serviceService;

    public MasterServiceImpl(MasterRepository masterRepository, ServiceService serviceService) {
        this.masterRepository = masterRepository;
        this.serviceService = serviceService;
    }

    @Override
    public Master save(Master master) {
        return masterRepository.save(master);
    }

    @Override
    public Master get(Long id) {
        return masterRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't get master with id: " + id));
    }

    @Override
    public List<Order> getOrders(Long masterId) {
        return get(masterId).getOrders();
    }

    @Override
    public BigDecimal calculateSalary(Long id) {
        Master master = get(id);
        List<com.example.autoservice.model.Service> services = master.getOrders().stream()
                .filter(order -> order.getStatus() == Order.Status.SUCCESSFULLY_COMPLETED
                        || order.getStatus() == Order.Status.UNSUCCESSFULLY_COMPLETED)
                .map(Order::getServices)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        List<com.example.autoservice.model.Service> servicesToPay = services.stream()
                .filter(service -> Objects.equals(service.getMaster().getId(), id))
                .filter(service ->
                        service.getStatus() != com.example.autoservice.model.Service.Status.PAID)
                .collect(Collectors.toList());
        BigDecimal finalSalary = servicesToPay.stream()
                .map(com.example.autoservice.model.Service::getPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .multiply(SALARY_RATE);
        updateServicesStatus(servicesToPay);
        return finalSalary;
    }

    private void updateServicesStatus(List<com.example.autoservice.model.Service> services) {
        for (com.example.autoservice.model.Service service : services) {
            service.setStatus(com.example.autoservice.model.Service.Status.PAID);
            serviceService.save(service);
        }
    }
}
