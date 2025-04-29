package com.sena.crud_2899747.DTO;

public class departmentDTO {
    
    private String name_department;
    private String location_department;
    private String director;
    private int budget;

    // Constructor vacío
    public departmentDTO() {

    }
    
    public departmentDTO(String name_department, String location_department, String director, int budget) {
        this.name_department = name_department;
        this.location_department = location_department;
        this.director = director;
        this.budget = budget;
    }

    // Getters y Setters
    public String getName_department() {
        return name_department;
    }

    public void setName_department(String name_department) {
        this.name_department = name_department;
    }

    public String getLocation_department() {
        return location_department;
    }

    public void setLocation_department(String location_department) {
        this.location_department = location_department;
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
}