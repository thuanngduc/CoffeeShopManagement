package com.project.qlquancoffeeapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "customer_code")
    @JsonProperty("customer")
    private Customer customer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    @JsonProperty("orderItems")
    private Set<OrderItem> orderItems = new HashSet<>();



    @Column(nullable = false)
    @JsonProperty("orderDate")
    private LocalDateTime orderDate;

    @Column(nullable = false)
    @JsonProperty("totalAmount")
    private Double totalAmount;

    public Order() {
    }

    public Order(Long id, Customer customer, Set<OrderItem> orderItems, LocalDateTime orderDate, Double totalAmount) {
        this.id = id;
        this.customer = customer;
        this.orderItems = orderItems;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
