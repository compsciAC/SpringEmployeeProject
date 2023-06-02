package com.sparta.employeedatabase.entities.repository;

import com.sparta.employeedatabase.entities.dto.Employee;
import com.sparta.employeedatabase.entities.dto.Salary;
import com.sparta.employeedatabase.entities.dto.SalaryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, SalaryId> {

    //Don't use below method until you have return type which can take 2 values
    @Query(value = "select min(s.salary), max(s.salary) \n" +
            "from Salary s, Title t\n" +
            "where s.empNo=t.empNo\n" +
            "and t.id.title=:jobTitle\n" +
            "and :yr = year(t.id.fromDate)\n" +
            "and :yr = year(t.toDate)\n" +
            "and :yr = year(s.id.fromDate)\n" +
            "and :yr = year(s.toDate)")
    Integer findJobSalaryRangesInYear(String jobTitle, String yr);

    @Query(value = "select min(s.salary) \n" +
            "from Salary s, Title t\n" +
            "where s.empNo=t.empNo\n" +
            "and t.id.title=:jobTitle\n" +
            "and :yr = year(t.id.fromDate)\n" +
            "and :yr = year(t.toDate)\n" +
            "and :yr = year(s.id.fromDate)\n" +
            "and :yr = year(s.toDate)")
    Integer findJobSalaryMinInYear(String jobTitle, String yr);

    @Query(value = "select max(s.salary) \n" +
            "from Salary s, Title t\n" +
            "where s.empNo=t.empNo\n" +
            "and t.id.title=:jobTitle\n" +
            "and :yr = year(t.id.fromDate)\n" +
            "and :yr = year(t.toDate)\n" +
            "and :yr = year(s.id.fromDate)\n" +
            "and :yr = year(s.toDate)")
    Integer findJobSalaryMaxInYear(String jobTitle, String yr);

    @Query(value ="select avg(s.salary) \n " +
            "from Salary s, DeptEmp de, Department d  \n " +
            "where s.empNo.id = de.empNo.id\n " +
            "and de.deptNo.id=d.id\n " +
            "and d.deptName=:deptName\n " +

            "and date(:dt) between de.fromDate and de.toDate\n " +
            "and date(:dt) between s.id.fromDate and s.toDate ")
    Double findDeptAvgSalaryOnDate( String deptName, String dt);

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

    @Query(value = "Select e from Employee e, Salary s "+
            " where e.id = s.id.empNo and s.salary >= :salary ")
    List<Employee> findSalariesAboveCertainSalary(@Param("salary")Integer salary);


    @Query(value = "Select max(s.salary) from Employee e, Salary s where s.id.empNo = e.id and e.id = :empId ")
    Optional<Integer> highestSalaryOfGivenEmployeeId(@Param("empId") Integer empId);

}