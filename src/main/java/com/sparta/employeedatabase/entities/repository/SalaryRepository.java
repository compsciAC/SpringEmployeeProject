package com.sparta.employeedatabase.entities.repository;

import com.sparta.employeedatabase.entities.dto.Salary;
import com.sparta.employeedatabase.entities.dto.SalaryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, SalaryId> {

    @Query(value = "select min(s.salary), max(s.salary) \n" +
            "from Salary s, Title t\n" +
            "where s.empNo=t.empNo\n" +
            "and t.id.title=:jobTitle\n" +
            "and :yr = year(t.id.fromDate)\n" +
            "and :yr = year(t.toDate)\n" +
            "and :yr = year(s.id.fromDate)\n" +
            "and :yr = year(s.toDate)")
    List<Integer> findJobSalaryRangesInYear(String jobTitle, String yr);

    @Query(value ="select avg(s.salary) \n " +
            "from Salary s, DeptEmp de, Department d  \n " +
            "where s.empNo.id = de.empNo.id\n " +
            "and de.deptNo.id=d.id\n " +
            "and d.deptName=:deptName\n " +
            "and :dt between de.fromDate and de.toDate\n " +
            "and :dt between s.id.fromDate and s.toDate ")
    List<Integer> findDeptAvgSalaryOnDate(@Param("deptName") String deptName, @Param("dt") String dt);

    @Query("select avg(s.salary) \n"
            + "from Salary s, Employee e \n"
            + "where s.empNo.id = e.id \n"
            + "and e.gender = :gender")
    double findAvgSalaryByGender(@Param("gender") String gender);

    @Query("select avg(s.salary) \n" +
            " from Employee e, Salary s, Department d, DeptEmp de \n" +
            " where de.empNo.id = e.id \n" +
            " and de.deptNo.id = d.id \n" +
            " and s.empNo.id = e.id \n" +
            " and d.deptName = :deptName \n" +
            " and e.gender = :gender")
    public double findAvgSalaryByDepartmentByGender(@Param("gender") String gender,@Param("deptName") String deptName);

}