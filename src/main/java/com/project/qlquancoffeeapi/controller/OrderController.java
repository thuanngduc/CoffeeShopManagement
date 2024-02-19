package com.project.qlquancoffeeapi.controller;

import com.project.qlquancoffeeapi.entity.Order;
import com.project.qlquancoffeeapi.exception.ResourceNotFoundException;
import com.project.qlquancoffeeapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Map<String, Object> request) {
        Order order = orderService.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Map<String, Object> request) {
        Order updatedOrder  = orderService.updateOrder(orderId, request);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>("Order deleted successfully", HttpStatus.OK);
    }
}
