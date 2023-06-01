package com.sparta.employeedatabase.controller;

import com.sparta.employeedatabase.entities.dto.DeptManager;
import com.sparta.employeedatabase.entities.dto.DeptManagerId;
import com.sparta.employeedatabase.entities.repository.DeptManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class DeptManagerController {
    private DeptManagerRepository deptManagerRepository;

    @Autowired
    public DeptManagerController(DeptManagerRepository deptManagerRepository) {
        this.deptManagerRepository = deptManagerRepository;
    }

    @GetMapping("/managers")
    public String getManagersByDepartmentAndYear(@RequestParam("department") String department, @RequestParam("year") String year) {
        return deptManagerRepository.findManagerForDeptInYear(department, year);
    }

    @PostMapping("/managers")
    public ResponseEntity<String> createManager(@RequestBody DeptManager manager) {
        deptManagerRepository.save(manager);
        return ResponseEntity.ok("Manager created successfully.");
    }

    @PutMapping("/managers/{id}")
    public ResponseEntity<String> updateManager(@PathVariable DeptManagerId id, @RequestBody DeptManager manager) {
        Optional<DeptManager> existingManager = deptManagerRepository.findById(id);
        if (existingManager.isPresent()) {
            manager.setId(id);
            deptManagerRepository.save(manager);
            return ResponseEntity.ok("Manager updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/managers/{id}")
    public ResponseEntity<String> deleteManager(@PathVariable("id") DeptManagerId id) {
        Optional<DeptManager> existingManager = deptManagerRepository.findById(id);
        if (existingManager.isPresent()) {
            deptManagerRepository.delete(existingManager.get());
            return ResponseEntity.ok("Manager deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

