package com.project.qlquancoffeeapi.service;

import com.project.qlquancoffeeapi.entity.Customer;
import com.project.qlquancoffeeapi.exception.ResourceNotFoundException;
import com.project.qlquancoffeeapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers()
    {
        return customerRepository.findAll();
    }
    public Optional<Customer> getCustomerByCode(String customerCode)
    {
        return customerRepository.findByCustomerCode(customerCode);
    }
    public Optional<Customer> getCustomerByPhoneNumber(String phoneNumber)
    {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }
    public Customer createCustomer(Customer customer)
    {
       return customerRepository.save(customer);
    }
    public Customer updateCustomer(String customerCode, Customer updatedCustomer)
    {
        Customer existingCustomer = customerRepository.findByCustomerCode(customerCode).orElseThrow(() -> new ResourceNotFoundException("Customer","code", customerCode));

        if (!existingCustomer.getPhoneNumber().equals(updatedCustomer.getPhoneNumber())
                && customerRepository.existsByPhoneNumber(updatedCustomer.getPhoneNumber())) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại cho khách hàng khác.");
        }
        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());

        return customerRepository.save(existingCustomer);
    }
    public void deleteCustomer(String customerCode)
    {
        customerRepository.deleteById(customerCode);
    }
}
