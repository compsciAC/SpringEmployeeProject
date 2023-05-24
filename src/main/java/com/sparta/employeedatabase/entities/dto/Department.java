package com.sparta.employeedatabase.entities.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "departments")
public class Department {
    @Id
    @Size(max = 4)
    @Column(name = "dept_no", nullable = false, length = 4)
    private String id;

    @Size(max = 40)
    @NotNull
    @Column(name = "dept_name", nullable = false, length = 40)
    private String deptName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

}