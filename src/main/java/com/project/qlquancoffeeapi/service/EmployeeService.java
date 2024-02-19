package com.project.qlquancoffeeapi.service;

import com.project.qlquancoffeeapi.entity.Employee;
import com.project.qlquancoffeeapi.exception.ResourceNotFoundException;
import com.project.qlquancoffeeapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees()
    {
        return employeeRepository.findAll();
    }
    public Employee getEmployeeById(Long id)
    {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
    }

    public  Employee addEmployee(Employee employee)
    {
        if(employeeRepository.findByEmployeeCode(employee.getEmployeeCode()).isPresent())
        {
            throw new DuplicateKeyException("Employee with code "+ employee.getEmployeeCode() + "already exists");
        }
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(String employeeCode, Employee updatedEmployee) {
        Employee existingEmployee = getEmployeeByEmployeeCode(employeeCode);
        if (!existingEmployee.getEmployeeCode().equals(updatedEmployee.getEmployeeCode())){
            if(employeeRepository.findByEmployeeCode(updatedEmployee.getEmployeeCode()).isPresent())
            {
                throw new DuplicateKeyException("Employee with code "+ updatedEmployee.getEmployeeCode() + "already exists");
            }
        }
        existingEmployee.setEmployeeCode(updatedEmployee.getEmployeeCode());
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setGender(updatedEmployee.getGender());
        existingEmployee.setDateOfBirth(updatedEmployee.getDateOfBirth());
        existingEmployee.setAddress(updatedEmployee.getAddress());
        existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setRole(updatedEmployee.getRole());
        return employeeRepository.save(existingEmployee);
    }
    public void deleteEmployee(String employeeCode)
    {
        Employee employeeByEmployeeCode = getEmployeeByEmployeeCode(employeeCode);
        Long id = employeeByEmployeeCode.getId();
        employeeRepository.deleteById(id);
    }
    public List<Employee> searchEmployees(String query)
    {
        return employeeRepository.findByNameContainingIgnoreCaseOrRoleContainingIgnoreCase(query, query);
    }
    public Employee getEmployeeByEmployeeCode(String employeeCode)
    {
        return employeeRepository.findByEmployeeCode(employeeCode).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeCode));
    }
}
