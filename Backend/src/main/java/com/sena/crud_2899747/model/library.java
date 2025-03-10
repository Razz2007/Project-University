package com.sena.crud_2899747.model;

import java.time.LocalTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "library")
public class library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "library_id")
    private int library_id;

    @Column (name = "name_library",length = 100, nullable = false)
    private String name_library;
    
    @Column (name = "location",length = 100, nullable = false)
    private String location;

    @Column (name = "opening_time", nullable = false)
    private  LocalTime opening_time;

    @Column (name = "Closing_time", nullable = false)
    private  LocalTime closing_time;

    @Column (name = "capacity",length = 11, nullable = false)
    private int capacity;

    public library(int library_id,String name_library,String location,LocalTime opening_time,LocalTime closing_time,int capacity){
        this.library_id = library_id;
        this.name_library = name_library;
        this.location = location;
        this.opening_time = opening_time;
        this.closing_time = closing_time;
        this.capacity = capacity;   
    }

    public int getLibrary_id() {
        return library_id;
    }

    public void setLibrary_id(int library_id) {
        this.library_id = library_id;
    }

    public String getName_library() {
        return name_library;
    }

    public void setName_library(String name_library) {
        this.name_library = name_library;
    }

    public String getLocation() {
        return location;
    }

    public void setOpening_time(LocalTime opening_time) {
        this.opening_time = opening_time;
    }

    public LocalTime getClosing_time() {
        return closing_time;
    }

    public void setClosing_time(LocalTime closing_time) {
        this.closing_time = closing_time;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
