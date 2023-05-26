package com.sparta.employeedatabase.entities.repository;

import com.sparta.employeedatabase.entities.dto.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, String> {

    Optional<Department> findDepartmentByDeptName(String deptName);
}