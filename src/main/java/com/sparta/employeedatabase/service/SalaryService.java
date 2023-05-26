package com.sparta.employeedatabase.service;


import com.sparta.employeedatabase.entities.dto.Department;
import com.sparta.employeedatabase.entities.repository.DepartmentRepository;
import com.sparta.employeedatabase.entities.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SalaryService {

    private final SalaryRepository salaryRepository;


    @Autowired
    public SalaryService(SalaryRepository salaryRepository, DepartmentRepository departmentRepository){
        this.salaryRepository = salaryRepository;

    }

    public double avgSalaryByGender(String gender){
        return salaryRepository.findAvgSalaryByGender(gender);
    }

    public String findGenderPercentageDifference(){
        double avgMaleSalary = salaryRepository.findAvgSalaryByGender("M");
        double avgFemaleSalary = salaryRepository.findAvgSalaryByGender("F");

        return percentageDifferenceBetweenGenders(avgMaleSalary, avgFemaleSalary);
    }

    public String percentageDifferenceBetweenGenders(double avgMaleSalary, double avgFemaleSalary){
        if (avgMaleSalary >= avgFemaleSalary){
            double percentageDiff = (avgMaleSalary - avgFemaleSalary)/avgMaleSalary * 100;
            return "Male employees earn " +  percentageDiff + "% more then female employees";
        } else{
            double percentageDiff = (avgFemaleSalary - avgMaleSalary)/avgFemaleSalary * 100;
            return "Female employees earn " + percentageDiff + "% more then male employees";
        }
    }

    public String findGenderPercentageDifferenceByDepartment(String deptName){
        double avgMaleSalary = salaryRepository.findAvgSalaryByDepartmentByGender("M", deptName);
        double avgFemaleSalary = salaryRepository.findAvgSalaryByDepartmentByGender("F", deptName);
        return percentageDifferenceBetweenGenders(avgMaleSalary, avgFemaleSalary) + " in the department " + deptName;
    }

}
