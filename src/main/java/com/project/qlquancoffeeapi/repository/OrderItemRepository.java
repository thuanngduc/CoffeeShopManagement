package com.project.qlquancoffeeapi.repository;

import com.project.qlquancoffeeapi.entity.OrderItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Transactional
    void deleteByOrderId(Long orderId);

    List<OrderItem> findByOrderId(Long orderId);
}
