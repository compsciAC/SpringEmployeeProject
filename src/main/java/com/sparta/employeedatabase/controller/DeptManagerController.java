package com.sparta.employeedatabase.controller;

import com.sparta.employeedatabase.entities.dto.DeptManager;
import com.sparta.employeedatabase.entities.dto.DeptManagerId;
import com.sparta.employeedatabase.entities.repository.DeptManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
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
//
//    @GetMapping
//    public ResponseEntity<List<DeptManager>> getAllManagers() {
//        List<DeptManager> managers = deptManagerRepository.findAll();
//        return ResponseEntity.ok(managers);
//    }

    @GetMapping("managers/{deptNo}/{empNo}")
    public ResponseEntity<DeptManager> getManagerById(@PathVariable("deptNo") String deptNo, @PathVariable("empNo") int empNo) {
        DeptManagerId id = new DeptManagerId();
        id.setDeptNo(deptNo);
        id.setEmpNo(empNo);
        Optional<DeptManager> manager = deptManagerRepository.findById(id);
        return manager.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/managers")
    public ResponseEntity<String> createManager(@RequestBody DeptManager manager) {
        deptManagerRepository.save(manager);
        return ResponseEntity.ok("Manager created successfully.");
    }

//    @PutMapping("/managers/{id}")
//    public ResponseEntity<String> updateManager(@PathVariable DeptManagerId id, @RequestBody DeptManager manager) {
//        Optional<DeptManager> existingManager = deptManagerRepository.findById(id);
//        if (existingManager.isPresent()) {
//            manager.setId(id);
//            deptManagerRepository.save(manager);
//            return ResponseEntity.ok("Manager updated successfully.");
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @PutMapping("/managers/{deptNo}/{empNo}")
    public ResponseEntity<String> updateManager(@PathVariable("deptNo") String deptNo, @PathVariable("empNo") int empNo, @RequestBody DeptManager manager) {
        DeptManagerId id = new DeptManagerId();
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

