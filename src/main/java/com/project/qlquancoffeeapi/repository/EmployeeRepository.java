package com.project.qlquancoffeeapi.repository;

import com.project.qlquancoffeeapi.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmployeeCode(String employeeCode);

    List<Employee> findByRole(String role);

    List<Employee> findByNameContainingIgnoreCaseOrRoleContainingIgnoreCase(String name, String role);
}
