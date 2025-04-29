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
    private int libraryId;

    @Column(name = "name_library", length = 100, nullable = false)
    private String nameLibrary;
    
    @Column(name = "location", length = 100, nullable = false)
    private String location;

    @Column(name = "opening_time", nullable = false)
    private LocalTime openingTime;

    @Column(name = "closing_time", nullable = false)
    private LocalTime closingTime;

    @Column(name = "capacity", nullable = false)
    private int capacity;
    
    @Column(name = "status", nullable = false, columnDefinition = "boolean default true")
    private boolean status;

    public library() {
    }

    public library(int libraryId, String nameLibrary, String location, LocalTime openingTime, LocalTime closingTime, int capacity, boolean status) {
        this.libraryId = libraryId;
        this.nameLibrary = nameLibrary;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.capacity = capacity;
        this.status = status;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }

    public String getNameLibrary() {
        return nameLibrary;
    }

    public void setNameLibrary(String nameLibrary) {
        this.nameLibrary = nameLibrary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
