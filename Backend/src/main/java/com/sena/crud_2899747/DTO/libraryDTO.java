package com.sena.crud_2899747.DTO;

import java.time.LocalTime;

public class libraryDTO {
    private int libraryId;
    private String nameLibrary;
    private String location;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private int capacity;

    public libraryDTO() {
    }

    public libraryDTO(int libraryId, String nameLibrary, String location, LocalTime openingTime, LocalTime closingTime, int capacity) {
        this.libraryId = libraryId;
        this.nameLibrary = nameLibrary;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.capacity = capacity;
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
}
