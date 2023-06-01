package com.sparta.employeedatabase.service;


import com.sparta.employeedatabase.entities.dto.Department;
import com.sparta.employeedatabase.entities.dto.Employee;
import com.sparta.employeedatabase.entities.dto.Salary;
import com.sparta.employeedatabase.entities.dto.SalaryId;
import com.sparta.employeedatabase.entities.repository.DepartmentRepository;
import com.sparta.employeedatabase.entities.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.round;

@Service
public class SalaryService {

    private final SalaryRepository salaryRepository;


    @Autowired
    public SalaryService(SalaryRepository salaryRepository, DepartmentRepository departmentRepository){
        this.salaryRepository = salaryRepository;

    }

    public Integer findJobSalaryMinInYear(String jobTitle, String yr){
        return salaryRepository.findJobSalaryMinInYear(jobTitle,yr);
    }

    public Integer findJobSalaryMaxInYear(String jobTitle, String yr){
        return salaryRepository.findJobSalaryMaxInYear(jobTitle,yr);
    }

    public Double findDeptAvgSalaryOnDate( String deptName, String dt){
        return salaryRepository.findDeptAvgSalaryOnDate(deptName,dt);
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
        DecimalFormat df = new DecimalFormat("0.00");
        if (avgMaleSalary >= avgFemaleSalary){
            double percentageDiff = (avgMaleSalary - avgFemaleSalary)/avgMaleSalary * 100;
            return "Male employees earn " +  df.format(percentageDiff) + "% more then female employees";
        } else{
            double percentageDiff = (avgFemaleSalary - avgMaleSalary)/avgFemaleSalary * 100;
            return "Female employees earn " + df.format(percentageDiff) + "% more then male employees";
        }
    }

    public String findGenderPercentageDifferenceByDepartment(String deptName){
        double avgMaleSalary = salaryRepository.findAvgSalaryByDepartmentByGender("M", deptName);
        double avgFemaleSalary = salaryRepository.findAvgSalaryByDepartmentByGender("F", deptName);
        return percentageDifferenceBetweenGenders(avgMaleSalary, avgFemaleSalary) + " in the department " + deptName;
    }

    public List<Employee> getEmployeeEarningAboveGivenSalary(int salary){

        return salaryRepository.findSalariesAboveCertainSalary(salary);
    }

    public Integer getEmployeeHighestSalaryByEmployeeId(Integer id){
        return salaryRepository.highestSalaryOfGivenEmployeeId(id);
    }

    public Optional<Salary> getSalaryByEmpIdAndFromDate(Integer id, LocalDate fromDate){
        SalaryId salaryId = new SalaryId();
        salaryId.setFromDate(fromDate);
        salaryId.setEmpNo(id);
        return salaryRepository.findById(salaryId);
    }

    public void saveSalary(Salary salary){
        salaryRepository.save(salary);
    }

}
