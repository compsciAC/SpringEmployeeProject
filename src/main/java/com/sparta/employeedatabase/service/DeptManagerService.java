package com.sparta.employeedatabase.service;

import com.sparta.employeedatabase.entities.repository.DeptManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DeptManagerService {
    private final DeptManagerRepository deptManagerRepository;


    public DeptManagerService(DeptManagerRepository deptManagerRepository) {
        this.deptManagerRepository = deptManagerRepository;
    }

    public String findManagerForDeptInYear(String deptName, String yr){
        return deptManagerRepository.findManagerForDeptInYear(deptName,yr);
    }
}
