package com.sparta.employeedatabase.entities.repository;

import com.sparta.employeedatabase.entities.dto.DeptManager;
import com.sparta.employeedatabase.entities.dto.DeptManagerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeptManagerRepository extends JpaRepository<DeptManager, DeptManagerId> {

    @Query(value = "select concat(e.firstName, ' ', e.lastName)\n" +
        "from DeptManager dm, Department d, Employee e\n" +
        "where dm.id.deptNo=d.id\n" +
        "and e.id=dm.id.empNo\n" +
        "and :yr >= year(dm.fromDate)\n" +
        "and :yr <= year(dm.toDate)\n" +
        "and d.deptName=:deptName")

    String findManagerForDeptInYear(String deptName, String yr);


}