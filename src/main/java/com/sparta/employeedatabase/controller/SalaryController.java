package com.sparta.employeedatabase.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.employeedatabase.entities.dto.Employee;
import com.sparta.employeedatabase.entities.dto.Salary;
import com.sparta.employeedatabase.entities.repository.EmployeeRepository;
import com.sparta.employeedatabase.entities.repository.SalaryRepository;
import com.sparta.employeedatabase.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class SalaryController {

    private SalaryService salaryService;
    private EmployeeRepository employeeRepository;
    private final SalaryRepository salaryRepository;

    private ObjectMapper objectMapper;

    @Autowired
    SalaryController(SalaryService salaryService, EmployeeRepository employeeRepository, SalaryRepository salaryRepository, ObjectMapper objectMapper){
        this.salaryService = salaryService;
        this.employeeRepository = employeeRepository;
        this.salaryRepository = salaryRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping("salary/avgDeptSalOnDate/{deptName}/{dt}")
    public ResponseEntity<String>getAvgDeptSalOnDate(@PathVariable String deptName,@PathVariable String dt){
        return getStringResponseEntity(salaryService.findDeptAvgSalaryOnDate(deptName,dt).toString());
    }

    @GetMapping("salary/JobSalaryRangesInYear/{jobTitle}/{yr}")
    public ResponseEntity<String>getJobSalaryRangesInYear(@PathVariable String jobTitle,@PathVariable String yr){
        return getStringResponseEntity("Min:"+salaryService.findJobSalaryMinInYear(jobTitle,yr).toString()+" - Max:"+salaryService.findJobSalaryMaxInYear(jobTitle,yr).toString());
    }

    @GetMapping("salary/paygap")
    public ResponseEntity<String>getGenderPayGap(){
        return getStringResponseEntity(salaryService.findGenderPercentageDifference());
    }

    @GetMapping("salary/avgSalary/{gender}")
    public ResponseEntity<String>getAverageSalaryByGender(@PathVariable String gender){
        String averageSalaryByGender = salaryRepository.findAvgSalaryByGender(gender) + "";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content-type", "application/json");
        ResponseEntity<String> response = null;
        if(averageSalaryByGender!= null){
            response = new ResponseEntity<>("{\" message\": \"" + averageSalaryByGender + "\" }", httpHeaders, HttpStatus.OK );
        }
        return response;
    }

    public ResponseEntity<String> getStringResponseEntity(String payGapInfo){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content-type", "application/json");
        ResponseEntity<String> response = null;
        if(payGapInfo!=null){
            response = new ResponseEntity<>("{\"message\": \""+payGapInfo+"\" }",
                    httpHeaders, HttpStatus.OK);
        }
        return response;
    }

    @GetMapping(value = "salary/empsalary/{id}")
    public ResponseEntity<String> getSalaryByEmpId(@PathVariable Integer id){
        Optional<Employee>  employee = employeeRepository.findById(id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content-type", "application/json");
        if(employee.isPresent()){
            ResponseEntity<String> employeePresentResponse = null;
            employeePresentResponse = new ResponseEntity<>(
                    "{"+ salaryService.getEmployeeHighestSalaryAndNameByEmployeeId(id) + "}",
                    httpHeaders,
                    HttpStatus.OK);
            return employeePresentResponse;
        } else {
            return new ResponseEntity<>(
                    "{\"message\" : \"That employee doesn't exist\"}",
                    httpHeaders,
                    HttpStatus.NOT_FOUND
            );
        }
    }


    @GetMapping(value = "salary/empabovsalary/{salary}")//this will return a list of employees
    public ResponseEntity<String> getEmployeesEarningAboveSalary(@PathVariable Integer salary){
        List<Employee> employees = salaryService.getEmployeeEarningAboveGivenSalary(salary);
        ArrayList<String> employeesAndSalaries = new ArrayList<>();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content-type", "application/json");
        for (Employee employee: employees) {
            employeesAndSalaries.add( employee.getId() +
                    " " + employee.getFirstName() +
                    " " + employee.getLastName() +
                    " : " +
                    salaryService.getEmployeeHighestSalaryByEmployeeId(employee.getId()));
        }
        if(employees.size()>0){
            return new ResponseEntity<>(
                    "{" + salary + ":" + employeesAndSalaries + "}",
                    httpHeaders,
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                "{\"message\" : \"no Employees exist\"}",
                httpHeaders,
                HttpStatus.NOT_FOUND
        );
    }


    @PutMapping(value = "/salary/{empId}/{fromDate}/{toDate}")
    ResponseEntity<String> salaryToUpdate(@PathVariable int empId,@PathVariable String fromDate,@PathVariable String toDate ,@RequestParam int newSalary){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content-type", "application/json");
        try {
            Date toDateAsDate = new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
            Date fromDateAsDate = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
            Optional<Salary> salaryToUpdate = salaryService.getSalaryByEmpIdAndFromDate(empId, fromDateAsDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

            if (salaryToUpdate.isPresent()){
                salaryToUpdate.get().setSalary(newSalary);
                salaryService.saveSalary(salaryToUpdate.get());
                ResponseEntity<String> salaryUpdatedResponse = new ResponseEntity<>(
                        objectMapper.writeValueAsString(salaryToUpdate.get().getSalary()),
                        httpHeaders,
                        HttpStatus.OK
                );
                return salaryUpdatedResponse;
            }else {
                ResponseEntity<String> noSalaryToUpdate = new ResponseEntity<>(
                        "{\"message\" : \"That salary doesn't exist to update\"}",
                        httpHeaders,
                        HttpStatus.NOT_FOUND);
                return noSalaryToUpdate;
            }
        } catch (ParseException | JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
