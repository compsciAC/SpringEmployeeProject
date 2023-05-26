package com.sparta.employeedatabase.entities.repository;

import com.sparta.employeedatabase.entities.dto.Department;
import com.sparta.employeedatabase.entities.dto.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, String> {
    @Query(value = "SELECT * FROM employees.departments;", nativeQuery = true)
    List<Department> findAll();

    @Query(value = "SELECT \n" +
            "\temployees.employees.* \n" +
            "FROM \n" +
            "\temployees.employees \n" +
            "INNER JOIN \n" +
            "\temployees.dept_emp \n" +
            "ON\n" +
            "\temployees.employees.emp_no=employees.dept_emp.emp_no\n" +
            "INNER JOIN \n" +
            "\temployees.departments\n" +
            "ON\n" +
            "\temployees.departments.dept_no=employees.dept_emp.dept_no\n" +
            "WHERE \n" +
            "\temployees.dept_emp.dept_no = \"d005\"\n" +
            "AND \n" +
            "\temployees.dept_emp.from_date >= \"1987-09-01\"\n" +
            "AND \n" +
            "\temployees.dept_emp.to_date <= \"1987-12-12\";", nativeQuery = true)
    List<Optional<Employee>> findByDepartment(String departmentId, String startDate, String endDate);

    @Query(value = "SELECT dept_name, COUNT(\n" +
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
    Optional<Department> countDepartmentsByDeptName();

}