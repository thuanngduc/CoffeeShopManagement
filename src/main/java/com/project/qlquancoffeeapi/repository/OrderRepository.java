package com.project.qlquancoffeeapi.repository;

import com.project.qlquancoffeeapi.entity.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
