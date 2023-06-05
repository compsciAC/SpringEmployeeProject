package com.sparta.employeedatabase.entities.repository;

import com.sparta.employeedatabase.entities.dto.Department;
import com.sparta.employeedatabase.entities.dto.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, String> {

    Optional<Department> findDepartmentByDeptName(String deptName);
    @Query(value = "SELECT * FROM employees.departments;", nativeQuery = true)
    List<Department> findAll();

    Optional<Department> findDepartmentById(String id);

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



    @Query(value = "SELECT employees.departments.dept_no, COUNT(employees.dept_emp.emp_no) " +
            "FROM employees.dept_emp " +
            "INNER JOIN employees.departments " +
            "ON employees.departments.dept_no = employees.dept_emp.dept_no " +
            "WHERE employees.dept_emp.from_date >= :fromDate " +
            "AND employees.dept_emp.to_date <= :toDate " +
            "GROUP BY employees.departments.dept_no", nativeQuery = true)
    List<Optional<Object[]>> findSizeOfAllDepartmentsInGivenYear(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

//    @Query(value = "SELECT COUNT(\n" +
//            "\temployees.dept_emp.dept_no\n" +
//            ")\n" +
//            "FROM \n" +
//            "\temployees.dept_emp \n" +
//            "INNER JOIN\n" +
//            "\temployees.departments\n" +
//            "ON\n" +
//            "\temployees.departments.dept_no=employees.dept_emp.dept_no\n" +
//            "WHERE \n" +
//            "\temployees.dept_emp.from_date >= :fromDate \n" +
//            "AND \n" +
//            "\temployees.dept_emp.to_date <= :toDate \n" +
//            "GROUP BY\n" +
//            "\temployees.departments.dept_name;", nativeQuery = true)
//    List<Integer> findSizeOfAllDepartmentsInGivenYear(@Param("fromDate") String fromDate, @Param("toDate") String toDate);

}