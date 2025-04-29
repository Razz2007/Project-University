package com.sena.crud_2899747.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "course")
public class course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private int courseId;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "credits", nullable = false)
    private int credits;

    @Column(name = "level", length = 50, nullable = false)
    private String level;

    @Column(name = "prerequisites", length = 200, nullable = true)
    private String prerequisites;

    @Column(name="status",nullable =false, columnDefinition = "boolean default true ")
    private boolean status;

    // Constructor vacío (sin parámetros)
    public course() {
    }
    // Constructor con parámetros
    public course(int courseId, String name, int credits, String level, String prerequisites, boolean status) {
        this.courseId = courseId;
        this.name = name;
        this.credits = credits;
        this.level = level;
        this.prerequisites = prerequisites;
        this.status = status;
    }

 


    // Getters y Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
}

