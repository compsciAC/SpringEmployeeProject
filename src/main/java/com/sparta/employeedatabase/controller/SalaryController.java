package com.sparta.employeedatabase.controller;

import com.sparta.employeedatabase.entities.dto.Salary;
import com.sparta.employeedatabase.entities.repository.SalaryRepository;
import com.sparta.employeedatabase.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/salaries")
    public List<Salary> getAllSalaries(){
        return salaryRepository.findAll();
    }

}
