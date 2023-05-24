package com.sparta.employeedatabase.entities.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "department")
    private Set<DeptEmp> deptEmps = new LinkedHashSet<>();

    @OneToMany(mappedBy = "department")
    private Set<DeptManager> deptManagers = new LinkedHashSet<>();

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

    public Set<DeptEmp> getDeptEmps() {
        return deptEmps;
    }

    public void setDeptEmps(Set<DeptEmp> deptEmps) {
        this.deptEmps = deptEmps;
    }

    public Set<DeptManager> getDeptManagers() {
        return deptManagers;
    }

    public void setDeptManagers(Set<DeptManager> deptManagers) {
        this.deptManagers = deptManagers;
    }

}