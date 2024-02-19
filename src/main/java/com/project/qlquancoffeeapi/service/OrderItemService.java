package com.project.qlquancoffeeapi.service;

import com.project.qlquancoffeeapi.entity.OrderItem;
import com.project.qlquancoffeeapi.exception.ResourceNotFoundException;
import com.project.qlquancoffeeapi.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderService orderService; // Để truy cập các phương thức liên quan đến Order

    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        // Kiểm tra xem Order có tồn tại không
        orderService.getOrderById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        // Lấy danh sách OrderItem theo OrderId
        return orderItemRepository.findByOrderId(orderId);
    }
}
