package com.sparta.employeedatabase.entities.repository;

import com.sparta.employeedatabase.entities.dto.Salary;
import com.sparta.employeedatabase.entities.dto.SalaryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, SalaryId> {

    @Query(value ="select avg(s.salary) \n" +
            "from Salary s, DeptEmp de, Department d\n" +
            "where s.empNo = de.empNo\n" +
            "and de.deptNo=d.id\n" +
            "and d.deptName=:deptName\n" +
            "and :dt between de.fromDate and de.toDate\n" +
            "and :dt between s.id.fromDate and s.toDate")
        List<Integer> findDeptAvgSalaryInYear(String deptName, String dt);
}