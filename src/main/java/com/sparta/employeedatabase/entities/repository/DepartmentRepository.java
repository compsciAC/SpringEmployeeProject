package com.sparta.employeedatabase.entities.repository;

import com.sparta.employeedatabase.entities.dto.Department;
import com.sparta.employeedatabase.entities.dto.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, String> {

    Optional<Department> findDepartmentByDeptName(String deptName);
    @Query(value = "SELECT * FROM employees.departments;", nativeQuery = true)
    List<Department> findAll();

    @Query("SELECT e FROM Employee e \n" +
            "INNER JOIN DeptEmp de \n" +
            "ON de.empNo.id = e.id \n" +
            "INNER JOIN Department d \n " +
            "ON de.deptNo.id = d.id \n" +
            "WHERE d.id = :departmentId \n" +
            "AND de.fromDate >= :startDate " +
            "AND de.toDate <= :endDate")
    List<Optional<Employee>> findEmployeesByDepartmentInAGivenYear(@Param("departmentId")String departmentId,
                                               @Param("startDate")LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT COUNT(\n" +
            "\temployees.dept_emp.dept_no\n" +
            ")\n" +
            "FROM \n" +
            "\temployees.dept_emp \n" +
            "INNER JOIN\n" +
            "\temployees.departments\n" +
            "ON\n" +
            "\temployees.departments.dept_no=employees.dept_emp.dept_no\n" +
            "WHERE \n" +
            "\temployees.dept_emp.from_date >= \"1987-01-01\"\n" +
            "AND \n" +
            "\temployees.dept_emp.to_date <= \"1987-02-28\"\n" +
            "GROUP BY\n" +
            "\temployees.departments.dept_name;", nativeQuery = true)
    List<Integer> findSizeOfAllDepartmentsInGivenYear(@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

}