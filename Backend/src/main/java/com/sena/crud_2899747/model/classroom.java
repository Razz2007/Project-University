package com.sena.crud_2899747.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity (name = "classroom")
public class classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classroom_id")
    private int classroom_id;

    @Column(name = "name_classroom",length = 50, nullable = false)
    private String nameClassroom;

    @Column(name = "building",length = 50, nullable = false)
    private  int building;

    @Column(name = "capacity",length = 11, nullable = false)
    private  int capacity;

    @Column(name = "type",length = 50, nullable = false)
    private  String type;

    @Column(name = "has_projector",length = 1, nullable = false)
    private  Byte has_projector;

    @Column(name="status",nullable =false, columnDefinition = "boolean default true ")
    private boolean status;
    
    public classroom() {
    }
    public classroom(int classroom_id, String name_classroom, int building, int capacity, String type, Byte has_projector, boolean status) {
        this.classroom_id = classroom_id;
        this.nameClassroom = name_classroom;
        this.building = building;
        this.capacity = capacity;
        this.type = type;
        this.has_projector = has_projector;
    }

    public int getClassroom_id() {
        return classroom_id;
    }

    public void setClassroom_id(int classroom_id) {
        this.classroom_id = classroom_id;
    }

    public String getName_classroom() {
        return nameClassroom;
    }

    public void setName_classroom(String name_classroom) {
        this.nameClassroom = name_classroom;
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
    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
