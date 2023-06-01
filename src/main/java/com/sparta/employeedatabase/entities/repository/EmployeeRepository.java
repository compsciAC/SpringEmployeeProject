package com.sparta.employeedatabase.entities.repository;

import com.sparta.employeedatabase.entities.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByLastName(String lastName);

    Optional<Employee> findById(int id);


}