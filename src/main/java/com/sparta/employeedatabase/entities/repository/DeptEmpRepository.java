package com.sparta.employeedatabase.entities.repository;

import com.sparta.employeedatabase.entities.dto.DeptEmp;
import com.sparta.employeedatabase.entities.dto.DeptEmpId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptEmpRepository extends JpaRepository<DeptEmp, DeptEmpId> {
}