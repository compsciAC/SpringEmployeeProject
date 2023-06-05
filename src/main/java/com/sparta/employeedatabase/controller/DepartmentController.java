package com.sparta.employeedatabase.controller;

import com.sparta.employeedatabase.entities.dto.Department;
import com.sparta.employeedatabase.entities.dto.Employee;
import com.sparta.employeedatabase.entities.repository.DepartmentRepository;
import com.sparta.employeedatabase.entities.repository.EmployeeRepository;
import com.sparta.employeedatabase.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
public class DepartmentController {

    private DepartmentRepository departmentRepository;

    private DepartmentService departmentService;



    @Autowired
    public DepartmentController(DepartmentRepository departmentRepository, DepartmentService departmentService){
        this.departmentRepository = departmentRepository;
        this.departmentService = departmentService;
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<String>findDepartmentById(@PathVariable String id){
        Optional<Department> departmentToFind = departmentRepository.findById(id);
        HttpHeaders httpHeaders = new HttpHeaders();
        if(departmentToFind.isPresent())
            return new ResponseEntity<>("{\"Departments\" : " + departmentToFind.get().toString() + "}", httpHeaders, HttpStatus.OK);
        else
            return new ResponseEntity<>("{\"Departments\" : \"Department with that id name does not exist\"}", httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/department/totalCounts")
    public ResponseEntity<String>findTotalCountForDepartmentsByYear(@RequestParam String fromDate ,@RequestParam String toDate){
        HashMap<String, Integer> departmentAndCounts = departmentService.findSizeOfAllDepartmentsInGivenYear(fromDate, toDate);
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>("{\"Departments\" : " + departmentAndCounts.toString() + "}", httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/department")
    public ResponseEntity<String> department(@RequestBody Department department) {
        departmentRepository.save(department);
        return ResponseEntity.ok("Department was created successfully." + department.getId());
    }

    @PutMapping("/department/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable String id, @RequestBody Department department) {
        Optional<Department> existingDepartment = departmentRepository.findDepartmentById(id);
        if (existingDepartment.isPresent()) {
            department.setId(id);
            departmentRepository.save(department);
            return ResponseEntity.ok("Department updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/department/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String id) {
        Optional<Department> existingDepartment = departmentRepository.findById(id);
        if (existingDepartment.isPresent()) {
            departmentRepository.delete(existingDepartment.get());
            return ResponseEntity.ok("Department deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
