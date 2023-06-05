package com.sparta.employeedatabase.controller;

import com.sparta.employeedatabase.entities.dto.Employee;
import com.sparta.employeedatabase.entities.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmployeeWebController {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeWebController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employees")
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "employees";
    }

    @GetMapping("/employee/{id}")
    public String getEmployee(Model model, @PathVariable Integer id) {
        model.addAttribute("employee",
                employeeRepository.findById(id).orElse(null));
        return "employee";
    }
//
    @GetMapping("/employee/edit/{id}")
    public String getEmployeeToEdit(@PathVariable Integer id, Model model) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        model.addAttribute("employeeToEdit", employee);
        return "employee-edit-form";
    }

    @PostMapping("/updateEmployee")
    public String updateEmployee(@ModelAttribute("employeeToEdit")Employee editedEmployee) {
        employeeRepository.saveAndFlush(editedEmployee);
        return "edit-success";
    }
}
