package com.sparta.employeedatabase.service;

import com.sparta.employeedatabase.entities.dto.Employee;
import com.sparta.employeedatabase.entities.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService {

    private final DepartmentRepository repository;

    @Autowired
    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    public String getEmployeesByDepartment(String departmentId, String startDate, String endDate) {
        String result = "No employees found";
        for(Optional<Employee> employee: repository.findByDepartment(departmentId, startDate, endDate)){
            if (employee.isPresent()) {
                result += employee.get().toString() + "\n";
            }

        }
        return result;
    }
}
