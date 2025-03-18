package com.sena.crud_2899747.DTO;

public class classroomDTO {
    
    private String name_classroom;
    private int building;
    private int capacity;
    private String type;
    private Byte has_projector;

    // Constructor con parámetros
    public classroomDTO(String name_classroom, int building, int capacity, String type, Byte has_projector) {
        this.name_classroom = name_classroom;
        this.building = building;
        this.capacity = capacity;
        this.type = type;
        this.has_projector = has_projector;
    }

    // Getters y Setters
    public String getName_classroom() {
        return name_classroom;
    }

    public void setName_classroom(String name_classroom) {
        this.name_classroom = name_classroom;
    }

    public int getBuilding() {
        return building;
    }

    public void setBuilding(int building) {
        this.building = building;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Byte getHas_projector() {
        return has_projector;
    }

    public void setHas_projector(Byte has_projector) {
        this.has_projector = has_projector;
    }
}