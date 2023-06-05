package com.sparta.employeedatabase.service;

import com.sparta.employeedatabase.entities.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }

    public HashMap<String, Integer> findSizeOfAllDepartmentsInGivenYear(String fromDate, String toDate){
        List<Optional<Object[]>> results = departmentRepository.findSizeOfAllDepartmentsInGivenYear(fromDate, toDate);
        HashMap<String, Integer> departmentCounts = new HashMap<>();
        for(Optional<Object[]> result : results){
            String deptId = (String) result.get()[0];
            Integer count = ((Number) result.get()[1]).intValue();
            departmentCounts.put(deptId, count);
        }
        return departmentCounts;
    }
}
