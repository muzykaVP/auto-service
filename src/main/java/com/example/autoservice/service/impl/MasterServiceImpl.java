package com.example.autoservice.service.impl;

import com.example.autoservice.model.Master;
import com.example.autoservice.model.Order;
import com.example.autoservice.repository.MasterRepository;
import com.example.autoservice.service.MasterService;
import com.example.autoservice.service.OrderService;
import com.example.autoservice.service.ServiceService;
import com.example.autoservice.service.exception.NoSuchElementPresentException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class MasterServiceImpl implements MasterService {
    private static final BigDecimal SALARY_RATE = BigDecimal.valueOf(0.4);
    private final MasterRepository masterRepository;
    private final OrderService orderService;
    private final ServiceService serviceService;

    public MasterServiceImpl(MasterRepository masterRepository,
                             OrderService orderService,
                             ServiceService serviceService) {
        this.masterRepository = masterRepository;
        this.orderService = orderService;
        this.serviceService = serviceService;
    }

    @Override
    public Master save(Master master) {
        return masterRepository.save(master);
    }

    @Override
    public Master get(Long id) {
        return masterRepository.findById(id).orElseThrow(
                () -> new NoSuchElementPresentException("Can't get master with id: " + id));
    }

    @Override
    public List<Order> getOrders(Long masterId) {
        Master master = get(masterId);
        return master.getOrders().stream()
                .map(order -> orderService.get(order.getId()))
                .toList();
    }

    @Override
    public BigDecimal calculateSalary(Long id) {
        Master master = get(id);
        List<com.example.autoservice.model.Service> services = getMasterServices(master);
        List<com.example.autoservice.model.Service> servicesToPay = services.stream()
                .filter(service ->
                        service.getStatus() != com.example.autoservice.model.Service.Status.PAID)
                .toList();
        BigDecimal finalSalary = servicesToPay.stream()
                .map(com.example.autoservice.model.Service::getPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .multiply(SALARY_RATE);
        updateServicesStatus(servicesToPay);
        return finalSalary;
    }

    List<com.example.autoservice.model.Service> getMasterServices(Master master) {
        return master.getOrders().stream()
                .filter(order -> order.getStatus() == Order.Status.SUCCESSFULLY_COMPLETED
                        || order.getStatus() == Order.Status.UNSUCCESSFULLY_COMPLETED)
                .map(Order::getServices)
                .flatMap(Collection::stream)
                .filter(service -> Objects.equals(service.getMaster().getId(), master.getId()))
                .toList();
    }

    private void updateServicesStatus(List<com.example.autoservice.model.Service> services) {
        for (com.example.autoservice.model.Service service : services) {
            service.setStatus(com.example.autoservice.model.Service.Status.PAID);
            serviceService.save(service);
        }
    }
}
