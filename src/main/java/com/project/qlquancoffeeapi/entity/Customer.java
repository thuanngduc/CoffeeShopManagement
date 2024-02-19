package com.project.qlquancoffeeapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @Column(name = "customer_code", nullable = false, unique = true)
    @JsonProperty("customerCode")
    private String customerCode;

    @Column(nullable = false, length = 255)
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "Số điện thoại không được trống")
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "Số điện thoại không hợp lệ")
    @Column(length = 10, nullable = false, unique = true)
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    public Customer() {
    }

    public Customer(String customerCode, String name, String phoneNumber) {
        this.customerCode = customerCode;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
