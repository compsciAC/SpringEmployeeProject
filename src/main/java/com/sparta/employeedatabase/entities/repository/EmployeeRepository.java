package com.sparta.employeedatabase.entities.repository;

import com.sparta.employeedatabase.entities.dto.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}