package com.example.autoservice.service.impl;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import com.example.autoservice.model.Car;
import com.example.autoservice.model.Master;
import com.example.autoservice.model.Order;
import com.example.autoservice.model.Owner;
import com.example.autoservice.model.Product;
import com.example.autoservice.model.Service;
import com.example.autoservice.repository.OrderRepository;
import com.example.autoservice.repository.OwnerRepository;
import com.example.autoservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OwnerRepository ownerRepository;
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
        OIL_REPLACE_SERVICE.setPrice(BigDecimal.valueOf(600L));
        OIL_REPLACE_SERVICE.setStatus(Service.Status.UNPAID);

        DIAGNOSTIC_SERVICE.setId(2L);
        DIAGNOSTIC_SERVICE.setMaster(MASTER_ALEX);
        DIAGNOSTIC_SERVICE.setOrder(ORDER);
        DIAGNOSTIC_SERVICE.setPrice(BigDecimal.valueOf(500L));
        DIAGNOSTIC_SERVICE.setStatus(Service.Status.FREE);

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
        ORDER.setOrderDate(LocalDateTime.parse("2007-12-05T10:15:30"));
        ORDER.setCar(CAR);

        MASTER_ALEX.setId(1L);
        MASTER_ALEX.setName("Alex");
        MASTER_ALEX.setOrders(List.of(ORDER));
    }

    @BeforeEach
    void setUp() {
        ORDER.setServices(List.of(OIL_REPLACE_SERVICE, DIAGNOSTIC_SERVICE));
        ORDER.setProducts(new ArrayList<>(List.of(PRODUCT)));

        Mockito.doReturn(Optional.of(ORDER))
                .when(orderRepository).findById(ORDER.getId());
        Mockito.doReturn(ORDER)
                .when(orderRepository).save(ORDER);
    }

    @Test
    void calculateOrderPriceWithTwoServicesAndOneProduct_ok() {
        Mockito.doReturn(OWNER)
                .when(ownerRepository).findByOrderId(ORDER.getId());
        BigDecimal expectedFinalOrderPrice = new BigDecimal(687);
        BigDecimal actualFinalOrderPrice = orderService.calculateOrderPrice(ORDER.getId());
        Assertions.assertEquals(expectedFinalOrderPrice.stripTrailingZeros(),
                actualFinalOrderPrice.stripTrailingZeros());
    }

    @Test
    void calculateOrderPriceWithEmptyServices_ok() {
        Mockito.doReturn(OWNER)
                .when(ownerRepository).findByOrderId(ORDER.getId());
        ORDER.setServices(Collections.emptyList());
        BigDecimal expectedFinalOrderPrice = new BigDecimal(99);
        BigDecimal actualFinalOrderPrice = orderService.calculateOrderPrice(ORDER.getId());
        Assertions.assertEquals(expectedFinalOrderPrice.stripTrailingZeros(),
                actualFinalOrderPrice.stripTrailingZeros());
    }

    @Test
    void calculateOrderPriceWithEmptyServicesAndProducts_ok() {
        Mockito.doReturn(OWNER)
                .when(ownerRepository).findByOrderId(ORDER.getId());
        ORDER.setServices(Collections.emptyList());
        ORDER.setProducts(Collections.emptyList());
        BigDecimal expectedFinalOrderPrice = BigDecimal.ZERO;
        BigDecimal actualFinalOrderPrice = orderService.calculateOrderPrice(ORDER.getId());
        Assertions.assertEquals(expectedFinalOrderPrice.stripTrailingZeros(),
                actualFinalOrderPrice.stripTrailingZeros());
    }

    @Test
    void addProduct_ok() {
        Product newProduct = new Product();
        newProduct.setId(2L);
        newProduct.setName("Antifreeze");
        newProduct.setPrice(BigDecimal.valueOf(120));
        Mockito.doReturn(Optional.of(newProduct))
                .when(productRepository).findById(newProduct.getId());
        Order actualOrder = orderService.addProduct(ORDER.getId(), newProduct.getId());
        ORDER.setProducts(List.of(PRODUCT, newProduct));
        Assertions.assertEquals(ORDER, actualOrder);
    }
}