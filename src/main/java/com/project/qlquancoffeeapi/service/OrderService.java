package com.project.qlquancoffeeapi.service;

import com.project.qlquancoffeeapi.entity.Customer;
import com.project.qlquancoffeeapi.entity.Order;
import com.project.qlquancoffeeapi.entity.OrderItem;
import com.project.qlquancoffeeapi.entity.Product;
import com.project.qlquancoffeeapi.exception.ResourceNotFoundException;
import com.project.qlquancoffeeapi.repository.OrderItemRepository;
import com.project.qlquancoffeeapi.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired private OrderItemRepository orderItemRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Transactional
    public Order createOrder(Map<String, Object> request) {
        String customerCode = (String) request.get("customerCode");
        Map<Long, Integer> productQuantities = (Map<Long, Integer>) request.get("productQuantities");

        Order order = new Order();

        if (customerCode != null && !customerCode.isEmpty()) {
            Customer customer = customerService.getCustomerByCode(customerCode)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "code", customerCode));

            order.setCustomer(customer);
        }

        Set<OrderItem> orderItems = new HashSet<>();
        double totalAmount = 0;

        for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {
            Long productId = Long.parseLong(String.valueOf(entry.getKey()));
            Integer quantity = entry.getValue();

            Product product = productService.getProductById(productId);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);

            orderItems.add(orderItem);

            totalAmount += product.getPrice() * quantity;
        }

        order.setOrderItems(orderItems);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(totalAmount);

        // Lưu thông tin đơn hàng và các chi tiết đơn hàng
        order = orderRepository.save(order);

        return order;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Transactional
    public Order updateOrder(Long orderId, Map<String, Object> request) {
        Order existingOrder = getOrderById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        // Update order details
        if (request.containsKey("customerCode"))
        {
            String customerCode = (String) request.get("customerCode");
            if(customerCode != null && !customerCode.isEmpty())
            {
                Customer customer = customerService.getCustomerByCode(customerCode)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer", "Code", customerCode));
                existingOrder.setCustomer(customer);
            }
        }
        if(request.containsKey("productQuantities"))
        {
            Map<String, Integer> productQuantities = (Map<String, Integer>) request.get("productQuantities");
            Set<OrderItem> orderItems = new HashSet<>();

            orderItemRepository.deleteByOrderId(existingOrder.getId());
            existingOrder.getOrderItems().removeIf(existingOrderItem -> !productQuantities.containsKey(existingOrderItem.getProduct().getId()));;

            double totalAmount = 0;

            for(Map.Entry<String, Integer> entry : productQuantities.entrySet())
            {
                Long productId = Long.parseLong(entry.getKey());
                Integer quantity = entry.getValue();
                Product product = productService.getProductById(productId);

                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(existingOrder);
                orderItem.setProduct(product);
                orderItem.setQuantity(quantity);

                orderItems.add(orderItem);
                totalAmount += product.getPrice() * quantity;
            }
            existingOrder.setOrderItems(orderItems);
            existingOrder.setTotalAmount(totalAmount);
        }
        existingOrder = orderRepository.save(existingOrder);
        return existingOrder;
    }

    @Transactional
    public void deleteOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        orderRepository.delete(order);
    }
}
