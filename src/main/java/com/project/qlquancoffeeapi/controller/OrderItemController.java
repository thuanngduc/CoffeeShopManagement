package com.project.qlquancoffeeapi.controller;

import com.project.qlquancoffeeapi.entity.OrderItem;
import com.project.qlquancoffeeapi.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/by-order/{orderId}")
    public ResponseEntity<List<OrderItem>> getOrderItemsByOrderId(@PathVariable Long orderId) {
        List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderId(orderId);
        return ResponseEntity.ok(orderItems);
    }
}
