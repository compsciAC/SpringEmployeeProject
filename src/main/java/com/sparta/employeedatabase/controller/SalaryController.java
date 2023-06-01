package com.sparta.employeedatabase.controller;

import com.sparta.employeedatabase.entities.dto.Salary;
import com.sparta.employeedatabase.entities.repository.SalaryRepository;
import com.sparta.employeedatabase.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class SalaryController {

    private SalaryService salaryService;
    private final SalaryRepository salaryRepository;

    @Autowired SalaryController(SalaryService salaryService,
                                SalaryRepository salaryRepository){
        this.salaryService = salaryService;
        this.salaryRepository = salaryRepository;
    }

    @GetMapping("salary/paygap")
    public ResponseEntity<String>getGenderPayGap(){
        return getStringResponseEntity(salaryService.percentageDifferenceBetweenGenders(salaryService.avgSalaryByGender("M"), salaryService.avgSalaryByGender("F")));
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

}
