package com.example.autoservice.repository;

import com.example.autoservice.model.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Override
    @Query(value = "SELECT o FROM Order o LEFT JOIN FETCH o.car")
    Optional<Order> findById(Long id);
}
