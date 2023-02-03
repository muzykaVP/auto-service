package com.example.autoservice.repository;

import com.example.autoservice.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    @Query(value = "SELECT o FROM Owner o "
            + "LEFT JOIN FETCH o.orders orders WHERE orders.id=:orderId ")
    Owner findByOrderId(@Param("orderId") Long orderId);
}
