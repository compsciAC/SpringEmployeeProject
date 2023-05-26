package com.sparta.employeedatabase;


import com.sparta.employeedatabase.entities.repository.DepartmentRepository;
import com.sparta.employeedatabase.service.DepartmentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmployeeDatabaseApplication {
    private Logger logger = LoggerFactory.getLogger(EmployeeDatabaseApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(EmployeeDatabaseApplication.class, args);
    }
        @Bean
        public CommandLineRunner runner(DepartmentService service){
            return args -> logger.info(service.getEmployeesByDepartment("d005", "1987-01-01","1989-01-01").toString());
        }
}
