package com.sena.crud_2899747.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "department")
public class department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private int departmentId;

    @Column(name = "name_department", length = 100, nullable = false)
    private String departmentName;

    @Column(name = "location_department", length = 100, nullable = false)
    private String locationDepartment;

    @Column(name = "director", length = 100, nullable = false)
    private String director;

    @Column(name = "budget", length = 11, nullable = false)
    private int budget;

    @Column(name = "status", nullable = false, columnDefinition = "boolean default true")
    private boolean status;

    // Constructor por defecto
    public department() {
    }

    // Constructor con argumentos
    public department(int departmentId, String departmentName, String locationDepartment, String director, int budget, boolean status) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.locationDepartment = locationDepartment;
        this.director = director;
        this.budget = budget;
        this.status = status;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getLocationDepartment() {
        return locationDepartment;
    }

    public void setLocationDepartment(String locationDepartment) {
        this.locationDepartment = locationDepartment;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
