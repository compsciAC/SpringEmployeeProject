package com.sparta.employeedatabase.controller;

import com.sparta.employeedatabase.entities.dto.Employee;
import com.sparta.employeedatabase.entities.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employee/{lastName}")
    public ResponseEntity<String> getEmployeeByLastName(@PathVariable String lastName){

        List<Employee> employeeList = employeeRepository.findByLastName(lastName);
        ArrayList<String> employeeString = new ArrayList<>();
        for(Employee employee: employeeList){
            employeeString.add("Name: "+ employee.toString() + "\n");
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("content-type", "application/json");
        if(employeeList.size()>0){
            return new ResponseEntity<>("{\"employees\" : " + employeeString + "}", httpHeaders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{\"employees\" : \"Employee with that last name does not exist\"}", httpHeaders, HttpStatus.OK);
        }
        //return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @GetMapping("/employee/id/{id}")
    public ResponseEntity<String> getEmployeeById(@PathVariable Integer id){
        Optional<Employee> employeeToFind = employeeRepository.findById(id);
        HttpHeaders httpHeaders = new HttpHeaders();
        if(employeeToFind.isPresent())
                return new ResponseEntity<>("{\"employees\" : " + employeeToFind.get().toString() + "}", httpHeaders, HttpStatus.OK);
        else
            return new ResponseEntity<>("{\"employees\" : \"Employee with that id name does not exist\"}", httpHeaders, HttpStatus.OK);
    }




    @PostMapping("/employee")
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        employeeRepository.save(employee);
        return ResponseEntity.ok("Employee created successfully." + employee.getId());
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            employee.setId(id);
            employeeRepository.save(employee);
            return ResponseEntity.ok("Employee updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer id) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            employeeRepository.delete(existingEmployee.get());
            return ResponseEntity.ok("Employee deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
