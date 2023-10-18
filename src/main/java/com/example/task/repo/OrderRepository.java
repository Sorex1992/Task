package com.example.task.repo;

import com.example.task.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByPaidFalseAndCreatedAtBefore(LocalDateTime date);
    List<Order> findByUserId(Long userId);
    @Query("SELECT o FROM Order o JOIN FETCH o.lineItems li JOIN FETCH li.product WHERE o.id = :orderId")
    Optional<Order> findByPaidFalseAndCreatedAtBefore(@Param("orderId") Long orderId);

}
