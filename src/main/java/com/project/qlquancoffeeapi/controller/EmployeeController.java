package com.project.qlquancoffeeapi.controller;

import com.project.qlquancoffeeapi.entity.Employee;
import com.project.qlquancoffeeapi.exception.ResourceNotFoundException;
import com.project.qlquancoffeeapi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees()
    {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id)
    {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee)
    {
        try {
            Employee addedEmployee = employeeService.addEmployee(employee);
            return new ResponseEntity<>(addedEmployee, HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    @PutMapping("/{employeeCode}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String employeeCode, @RequestBody Employee updatedEmployee)
    {
        try {
            Employee employee = employeeService.updateEmployee(employeeCode, updatedEmployee);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (DuplicateKeyException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{employeeCode}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable String employeeCode)
    {
        try{
            employeeService.deleteEmployee(employeeCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (ResourceNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestParam String query) {
        try {
            List<Employee> employees = employeeService.searchEmployees(query);
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/by-code/{employeeCode}")
    public ResponseEntity<Employee> getEmployeeByEmployeeCode(@PathVariable String employeeCode) {
        try {
            Employee employee = employeeService.getEmployeeByEmployeeCode(employeeCode);
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
