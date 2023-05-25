package com.sparta.employeedatabase.service;

import com.sparta.employeedatabase.entities.dto.Employee;
import com.sparta.employeedatabase.entities.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findByLastName(String lastName){return employeeRepository.findByLastName(lastName);}
}
