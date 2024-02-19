package com.project.qlquancoffeeapi.controller;

import com.project.qlquancoffeeapi.entity.Customer;
import com.project.qlquancoffeeapi.exception.ResourceNotFoundException;
import com.project.qlquancoffeeapi.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers()
    {
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    @GetMapping("/{customerCode}")
    public ResponseEntity<Customer> getCustomerByCode(@PathVariable String customerCode)
    {
        Optional<Customer> customer = customerService.getCustomerByCode(customerCode);
        return customer.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/search/{phoneNumber}")
    public ResponseEntity<Customer> getCustomerByPhoneNumber(@PathVariable String phoneNumber)
    {
        Optional<Customer> customer = customerService.getCustomerByPhoneNumber(phoneNumber);
        return customer.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody @Valid Customer customer, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            Map<String, String> errors = new HashMap<>();
            for(FieldError error : bindingResult.getFieldErrors())
            {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Customer createdCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/{customerCode}")
    public ResponseEntity<?> updateCustomer(@PathVariable String customerCode, @RequestBody @Valid Customer updatedCustomer, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        try {
            customerService.updateCustomer(customerCode, updatedCustomer);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("phoneNumber", ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }catch (ResourceNotFoundException e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping("/{customerCode}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String customerCode)
    {
        customerService.deleteCustomer(customerCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
