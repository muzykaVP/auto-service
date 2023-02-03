package com.example.autoservice.service.impl;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import com.example.autoservice.model.Car;
import com.example.autoservice.model.Master;
import com.example.autoservice.model.Order;
import com.example.autoservice.model.Owner;
import com.example.autoservice.model.Product;
import com.example.autoservice.model.Service;
import com.example.autoservice.repository.MasterRepository;
import com.example.autoservice.service.OrderService;
import com.example.autoservice.service.ServiceService;
import com.example.autoservice.service.exception.NoSuchElementPresentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MasterServiceImplTest {
    @InjectMocks
    private MasterServiceImpl masterService;
    @Mock
    private MasterRepository masterRepository;
    @Mock
    private ServiceService serviceService;
    @Mock
    private OrderService orderService;

    private final static Master MASTER_ALEX = new Master();
    private final static Order ORDER = new Order();
    private final static Car CAR = new Car();
    private final static Owner OWNER = new Owner();
    private final static Service OIL_REPLACE_SERVICE = new Service();
    private final static Service DIAGNOSTIC_SERVICE = new Service();
    private final static Product PRODUCT = new Product();

    @BeforeAll
    static void beforeAll() {
        PRODUCT.setId(1L);
        PRODUCT.setName("Oil");
        PRODUCT.setPrice(BigDecimal.valueOf(100L));

        OIL_REPLACE_SERVICE.setId(1L);
        OIL_REPLACE_SERVICE.setMaster(MASTER_ALEX);
        OIL_REPLACE_SERVICE.setOrder(ORDER);
        OIL_REPLACE_SERVICE.setStatus(Service.Status.UNPAID);
        OIL_REPLACE_SERVICE.setPrice(BigDecimal.valueOf(600L));

        DIAGNOSTIC_SERVICE.setId(2L);
        DIAGNOSTIC_SERVICE.setMaster(MASTER_ALEX);
        DIAGNOSTIC_SERVICE.setOrder(ORDER);
        DIAGNOSTIC_SERVICE.setStatus(Service.Status.FREE);
        DIAGNOSTIC_SERVICE.setPrice(BigDecimal.valueOf(500L));

        OWNER.setId(1L);
        OWNER.setCars(List.of(CAR));
        OWNER.setOrders(List.of(ORDER));

        CAR.setId(1L);
        CAR.setOwner(OWNER);
        CAR.setNumber("12345QWE");
        CAR.setManufactureYear(2016);
        CAR.setModel("Camry");
        CAR.setBrand(Car.Brand.TOYOTA);

        ORDER.setId(1L);
        ORDER.setStatus(Order.Status.SUCCESSFULLY_COMPLETED);
        ORDER.setDescription("Oil replacement");
        ORDER.setFinalPrice(BigDecimal.valueOf(700));
        ORDER.setOrderDate(LocalDateTime.parse("2007-12-03T10:15:30"));
        ORDER.setCompletionDate(LocalDateTime.parse("2007-12-05T10:15:30"));
        ORDER.setCar(CAR);
        ORDER.setServices(List.of(OIL_REPLACE_SERVICE, DIAGNOSTIC_SERVICE));
        ORDER.setProducts(List.of(PRODUCT));

        MASTER_ALEX.setId(1L);
        MASTER_ALEX.setName("Alex");
        MASTER_ALEX.setOrders(List.of(ORDER));
    }

    @Test
    void calculateSalaryWithTwoServices_ok() {
        Mockito.doReturn(Optional.of(MASTER_ALEX))
                .when(masterRepository).findById(MASTER_ALEX.getId());
        Mockito.doReturn(OIL_REPLACE_SERVICE)
                .when(serviceService).save(OIL_REPLACE_SERVICE);
        Mockito.doReturn(DIAGNOSTIC_SERVICE)
                .when(serviceService).save(DIAGNOSTIC_SERVICE);
        DIAGNOSTIC_SERVICE.setStatus(Service.Status.FREE);
        OIL_REPLACE_SERVICE.setStatus(Service.Status.UNPAID);
        BigDecimal expectedSalary = BigDecimal.valueOf(440d);
        BigDecimal actualSalary = masterService.calculateSalary(MASTER_ALEX.getId());
        Assertions.assertEquals(expectedSalary.stripTrailingZeros(),
                actualSalary.stripTrailingZeros());
        List<Service.Status> expectedServiceStatuses = List.of(Service.Status.PAID, Service.Status.PAID);
        List<Service.Status> actualServiceStatuses = ORDER.getServices().stream()
                .map(Service::getStatus)
                .toList();
        Assertions.assertIterableEquals(expectedServiceStatuses, actualServiceStatuses);
    }

    @Test
    void getOrdersWithExistingMaster_ok() {
        Mockito.doReturn(Optional.of(MASTER_ALEX))
                .when(masterRepository).findById(MASTER_ALEX.getId());
        Mockito.doReturn(ORDER)
                .when(orderService).get(ORDER.getId());
        List<Order> expectedOrders = List.of(ORDER);
        List<Order> actualOrders = masterService.getOrders(MASTER_ALEX.getId());
        Assertions.assertIterableEquals(expectedOrders, actualOrders);
    }

    @Test
    void getOrdersWithNotExistingMasterThrowsException_notOk() {
        Long nonExistingMasterId = 2L;
        Assertions.assertThrows(NoSuchElementPresentException.class,
                () -> masterService.getOrders(nonExistingMasterId));
    }

    @Test
    void getMasterServicesWithTwoServices_ok() {
        List<Service> expectedServices = List.of(OIL_REPLACE_SERVICE, DIAGNOSTIC_SERVICE);
        List<Service> actualServices = masterService.getMasterServices(MASTER_ALEX);
        Assertions.assertIterableEquals(expectedServices, actualServices);
    }
}