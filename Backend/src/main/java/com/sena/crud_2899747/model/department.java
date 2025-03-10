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
    private int department_id;

    @Column(name = "name_department",length = 100, nullable = false)
    private String name_department;

    @Column(name = "location_department",length = 100, nullable = false)
    private String location_department;

    @Column(name = "director",length = 100, nullable = false)
    private String director;

    @Column(name = "budget",length = 11, nullable = false)
    private int budget;

    public department(int department_id,String name_department,String location_department,String director, int budget){
        this.department_id = department_id;
        this.name_department = name_department;
        this.location_department = location_department;
        this.director = director;
        this.budget = budget;
    } 
    public int getDepartment_id(){
        return department_id;
    }
    public void setDepartment_id( int department_id){
        this.department_id = department_id;
    }
    public String getName_department(){
        return name_department;
    }
    public void setName_department(String name_department){
        this.name_department = name_department;
    }
    public String getLocation_department(){
        return location_department;
    }
    public void setLocation_department(String location_department){
        this.location_department =location_department;
    }
    public String getDirector(){
        return director;
    }
    public void setDirector(String director){
        this.director = director;
    }
    public int getBudget(){
        return budget;
    }
    public void setBudget(int budget){
        this.budget = budget;
    }
}
