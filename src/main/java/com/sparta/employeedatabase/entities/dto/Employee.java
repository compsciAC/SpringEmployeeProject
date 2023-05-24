package com.sparta.employeedatabase.entities.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "emp_no", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Size(max = 14)
    @NotNull
    @Column(name = "first_name", nullable = false, length = 14)
    private String firstName;

    @Size(max = 16)
    @NotNull
    @Column(name = "last_name", nullable = false, length = 16)
    private String lastName;

    @Size(max = 1)
    @NotNull
    @Column(name = "gender", nullable = false, length = 1)
    private String gender;

    @NotNull
    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

}