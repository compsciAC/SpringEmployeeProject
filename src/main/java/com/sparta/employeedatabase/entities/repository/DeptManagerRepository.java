package com.sparta.employeedatabase.entities.repository;

import com.sparta.employeedatabase.entities.dto.DeptManager;
import com.sparta.employeedatabase.entities.dto.DeptManagerId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptManagerRepository extends JpaRepository<DeptManager, DeptManagerId> {
}