package com.project.qlquancoffeeapi.repository;

import com.project.qlquancoffeeapi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByCustomerCode(String customerCode);
    Optional<Customer> findByPhoneNumber(String phoneNumber);
    @Query("SELECT COUNT(c) > 0 FROM Customer c WHERE c.phoneNumber = :phoneNumber")
    boolean existsByPhoneNumber(@Param("phoneNumber") String phoneNumber);

}
